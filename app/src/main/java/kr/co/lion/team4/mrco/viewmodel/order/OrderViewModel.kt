package kr.co.lion.team4.mrco.viewmodel.order

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class OrderViewModel : ViewModel() {
    // 수령인 이름
    val textinputOrderName = MutableLiveData<String>()
    // 휴대폰번호
    val textinputOrderPhone = MutableLiveData<String>()
    // 우편번호
    val textinputOrderPostalCode = MutableLiveData<String>()
    // 주소지
    val textinputOrderAddress = MutableLiveData<String>()
    // 상세주소
    val textinputOrderAddressDetail = MutableLiveData<String>()
    // 배송메모
    val textinputOrderMemo = MutableLiveData<String>()
    // 결제금액 (상품금액 합계)
    val textinputOrderTotalPrice = MutableLiveData<String>()
    // 결제방식 - 둘 중 체크된 radio button의 id를 반환
    val radiogroupOrderPaymentMethod = MutableLiveData<Int>()
    // 주문 동의 여부 (체크박스 체크하면 true 반환)
    val checkboxOrderAgreement = MutableLiveData<Boolean>()
}