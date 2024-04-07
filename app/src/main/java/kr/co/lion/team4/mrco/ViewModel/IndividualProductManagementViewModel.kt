package kr.co.lion.team4.mrco.ViewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class IndividualProductManagementViewModel: ViewModel() {
    var productName = MutableLiveData<String>()

    var productSerialNum = MutableLiveData<String>()
    var productStockNum = MutableLiveData<Int>().toString()
}