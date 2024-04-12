package kr.co.lion.team4.mrco.viewmodel.product

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ProductViewModel: ViewModel() {
    // 코디네이터 이름
    val textViewCoordinatorNameProduct = MutableLiveData<String>("코디네이터이름")

    // 코디상품 MBTI
    val textIconMbtiProduct = MutableLiveData<String>("ESFJ")

    // 상품 like 개수
    val textViewLikeCountProduct = MutableLiveData<String>("807")

    // 상품 이름
    val textViewProductNameProduct = MutableLiveData<String>("코디상품이름")

    // 상품 가격
    val textViewProductPriceProduct = MutableLiveData<String>("000,000원")

//    // 상품 리뷰 개수 -> "후기\n138" 의 형식이 되어야 함
//    val tabItemReviewProduct = MutableLiveData<String>("후기\n138")
//
//    // 상품 문의 개수 -> "문의\n77" 의 형식이 되어야 함
//    val tabItemQnaProduct = MutableLiveData<String>("문의\n77")
}