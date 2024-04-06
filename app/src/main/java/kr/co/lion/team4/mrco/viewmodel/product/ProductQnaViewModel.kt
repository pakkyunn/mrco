package kr.co.lion.team4.mrco.viewmodel.product

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ProductQnaViewModel: ViewModel() {
    // 상품 상세페이지 문의탭 비밀글제외 체크박스
    val product_question_secret_checkBox = MutableLiveData<Boolean>()

    fun onCheckBoxChanged() {
        var checkedCnt = 0

        if (product_question_secret_checkBox.value == true) {
            checkedCnt++
        }
    }
}