package kr.co.lion.team4.mrco.fragment.salesManagement

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.google.android.material.tabs.TabLayout
import kr.co.lion.team4.mrco.MainActivity
import kr.co.lion.team4.mrco.MainFragmentName
import kr.co.lion.team4.mrco.R
import kr.co.lion.team4.mrco.databinding.FragmentInvoiceReportBinding
import kr.co.lion.team4.mrco.viewmodel.salesManagement.InvoiceReportViewModel

/* 매출 관리 - 리포트 */
class SalesManagementInvoiceReportFragment : Fragment() {
    lateinit var fragmentInvoiceReportBinding: FragmentInvoiceReportBinding
    lateinit var mainActivity: MainActivity

    lateinit var invoiceReportViewModel: InvoiceReportViewModel
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        fragmentInvoiceReportBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_invoice_report, container, false)
        invoiceReportViewModel = InvoiceReportViewModel()
        fragmentInvoiceReportBinding.ir = invoiceReportViewModel
        fragmentInvoiceReportBinding.lifecycleOwner = this

        mainActivity = activity as MainActivity

        // 툴바, 하단바, 탭 관련
        mainActivity.removeBottomSheet()
        toolbarSetting()
        settingTabs()
        settingSalesManagementTab()

        return fragmentInvoiceReportBinding.root
    }

    fun toolbarSetting(){
        fragmentInvoiceReportBinding.toolbarInvoiceReport.apply {
            setNavigationOnClickListener {
                backProcess()
            }
        }
    }

    // 탭바 위치 설정
    fun settingTabs(){
        fragmentInvoiceReportBinding.apply {
            val tabLayout = tabsInvoiceReport
            tabLayout.getTabAt(0)?.select()
        }
    }

    // 상단 탭 선택 설정
    fun settingSalesManagementTab(){
        fragmentInvoiceReportBinding.apply {
            val tabLayout = tabsInvoiceReport

            // 탭 선택 리스너 설정
            tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener{
                override fun onTabSelected(tab: TabLayout.Tab?) {
                    when(tab?.position){
                        1-> { // 캘린더
                            mainActivity.replaceFragment(MainFragmentName.SALES_MANAGEMENT_CALENDAR, true, false, null)
                        }
                        2 -> { // 정산 내역
                            mainActivity.replaceFragment(MainFragmentName.SALES_MANAGEMENT, true, false, null)
                        }
                    }
                }

                override fun onTabUnselected(tab: TabLayout.Tab?) {
                    //"Not yet implemented"
                }

                override fun onTabReselected(tab: TabLayout.Tab?) {
                    //"Not yet implemented"
                }
            })
        }
    }

    // 이전 화면으로 뒤로 가기
    fun backProcess(){
        mainActivity.removeFragment(MainFragmentName.SALES_MANAGEMENT_INVOICE_REPORT)
    }
}