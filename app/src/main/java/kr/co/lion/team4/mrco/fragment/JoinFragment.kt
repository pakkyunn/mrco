package kr.co.lion.team4.mrco.fragment

import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.snackbar.Snackbar
import kr.co.lion.team4.mrco.MainActivity
import kr.co.lion.team4.mrco.R
import kr.co.lion.team4.mrco.MainFragmentName
import kr.co.lion.team4.mrco.databinding.FragmentJoinBinding


class JoinFragment : Fragment() {

    lateinit var fragmentJoinBinding: FragmentJoinBinding
    lateinit var mainActivity: MainActivity


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment

        fragmentJoinBinding = FragmentJoinBinding.inflate(inflater)
        mainActivity = activity as MainActivity

        // 툴바, 하단 바 세팅
        settingToolbar()

        settingButtonJoinSubmit()

        return fragmentJoinBinding.root
    }

    // 툴바 설정
    fun settingToolbar() {
        fragmentJoinBinding.apply {
            toolbarJoin.apply {
                // 네비게이션
                setNavigationIcon(R.drawable.arrow_back_24px)
                setNavigationOnClickListener {
                    backProcesss()
                }
            }
        }
    }

    fun settingButtonJoinSubmit(){
        fragmentJoinBinding.buttonJoinSubmit.setOnClickListener {
            mainActivity.removeFragment(MainFragmentName.JOIN_FRAGMENT)
            val joinSnackbar = Snackbar.make(it, "회원가입이 완료되었습니다", Snackbar.LENGTH_SHORT)
            joinSnackbar.setTextColor(Color.WHITE)
            joinSnackbar.setBackgroundTint(Color.parseColor("#757575"))
            joinSnackbar.animationMode = Snackbar.ANIMATION_MODE_FADE
            joinSnackbar.show()
        }
    }

    // 뒤로가기 처리
    fun backProcesss(){
        mainActivity.removeFragment(MainFragmentName.JOIN_FRAGMENT)
    }
}