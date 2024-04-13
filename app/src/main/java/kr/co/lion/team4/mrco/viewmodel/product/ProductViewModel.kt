package kr.co.lion.team4.mrco.viewmodel.product

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ProductViewModel: ViewModel() {
    // 코디네이터 이름
    val textViewCoordinatorName = MutableLiveData<String>("코디네이터이름")

    // 코디상품 MBTI
    val textIconMbti = MutableLiveData<String>("ESFJ")

    // 상품 like 개수
    val textViewLikeCount = MutableLiveData<String>("807")

    // 상품 이름
    val textViewProductName = MutableLiveData<String>("코디상품명")

    // 상품 가격
    val textViewProductPrice = MutableLiveData<String>("178,000")

//    // 상품 리뷰 개수 -> "후기\n138" 의 형식이 되어야 함
//    val tabItemReview = MutableLiveData<String>("후기\n138")
//
//    // 상품 문의 개수 -> "문의\n77" 의 형식이 되어야 함
//    val tabItemQna = MutableLiveData<String>("문의\n77")
}