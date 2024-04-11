package kr.co.lion.team4.mrco.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import kr.co.lion.team4.mrco.databinding.FragmentLoginBinding
import kr.co.lion.team4.mrco.MainActivity
import kr.co.lion.team4.mrco.MainFragmentName


class LoginFragment : Fragment() {

    lateinit var fragmentLoginBinding: FragmentLoginBinding
    lateinit var mainActivity: MainActivity

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment

        fragmentLoginBinding = FragmentLoginBinding.inflate(inflater)
        mainActivity = activity as MainActivity

        settingButtonLoginJoin()
        settingButtonLoginSubmit()

        return fragmentLoginBinding.root
    }

    fun settingButtonLoginJoin() {
        fragmentLoginBinding.buttonLoginJoin.setOnClickListener {
            mainActivity.replaceFragment(MainFragmentName.JOIN_FRAGMENT, true, true, null )

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
    }

    fun settingButtonLoginSubmit() {

        //테스트 용 회원정보 수정 화면으로 이동
        fragmentLoginBinding.buttonLoginSubmit.setOnClickListener {
            mainActivity.replaceFragment(MainFragmentName.MODIFY_USER_FRAGMENT, true, true, null )

        }
    }


}