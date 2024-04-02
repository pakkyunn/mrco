package kr.co.lion.team4.mrco

import android.graphics.Rect
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.tabs.TabLayout
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kr.co.lion.team4.mrco.databinding.FragmentCoordinatorRankBinding
import kr.co.lion.team4.mrco.databinding.RowCoordinatorInfoBinding
import kr.co.lion.team4.mrco.databinding.RowCoordinatorRank2Binding
import kr.co.lion.team4.mrco.databinding.RowCoordinatorRankBinding

class CoordinatorRankFragment : Fragment() {

    lateinit var fragmentCoordinatorRankBinding: FragmentCoordinatorRankBinding
    lateinit var mainActivity: MainActivity

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment

        fragmentCoordinatorRankBinding = FragmentCoordinatorRankBinding.inflate(layoutInflater)
        mainActivity = activity as MainActivity

        mainActivity.viewTabsBar()
        mainActivity.settingToolbarMain()

        settingRecyclerViewCoordinatorRank()
        settingTabs()
        settingCoorditab()

        return fragmentCoordinatorRankBinding.root
    }


    // 인기 코디네이터 리사이클러 뷰 설정
    fun settingRecyclerViewCoordinatorRank() {
        fragmentCoordinatorRankBinding.apply {
            recyclerViewCoordinatorRank.apply {
                // 어뎁터 및 레이아웃 매니저 설정
                adapter = CoordinatorRankRecyclerViewAdapter()
                layoutManager = LinearLayoutManager(mainActivity)
            }
        }
    }

    // 탭바 위치 설정
    fun settingTabs(){
        mainActivity.activityMainBinding.apply {
            val tabLayout = tabsMain
            tabLayout.getTabAt(2)?.select()
        }
        fragmentCoordinatorRankBinding.apply {
            val tabLayout = tabs
            tabLayout.getTabAt(0)?.select()
        }
        mainActivity.activityMainBinding.apply {
            val bottomBar = mainBottomNavi
            bottomBar.selectedItemId = R.id.main_bottom_navi_home
        }
    }

    // 인기 코디네이터 리사이클러 뷰 어뎁터
    inner class CoordinatorRankRecyclerViewAdapter: RecyclerView.Adapter<CoordinatorRankRecyclerViewAdapter.CorrdinatorRankViewHolder>(){
        inner class CorrdinatorRankViewHolder(rowCoordinatorRankBinding: RowCoordinatorRankBinding): RecyclerView.ViewHolder(rowCoordinatorRankBinding.root){
            val rowCoordinatorRankBinding: RowCoordinatorRankBinding

            init {
                this.rowCoordinatorRankBinding = rowCoordinatorRankBinding

                this.rowCoordinatorRankBinding.root.layoutParams = ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT
                )
            }
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CorrdinatorRankViewHolder {
            val rowCoordinatorRankBinding = RowCoordinatorRankBinding.inflate(layoutInflater)
            val coordinatorRankViewHolder = CorrdinatorRankViewHolder(rowCoordinatorRankBinding)

            return coordinatorRankViewHolder
        }

        override fun getItemCount(): Int {
            return 6
        }

        override fun onBindViewHolder(holder: CorrdinatorRankViewHolder, position: Int) {
            holder.rowCoordinatorRankBinding.textViewRowCoordinatorRankRank.text = "${position + 1}"

            // 내부 리사이클러 뷰 설정
            val innerRecyclerView = holder.rowCoordinatorRankBinding.recyclerViewCoordinatorRank2
            innerRecyclerView.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            innerRecyclerView.adapter  = InnerRecyclerViewAdapter() //내부 리사이클러 뷰 어댑터 설정

            // ItemDecoration 적용?
        }
    }

    // 내부 리사이클러 뷰 어댑터
    inner class InnerRecyclerViewAdapter : RecyclerView.Adapter<InnerRecyclerViewAdapter.InnerViewHolder>() {
        inner class InnerViewHolder(rowCoordinatorRank2Binding: RowCoordinatorRank2Binding) : RecyclerView.ViewHolder(rowCoordinatorRank2Binding.root) {
            // 내부 리사이클러 뷰의 ViewHolder 내용 정의
            val rowCoordinatorRank2Binding: RowCoordinatorRank2Binding

            init {
                this.rowCoordinatorRank2Binding = rowCoordinatorRank2Binding

                this.rowCoordinatorRank2Binding.root.layoutParams = ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT
                )
            }
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): InnerViewHolder {
            val rowCoordinatorRank2Binding = RowCoordinatorRank2Binding.inflate(layoutInflater)
            val innerViewHolder = InnerViewHolder(rowCoordinatorRank2Binding)

            return innerViewHolder
        }

        override fun getItemCount(): Int {
            return 10
        }

        override fun onBindViewHolder(holder: InnerViewHolder, position: Int) {

        }
    }

    fun settingCoorditab(){
        CoroutineScope(Dispatchers.Main).launch {
            fragmentCoordinatorRankBinding.apply {
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