package kr.co.lion.team4.mrco.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class IndividualProductManagementViewModel: ViewModel() {
    var productName = MutableLiveData<String>("상품이름")

    var productSerialNum = MutableLiveData<String>("12345678")
    var productStockNum = MutableLiveData<String>("1234")
//    var productStockNum = MutableLiveData<Int>(1234).toString()
}