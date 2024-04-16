package kr.co.lion.team4.mrco.viewmodel.home.recommend

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class RowHomeRecommendNewCoordiViewModel: ViewModel() {
    val textViewRowHomeRecommendNewCoordiCoordinatorName = MutableLiveData<String>("코디네이터")
    val textViewRowHomeRecommendNewCoordiProductMbti = MutableLiveData<String>("INTP")
    val textViewRowHomeRecommendNewCoordiProductName = MutableLiveData<String>("세련된 분위기 데이트룩 SET")
    val textViewRowHomeRecommendNewCoordiProductDiscountPercent = MutableLiveData<String>("30%")
    val textViewRowHomeRecommendNewCoordiProductPrice = MutableLiveData<String>("178,000")
}