package kr.co.lion.team4.mrco.fragment.salesManagement

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.BindingAdapter
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.github.aachartmodel.aainfographics.aachartcreator.AAChartModel
import com.github.aachartmodel.aainfographics.aachartcreator.AAChartView
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
        fragmentInvoiceReportBinding.invoiceReportViewModel = invoiceReportViewModel
        fragmentInvoiceReportBinding.lifecycleOwner = this

        mainActivity = activity as MainActivity

        setChartModel(fragmentInvoiceReportBinding.invoiceReportTimeChart, invoiceReportViewModel.pieChartModel)

        return fragmentInvoiceReportBinding.root
    }



    companion object{
        // 차트 뷰 설정
        @BindingAdapter("aa_chartModel")
        @JvmStatic
        fun setChartModel(view: AAChartView, model: AAChartModel){
            view.aa_drawChartWithChartModel(model)
        }
    }
}