package kr.co.lion.team4.mrco.viewmodel.product

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class RowProductDetailViewModel: ViewModel() {
    // 상품 상세페이지 코디상품명
    val textViewCoordiName = MutableLiveData<String>()

    // 상품 상세페이지 단일상품명
    val textViewCoordiItemName = MutableLiveData<String>()

    // 상품 상세페이지 단일상품 색상
    val textViewCoordiItemColor = MutableLiveData<String>()
}