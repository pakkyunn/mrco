package kr.co.lion.team4.mrco.viewmodel.salesManagement

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.github.aachartmodel.aainfographics.aachartcreator.AAChartModel
import com.github.aachartmodel.aainfographics.aachartcreator.AAChartType
import com.github.aachartmodel.aainfographics.aachartcreator.AAChartView
import com.github.aachartmodel.aainfographics.aachartcreator.AASeriesElement

class InvoiceReportViewModel: ViewModel() {
    var invoiceDate = MutableLiveData<String>()

    var invoiceReportValue1 = MutableLiveData<String>()

    // 차트 생성
    val pieChartModel: AAChartModel = AAChartModel()
        .chartType(AAChartType.Pie)
        .title("${invoiceDate} 에 결제한 고객 정보 분석")
        .backgroundColor("ffffff")
        .dataLabelsEnabled(true)
        .series(
            arrayOf(
                AASeriesElement()
                    .name("파이 차트 데이터")
                    .data(arrayOf(
                        arrayOf("항목1", 10),
                        arrayOf("항목2", 20),
                        arrayOf("항목3", 30)
                    ))
            )
        )

}