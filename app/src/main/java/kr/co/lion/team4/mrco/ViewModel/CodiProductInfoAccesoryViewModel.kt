package kr.co.lion.team4.mrco.ViewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class CodiProductInfoAccesoryViewModel: ViewModel() {// 코디 상품명
    // 코디 상품명
    var codiProductName = MutableLiveData<String>()

    // 악세서리 상품명
    var codiProductNameAccessory  = MutableLiveData<String>()
    var codiProductTypeAccessory = MutableLiveData<String>()
    var codiProductSizeAccessory = MutableLiveData<String>()
    var codiProductColorAccessory = MutableLiveData<String>()
    var codiProductSerialNumAccessory = MutableLiveData<String>()
    var codiProductPriceAccessory = MutableLiveData<String>()
}