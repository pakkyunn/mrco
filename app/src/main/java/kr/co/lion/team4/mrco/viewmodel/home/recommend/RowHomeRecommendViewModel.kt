package kr.co.lion.team4.mrco.viewmodel.home.recommend

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class RowHomeRecommendViewModel: ViewModel() {
    val textViewRowHomeRecommendProductMbti = MutableLiveData<String>("ISFP")
    val textViewRowHomeRecommendProductName = MutableLiveData<String>("홍길동 코디이름")
    val textViewRowHomeRecommendProductDiscountPercent = MutableLiveData<String>("30%")
    val textViewRowHomeRecommendProductPrice = MutableLiveData<String>("178,000")
}