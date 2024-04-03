package kr.co.lion.team4.mrco.fragment.coordinator

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.tabs.TabLayout
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kr.co.lion.team4.mrco.MainActivity
import kr.co.lion.team4.mrco.MainFragmentName
import kr.co.lion.team4.mrco.R
import kr.co.lion.team4.mrco.databinding.FragmentCoordinatorInfoBinding
import kr.co.lion.team4.mrco.databinding.RowCoordinatorInfoBinding
import kr.co.lion.team4.mrco.viewmodel.RowCoordinatorInfoViewModel

class CoordinatorInfoFragment : Fragment() {

    lateinit var fragmentCoordinatorInfoBinding: FragmentCoordinatorInfoBinding
    lateinit var mainActivity: MainActivity

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment

        fragmentCoordinatorInfoBinding = FragmentCoordinatorInfoBinding.inflate(inflater)

        mainActivity = activity as MainActivity

        mainActivity.viewTabsBar()
        mainActivity.settingToolbarMain()

        settingRecyclerViewCoordinatorInfo()
        settingTabs()
        settingCoorditab()

        return fragmentCoordinatorInfoBinding.root
    }

    // 코디네이터 소개 리사이클러 뷰 설정
    fun settingRecyclerViewCoordinatorInfo() {
        fragmentCoordinatorInfoBinding.apply {
            recyclerViewCoordinatorInfo.apply {
                // 어뎁터 및 레이아웃 매니저 설정
                adapter = CoordinatorInfoRecyclerViewAdapter()
                layoutManager = GridLayoutManager(mainActivity, 2)
            }
        }
    }

    // 탭바 위치 설정
    fun settingTabs(){
        mainActivity.activityMainBinding.apply {
            val tabLayout = tabsMain
            tabLayout.getTabAt(2)?.select()
        }
        fragmentCoordinatorInfoBinding.apply {
            val tabLayout = tabs
            tabLayout.getTabAt(1)?.select()
        }
        mainActivity.activityMainBinding.apply {
            val bottomBar = mainBottomNavi
            bottomBar.selectedItemId = R.id.main_bottom_navi_home
        }
    }

    // 코디네이터 소개 리사이클러 뷰 어뎁터
    inner class CoordinatorInfoRecyclerViewAdapter: RecyclerView.Adapter<CoordinatorInfoRecyclerViewAdapter.CorrdinatorInfoViewHolder>(){
        inner class CorrdinatorInfoViewHolder(rowCoordinatorInfoBinding: RowCoordinatorInfoBinding): RecyclerView.ViewHolder(rowCoordinatorInfoBinding.root){
            val rowCoordinatorInfoBinding: RowCoordinatorInfoBinding

            init {
                this.rowCoordinatorInfoBinding = rowCoordinatorInfoBinding

                this.rowCoordinatorInfoBinding.root.layoutParams = ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT
                )
            }
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CorrdinatorInfoViewHolder {
            // val rowCoordinatorInfoBinding = RowCoordinatorInfoBinding.inflate(layoutInflater)

            val rowCoordinatorInfoBinding = DataBindingUtil.inflate<RowCoordinatorInfoBinding>(
                layoutInflater, R.layout.row_coordinator_info, parent, false
            )
            val rowCoordinatorInfoViewModel = RowCoordinatorInfoViewModel()
            rowCoordinatorInfoBinding.rowCoordinatorInfoViewModel = rowCoordinatorInfoViewModel
            rowCoordinatorInfoBinding.lifecycleOwner = this@CoordinatorInfoFragment

            val coordinatorInfoViewHolder = CorrdinatorInfoViewHolder(rowCoordinatorInfoBinding)

            return coordinatorInfoViewHolder
        }

        override fun getItemCount(): Int {
            return 8
        }

        override fun onBindViewHolder(holder: CorrdinatorInfoViewHolder, position: Int) {
            holder.rowCoordinatorInfoBinding.root.setOnClickListener {
                mainActivity.replaceFragment(MainFragmentName.COORDINATOR_MAIN, true, true, null)
            }
        }
    }

    fun settingCoorditab(){
        CoroutineScope(Dispatchers.Main).launch {
            fragmentCoordinatorInfoBinding.apply {
                val tabLayout = tabs

                // 탭 선택 리스너 설정
                tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
                    override fun onTabSelected(tab: TabLayout.Tab?) {
                        // 선택된 탭이 첫 번째 탭인 경우
                        if (tab?.position == 0) {
                            mainActivity.replaceFragment(MainFragmentName.COORDINATOR_RANK, false, true, null)
                            mainActivity.removeFragment(MainFragmentName.COORDINATOR_INFO)
                        }
                        else {
                            mainActivity.replaceFragment(MainFragmentName.COORDINATOR_INFO, false, true, null)
                            mainActivity.removeFragment(MainFragmentName.COORDINATOR_RANK)
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
}