package kr.co.lion.team4.mrco

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

// 주문 내역 - 상품 목록의 ViewModel
class OrderHistoryProductViewModel : ViewModel() {
    // 주문 상태
    val textViewOrderedItemState = MutableLiveData<String>()
    // 주문 상품명
    val textViewOrderedItemCoordiName = MutableLiveData<String>()
    // 주문 옵션
    val textViewOrderedItemOption = MutableLiveData<String>()
    // 주문 가격
    val textViewOrderedItemPrice = MutableLiveData<String>()

    // (주문 상태)
    // 1. 발송 준비 상태일 때
    val radiobuttonOrderedItemStateReady = MutableLiveData<Boolean>()
    // 2. 배송 시작 상태일 때
    val radiobuttonOrderedItemStateShipped = MutableLiveData<Boolean>()
    // 3. 배송중 상태일 때
    val radiobuttonOrderedItemStateInTransit = MutableLiveData<Boolean>()
    // 4. 배송완료 상태일 때
    val radiobuttonOrderedItemStateDelivered = MutableLiveData<Boolean>()
    // 주문 상태 - 상태바
    val progressbarOrderedItemState = MutableLiveData<Int>()
}