package kr.co.lion.team4.mrco.fragment

import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isGone
import androidx.core.view.isVisible
import androidx.core.view.size
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import kr.co.lion.team4.mrco.MainActivity
import kr.co.lion.team4.mrco.MainFragmentName
import kr.co.lion.team4.mrco.R
import kr.co.lion.team4.mrco.Tools
import kr.co.lion.team4.mrco.dao.CoordinatorDao
import kr.co.lion.team4.mrco.databinding.FragmentJoinCoordinatorBinding
import kr.co.lion.team4.mrco.viewmodel.JoinCoordinatorViewModel

class JoinCoordinatorFragment : Fragment() {

    lateinit var fragmentJoinCoordinatorBinding: FragmentJoinCoordinatorBinding
    lateinit var mainActivity: MainActivity
    lateinit var joinCoordinatorViewModel: JoinCoordinatorViewModel

    var coordiNameChk: Boolean = false

    // 활동명 중복 확인 검사를 했는지..
    // true면 활동명 중복 확인 검사를 완료한 것으로 취급한다.
    var checkCoordinatorNameDuplicate = false

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        //fragmentJoinCoordinatorBinding = FragmentJoinCoordinatorBinding.inflate(inflater)
        fragmentJoinCoordinatorBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_join_coordinator, container, false)
        joinCoordinatorViewModel = JoinCoordinatorViewModel()
        fragmentJoinCoordinatorBinding.joinCoordinatorViewModel = joinCoordinatorViewModel
        fragmentJoinCoordinatorBinding.lifecycleOwner = this

        mainActivity = activity as MainActivity

        // 툴바 및 하단 바 세팅
        settingToolbar()

        initInput()

        settingButtonCheckName()

        showMbtiBottomSheet()

        settingButtonNext()


//        settingButtonJoinCoordinatorNext()
//        settingJoinCoordinatorTextField()
//        settingButtonJoinCoordinatorCheckName()
//        settingMBTIEditText()

        return fragmentJoinCoordinatorBinding.root
    }

    // 툴바 설정
    fun settingToolbar() {
        fragmentJoinCoordinatorBinding.apply {
            toolbarJoinCoordinator.apply {
                // 네비게이션
                setNavigationIcon(R.drawable.arrow_back_24px)
                setNavigationOnClickListener {
                    backProcess()
                }
            }
        }
    }

    fun initInput() {
        joinCoordinatorViewModel.textFieldJoinCoordinatorName.value = ""
        joinCoordinatorViewModel.textFieldJoinCoordinatorIntro.value = ""
        joinCoordinatorViewModel.textFieldJoinCoordinatorCertificationNumber.value = ""
        joinCoordinatorViewModel.textFieldJoinCoordinatorBizLicenseNumber.value = ""
        joinCoordinatorViewModel.textFieldJoinCoordinatorMBTI.value = ""
        joinCoordinatorViewModel.textFieldJoinCoordinatorContactNumber.value = ""
        joinCoordinatorViewModel.checkBoxJoinCoordinatorConsent.value = false
    }

    fun settingButtonCheckName() {
        fragmentJoinCoordinatorBinding.apply {
            buttonJoinCoordinatorCheckName.setOnClickListener {
                val coordiName = joinCoordinatorViewModel!!.textFieldJoinCoordinatorName.value

                if (10 < coordiName!!.length) {
                    // 활동명은 10자 이내(공백포함)로 생성 가능합니다. 문구 출력
                } else {
                    CoroutineScope(Dispatchers.Main).launch {
                        coordiNameChk =
                            CoordinatorDao.checkCoordiName(joinCoordinatorViewModel?.textFieldJoinCoordinatorName?.value!!)
                        if (coordiNameChk == false) {
                            // 중복된 활동명이 있다는 문구 출력
                        } else {
                            // 사용 가능한 활동명이라는 문구 출력과 함께 editable과 clickable을 false로 설정 -> 중복확인 클릭 후 수정 방지
                        }
                    }
                }
            }
        }
    }


    fun settingButtonCoordinatorImage() {
        fragmentJoinCoordinatorBinding.buttonCoordinatorImage.setOnClickListener {
            // 코디네이터 메인 사진 첨부
        }
    }

    fun settingButtonCoordinatorCertification() {
        fragmentJoinCoordinatorBinding.buttonJoinCoordinatorCertificationSubmit.setOnClickListener {
            // 스타일리스트 자격증 사진 첨부
        }
    }

    fun settingButtonCoordinatorPortfolio() {
        fragmentJoinCoordinatorBinding.buttonJoinCoordinatorPortfolioSubmit.setOnClickListener {
            // 포트폴리오 사진 첨부
        }
    }

    fun settingButtonCoordinatorBizLicenseSubmit() {
        fragmentJoinCoordinatorBinding.buttonJoinCoordinatorBizLicenseSubmit.setOnClickListener {
            // 사업자 등록 증명서 사진 첨부
        }
    }

    fun showMbtiBottomSheet() {
        fragmentJoinCoordinatorBinding.apply {
            textFieldJoinCoordinatorMBTI.setOnClickListener {
                val bottomSheet = MbtiBottomSheetFragment()
                bottomSheet.show(mainActivity.supportFragmentManager, "MbtiBottomSheet")
            }
        }
    }

    fun settingButtonNext() {
        fragmentJoinCoordinatorBinding.apply {
            buttonJoinCoordinatorNext.setOnClickListener {
                if (checkInput()) {
                    // JoinCoordinatorNextFragment를 보여준다.
                    val joinCoordinatorBundle = Bundle()

                    joinCoordinatorBundle.putString(
                        "coordiName",
                        textFieldJoinCoordinatorName.text.toString()
                    )
                    joinCoordinatorBundle.putString(
                        "coordIntro",
                        textFieldJoinCoordinatorIntro.text.toString()
                    )

                    //코디 메인 사진은 파이어스토어에 파일 업로드 후 접근할 수 있는 파일명만 넘겨준다.
                    joinCoordinatorBundle.putString(
                        "coordiMainImage",
                        textFieldJoinCoordinatorIntro.text.toString()
                    )

                    //스타일리스트 자격증 파일은 파이어스토어에 파일 업로드 후 접근할 수 있는 파일명만 넘겨준다.
                    joinCoordinatorBundle.putString(
                        "coordiCertification",
                        textFieldJoinCoordinatorIntro.text.toString()
                    )
                    joinCoordinatorBundle.putString(
                        "coordiCertificationNumber",
                        textFieldJoinCoordinatorCertificationNumber.text.toString()
                    )

                    //포트폴리오 파일은 파이어스토어에 파일 업로드 후 접근할 수 있는 파일명만 넘겨준다.
                    joinCoordinatorBundle.putString(
                        "coordiPortfolio",
                        textFieldJoinCoordinatorIntro.text.toString()
                    )

                    //사업자 등록 증명 파일은 파이어스토어에 파일 업로드 후 접근할 수 있는 파일명만 넘겨준다.
                    joinCoordinatorBundle.putString(
                        "coordiBizLicense",
                        textFieldJoinCoordinatorIntro.text.toString()
                    )
                    joinCoordinatorBundle.putString(
                        "coordiBizLicenseNumber",
                        textFieldJoinCoordinatorBizLicenseNumber.text.toString()
                    )

                    joinCoordinatorBundle.putString(
                        "coordiMBTI",
                        textFieldJoinCoordinatorMBTI.text.toString()
                    )

                    joinCoordinatorBundle.putString(
                        "coordiContactNumber",
                        textFieldJoinCoordinatorContactNumber.text.toString()
                    )


                    mainActivity.replaceFragment(
                        MainFragmentName.JOIN_COORDINATOR_NEXT_FRAGMENT,
                        true,
                        true,
                        joinCoordinatorBundle
                    )
                }
            }
        }
    }


    // 뒤로가기 처리
    fun backProcess() {
        mainActivity.removeFragment(MainFragmentName.JOIN_COORDINATOR_FRAGMENT)

//        // 입력요소 초기설정
//        fun settingJoinCoordinatorTextField() {
//            // 입력 요소들을 초기화 한다.
//            //활동명
//            joinCoordinatorViewModel.textFieldJoinCoordinatorName.value = ""
//            //소개글
//            joinCoordinatorViewModel.textFieldJoinCoordinatorIntroText.value = ""
//            //자격증번호
//            joinCoordinatorViewModel.textFieldJoinCoordinatorStylelistCertiNumber.value = ""
//            //사업자번호
//            joinCoordinatorViewModel.textFieldJoinCoordinatorBizLicenseNumber.value = ""
//            //MBTI
//            joinCoordinatorViewModel.textFieldJoinCoordinatorMBTI.value = ""
//            //고객노출전화번호
//            joinCoordinatorViewModel.textFieldJoinCoordinatorCustomerRelationPhone.value = ""
//            //약관동의
//            joinCoordinatorViewModel.checkBoxJoinCoordinatorConsent.value = true
//
//            // 첫 번째 입력 요소에 포커스를 준다.
//            Tools.showSoftInput(
//                mainActivity,
//                fragmentJoinCoordinatorBinding.textFieldJoinCoordinatorName
//            )
//        }

        fun settingMBTIEditText() {
            fragmentJoinCoordinatorBinding.textFieldJoinCoordinatorMBTI.setOnClickListener {
                showMbtiBottomSheet()
            }
        }

        // BottomSheet를 띄워준다.
        fun showMbtiBottomSheet() {
            val mbtiBottomSheetFragment = MbtiBottomSheetFragment()
            mbtiBottomSheetFragment.show(mainActivity.supportFragmentManager, "MbtiBottomSheet")
        }

//        fun settingButtonJoinCoordinatorNext() {
//
//            fragmentJoinCoordinatorBinding.buttonJoinCoordinatorNext.setOnClickListener {
//                // 입력을 검사한다.
//                val validation = checkJoinCoordinatorTextInput()
//                // 입력이 모두 잘 되어 있다면..
//                if (validation == true) {
//                    // JoinCoordinatorNextFragment를 보여준다.
//                    mainActivity.replaceFragment(
//                        MainFragmentName.JOIN_COORDINATOR_NEXT_FRAGMENT,
//                        true,
//                        true,
//                        null
//                    )
//
//                }
//            }
//        }

//        // 입력요소 유효성 검사 메서드
//        fun checkJoinCoordinatorTextInput(): Boolean {
//
//            // 사용자가 입력한 내용을 가져온다
//            //활동명
//            val coordinatorName = joinCoordinatorViewModel.textFieldJoinCoordinatorName.value!!
//            //소개글
//            val coordinatorIntroText =
//                joinCoordinatorViewModel.textFieldJoinCoordinatorIntroText.value!!
//            //자격증번호
//            val coordinatorStylelistCertiNumber =
//                joinCoordinatorViewModel.textFieldJoinCoordinatorStylelistCertiNumber.value!!
//            //사업자번호
//            val coordinatorBizLicenseNumber =
//                joinCoordinatorViewModel.textFieldJoinCoordinatorBizLicenseNumber.value!!
//            //MBTI
//            val coordinatorMBTI = joinCoordinatorViewModel.textFieldJoinCoordinatorMBTI.value!!
//            //고객노출전화번호
//            val coordinatorCustomerRelationPhone =
//                joinCoordinatorViewModel.textFieldJoinCoordinatorCustomerRelationPhone.value!!
//
//            // 활동명을 입력하지 않았다면
//            if (coordinatorName.isEmpty()) {
//                Tools.showErrorDialog(
//                    mainActivity,
//                    fragmentJoinCoordinatorBinding.textFieldJoinCoordinatorName,
//                    "활동명 입력 오류",
//                    "활동명을 입력해주세요"
//                )
//                return false
//            }
//
//            // 소개글을 입력하지 않았다면
//            if (coordinatorIntroText.isEmpty()) {
//                Tools.showErrorDialog(
//                    mainActivity,
//                    fragmentJoinCoordinatorBinding.textFieldJoinCoordinatorIntroText,
//                    "소개글 입력 오류",
//                    "소개글을 입력해주세요"
//                )
//                return false
//            }
//
//            // 자격증번호를 입력하지 않았다면
//            if (coordinatorStylelistCertiNumber.isEmpty()) {
//                Tools.showErrorDialog(
//                    mainActivity,
//                    fragmentJoinCoordinatorBinding.textFieldJoinCoordinatorStylelistCertiNumber,
//                    "자격증번호 입력 오류",
//                    "자격증번호를 입력해주세요"
//                )
//                return false
//            }
//
//            // 사업자번호를 입력하지 않았다면
//            if (coordinatorBizLicenseNumber.isEmpty()) {
//                Tools.showErrorDialog(
//                    mainActivity,
//                    fragmentJoinCoordinatorBinding.textFieldJoinCoordinatorBizLicenseNumber,
//                    "사업자번호 입력 오류",
//                    "사업자번호를 입력해주세요"
//                )
//                return false
//            }
//
//            // MBTI를 입력하지 않았다면
////        if(coordinatorMBTI.isEmpty()){
////            Tools.showErrorDialog(mainActivity, fragmentJoinCoordinatorBinding.textFieldJoinCoordinatorMBTI, "MBTI 입력 오류",
////                "MBTI를 입력해주세요")
////            return false
////        }
//
//            // 고객노출전화번호를 입력하지 않았다면
//            if (coordinatorCustomerRelationPhone.isEmpty()) {
//                Tools.showErrorDialog(
//                    mainActivity,
//                    fragmentJoinCoordinatorBinding.textFieldJoinCoordinatorCustomerRelationPhone,
//                    "고객노출전화번호 입력 오류",
//                    "고객노출전화번호를 입력해주세요"
//                )
//                return false
//            }
//
//
//            // 아이디 중복확인을 하지 않았다면..
//            if (checkCoordinatorNameDuplicate == false) {
//                Tools.showErrorDialog(
//                    mainActivity,
//                    fragmentJoinCoordinatorBinding.textFieldJoinCoordinatorName,
//                    "활동명 중복 확인 오류",
//                    "활동명 중복확인을 해주세요"
//                )
//                return false
//            }
//
//            return true
//        }

//        // 활동명 중복확인 버튼
//        fun settingButtonJoinCoordinatorCheckName() {
//            fragmentJoinCoordinatorBinding.buttonJoinCoordinatorCheckName.setOnClickListener {
//                checkCoordinatorNameDuplicate = true
//            }
//        }
    }

    fun checkInput(): Boolean {
        // 입력을 체크할 항목 구성
        val coordiName = joinCoordinatorViewModel.textFieldJoinCoordinatorName.value!!
        // coordiNameChk
        val coordiIntro = joinCoordinatorViewModel.textFieldJoinCoordinatorIntro.value!!
        val coordiPhoto = fragmentJoinCoordinatorBinding.imageJoinCoordinatorPhoto
        val coordiCertifiPhoto =
            fragmentJoinCoordinatorBinding.imageJoinCoordinatorCertification
        val coordiCertifiNum =
            joinCoordinatorViewModel.textFieldJoinCoordinatorCertificationNumber.value!!
        val coordiPortfolio =
            fragmentJoinCoordinatorBinding.recyclerViewJoinCoordinatorPortfolio.size
        val coordiBizLicensePhoto =
            fragmentJoinCoordinatorBinding.imageJoinCoordinatorBizLicense
        val coordiBizLicenseNum =
            joinCoordinatorViewModel.textFieldJoinCoordinatorBizLicenseNumber.value!!
        val coordiMBTI = joinCoordinatorViewModel.textFieldJoinCoordinatorMBTI.value!!
        val coordiContactNum =
            joinCoordinatorViewModel.textFieldJoinCoordinatorContactNumber.value!!
        val checkBoxConsent = joinCoordinatorViewModel.checkBoxJoinCoordinatorConsent.value!!


        if (coordiName.isEmpty()) {
            Tools.showErrorDialog(
                mainActivity, fragmentJoinCoordinatorBinding.textFieldJoinCoordinatorName,
                "활동명 입력 오류", "활동명을 입력해주세요"
            )
            return false
        }

        if (coordiNameChk == false) {
            Tools.showErrorDialog(
                mainActivity, fragmentJoinCoordinatorBinding.textFieldJoinCoordinatorName,
                "활동명 입력 오류", "활동명 중복체크를 해주세요"
            )
            return false
        }

        if (coordiIntro.isEmpty() || coordiIntro.length > 50) {
            Tools.showErrorDialog(
                mainActivity, fragmentJoinCoordinatorBinding.textFieldJoinCoordinatorIntro,
                "소개글 입력 오류", "소개글을 입력해주세요(공백 포함 50자 이내)"
            )
            return false
        }

        if (coordiCertifiPhoto.isGone && coordiPortfolio == 0) {
            Tools.showErrorDialog(
                mainActivity,
                fragmentJoinCoordinatorBinding.textFieldJoinCoordinatorCertificationNumber,
                "자격 증명 오류",
                "자격증(사진 포함) 또는 포트폴리오 중 하나는 필수로 제출 되어야 합니다."
            )
            return false
        }

        if (coordiCertifiPhoto.isVisible && coordiCertifiNum.isEmpty()) {
            Tools.showErrorDialog(
                mainActivity,
                fragmentJoinCoordinatorBinding.textFieldJoinCoordinatorCertificationNumber,
                "자격 증명 오류",
                "자격증 사진과 번호 모두 제출 되어야 합니다."
            )
            return false
        }

        if (coordiBizLicensePhoto.isGone || coordiBizLicenseNum.isEmpty()) {
            Tools.showErrorDialog(
                mainActivity,
                fragmentJoinCoordinatorBinding.textFieldJoinCoordinatorBizLicenseNumber,
                "사업자 등록 입력 오류",
                "사업자 등록증 사진과 등록번호 모두 제출 되어야 합니다."
            )
            return false
        }

        if (coordiMBTI.isEmpty()) {
            Tools.showErrorDialog(
                mainActivity, fragmentJoinCoordinatorBinding.textFieldJoinCoordinatorMBTI,
                "MBTI 입력 오류", "MBTI를 선택해주세요."
            )
            return false
        }

        if (checkBoxConsent == false) {
            Tools.showErrorDialog(
                mainActivity, fragmentJoinCoordinatorBinding.checkBoxJoinCoordinatorConsent,
                "약관 동의", "약관 미동의시 등록 신청을 진행할 수 없습니다."
            )
            return false
        }
        return true
    }
}