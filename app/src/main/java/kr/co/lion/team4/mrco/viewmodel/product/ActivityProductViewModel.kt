package kr.co.lion.team4.mrco.viewmodel.product

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ActivityProductViewModel: ViewModel() {
    // 코디네이터 이름
    val product_stylist_text = MutableLiveData<String>()

    // 상품 like 개수
    val product_like_count = MutableLiveData<Int>()

    // 상품 이름
    val product_name = MutableLiveData<String>()

    // 상품 가격
    val product_price = MutableLiveData<Int>()

    // 상품 리뷰 개수 -> "후기\n138" 의 형식이 되어야 함
    val product_tabItem_review = MutableLiveData<String>()

    // 상품 문의 개수 -> "문의\n77" 의 형식이 되어야 함
    val product_tabItem_question = MutableLiveData<String>()
}