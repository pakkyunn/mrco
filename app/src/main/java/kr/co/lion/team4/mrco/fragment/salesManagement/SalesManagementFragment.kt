package kr.co.lion.team4.mrco.fragment.salesManagement

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.google.android.material.tabs.TabLayout
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kr.co.lion.team4.mrco.MainActivity
import kr.co.lion.team4.mrco.MainFragmentName
import kr.co.lion.team4.mrco.R
import kr.co.lion.team4.mrco.SalesManagementSubFragmentName
import kr.co.lion.team4.mrco.databinding.FragmentSalesManagementBinding
import kr.co.lion.team4.mrco.viewmodel.salesManagement.SalesManagementViewModel

class SalesManagementFragment : Fragment() {

    // 원빈 - 매출 관리(정산) - 캘린더 화면 (태진님 관련)

    lateinit var fragmentSalesManagementBinding: FragmentSalesManagementBinding
    lateinit var mainActivity: MainActivity

    lateinit var salesManagementViewModel: SalesManagementViewModel

    var oldFragment: Fragment? = null
    var newFragment: Fragment? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        fragmentSalesManagementBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_sales_management, container, false)
        salesManagementViewModel = SalesManagementViewModel()
        fragmentSalesManagementBinding.salesManagementViewModel = SalesManagementViewModel()
        fragmentSalesManagementBinding.lifecycleOwner = this

        mainActivity = activity as MainActivity

        // 툴바, 하단바, 탭 관련
        toolbarSetting()
        settingTabs()
        settingSalesManagementTab()

        return fragmentSalesManagementBinding.root
    }

    // 툴바 설정
    fun toolbarSetting(){
        fragmentSalesManagementBinding.toolbarSalesManagement.apply {
            title = "매출 관리(정산)"
            // 네비게이션
            setNavigationIcon(R.drawable.arrow_back_24px)
            setNavigationOnClickListener {
                backProcess()
            }
        }
    }

    // 탭바 및 바텀바 위치 설정
    fun settingTabs(){
        fragmentSalesManagementBinding.apply {
            val tabLayout = tabs
            tabLayout.getTabAt(0)?.select()
            replaceFragment(SalesManagementSubFragmentName.SALES_INVOICE_REPORT,null)
        }
    }

    // 뒤로가기 처리
    fun backProcess(){
        mainActivity.removeFragment(MainFragmentName.SALES_MANAGEMENT)
    }

    // 상단 탭 선택 설정
    fun settingSalesManagementTab(){
        fragmentSalesManagementBinding.apply {
            val tabLayout = tabs

            // 탭 선택 리스너 설정
            tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
                override fun onTabSelected(tab: TabLayout.Tab?) {
                    when(tab?.position) {
                        // 리포트
                        0 -> {
                            replaceFragment(SalesManagementSubFragmentName.SALES_INVOICE_REPORT, null)
                            salesManagementViewModel?.textViewSalesManagementName?.value =
                                "XXX 코디네이터님의 매출 리포트입니다"
                        }
                        // 캘린더
                        1 -> {
                            replaceFragment(SalesManagementSubFragmentName.SALES_CALENDAR,null)
                            salesManagementViewModel?.textViewSalesManagementName?.value =
                                "XXX 코디네이터님의 매출 캘린더입니다"
                        }
                        // 정산 내역
                        2 -> {
                            replaceFragment(SalesManagementSubFragmentName.SALES_HISTORY,null)
                            salesManagementViewModel?.textViewSalesManagementName?.value =
                                "XXX 코디네이터님의 정산 내역입니다"
                        }
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

    // Fragment 교체
    fun replaceFragment(name: SalesManagementSubFragmentName, data: Bundle?) {
        // Fragment를 교체할 수 있는 객체를 추출한다.
        val fragmentTransaction = childFragmentManager.beginTransaction()

        // oldFragment에 newFragment가 가지고 있는 Fragment 객체를 담아준다.
        if(newFragment != null){
            oldFragment = newFragment
        }

        when(name){
            SalesManagementSubFragmentName.SALES_INVOICE_REPORT -> newFragment = SalesManagementInvoiceReportFragment()

            SalesManagementSubFragmentName.SALES_CALENDAR -> newFragment = SalesManagementCalendarFragment()

            SalesManagementSubFragmentName.SALES_HISTORY -> newFragment = SalesManagementHistoryFragment()
        }

        // 새로운 Fragment에 전달할 객체가 있다면 arguments 프로퍼티에 넣어준다.
        if(data != null){
            newFragment?.arguments = data
        }

        if(newFragment != null){
            // Fragment를 교체한다.(이전 Fragment가 없으면 새롭게 추가하는 역할을 수행한다)
            fragmentTransaction.replace(R.id.fragment_container_view_sales_management, newFragment!!)

            // Fragment 교체를 확정한다.
            fragmentTransaction.commit()
        }
    }
}