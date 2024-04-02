package kr.co.lion.team4.mrco

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.HorizontalScrollView
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.tabs.TabLayout
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kr.co.lion.team4.mrco.databinding.FragmentSalesManagementBinding
import kr.co.lion.team4.mrco.databinding.FragmentSalesManagementCalendarBinding
import kr.co.lion.team4.mrco.databinding.RowSalesManagementBinding
import kr.co.lion.team4.mrco.databinding.RowSalesManagementCalBinding

class SalesManagementCalendarFragment : Fragment() {

    lateinit var fragmentSalesManagementCalendarBinding: FragmentSalesManagementCalendarBinding
    lateinit var mainActivity: MainActivity

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment

        fragmentSalesManagementCalendarBinding = FragmentSalesManagementCalendarBinding.inflate(inflater)
        mainActivity = activity as MainActivity

        // 툴바, 하단바, 탭 관련
        mainActivity.removeTabsBar()
        mainActivity.removeBottomSheet()
        toolbarSetting()
        settingTabs()
        settingSalesManagementTab()

        // 리사이클러 뷰
        settingRecyclerViewSalesManagement()

        fragmentSalesManagementCalendarBinding.apply {
            textInputCalendarView.setOnClickListener{
                layoutCalendarView.isVisible = true
            }
        }

        return fragmentSalesManagementCalendarBinding.root
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
        fragmentSalesManagementCalendarBinding.apply {
            val tabLayout = tabs
            tabLayout.getTabAt(1)?.select()
        }
    }

    // 리사이클러 뷰 설정
    fun settingRecyclerViewSalesManagement() {
        fragmentSalesManagementCalendarBinding.apply {
            recyclerViewSalesManagementCalendar.apply {
                // 어뎁터 및 레이아웃 매니저 설정
                adapter = SalesManagementCalendarRecyclerViewAdapter()
                layoutManager = LinearLayoutManager(mainActivity, LinearLayoutManager.HORIZONTAL, false)
            }
        }
    }

    // 리사이클러 뷰 어뎁터
    inner class SalesManagementCalendarRecyclerViewAdapter: RecyclerView.Adapter<SalesManagementCalendarRecyclerViewAdapter.SalesManagementCalViewHolder>(){
        inner class SalesManagementCalViewHolder(rowSalesManagementCalBinding: RowSalesManagementCalBinding): RecyclerView.ViewHolder(rowSalesManagementCalBinding.root){
            val rowSalesManagementCalBinding: RowSalesManagementCalBinding

            init {
                this.rowSalesManagementCalBinding = rowSalesManagementCalBinding

                this.rowSalesManagementCalBinding.root.layoutParams = ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT
                )
            }
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SalesManagementCalViewHolder {
            val rowSalesManagementCalBinding = RowSalesManagementCalBinding.inflate(layoutInflater)
            val salesManagementCalViewHolder = SalesManagementCalViewHolder(rowSalesManagementCalBinding)

            return salesManagementCalViewHolder
        }

        override fun getItemCount(): Int {
            return 6
        }

        override fun onBindViewHolder(holder: SalesManagementCalViewHolder, position: Int) {
            holder.rowSalesManagementCalBinding.textViewDate.text = "4월 ${position+1}일"
        }
    }

    // 뒤로가기 처리
    fun backProcesss(){
        mainActivity.removeFragment(MainFragmentName.SALES_MANAGEMENT)
    }

    // 상단 탭 선택 설정
    fun settingSalesManagementTab(){
        CoroutineScope(Dispatchers.Main).launch {
            fragmentSalesManagementCalendarBinding.apply {
                val tabLayout = tabs

                // 탭 선택 리스너 설정
                tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
                    override fun onTabSelected(tab: TabLayout.Tab?) {
                        // 선택된 탭이 첫 번째 탭인 경우
                        if (tab?.position == 0) {

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