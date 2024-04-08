package kr.co.lion.team4.mrco.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class CodiProductInfoBottomViewModel: ViewModel() {
    // 코디 상품명
    var codiProductName = MutableLiveData<String>()

    // 하의 상품명
    var codiProductNameBottom  = MutableLiveData<String>()
    var codiProductTypeBottom = MutableLiveData<String>()
    var codiProductSizeBottom = MutableLiveData<String>()
    var codiProductColorBottom = MutableLiveData<String>()
    var codiProductSerialNumBottom = MutableLiveData<String>()
    var codiProductPriceBottom = MutableLiveData<String>()
}