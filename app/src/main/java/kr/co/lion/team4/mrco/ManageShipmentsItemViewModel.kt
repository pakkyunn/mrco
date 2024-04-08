package kr.co.lion.team4.mrco

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

// (판매자) 배송관리 화면의 판매내역 목록 RecyclerView의 ViewModel
class ManageShipmentsItemViewModel : ViewModel() {
    // 주문일자
    val textViewManageShipmentsDate = MutableLiveData<String>()
}