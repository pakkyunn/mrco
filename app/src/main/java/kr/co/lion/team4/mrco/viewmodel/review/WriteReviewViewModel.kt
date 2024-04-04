package kr.co.lion.team4.mrco.viewmodel.review

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class WriteReviewViewModel: ViewModel() {
    // 상단
    val textViewWriteReviewCoordinatorName = MutableLiveData<String>("아이유(코디네이터 이름)")
    val textViewWriteReviewProductName = MutableLiveData<String>("호불호 없는 출근룩 코디 SET (코디상품명)")

    val textViewWriteReviewProductItem1 = MutableLiveData<String>("셔츠 · M")
    val textViewWriteReviewProductItem2 = MutableLiveData<String>("베스트 · M")
    val textViewWriteReviewProductItem3 = MutableLiveData<String>("바지 · 28")
    val textViewWriteReviewProductItem4 = MutableLiveData<String>("가방 · Free")
    val textViewWriteReviewProductItem5 = MutableLiveData<String>("신발 · 230mm")
    val textViewWriteReviewProductItem6 = MutableLiveData<String>("")
    
    // 사용자 입력 칸
    // - 상품 관련 (일단 Float, Int로 해두었는데 이게 맞는지 모르겠다...)
    // 별점
    val ratingBarWriteReview = MutableLiveData<Float>()
    // 내 MBTI와 코디 매칭
    val toggleWriteReviewMBTI = MutableLiveData<Int>()
    // 코디의 밝기
    val toggleWriteReviewBrightness = MutableLiveData<Int>()
    // 코디의 사이즈
    val toggleWriteReviewSize = MutableLiveData<Int>()

    // - 신체 정보
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