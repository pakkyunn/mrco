package kr.co.lion.team4.mrco.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class OrderDetailViewModel: ViewModel() {
    // 주문 정보 (주문 번호, 주문 일자)
    val textViewOrderDetailOrderNumber = MutableLiveData<String>("12345678")
    val textViewOrderDetailOrderDate = MutableLiveData<String>("2024.04.01")
    
    // 상품 정보 (코디이름)
    val textViewOrderDetailProductName = MutableLiveData<String>("호불호 없는 출근룩 코디 SET")
    
    // 배송 정보 (수령인, 연락처, 주소, 배송 메모)
    val textViewOrderDetailUserName = MutableLiveData<String>("홍길동")
    val textViewOrderDetailUserPhone = MutableLiveData<String>("010-1234-5678")
    val textViewOrderDetailUserAddress = MutableLiveData<String>("서울특별시 OO구 OOO동 12-34길 123456789")
    val textViewOrderDetailOrderMemo = MutableLiveData<String>("빨리 와주세요!!")
    
    // 결제 내역 (상품 금액, 할인 금액, 쿠폰명, 쿠폰 할인 금액)
    val textViewOrderDetailOrderPrice = MutableLiveData<String>("138,000원")
    val textViewOrderDetailOrderDiscountPrice = MutableLiveData<String>("6,900원")
    val textViewOrderDetailOrderCouponName = MutableLiveData<String>("- 신규가입쿠폰(5%)")
    val textViewOrderDetailOrderCouponDiscountPrice = MutableLiveData<String>("6,900원")
    val textViewOrderDetailOrderTotalPrice = MutableLiveData<String>("131,100원")
}