package kr.co.lion.team4.mrco.viewmodel.productQna

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class RegisterQnaAnswerViewModel : ViewModel() {
    // 문의한 코디 상품의 상품명
    val textviewRegisterAnswerCoordiName = MutableLiveData<String>()
    // 문의한 코디 상품의 상품번호
    val textviewRegisterAnswerCoordiIndex = MutableLiveData<String>()

    // 문의글 작성자 아이디와 작성일
    val textviewRegisterAnswerWriter = MutableLiveData<String>()
    // 문의 내용
    val textvieRegisterAnswerContent = MutableLiveData<String>()

    // 답변 내용
    val edittextRegisterAnswerContent = MutableLiveData<String>()
}