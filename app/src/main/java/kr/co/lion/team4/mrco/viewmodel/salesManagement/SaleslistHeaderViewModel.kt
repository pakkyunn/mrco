package kr.co.lion.team4.mrco.viewmodel.salesManagement

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class SaleslistHeaderViewModel : ViewModel() {
    // 판매내역 - 날짜
    val textviewSaleslistDate = MutableLiveData<String>()
}