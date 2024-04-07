package kr.co.lion.team4.mrco.viewmodel

import android.graphics.drawable.Drawable
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ReviewViewModel: ViewModel() {

    var reviewProductImage = MutableLiveData<Drawable>()

    var reviewDate = MutableLiveData<String>() // 작성일자

    var reviewProductName = MutableLiveData<String>() // 코디 이름

    var reviewProductMBTI = MutableLiveData<String>() // 스타일링 / MBTI

    var reviewStar = MutableLiveData<Int>() // 별 갯수

    var reviewProductSize = MutableLiveData<String>()

    var reviewProductBright = MutableLiveData<String>()

    var reviewProductColor = MutableLiveData<String>()

    var reviewProductPack = MutableLiveData<String>()

    var reviewProductThickness = MutableLiveData<String>()

    var reviewProductDelivery = MutableLiveData<String>()

    var reviewContent = MutableLiveData<String>()


}