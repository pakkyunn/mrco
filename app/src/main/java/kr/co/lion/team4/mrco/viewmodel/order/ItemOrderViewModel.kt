package kr.co.lion.team4.mrco.viewmodel.order

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ItemOrderViewModel : ViewModel() {
    // 코디네이터명
    val textviewOrderItemCoordinator = MutableLiveData<String>()
    // 코디 상품명
    val textviewOrderItemCoordi_name = MutableLiveData<String>()
    // 옵션
    val textviewOrderItemOption = MutableLiveData<String>()
    // 가격
    val textviewOrderItemPrice = MutableLiveData<String>()
}