package kr.co.lion.team4.mrco.viewmodel.home.recommend

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class RowHomeRecommendNewCoordiViewModel: ViewModel() {
    val textViewRowHomeRecommendNewCoordiProductMbti = MutableLiveData<String>("INTP")
    val textViewRowHomeRecommendNewCoordiProductName = MutableLiveData<String>("홍길동 코디이름")
    val textViewRowHomeRecommendNewCoordiProductDiscountPercent = MutableLiveData<String>("30%")
    val textViewRowHomeRecommendNewCoordiProductPrice = MutableLiveData<String>("178,000")
}