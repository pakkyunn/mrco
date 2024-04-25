package kr.co.lion.team4.mrco.viewmodel.order

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class OrderDetailProductViewModel : ViewModel() {
    // 주문 상태
    val textviewOrderDetailProductState = MutableLiveData<String>()
    // 코디 상품명
    val textviewOrderDetailProductCoordiName = MutableLiveData<String>()
    // 주문 옵션
    val textviewOrderDetailProductOption = MutableLiveData<String>()
    // 가격
    val textviewOrderDetailProductPrice = MutableLiveData<String>()
}