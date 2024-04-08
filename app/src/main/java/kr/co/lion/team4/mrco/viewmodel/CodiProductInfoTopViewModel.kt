package kr.co.lion.team4.mrco.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class CodiProductInfoTopViewModel: ViewModel() {
    // 코디 상품명
    var codiProductName = MutableLiveData<String>()

    // 상의 상품명
    var codiProductNameTop  = MutableLiveData<String>()
    var codiProductTypeTop = MutableLiveData<String>()
    var codiProductSizeTop = MutableLiveData<String>()
    var codiProductColorTop = MutableLiveData<String>()
    var codiProductSerialNumTop = MutableLiveData<String>()
    var codiProductPriceTop = MutableLiveData<String>()
}