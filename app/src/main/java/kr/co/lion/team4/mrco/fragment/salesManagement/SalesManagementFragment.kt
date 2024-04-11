package kr.co.lion.team4.mrco.fragment.salesManagement

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.tabs.TabLayout
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kr.co.lion.team4.mrco.MainActivity
import kr.co.lion.team4.mrco.MainFragmentName
import kr.co.lion.team4.mrco.R
import kr.co.lion.team4.mrco.databinding.FragmentSalesManagementBinding
import kr.co.lion.team4.mrco.databinding.RowSalesManagementBinding
import kr.co.lion.team4.mrco.viewmodel.salesManagement.RowSalesManagementViewModel
import kr.co.lion.team4.mrco.viewmodel.salesManagement.SalesManagementViewModel

class SalesManagementFragment : Fragment() {

    // 원빈 - 매출 관리(정산) - 캘린더 화면 (태진님 관련)

    lateinit var fragmentSalesManagementBinding: FragmentSalesManagementBinding
    lateinit var mainActivity: MainActivity

    lateinit var salesManagementViewModel: SalesManagementViewModel


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        fragmentSalesManagementBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_sales_management, container, false)
        salesManagementViewModel = SalesManagementViewModel()
        fragmentSalesManagementBinding.salesManagementViewModel = SalesManagementViewModel()
        fragmentSalesManagementBinding.lifecycleOwner = this

        mainActivity = activity as MainActivity

        // 툴바, 하단바, 탭 관련
        mainActivity.removeBottomSheet()
        toolbarSetting()
        settingTabs()
        settingSalesManagementTab()

        // 리사이클러
        settingRecyclerViewSalesManagement()

        return fragmentSalesManagementBinding.root
    }

    // 툴바 설정
    fun toolbarSetting(){
        fragmentSalesManagementBinding.toolbarSalesManagement.apply {
            title = "매출 관리(정산)"
            // 네비게이션
            setNavigationIcon(R.drawable.arrow_back_24px)
            setNavigationOnClickListener {
                backProcesss()
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

    // 리사이클러 뷰 설정
    fun settingRecyclerViewSalesManagement() {
        fragmentSalesManagementBinding.apply {
            recyclerViewSalesManagement.apply {
                // 어뎁터 및 레이아웃 매니저 설정
                adapter = SalesManagementRecyclerViewAdapter()
                layoutManager = LinearLayoutManager(mainActivity)
            }
        }
    }

    // 리사이클러 뷰 어뎁터
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
            val rowSalesManagementBinding = DataBindingUtil.inflate<RowSalesManagementBinding>(
                layoutInflater, R.layout.row_sales_management, parent, false
            )
            val rowSalesManagementViewModel = RowSalesManagementViewModel()
            rowSalesManagementBinding.rowSalesManagementViewModel = rowSalesManagementViewModel
            rowSalesManagementBinding.lifecycleOwner = this@SalesManagementFragment

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

    // 상단 탭 선택 설정
    fun settingSalesManagementTab(){
        CoroutineScope(Dispatchers.Main).launch {
            fragmentSalesManagementBinding.apply {
                val tabLayout = tabs

                // 탭 선택 리스너 설정
                tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
                    override fun onTabSelected(tab: TabLayout.Tab?) {
                        // 선택된 탭이 첫 번째 탭인 경우
                        if (tab?.position == 0) {
                            mainActivity.removeFragment(MainFragmentName.SALES_MANAGEMENT_CALENDAR)
                            mainActivity.removeFragment(MainFragmentName.SALES_MANAGEMENT)
                        }
                        else if (tab?.position == 1) {
                            mainActivity.replaceFragment(MainFragmentName.SALES_MANAGEMENT_CALENDAR, false, true, null)
                            mainActivity.removeFragment(MainFragmentName.SALES_MANAGEMENT)
                        }
                        else {
                            mainActivity.replaceFragment(MainFragmentName.SALES_MANAGEMENT, false, true, null)
                            mainActivity.removeFragment(MainFragmentName.SALES_MANAGEMENT_CALENDAR)
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