package kr.co.lion.team4.mrco

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

// (판매자) 배송관리 화면의 판매내역 목록 RecyclerView에 표시될 상품 목록들을 보여주는 RecyclerView의 ViewModel
class ManageShipmentsProductViewModel : ViewModel() {
    // 주문 상태
    val textViewManageShipmentsState = MutableLiveData<String>()
    // 상품명
    val textViewManageShipmentsCoordiName = MutableLiveData<String>()
    // 주문 옵션
    val textViewManageShipmentsOption = MutableLiveData<String>()
    // 가격
    val textViewManageShipmentsPrice = MutableLiveData<String>()
}