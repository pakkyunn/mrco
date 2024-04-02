package kr.co.lion.team4.mrco

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kr.co.lion.team4.mrco.databinding.FragmentLikeCoordinatorBinding
import kr.co.lion.team4.mrco.databinding.FragmentSalesManagementBinding
import kr.co.lion.team4.mrco.databinding.RowLikeCoordinatorBinding
import kr.co.lion.team4.mrco.databinding.RowSalesManagementBinding

class SalesManagementFragment : Fragment() {

    lateinit var fragmentSalesManagementBinding: FragmentSalesManagementBinding
    lateinit var mainActivity: MainActivity

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment

        fragmentSalesManagementBinding = FragmentSalesManagementBinding.inflate(inflater)
        mainActivity = activity as MainActivity

        // 툴바, 하단바, 탭 관련
        mainActivity.removeTabsBar()
        mainActivity.removeBottomSheet()
        toolbarSetting()
        settingTabs()

        // 리사이클러
        settingRecyclerViewSalesManagement()

        return fragmentSalesManagementBinding.root
    }

    // 툴바 설정
    fun toolbarSetting(){
        mainActivity.activityMainBinding.apply {
            toolbarMain.apply {
                title = "매출 관리(정산)"
                // 네비게이션
                setNavigationIcon(R.drawable.arrow_back_24px)
                setNavigationOnClickListener {
                    backProcesss()
                }
                // 메뉴제거
                menu.clear()
            }
        }
    }

    // 탭바 및 바텀바 위치 설정
    fun settingTabs(){
        fragmentSalesManagementBinding.apply {
            val tabLayout = tabs
            tabLayout.getTabAt(2)?.select()
        }
    }

    // 인기 코디네이터 리사이클러 뷰 설정
    fun settingRecyclerViewSalesManagement() {
        fragmentSalesManagementBinding.apply {
            recyclerViewSalesManagement.apply {
                // 어뎁터 및 레이아웃 매니저 설정
                adapter = SalesManagementRecyclerViewAdapter()
                layoutManager = LinearLayoutManager(mainActivity)
            }
        }
    }

    // 인기 코디네이터 리사이클러 뷰 어뎁터
    inner class SalesManagementRecyclerViewAdapter: RecyclerView.Adapter<SalesManagementRecyclerViewAdapter.SalesManagementViewHolder>(){
        inner class SalesManagementViewHolder(rowSalesManagementBinding: RowSalesManagementBinding): RecyclerView.ViewHolder(rowSalesManagementBinding.root){
            val rowSalesManagementBinding: RowSalesManagementBinding

            init {
                this.rowSalesManagementBinding = rowSalesManagementBinding

                this.rowSalesManagementBinding.root.layoutParams = ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT
                )
            }
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SalesManagementViewHolder {
            val rowSalesManagementBinding = RowSalesManagementBinding.inflate(layoutInflater)
            val salesManagementViewHolder = SalesManagementViewHolder(rowSalesManagementBinding)

            return salesManagementViewHolder
        }

        override fun getItemCount(): Int {
            return 6
        }

        override fun onBindViewHolder(holder: SalesManagementViewHolder, position: Int) {

        }
    }

    // 뒤로가기 처리
    fun backProcesss(){
        mainActivity.removeFragment(MainFragmentName.SALES_MANAGEMENT)
    }
}