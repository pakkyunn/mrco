package kr.co.lion.team4.mrco

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ProductQnaViewModel : ViewModel() {
    // 문의하는 상품의 코디네이터명
    val textview_productqna_coordinator = MutableLiveData<String>()
    // 문의하는 상품의 상품명
    val textview_productqna_coordi_name = MutableLiveData<String>()
    // 문의하는 상품의 가격
    val textview_productqna_price = MutableLiveData<String>()

    // 문의 내용
    val edittext_productqna_content = MutableLiveData<String>()
    val checkbox_productqna_secret = MutableLiveData<Boolean>()

}