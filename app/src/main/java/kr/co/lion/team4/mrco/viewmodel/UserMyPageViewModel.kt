package kr.co.lion.team4.mrco.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class UserMyPageViewModel: ViewModel() {
    // 사용자 닉네임
    val textViewNicknameUserMyPage = MutableLiveData<String>()
    // 성별
    val textViewGenderUserMyPage = MutableLiveData<String>()
    // MBTI
    val textViewMbtiUserMyPage = MutableLiveData<String>()
}