package kr.co.lion.team4.mrco.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class InvoiceReportViewModel: ViewModel() {
    var invoiceDate = MutableLiveData<String>()

    var invoiceReportValue1 = MutableLiveData<String>()
    
}