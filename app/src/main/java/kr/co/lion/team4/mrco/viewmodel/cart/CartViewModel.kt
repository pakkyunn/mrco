package kr.co.lion.team4.mrco.viewmodel.cart

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

// (구매자) 장바구니 화면 View Model
class CartViewModel : ViewModel() {
    // 전체선택 CheckBox
    // to do  Binding Adapter
    val checkboxCartSelectAll = MutableLiveData<Boolean>()
    // 총 상품 금액
    val textviewCartSubtotal = MutableLiveData<String>()
    // 총 배송비
    val textviewCartShippingFee = MutableLiveData<String>()
    // 장바구니에 담긴 상품 중 선택한 금액들의 총 주문 금액 (상품금액과 배송비의 합계)
    val textviewCartTotal = MutableLiveData<String>()

    // 장바구니에 담긴 아이템들의 checkBox checked 상태 리스트 (CartItemViewModel)
    val cartItemCheckBoxList = mutableListOf<MutableLiveData<Boolean>>()

    // '전체 선택' 체크박스 설정
    fun onCheckBoxCartSelectAllChanged(){
        settingCartItemCheckedState(checkboxCartSelectAll.value!!)
    }

    fun settingCartItemCheckedState(checked: Boolean){
        cartItemCheckBoxList.forEach {
            // 장바구니에 담긴 아이템들 선택 상태를 "전체 선택 체크박스"의 체크 상태와 동일하게 바꿔준다.
            it.value = checked
        }
    }

    // 각 체크박스를 눌렀을 때
    fun onItemCheckBoxChanged(){
        // checked == true 인 체크박스 수
        var numOfChecked = 0

        cartItemCheckBoxList.forEach{
            if(it.value == true){
                numOfChecked++
            }
        }

        // 장바구니 아이템이 하나라도 체크 해제되어 있다면
        if(numOfChecked < cartItemCheckBoxList.size){
            checkboxCartSelectAll.value = false // 전체선택 체크박스 체크 해제
        }

        // 장바구니 아이템이 전부 체크되었다면
        if(numOfChecked == cartItemCheckBoxList.size){
            checkboxCartSelectAll.value = true // 전체선택 체크박스 체크
        }
    }
}