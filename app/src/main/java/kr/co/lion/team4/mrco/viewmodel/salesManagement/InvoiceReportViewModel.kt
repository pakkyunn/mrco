package kr.co.lion.team4.mrco.viewmodel.salesManagement

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class InvoiceReportViewModel: ViewModel() {
    // 코디네이터 이름
    val textviewInvoiceReportName = MutableLiveData<String>("XXX 코디네이터님의 매출 리포트입니다")
    var invoiceDate = MutableLiveData<String>()

    var invoiceReportValue1 = MutableLiveData<String>()
    
}