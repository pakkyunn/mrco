package kr.co.lion.team4.mrco.viewmodel.mbtiproductmain

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class RowMbtiProductMainViewModel: ViewModel() {
    val textViewRowMbtiProductMainCoordinatorName = MutableLiveData<String>("코디네이터")
    val textViewRowMbtiProductMainProductName = MutableLiveData<String>("호불호 없는 출근룩 코디 SET")
    val textViewRowMbtiProductMainProductDiscountPercent = MutableLiveData<String>("10%")
    val textViewRowMbtiProductMainProductPrice = MutableLiveData<String>("124,545")
}