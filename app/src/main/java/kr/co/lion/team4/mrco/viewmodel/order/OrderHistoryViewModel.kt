package kr.co.lion.team4.mrco.viewmodel.order

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

/* (구매자) 주문 내역 화면(OrderHistoryFragment)의 ViewModel */
class OrderHistoryViewModel : ViewModel() {
    // 검색어 (주문 내역 검색어)
    val editTextOrderHistoryKeyword = MutableLiveData<String>()

    // 조회 기간 - 시작일
    val periodStart = MutableLiveData<String>()
    // 조회 기간 - 종료일
    val periodEnd = MutableLiveData<String>()
}