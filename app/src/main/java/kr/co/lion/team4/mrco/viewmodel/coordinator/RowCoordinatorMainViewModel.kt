package kr.co.lion.team4.mrco.viewmodel.coordinator

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class RowCoordinatorMainViewModel: ViewModel() {
    val textViewRowCoordinatorMainMBTI = MutableLiveData<String>("INTP")
    val textViewRowCoordinatorMainCoordiname = MutableLiveData<String>("아이유")
    val textViewRowCoordinatorMainProductName = MutableLiveData<String>("(상품명) 호불호 없는 출근룩 코디 SET")
    val textViewRowCoordinatorMainProductPrice = MutableLiveData<String>("138,000")
    val textViewRowCoordinatorMainProductDiscountPercent = MutableLiveData<String>("10%")
    val textViewRowCoordinatorMainProductFinalPrice = MutableLiveData<String>("124,545")
}