package kr.co.lion.team4.mrco.viewmodel.order

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class OrderDetailViewModel: ViewModel() {
    // 주문 정보 (주문 번호, 주문 일자)
    val textViewOrderDetailOrderNumber = MutableLiveData<String>("")
    val textViewOrderDetailOrderDate = MutableLiveData<String>("")
    
    // 배송 정보 (수령인, 연락처, 주소, 배송 메모)
    val textViewOrderDetailUserName = MutableLiveData<String>("")
    val textViewOrderDetailUserPhone = MutableLiveData<String>("")
    val textViewOrderDetailUserAddress = MutableLiveData<String>("")
    val textViewOrderDetailOrderMemo = MutableLiveData<String>("")
    
    // 결제 내역 (상품 금액, 할인 금액, 쿠폰명, 쿠폰 할인 금액)
    val textViewOrderDetailOrderPrice = MutableLiveData<String>("")
    val textViewOrderDetailOrderDiscountPrice = MutableLiveData<String>("")
    val textViewOrderDetailOrderCouponName = MutableLiveData<String>("")
    val textViewOrderDetailOrderCouponDiscountPrice = MutableLiveData<String>("")
    val textViewOrderDetailOrderTotalPrice = MutableLiveData<String>("")
}