package kr.co.lion.team4.mrco.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class RowCoordinatorMainViewModel: ViewModel() {
    val textViewRowCoordinatorMainMBTI = MutableLiveData<String>("ISFP")
    val textViewRowCoordinatorMainProductName = MutableLiveData<String>("홍길동 코디이름")
    val textViewRowCoordinatorMainProductPrice = MutableLiveData<String>("239,000")
    val textViewRowCoordinatorMainProductDiscountPercent = MutableLiveData<String>("10%")

}