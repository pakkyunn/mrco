package kr.co.lion.team4.mrco.viewmodel.customerService

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class CustomerServiceViewModel: ViewModel() {
    // 검색 텍스트필드
    val textFieldCustomerServiceSearch = MutableLiveData<String>()
}