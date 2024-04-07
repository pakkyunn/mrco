package kr.co.lion.team4.mrco.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ReviewCreateViewModel: ViewModel() {
    var reviewContentInfo = MutableLiveData<String>()

    var reviewProductImage = MutableLiveData<String>()

    var reviewCodiProductName = MutableLiveData<String>()

    var reviewCodiProductMBTI = MutableLiveData<String>()

    var reviewCodiProductPrice = MutableLiveData<Int>().toString()

    var reviewCodiProductPurchaseDate = MutableLiveData<String>()


}