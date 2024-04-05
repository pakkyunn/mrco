package kr.co.lion.team4.mrco.viewmodel.like

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class RowLikeProductViewModel: ViewModel() {
    val textViewRowLikeProductName = MutableLiveData<String>("홍길동 코디이름")
    val textViewRowLikeProductMBTI = MutableLiveData<String>("ISFP")
    val textViewRowLikeProductDiscountPrice = MutableLiveData<String>("30%")
    val textViewRowLikeProductPrice = MutableLiveData<String>("178,000원")
}