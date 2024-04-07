package kr.co.lion.team4.mrco.ViewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class CodiProductInfoShoesViewModel: ViewModel() {
    // 코디 상품명
    var codiProductName = MutableLiveData<String>()

    // 신발 상품명
    var codiProductNameShoes  = MutableLiveData<String>()
    var codiProductTypeShoes = MutableLiveData<String>()
    var codiProductSizeShoes = MutableLiveData<String>()
    var codiProductColorShoes = MutableLiveData<String>()
    var codiProductSerialNumShoes = MutableLiveData<String>()
    var codiProductPriceShoes = MutableLiveData<String>()
}