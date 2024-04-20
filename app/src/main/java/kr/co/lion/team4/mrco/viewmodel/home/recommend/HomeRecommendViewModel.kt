package kr.co.lion.team4.mrco.viewmodel.home.recommend

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import java.text.SimpleDateFormat
import java.util.Date

class HomeRecommendViewModel: ViewModel() {
    val simpleDateFormat = SimpleDateFormat("yyyy.MM.dd 기준")
    val homeRecommendDate = simpleDateFormat.format(Date())

    val textViewHomeRecommendContent1 = MutableLiveData<String>("홍길동(ENFP")
    val textViewHomeRecommendDate = MutableLiveData<String>(homeRecommendDate)
}