package kr.co.lion.team4.mrco.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class WriteReviewViewModel: ViewModel() {
    // 상단
    val textViewWriteReviewCoordinatorName = MutableLiveData<String>("아이유(코디네이터 이름)")
    val textViewWriteReviewProductName = MutableLiveData<String>("호불호 없는 출근룩 코디 SET (코디상품명)")
    
    // 사용자 입력 칸
    // 성별
    val toggleWriteReviewGender = MutableLiveData<Int>()
    // 키
    val textFieldWriteReviewHeight = MutableLiveData<String>()
    // 체중
    val textFieldWriteReviewWeight = MutableLiveData<String>()
    // 기본 신체 정보로 등록(체크)
    val checkBoxWriteReviewBasic = MutableLiveData<Boolean>()
    // 내용
    val textFieldWriteReviewText = MutableLiveData<String>()
}