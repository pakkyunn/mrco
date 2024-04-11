package kr.co.lion.team4.mrco.viewmodel.mypage

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class CoordinatorMyPageViewModel: ViewModel() {
    // 코디네이터 이름
    val textViewNameCoordinatorMyPage = MutableLiveData<String>()
    // 성별
    val textViewGenderCoordinatorMyPage = MutableLiveData<String>()
    // MBTI
    val textViewMbtiCoordinatorMyPage = MutableLiveData<String>()
    // 소개글
    val textViewIntroductionCoordinatorMyPage = MutableLiveData<String>()
}