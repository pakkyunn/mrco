package kr.co.lion.team4.mrco.fragment.home.mbti

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.tabs.TabLayout
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kr.co.lion.team4.mrco.MainActivity
import kr.co.lion.team4.mrco.MainFragmentName
import kr.co.lion.team4.mrco.R
import kr.co.lion.team4.mrco.databinding.FragmentCoordinatorRankBinding
import kr.co.lion.team4.mrco.databinding.FragmentHomeMbtiBinding
import kr.co.lion.team4.mrco.databinding.RowHomeRecommendBinding

class HomeMbtiFragment : Fragment() {

    lateinit var fragmentHomeMbtiBinding: FragmentHomeMbtiBinding
    lateinit var mainActivity: MainActivity

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment

        fragmentHomeMbtiBinding = FragmentHomeMbtiBinding.inflate(inflater)
        mainActivity = activity as MainActivity

        // 툴바, 하단바, 탭 관련
        settingTabs()
        settingMainTab()

        return fragmentHomeMbtiBinding.root
    }

    // 탭바 위치 설정
    fun settingTabs(){
        fragmentHomeMbtiBinding.apply {
            val tabLayout = tabsMain
            tabLayout.getTabAt(1)?.select()
        }
        mainActivity.activityMainBinding.apply {
            val bottomBar = mainBottomNavi
            bottomBar.selectedItemId = R.id.main_bottom_navi_home
        }
    }

    fun settingMainTab(){
        CoroutineScope(Dispatchers.Main).launch {
            fragmentHomeMbtiBinding.apply {
                val tabLayout = tabsMain

                // 탭 선택 리스너 설정
                tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
                    override fun onTabSelected(tab: TabLayout.Tab?) {
                        // 선택된 탭이 첫 번째 탭인 경우
                        if (tab?.position == 0) {
                            mainActivity.removeFragment(MainFragmentName.HOME_MBTI)
                            mainActivity.removeFragment(MainFragmentName.HOME_COORDINATOR_RANK)
                            mainActivity.removeFragment(MainFragmentName.HOME_COORDINATOR_INFO)
                            mainActivity.replaceFragment(MainFragmentName.HOME_RECOMMEND, false, false, null)
                        }
                        else if (tab?.position == 1) {
                            mainActivity.removeFragment(MainFragmentName.HOME_RECOMMEND)
                            mainActivity.removeFragment(MainFragmentName.HOME_COORDINATOR_RANK)
                            mainActivity.removeFragment(MainFragmentName.HOME_COORDINATOR_INFO)
                            mainActivity.replaceFragment(MainFragmentName.HOME_MBTI, false, false, null)
                        } else {
                            mainActivity.removeFragment(MainFragmentName.HOME_RECOMMEND)
                            mainActivity.removeFragment(MainFragmentName.HOME_MBTI)
                            mainActivity.removeFragment(MainFragmentName.HOME_COORDINATOR_INFO)
                            mainActivity.replaceFragment(MainFragmentName.HOME_COORDINATOR_RANK, false, false, null)
                        }
                    }

                    override fun onTabUnselected(tab: TabLayout.Tab?) {
                        // Not implemented
                    }

                    override fun onTabReselected(tab: TabLayout.Tab?) {
                        // Not implemented
                    }
                })
            }
        }
    }

    // 홈(MBTI) - MBTI @@에게 잘 어울리는 코디 리사이클러 뷰 어뎁터
    inner class HomeRecommendRecyclerViewAdapter: RecyclerView.Adapter<HomeRecommendRecyclerViewAdapter.HomeRecommendViewHolder>(){
        inner class HomeRecommendViewHolder(rowHomeRecommendBinding: RowHomeRecommendBinding): RecyclerView.ViewHolder(rowHomeRecommendBinding.root){
            val rowHomeRecommendBinding: RowHomeRecommendBinding

            init {
                this.rowHomeRecommendBinding = rowHomeRecommendBinding

                this.rowHomeRecommendBinding.root.layoutParams = ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT
                )
            }
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeRecommendViewHolder {
            // val rowCoordinatorInfoBinding = RowCoordinatorInfoBinding.inflate(layoutInflater)

            val rowHomeRecommendBinding = RowHomeRecommendBinding.inflate(layoutInflater)
            val homeRecommendViewHolder = HomeRecommendViewHolder(rowHomeRecommendBinding)

            return homeRecommendViewHolder
        }

        override fun getItemCount(): Int {
            return 6
        }

        override fun onBindViewHolder(holder: HomeRecommendViewHolder, position: Int) {

        }
    }
}