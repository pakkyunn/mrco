package kr.co.lion.team4.mrco.viewmodel.like

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class RowLikeProductViewModel: ViewModel() {
    var textViewRowLikeProductName = MutableLiveData<String>("홍길동 코디이름")
    var textViewRowLikeProductMBTI = MutableLiveData<String>("ISFP")
    var textViewRowLikeProductDiscountPrice = MutableLiveData<String>("30%")
    var textViewRowLikeProductPrice = MutableLiveData<String>("178,000원")
}