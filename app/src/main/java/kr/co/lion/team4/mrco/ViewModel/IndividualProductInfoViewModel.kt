package kr.co.lion.team4.mrco.ViewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class IndividualProductInfoViewModel: ViewModel() {

    var individualProductName = MutableLiveData<String>()

    var individualProductSerialNum = MutableLiveData<String>()

    var individualProductStockNum = MutableLiveData<Int>().toString()

    var individualProductSalePrice = MutableLiveData<Int>().toString()

    var individualProductPurchasePrice = MutableLiveData<Int>().toString()

    var individualProductProfit = MutableLiveData<Int>().toString()

    var individualProductNetProfit = MutableLiveData<Int>().toString()

    var individualProductTax = MutableLiveData<Int>().toString()


}