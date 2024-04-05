package kr.co.lion.team4.mrco

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class CartItemViewModel : ViewModel() {
    // 체크박스
    val checkboxCartItemSelect = MutableLiveData<Boolean>()
    // 코디네이터 명
    val textviewCartItemCoordinator = MutableLiveData<String>()
    // 코디 상품명
    val textviewCartItemCoordiName = MutableLiveData<String>()
    // 코디 상품 옵션 + 수량
    val textviewCartItemOption = MutableLiveData<String>()
    // 상품 가격
    val textviewCartItemPrice = MutableLiveData<String>()
}