package kr.co.lion.team4.mrco.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kr.co.lion.team4.mrco.ProductColor

class CodiProductInfoTopViewModel: ViewModel() {
    // 코디 상품명
    val codiProductNameTop = MutableLiveData("나는 아이유다!")

    // 상의 상품명
    val productNameTop  = MutableLiveData("나는 아이유 상의!")
    val productTypeTop = MutableLiveData("상의1")
    val productSizeTop = MutableLiveData("M")
    val productColorTop = MutableLiveData("WHITE")
    val productSerialNumTop = MutableLiveData("esdf-12345678")
    val productPriceTop = MutableLiveData("₩ 70,000")
}