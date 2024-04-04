package kr.co.lion.team4.mrco.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class SalesManagementViewModel: ViewModel() {
    val textViewSalesManagementName = MutableLiveData<String>("XXX 코디네이터님의 정산 내역입니다")
}