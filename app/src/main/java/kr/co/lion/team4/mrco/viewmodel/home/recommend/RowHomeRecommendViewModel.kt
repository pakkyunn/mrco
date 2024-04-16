package kr.co.lion.team4.mrco.viewmodel.home.recommend

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class RowHomeRecommendViewModel: ViewModel() {
    val textViewRowHomeRecommendCoordinatorName = MutableLiveData<String>("코디네이터")
    val textViewRowHomeRecommendProductMbti = MutableLiveData<String>("ISFP")
    val textViewRowHomeRecommendProductName = MutableLiveData<String>("세련된 분위기 데이트룩 SET")
    val textViewRowHomeRecommendProductDiscountPercent = MutableLiveData<String>("10%")
    val textViewRowHomeRecommendProductPrice = MutableLiveData<String>("178,000")
}