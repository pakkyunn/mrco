package kr.co.lion.team4.mrco

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kr.co.lion.team4.mrco.databinding.FragmentCoordinatorInfoBinding
import kr.co.lion.team4.mrco.databinding.FragmentCoordinatorMainBinding


class CoordinatorMainFragment : Fragment() {

    lateinit var fragmentCoordinatorMainBinding: FragmentCoordinatorMainBinding
    lateinit var mainActivity: MainActivity

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment

        fragmentCoordinatorMainBinding = FragmentCoordinatorMainBinding.inflate(inflater)
        mainActivity = activity as MainActivity

        mainActivity.removeTabsBar()

        toolbarSetting()
        settingTabs()

        return fragmentCoordinatorMainBinding.root
    }

    // 툴바 설정
    fun toolbarSetting(){
        mainActivity.activityMainBinding.apply {
            toolbarMain.apply {
                title = "(스타일리스트 명)"
                // 네비게이션
                setNavigationIcon(R.drawable.arrow_back_24px)
                setNavigationOnClickListener {
                    backProcesss()
                }
            }
        }
    }

    // 바텀바 위치 설정
    fun settingTabs(){
        mainActivity.activityMainBinding.apply {
            val bottomBar = mainBottomNavi
            bottomBar.selectedItemId = R.id.main_bottom_navi_category
        }
    }

    // 뒤로가기 처리
    fun backProcesss(){
        mainActivity.removeFragment(MainFragmentName.COORDINATOR_MAIN)
    }
}