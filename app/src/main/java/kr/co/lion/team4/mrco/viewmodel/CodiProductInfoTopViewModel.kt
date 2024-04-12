package kr.co.lion.team4.mrco.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class CodiProductInfoTopViewModel: ViewModel() {
    // 코디 상품명
    var codiProductNameTop = MutableLiveData<String>()

    // 상의 상품명
    var productNameTop  = MutableLiveData<String>()
    var productTypeTop = MutableLiveData<String>()
    var productSizeTop = MutableLiveData<String>()
    var productColorTop = MutableLiveData<String>()
    var productSerialNumTop = MutableLiveData<String>()
    var productPriceTop = MutableLiveData<String>()
}