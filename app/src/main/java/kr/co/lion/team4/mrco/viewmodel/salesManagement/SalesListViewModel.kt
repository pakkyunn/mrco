package kr.co.lion.team4.mrco.viewmodel.salesManagement

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class SalesListViewModel : ViewModel() {
    // 조회 기간 (MaterialButtonToggleGroup)
    val togglebuttonSalesListPeriod = MutableLiveData<Int>()

    // 조회 기간
    val salesPeriodStart = MutableLiveData<String>()
    val salesPeriodEnd = MutableLiveData<String>()

    // to do - 값을 넣고 받아오는 BindingAdapter 생성할 것
}