package kr.co.lion.team4.mrco.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class RowCustomerServiceViewModel: ViewModel() {
    // 문의 카테고리
    val textViewCategoryRowCustomerService = MutableLiveData<String>()
    // 자주 묻는 문의 제목
    val textViewSubtitleRowCustomerService = MutableLiveData<String>()
    // 자주 묻는 문의 답변
    val textViewAnswerRowCustomerService = MutableLiveData<String>()
}