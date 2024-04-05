package kr.co.lion.team4.mrco.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class RowAppNoticeViewModel: ViewModel() {
    // 알림 제목
    val textViewSubtitleRowAppNotice = MutableLiveData<String>()
    // 알림 내용
    val textViewContentRowAppNotice = MutableLiveData<String>()
}