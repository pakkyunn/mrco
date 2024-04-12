package kr.co.lion.team4.mrco.viewmodel

import android.graphics.drawable.Drawable
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class CodiProductManagementViewModel: ViewModel() {
    var codiProductImage = MutableLiveData<Drawable>()

    var codiProductName = MutableLiveData<String>("상품이름")
    var codiProductSerialNum = MutableLiveData<String>("12345678")
}