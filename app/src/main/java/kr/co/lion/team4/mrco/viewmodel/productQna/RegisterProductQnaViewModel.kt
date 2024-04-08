package kr.co.lion.team4.mrco.viewmodel.productQna

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class RegisterProductQnaViewModel : ViewModel() {
    // 문의하는 상품의 코디네이터명
    val textviewProductqnaCoordinator = MutableLiveData<String>()
    // 문의하는 상품의 상품명
    val textviewProductqnaCoordiName = MutableLiveData<String>()
    // 문의하는 상품의 가격
    val textviewProductqnaPrice = MutableLiveData<String>()

    // 문의 내용
    val edittextProductqnaContent = MutableLiveData<String>()
    val checkboxProductqnaSecret = MutableLiveData<Boolean>()

}