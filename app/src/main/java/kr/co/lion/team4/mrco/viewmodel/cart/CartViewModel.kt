package kr.co.lion.team4.mrco.viewmodel.cart

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

// (구매자) 장바구니 화면 View Model
class CartViewModel : ViewModel() {
    // 전체선택 CheckBox
    // to do  Binding Adapter
    val checkboxCartSelectAll = MutableLiveData<Boolean>()
    // 총 상품 금액
    val textviewCartSubtotal = MutableLiveData<String>()
    // 총 배송비
    val textviewCartShippingFee = MutableLiveData<String>()
    // 장바구니에 담긴 상품 중 선택한 금액들의 총 주문 금액 (상품금액과 배송비의 합계)
    val textviewCartTotal = MutableLiveData<String>()
}