package kr.co.lion.team4.mrco.viewmodel.order

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

// 주문 내역 목록의 ViewModel
class OrderHistoryItemViewModel : ViewModel() {
    // 주문 일자
    val textViewOrderedItemDate = MutableLiveData<String>()
}