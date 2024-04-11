package kr.co.lion.team4.mrco.viewmodel.home.mbti

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class HomeMbtiViewModel: ViewModel() {
    val textViewHomeMbtiTextFirst = MutableLiveData<String>("ISFP 여성에게 잘 어울리는 코디")
    val textViewHomeMbtiTextSecond = MutableLiveData<String>("ISFP 남성이 좋아하는 이성의 코디")
}