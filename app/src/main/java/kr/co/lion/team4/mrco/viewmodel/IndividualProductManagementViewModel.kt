package kr.co.lion.team4.mrco.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class IndividualProductManagementViewModel: ViewModel() {
    var productName = MutableLiveData<String>("상품명")

    var productSerialNum = MutableLiveData<String>("상품 S/N")
    var productStockNum = MutableLiveData<String>("재고 : 36개")
    // var productStockNum = MutableLiveData<Int>(1234).toString()
}