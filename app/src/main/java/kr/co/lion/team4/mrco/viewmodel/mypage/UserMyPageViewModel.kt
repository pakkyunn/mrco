package kr.co.lion.team4.mrco.viewmodel.mypage

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class UserMyPageViewModel: ViewModel() {
    // 사용자 닉네임
    val textViewNicknameUserMyPage = MutableLiveData<String>("Nickname")
    // 성별
    val textViewGenderUserMyPage = MutableLiveData<String>("남성")
    // MBTI
    val textViewMbtiUserMyPage = MutableLiveData<String>("MBTI")
}