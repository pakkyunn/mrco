package kr.co.lion.team4.mrco.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class SalesManagementCalendarViewModel: ViewModel() {
    val textViewSalesManagementCalendarName = MutableLiveData<String>("XXX 코디네이터님의 매출 캘린더입니다")
    val editTextSalesManagementCalendarDate = MutableLiveData<String>("04/08/2024")
    val textViewSalesManagementCalendarText = MutableLiveData<String>("XXX 코디네이터님의 매출 캘린더입니다")
}