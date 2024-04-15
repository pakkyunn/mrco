package kr.co.lion.team4.mrco.viewmodel.like

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class RowLikeProductViewModel: ViewModel() {
    var textViewRowLikeCoordinatorName = MutableLiveData<String>("MRCO 코디네이터")
    var textViewRowLikeProductName = MutableLiveData<String>("호불호 없는 출근룩 코디 SET")
    var textViewRowLikeProductMBTI = MutableLiveData<String>("ISFP")
    var textViewRowLikeProductDiscountPrice = MutableLiveData<String>("30%")
    var textViewRowLikeProductPrice = MutableLiveData<String>("178,000원")
}