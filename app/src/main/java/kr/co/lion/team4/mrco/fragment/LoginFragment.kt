package kr.co.lion.team4.mrco.fragment

import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kr.co.lion.team4.mrco.MainActivity
import kr.co.lion.team4.mrco.MainFragmentName
import kr.co.lion.team4.mrco.R
import kr.co.lion.team4.mrco.Tools
import kr.co.lion.team4.mrco.UserState
import kr.co.lion.team4.mrco.dao.UserDao
import kr.co.lion.team4.mrco.databinding.FragmentLoginBinding
import kr.co.lion.team4.mrco.viewmodel.LoginViewModel


class LoginFragment : Fragment() {

    lateinit var fragmentLoginBinding: FragmentLoginBinding
    lateinit var mainActivity: MainActivity

    lateinit var loginViewModel: LoginViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        //fragmentLoginBinding = FragmentLoginBinding.inflate(inflater)
        fragmentLoginBinding = DataBindingUtil.inflate(layoutInflater, R.layout.fragment_login, container, false)
        loginViewModel = LoginViewModel()
        fragmentLoginBinding.loginViewModel = loginViewModel
        fragmentLoginBinding.lifecycleOwner = this


        mainActivity = activity as MainActivity

        settingLoginInput()

        settingButtonLoginJoin()
        settingButtonLoginSubmit()

        return fragmentLoginBinding.root
    }

    fun settingButtonLoginJoin() {
        fragmentLoginBinding.buttonLoginJoin.setOnClickListener {
            mainActivity.replaceFragment(MainFragmentName.JOIN_FRAGMENT, true, true, null)
        }

        //테스트 용 회원정보 수정 화면으로 이동
//        fragmentLoginBinding.buttonLoginJoin.setOnClickListener {
//            mainActivity.replaceFragment(MainFragmentName.MODIFY_USER_FRAGMENT, true, true, null )

//        //테스트 용 코디네이터 등록 신청 화면으로 이동
//        fragmentLoginBinding.buttonLoginJoin.setOnClickListener {
//            mainActivity.replaceFragment(MainFragmentName.JOIN_COORDINATOR_FRAGMENT, true, true, null )

        //테스트 용 코디네이터 정보 수정 화면으로 이동
//        fragmentLoginBinding.buttonLoginJoin.setOnClickListener {
//            mainActivity.replaceFragment(MainFragmentName.MODIFY_COORDINATOR_FRAGMENT, true, true, null )


    }

    fun settingButtonLoginSubmit() {
//        //테스트 용 회원정보 수정 화면으로 이동
//        fragmentLoginBinding.buttonLoginSubmit.setOnClickListener {
//            mainActivity.replaceFragment(MainFragmentName.HOME_RECOMMEND, false, false, null )
//        }
        fragmentLoginBinding.buttonLoginSubmit.setOnClickListener {

            // 유효성 검사
            // 임시 주석 및 임시 이동
            /*
            val validation = checkLoginInput()

            if (validation) {
                loginProcess()
            }
            */
            mainActivity.replaceFragment(MainFragmentName.HOME_MAIN_FULL, false, false, null )
        }
    }

    // 유효성 검사
    fun checkLoginInput():Boolean{

        // 입력한 값들을 가져온다.
        val userId = loginViewModel.textFieldLoginUserId.value!!
        val userPw = loginViewModel.textFieldLoginUserPw.value!!

        if(userId.isEmpty()){
            Tools.showErrorDialog(mainActivity, fragmentLoginBinding.textFieldLoginUserId, "아이디 입력 오류",
                "아이디를 입력해주세요")
            return false
        }
        if(userPw.isEmpty()){
            Tools.showErrorDialog(mainActivity, fragmentLoginBinding.textFieldLoginUserPw, "비밀번호 입력 오류",
                "비밀번호를 입력해주세요")
            return false
        }

        return true
    }

    // 입력 요소들 초기화
    fun settingLoginInput(){
        loginViewModel.textFieldLoginUserId.value = ""
        loginViewModel.textFieldLoginUserPw.value = ""
    }

    // 로그인 처리
    fun loginProcess(){
        // 사용자가 입력한 정보를 가져온다.
        val userId = loginViewModel.textFieldLoginUserId.value!!
        val userPw = loginViewModel.textFieldLoginUserPw.value!!
        val job1 = CoroutineScope(Dispatchers.Main).launch {
            val loginUserModel = UserDao.getUserDataById(userId)
            // 만약 null 이라면..
            if(loginUserModel == null){
                Tools.showErrorDialog(mainActivity, fragmentLoginBinding.textFieldLoginUserId, "로그인 오류",
                    "존재하지 않는 아이디 입니다")
            }
            // 만약 정보를 가져온 것이 있다면
            else {
                // 입력한 비밀번호와 서버에서 받아온 사용자의 비밀호가 다르다면..
                if(userPw != loginUserModel.userPw){
                    Tools.showErrorDialog(mainActivity, fragmentLoginBinding.textFieldLoginUserPw, "로그인 오류",
                        "비밀번호가 잘못되었습니다")
                }
                // 비밀번호가 일치한다면
                else {

                    // 회원이 탈퇴 상태라면
                    if(loginUserModel.userState == UserState.USER_STATE_SIGNOUT.num){
                        MaterialAlertDialogBuilder(mainActivity).apply {
                            setTitle("로그인 오류")
                            setMessage("탈퇴한 회원입니다")
                            setPositiveButton("확인"){ dialogInterface: DialogInterface, i: Int ->
                                loginViewModel.textFieldLoginUserId.value = ""
                                loginViewModel.textFieldLoginUserPw.value = ""
                                Tools.showSoftInput(mainActivity, fragmentLoginBinding.textFieldLoginUserId)
                            }
                            show()
                        }
                    } else {
                        // 자동 로그인이 체크되어 있다면...
                        if (loginViewModel.checkBoxLoginAutoLogin.value == true) {
                            // Preferences 에 사용자 정보를 저장해둔다.
                            val sharedPreferences =
                                mainActivity.getSharedPreferences("AutoLogin", Context.MODE_PRIVATE)
                            val editor = sharedPreferences.edit()
                            editor.putInt("loginUserIdx", loginUserModel.userIdx)
                            editor.putString("loginUserName", loginUserModel.userName)
                            editor.apply()
                        }
                    }

                    Snackbar.make(fragmentLoginBinding.root, "로그인에 성공하였습니다", Snackbar.LENGTH_SHORT).show()
                    // 홈화면 추천화면 fragment로 이동
                    mainActivity.replaceFragment(MainFragmentName.HOME_MAIN_FULL, true, true, null)
                }
            }

            mainActivity.replaceFragment(MainFragmentName.HOME_MAIN_FULL, false, false, null )

        }
    }


}