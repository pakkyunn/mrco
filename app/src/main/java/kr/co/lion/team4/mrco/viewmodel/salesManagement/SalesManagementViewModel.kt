package kr.co.lion.team4.mrco.viewmodel.salesManagement

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class SalesManagementViewModel: ViewModel() {
    val textViewSalesManagementName = MutableLiveData<String>("XXX 코디네이터님의 매출 리포트입니다")
}