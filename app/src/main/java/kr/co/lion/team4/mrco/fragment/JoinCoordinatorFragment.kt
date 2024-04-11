package kr.co.lion.team4.mrco.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kr.co.lion.team4.mrco.MainActivity
import kr.co.lion.team4.mrco.MainFragmentName
import kr.co.lion.team4.mrco.R
import kr.co.lion.team4.mrco.databinding.FragmentJoinCoordinatorBinding


class JoinCoordinatorFragment : Fragment() {

    lateinit var fragmentJoinCoordinatorBinding: FragmentJoinCoordinatorBinding
    lateinit var mainActivity: MainActivity

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        fragmentJoinCoordinatorBinding = FragmentJoinCoordinatorBinding.inflate(inflater)
        mainActivity = activity as MainActivity

        // 툴바 및 하단 바 세팅
        mainActivity.removeBottomSheet()
        settingToolbar()

        settingButtonJoinCoordinatorNext()

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

    fun settingButtonJoinCoordinatorNext() {
        fragmentJoinCoordinatorBinding.buttonJoinCoordinatorNext.setOnClickListener {
            // JoinCoordinatorNextFragment를 보여준다.
            mainActivity.replaceFragment(MainFragmentName.JOIN_COORDINATOR_NEXT_FRAGMENT, true, true, null)

        }
    }

    // 뒤로가기 처리
    fun backProcesss(){
        mainActivity.removeFragment(MainFragmentName.JOIN_COORDINATOR_FRAGMENT)
    }

}