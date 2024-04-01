package kr.co.lion.team4.mrco

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kr.co.lion.team4.mrco.databinding.FragmentCoordinatorInfoBinding
import kr.co.lion.team4.mrco.databinding.FragmentCoordinatorMainBinding
import kr.co.lion.team4.mrco.databinding.RowCoordinatorInfoBinding
import kr.co.lion.team4.mrco.databinding.RowCoordinatorMainBinding


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
        settingRecyclerViewCoordinatorInfo()

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

    // 코디네이터 메인 리사클러뷰 설정
    fun settingRecyclerViewCoordinatorInfo() {
        fragmentCoordinatorMainBinding.apply {
            recyclerViewCoordinatorMain.apply {
                // 어뎁터 및 레이아웃 매니저 설정
                adapter = CoordinatorMainRecyclerViewAdapter()
                layoutManager = GridLayoutManager(mainActivity, 2)
            }
        }
    }

    // 코디네이터 메인 리사이클러 뷰 어뎁터
    inner class CoordinatorMainRecyclerViewAdapter: RecyclerView.Adapter<CoordinatorMainRecyclerViewAdapter.CorrdinatorMainViewHolder>(){
        inner class CorrdinatorMainViewHolder(rowCoordinatorMainBinding: RowCoordinatorMainBinding): RecyclerView.ViewHolder(rowCoordinatorMainBinding.root){
            val rowCoordinatorMainBinding: RowCoordinatorMainBinding

            init {
                this.rowCoordinatorMainBinding = rowCoordinatorMainBinding

                this.rowCoordinatorMainBinding.root.layoutParams = ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT
                )
            }
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CorrdinatorMainViewHolder {
            val rowCoordinatorMainBinding = RowCoordinatorMainBinding.inflate(layoutInflater)
            val coordinatorMainViewHolder = CorrdinatorMainViewHolder(rowCoordinatorMainBinding)

            return coordinatorMainViewHolder
        }

        override fun getItemCount(): Int {
            return 8
        }

        override fun onBindViewHolder(holder: CorrdinatorMainViewHolder, position: Int) {

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