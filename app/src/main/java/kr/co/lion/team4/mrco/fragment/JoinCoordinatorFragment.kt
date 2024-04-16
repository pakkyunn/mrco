package kr.co.lion.team4.mrco.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import kr.co.lion.team4.mrco.MainActivity
import kr.co.lion.team4.mrco.MainFragmentName
import kr.co.lion.team4.mrco.R
import kr.co.lion.team4.mrco.Tools
import kr.co.lion.team4.mrco.databinding.FragmentJoinCoordinatorBinding
import kr.co.lion.team4.mrco.viewmodel.JoinCoordinatorViewModel

class JoinCoordinatorFragment : Fragment() {

    lateinit var fragmentJoinCoordinatorBinding: FragmentJoinCoordinatorBinding
    lateinit var mainActivity: MainActivity

    lateinit var joinCoordinatorViewModel: JoinCoordinatorViewModel

    // 활동명 중복 확인 검사를 했는지..
    // true면 활동명 중복 확인 검사를 완료한 것으로 취급한다.
    var checkCoordinatorNameDuplicate = false

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        //fragmentJoinCoordinatorBinding = FragmentJoinCoordinatorBinding.inflate(inflater)
        fragmentJoinCoordinatorBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_join_coordinator, container, false)
        // ViewModel 객체를 생성한다.
        joinCoordinatorViewModel = JoinCoordinatorViewModel()
        // 생성한 ViewModel 객체를 layout 파일에 설정해준다.
        fragmentJoinCoordinatorBinding.joinCoordinatorViewModel = joinCoordinatorViewModel
        // ViewModel의 생명 주기를 Fragment와 일치시킨다. Fragment가 살아 있을 때 ViewModel 객체도 살아 있겠금 해준다.
        fragmentJoinCoordinatorBinding.lifecycleOwner = this

        mainActivity = activity as MainActivity

        // 툴바 및 하단 바 세팅
        settingToolbar()

        settingButtonJoinCoordinatorNext()
        settingJoinCoordinatorTextField()
        settingButtonJoinCoordinatorCheckName()
        settingMBTIEditText()

        return fragmentJoinCoordinatorBinding.root
    }

    // 툴바 설정
    fun settingToolbar() {
        fragmentJoinCoordinatorBinding.apply {
            toolbarJoinCoordinator.apply {
                // 네비게이션
                setNavigationIcon(R.drawable.arrow_back_24px)
                setNavigationOnClickListener {
                    backProcesss()
                }
            }
        }
    }

    // 뒤로가기 처리
    fun backProcesss(){
        mainActivity.removeFragment(MainFragmentName.JOIN_COORDINATOR_FRAGMENT)
    }

    // 입력요소 초기설정
    fun settingJoinCoordinatorTextField(){
        // 입력 요소들을 초기화 한다.
        //활동명
        joinCoordinatorViewModel.textFieldJoinCoordinatorName.value = ""
        //소개글
        joinCoordinatorViewModel.textFieldJoinCoordinatorIntroText.value = ""
        //자격증번호
        joinCoordinatorViewModel.textFieldJoinCoordinatorStylelistCertiNumber.value = ""
        //사업자번호
        joinCoordinatorViewModel.textFieldJoinCoordinatorBizLicenseNumber.value = ""
        //MBTI
        joinCoordinatorViewModel.textFieldJoinCoordinatorMBTI.value = ""
        //고객노출전화번호
        joinCoordinatorViewModel.textFieldJoinCoordinatorCustomerRelationPhone.value = ""
        //약관동의
        joinCoordinatorViewModel.checkBoxJoinCoordinatorConsent.value = true

        // 첫 번째 입력 요소에 포커스를 준다.
        Tools.showSoftInput(mainActivity, fragmentJoinCoordinatorBinding.textFieldJoinCoordinatorName)
    }

    fun settingMBTIEditText(){
        fragmentJoinCoordinatorBinding.textFieldJoinCoordinatorMBTI.setOnClickListener {
            showMbtiBottomSheet()
        }
    }

    // BottomSheet를 띄워준다.
    fun showMbtiBottomSheet(){
        val mbtiBottomSheetFragment = MbtiBottomSheetFragment()
        mbtiBottomSheetFragment.show(mainActivity.supportFragmentManager, "MbtiBottomSheet")
    }

    fun settingButtonJoinCoordinatorNext() {

        fragmentJoinCoordinatorBinding.buttonJoinCoordinatorNext.setOnClickListener {
            // 입력을 검사한다.
            val validation = checkJoinCoordinatorTextInput()
            // 입력이 모두 잘 되어 있다면..
            if(validation == true) {
                // JoinCoordinatorNextFragment를 보여준다.
                mainActivity.replaceFragment(MainFragmentName.JOIN_COORDINATOR_NEXT_FRAGMENT, true, true, null)

            }
        }
    }

    // 입력요소 유효성 검사 메서드
    fun checkJoinCoordinatorTextInput():Boolean{

        // 사용자가 입력한 내용을 가져온다
        //활동명
        val coordinatorName = joinCoordinatorViewModel.textFieldJoinCoordinatorName.value!!
        //소개글
        val coordinatorIntroText = joinCoordinatorViewModel.textFieldJoinCoordinatorIntroText.value!!
        //자격증번호
        val coordinatorStylelistCertiNumber = joinCoordinatorViewModel.textFieldJoinCoordinatorStylelistCertiNumber.value!!
        //사업자번호
        val coordinatorBizLicenseNumber = joinCoordinatorViewModel.textFieldJoinCoordinatorBizLicenseNumber.value!!
        //MBTI
        val coordinatorMBTI = joinCoordinatorViewModel.textFieldJoinCoordinatorMBTI.value!!
        //고객노출전화번호
        val coordinatorCustomerRelationPhone = joinCoordinatorViewModel.textFieldJoinCoordinatorCustomerRelationPhone.value!!

        // 활동명을 입력하지 않았다면
        if(coordinatorName.isEmpty()){
            Tools.showErrorDialog(mainActivity, fragmentJoinCoordinatorBinding.textFieldJoinCoordinatorName, "활동명 입력 오류",
                "활동명을 입력해주세요")
            return false
        }

        // 소개글을 입력하지 않았다면
        if(coordinatorIntroText.isEmpty()){
            Tools.showErrorDialog(mainActivity, fragmentJoinCoordinatorBinding.textFieldJoinCoordinatorIntroText, "소개글 입력 오류",
                "소개글을 입력해주세요")
            return false
        }

        // 자격증번호를 입력하지 않았다면
        if(coordinatorStylelistCertiNumber.isEmpty()){
            Tools.showErrorDialog(mainActivity, fragmentJoinCoordinatorBinding.textFieldJoinCoordinatorStylelistCertiNumber, "자격증번호 입력 오류",
                "자격증번호를 입력해주세요")
            return false
        }

        // 사업자번호를 입력하지 않았다면
        if(coordinatorBizLicenseNumber.isEmpty()){
            Tools.showErrorDialog(mainActivity, fragmentJoinCoordinatorBinding.textFieldJoinCoordinatorBizLicenseNumber, "사업자번호 입력 오류",
                "사업자번호를 입력해주세요")
            return false
        }

        // MBTI를 입력하지 않았다면
//        if(coordinatorMBTI.isEmpty()){
//            Tools.showErrorDialog(mainActivity, fragmentJoinCoordinatorBinding.textFieldJoinCoordinatorMBTI, "MBTI 입력 오류",
//                "MBTI를 입력해주세요")
//            return false
//        }

        // 고객노출전화번호를 입력하지 않았다면
        if(coordinatorCustomerRelationPhone.isEmpty()){
            Tools.showErrorDialog(mainActivity, fragmentJoinCoordinatorBinding.textFieldJoinCoordinatorCustomerRelationPhone, "고객노출전화번호 입력 오류",
                "고객노출전화번호를 입력해주세요")
            return false
        }


        // 아이디 중복확인을 하지 않았다면..
        if(checkCoordinatorNameDuplicate == false){
            Tools.showErrorDialog(mainActivity, fragmentJoinCoordinatorBinding.textFieldJoinCoordinatorName, "활동명 중복 확인 오류",
                "활동명 중복확인을 해주세요")
            return false
        }

        return true
    }

    // 활동명 중복확인 버튼
    fun settingButtonJoinCoordinatorCheckName(){
        fragmentJoinCoordinatorBinding.buttonJoinCoordinatorCheckName.setOnClickListener {
            checkCoordinatorNameDuplicate = true
        }
    }

}