package kr.co.lion.team4.mrco.viewmodel.product

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class FragmentProductDetailViewModel: ViewModel() {
    // 상품 상세페이지 코디상품명
    val product_detail_coordi_name = MutableLiveData<String>()

    // 상품 상세페이지 단일상품 분류
    val product_detail_item_category = MutableLiveData<String>()

    // 상품 상세페이지 단일상품명
    val product_detail_item_name = MutableLiveData<String>()

    // 상품 상세페이지 단일상품명
    val product_detail_item_size = MutableLiveData<String>()

    // 상품 상세페이지 단일상품 색상
    val product_detail_item_color = MutableLiveData<String>()

    // 상품 상세페이지 단일상품 시리얼넘버
    val product_detail_item_number = MutableLiveData<String>()

}