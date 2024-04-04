package kr.co.lion.team4.mrco.viewmodel.salesManagement

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class RowSalesManagementViewModel: ViewModel() {
    val textViewRowSalesManagementProductState = MutableLiveData<String>("구매 확정")
    val textViewRowSalesManagementProductName = MutableLiveData<String>("호불호 없는 출근룩 코디 SET")
    val textViewRowSalesManagementProductsSize = MutableLiveData<String>("상의 · M / 베스트 · M / 바지 · 28 / 가방 · free / 신발 · 230mm")
    val textViewRowSalesManagementProductPrice = MutableLiveData<String>("100,000원")
}