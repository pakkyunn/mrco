package kr.co.lion.team4.mrco.viewmodel.home.mbti

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class RowHomeMbti2ViewModel: ViewModel() {
    val textViewRowHomeMbtiProductMbti = MutableLiveData<String>("ENTJ")
    val textViewRowHomeMbtiCoordinatorName = MutableLiveData<String>("코디네이터")
    val textViewRowHomeMbtiProductName = MutableLiveData<String>("호불호 없는 출근룩 코디 SET")
    val textViewRowHomeMbtiProductDiscountPercent = MutableLiveData<String>("10%")
    val textViewRowHomeMbtiProductPrice = MutableLiveData<String>("124,545")
}