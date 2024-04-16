package kr.co.lion.team4.mrco.fragment

import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kr.co.lion.team4.mrco.MainActivity
import kr.co.lion.team4.mrco.MainFragmentName
import kr.co.lion.team4.mrco.R
import kr.co.lion.team4.mrco.Tools
import kr.co.lion.team4.mrco.UserState
import kr.co.lion.team4.mrco.dao.UserDao
import kr.co.lion.team4.mrco.databinding.FragmentJoinBinding
import kr.co.lion.team4.mrco.model.UserModel
import kr.co.lion.team4.mrco.viewmodel.JoinViewModel


class JoinFragment : Fragment() {

    lateinit var fragmentJoinBinding: FragmentJoinBinding
    lateinit var mainActivity: MainActivity

    lateinit var joinViewModel: JoinViewModel

    // 아이디 중복 확인 검사를 했는지..
    // true면 아이디 중복 확인 검사를 완료한 것으로 취급한다.
    var checkUserIdNotDuplicate = false


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment

        //fragmentJoinBinding = FragmentJoinBinding.inflate(inflater)
        // 바인딩 객체를 생성한다. ViewBinding의 기능을 포함한다
        // 첫 번째 : LayoutInflater
        // 두 번째 : 화면을 만들 때 사용할 layout폴더의 xml 파일
        // 세 번째 : xml 을 통해서 만들어진 화면을 누가 관리하게 할 것인가. 여기서는 Fragment를 의미한다.
        // 네 번째 : Fragment 상태에 영향을 받을 것인가...
        fragmentJoinBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_join, container, false)
        // ViewModel 객체를 생성한다.
        joinViewModel = JoinViewModel()
        // 생성한 ViewModel 객체를 layout 파일에 설정해준다.
        fragmentJoinBinding.joinViewModel = joinViewModel
        // ViewModel의 생명 주기를 Fragment와 일치시킨다. Fragment가 살아 있을 때 ViewModel 객체도 살아 있겠금 해준다.
        fragmentJoinBinding.lifecycleOwner = this

        mainActivity = activity as MainActivity

        // 툴바, 하단 바 세팅
        settingToolbar()

        settingButtonJoinSubmit()
        settingTextField()
        settingButtonJoinCheckId()
        settingMBTIEditText()

        return fragmentJoinBinding.root
    }

    // 툴바 설정
    fun settingToolbar() {
        fragmentJoinBinding.apply {
            toolbarJoin.apply {
                // 네비게이션
                setNavigationIcon(R.drawable.arrow_back_24px)
                setNavigationOnClickListener {
                    backProcess()
                }
            }
        }
    }

//    fun settingButtonJoinSubmit(){
//        fragmentJoinBinding.buttonJoinSubmit.setOnClickListener {
//            mainActivity.removeFragment(MainFragmentName.JOIN_FRAGMENT)
//            val joinSnackbar = Snackbar.make(it, "회원가입이 완료되었습니다", Snackbar.LENGTH_SHORT)
//            joinSnackbar.setTextColor(Color.WHITE)
//            joinSnackbar.setBackgroundTint(Color.parseColor("#757575"))
//            joinSnackbar.animationMode = Snackbar.ANIMATION_MODE_FADE
//            joinSnackbar.show()
//        }
//    }

    // 입력요소 초기설정
    fun settingTextField(){
        // 입력 요소들을 초기화 한다.
        joinViewModel.textFieldJoinUserId.value = ""
        joinViewModel.textFieldJoinUserPw.value = ""
        joinViewModel.textFieldJoinUserPw2.value = ""

        //부가정보
        //이름
        joinViewModel.textFieldJoinUserName.value = ""
        //이메일
        joinViewModel.textFieldJoinUserEmail.value = ""
        //MBTI
        joinViewModel.textFieldJoinUserMBTI.value = ""
        //성별
        joinViewModel.checkBoxJoinMale.value = true
        joinViewModel.checkBoxJoinFemale.value = false
        //동의 선택01
        joinViewModel.checkBoxJoinUserConsent01.value = true
        //동의 선택02
        joinViewModel.checkBoxJoinUserConsent02.value = true

        // 첫 번째 입력 요소에 포커스를 준다.
        Tools.showSoftInput(mainActivity, fragmentJoinBinding.textFieldJoinUserId)
    }

    fun settingMBTIEditText(){
        fragmentJoinBinding.textFieldJoinUserMBTI.setOnClickListener {
            showMbtiBottomSheet()
        }
    }

    // BottomSheet를 띄워준다.
    fun showMbtiBottomSheet(){
        val mbtiBottomSheetFragment = MbtiBottomSheetFragment()
        mbtiBottomSheetFragment.show(mainActivity.supportFragmentManager, "MbtiBottomSheet")
    }

    fun settingButtonJoinSubmit(){
        fragmentJoinBinding.buttonJoinSubmit.setOnClickListener {
            // 입력을 검사한다.
            val validation = checkJoinTextInput()
            // 입력이 모두 잘 되어 있다면..
            if(validation == true) {
                // 키보드를 내려준다.
                Tools.hideSoftInput(mainActivity)

                // 사용자 정보를 저장한다.
                saveUserData()

//                val materialAlertDialogBuilder = MaterialAlertDialogBuilder(mainActivity)
//                materialAlertDialogBuilder.setTitle("가입완료")
//                materialAlertDialogBuilder.setMessage("가입이 완료되었습니다\n로그인해주세요")
//
//                materialAlertDialogBuilder.setPositiveButton("확인") { dialogInterface: DialogInterface, i: Int ->
//                    mainActivity.removeFragment(MainFragmentName.JOIN_FRAGMENT)
//                }
//
//                materialAlertDialogBuilder.show()
            }

        }
    }


    // 입력요소 유효성 검사 메서드
    fun checkJoinTextInput():Boolean{

        // 사용자가 입력한 내용을 가져온다
        val userId = joinViewModel.textFieldJoinUserId.value!!
        val userPw = joinViewModel.textFieldJoinUserPw.value!!
        val userPw2 = joinViewModel.textFieldJoinUserPw2.value!!

        //이름
        val userName = joinViewModel.textFieldJoinUserName.value!!
        //이메일
        val userEmail = joinViewModel.textFieldJoinUserEmail.value!!
        //MBTI
        val userMBTI = joinViewModel.textFieldJoinUserMBTI.value!!

        // 아이디를 입력하지 않았다면
        if(userId.isEmpty()){
            Tools.showErrorDialog(mainActivity, fragmentJoinBinding.textFieldJoinUserId, "아이디 입력 오류",
                "아이디를 입력해주세요")
            return false
        }

        // 비밀번호를 입력하지 않았다면
        if(userPw.isEmpty()){
            Tools.showErrorDialog(mainActivity, fragmentJoinBinding.textFieldJoinUserPw, "비밀번호 입력 오류",
                "비밀번호를 입력해주세요")
            return false
        }

        // 비밀번호 확인을 입력하지 않았다면
        if(userPw2.isEmpty()){
            Tools.showErrorDialog(mainActivity, fragmentJoinBinding.textFieldJoinUserPw2, "비밀번호 입력 오류",
                "비밀번호를 입력해주세요")
            return false
        }

        // 입력한 비밀번호가 서로 다르다면
        if(userPw != userPw2){
            joinViewModel.textFieldJoinUserPw.value = ""
            joinViewModel.textFieldJoinUserPw2.value = ""
            Tools.showErrorDialog(mainActivity, fragmentJoinBinding.textFieldJoinUserPw, "비밀번호 입력 오류",
                "비밀번호가 다릅니다")
            return false
        }

        // 아이디 중복확인을 하지 않았다면..
        if(checkUserIdNotDuplicate == false){
            Tools.showErrorDialog(mainActivity, fragmentJoinBinding.textFieldJoinUserId, "아이디 중복 확인 오류",
                "아이디 중복확인을 해주세요")
            return false
        }

        // 이름을 입력하지 않았다면
        if(userName.isEmpty()){
            Tools.showErrorDialog(mainActivity, fragmentJoinBinding.textFieldJoinUserName, "이름 입력 오류",
                "이름을 입력해주세요")
            return false
        }

        // 이메일을 입력하지 않았다면
        if(userEmail.isEmpty()){
            Tools.showErrorDialog(mainActivity, fragmentJoinBinding.textFieldJoinUserEmail, "이메일 입력 오류",
                "이메일을 입력해주세요")
            return false
        }

        // MBTI를 입력하지 않았다면
        if(userMBTI.isEmpty()){
            Tools.showErrorDialog(mainActivity, fragmentJoinBinding.textFieldJoinUserMBTI, "MBTI 입력 오류",
                "MBTI를 입력해주세요")
            return false
        }

        return true
    }

    // 중복확인 버튼
    fun settingButtonJoinCheckId(){
        fragmentJoinBinding.buttonJoinCheckId.setOnClickListener {
            CoroutineScope(Dispatchers.Main).launch {
                checkUserIdNotDuplicate = UserDao.checkUserIdExist(joinViewModel?.textFieldJoinUserId?.value!!)
                // Log.d("test1234", "$checkUserIdExist")
                if(checkUserIdNotDuplicate == false) {
                    joinViewModel?.textFieldJoinUserId?.value = ""
                    Tools.showErrorDialog(mainActivity, fragmentJoinBinding.textFieldJoinUserId,
                        "아이디 입력 오류", "존재하는 아이디입니다\n다른 아이디를 입력해주세요")
                } else {
                    val materialAlertDialogBuilder = MaterialAlertDialogBuilder(mainActivity)
                    materialAlertDialogBuilder.setMessage("사용 가능한 아이디 입니다")
                    materialAlertDialogBuilder.setPositiveButton("확인", null)
                    materialAlertDialogBuilder.show()
                }
            }

            //checkUserIdNotDuplicate = true
        }
    }

    // 데이터를 저장하고 이동한다.
    fun saveUserData(){
        CoroutineScope(Dispatchers.Main).launch {
            // 사용자 번호 시퀀스 값을 가져온다.
            val userSequence = UserDao.getUserSequence()
            // Log.d("test1234", "UserSequence : $userSequence")
            // 시퀀스값을 1 증가시켜 덮어씌워준다.
            UserDao.updateUserSequence(userSequence + 1)

            // 저장할 데이터를 가져온다.
            val userIdx = userSequence + 1
            val userId = joinViewModel.textFieldJoinUserId.value!!
            val userPw = joinViewModel.textFieldJoinUserPw.value!!
            val userName = joinViewModel.textFieldJoinUserName.value!!

            val userGender = joinViewModel.gettingGender().num
            val userEmail = joinViewModel.textFieldJoinUserEmail.value!!
            val userMBTI = joinViewModel.textFieldJoinUserMBTI.value!!
            val userConsent01 = joinViewModel.checkBoxJoinUserConsent01.value!!
            val userConsent02 = joinViewModel.checkBoxJoinUserConsent02.value!!

            val userState = UserState.USER_STATE_NORMAL.num

            // 저장할 데이터를 객체에 담는다.
            val userModel = UserModel(userIdx, userId, userPw, userName, userGender,
                userEmail, userMBTI, userConsent01, userConsent02, userState)

            // 사용자 정보를 저장한다.
            UserDao.insertUserData(userModel)

            val materialAlertDialogBuilder = MaterialAlertDialogBuilder(mainActivity)
            materialAlertDialogBuilder.setTitle("가입완료")
            materialAlertDialogBuilder.setMessage("가입이 완료되었습니다\n로그인해주세요")
            materialAlertDialogBuilder.setPositiveButton("확인") { dialogInterface: DialogInterface, i: Int ->
                mainActivity.removeFragment(MainFragmentName.JOIN_FRAGMENT)
            }
            materialAlertDialogBuilder.show()
        }
    }

    // 뒤로가기 처리
    fun backProcess(){
        mainActivity.removeFragment(MainFragmentName.JOIN_FRAGMENT)
    }
}