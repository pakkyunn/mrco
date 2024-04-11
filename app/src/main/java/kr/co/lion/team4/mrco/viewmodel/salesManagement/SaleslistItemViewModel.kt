package kr.co.lion.team4.mrco.viewmodel.salesManagement

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class SaleslistItemViewModel : ViewModel() {
    // 판매내역 - 판매 상품명
    val textviewSaleslistProduct = MutableLiveData<String>()
    // 판매내역 - 주문번호
    val textviewSaleslistOrderNumber = MutableLiveData<String>()
    // 판매내역 - 주문 가격
    val textviewSaleslistPrice = MutableLiveData<String>()
}