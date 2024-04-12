package kr.co.lion.team4.mrco.viewmodel.productManagement

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

/* 코디 상품 등록 Dialog의 ViewModel*/
class DialogAddProductViewModel : ViewModel() {
    // 코디 상품명
    val edittextDialogAddProductName = MutableLiveData<String>()
    // 코디 사이즈
    val edittextDialogAddProductSize = MutableLiveData<String>()
    // 코디 재고
    val edittextDialogAddProductStock = MutableLiveData<String>()
    // 코디 분류
    val edittextDialogAddProductType = MutableLiveData<String>()
    // 코디 색상
    val edittextDialogAddProductColor = MutableLiveData<String>()
}