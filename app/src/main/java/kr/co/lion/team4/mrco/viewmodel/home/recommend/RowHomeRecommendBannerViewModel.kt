package kr.co.lion.team4.mrco.viewmodel.home.recommend

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class RowHomeRecommendBannerViewModel: ViewModel() {
    val textViewHomeRecommendBannerTitle = MutableLiveData<String>("광고, 이벤트, 가이드")
    val textViewHomeRecommendBannerContent = MutableLiveData<String>("(광고, 이벤트, 가이드 설명)")
}