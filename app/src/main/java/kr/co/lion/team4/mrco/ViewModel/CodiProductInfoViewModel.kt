package kr.co.lion.team4.mrco.ViewModel

import android.graphics.drawable.Drawable
import android.media.Image
import android.widget.ImageView
import android.widget.TextView
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

// row_codi_product.xml에 들어갈 내용들
class CodiProductInfoViewModel:ViewModel() {
    // 코디 상품명
    var codiProductName = MutableLiveData<String>()

    // Top, Bottom, Shoes, Accessory 상품명
    // productName <- codiProductTop(CodiProductInfoTopViewModel)
    var productName = MutableLiveData<String>()

    // 일단 보류
    var codiProductImage = MutableLiveData<Drawable>()


    var codiProductType = MutableLiveData<String>() // 상의, 하의, 신발, 악세서리 종류 표기

    var codiProductIndex = MutableLiveData<Int>().toString() // 상의1, 상의2, ... 표기

    var codiProductSize = MutableLiveData<String>()
    var codiProductColor = MutableLiveData<String>()
    var codiProductSerialNum = MutableLiveData<String>()
    var codiProductPrice = MutableLiveData<String>()
}