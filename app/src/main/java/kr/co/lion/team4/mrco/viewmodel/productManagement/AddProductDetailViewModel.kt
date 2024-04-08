package kr.co.lion.team4.mrco.viewmodel.productManagement

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class AddProductDetailViewModel : ViewModel() {
    // 상품 옵션 명
    val textviewAddProductDetailName = MutableLiveData<String>()
    // 상품 옵션 사이즈, 재고 등
    val textviewAddProductDetailOption = MutableLiveData<String>()
}