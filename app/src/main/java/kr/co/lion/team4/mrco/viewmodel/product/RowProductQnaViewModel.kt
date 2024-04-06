package kr.co.lion.team4.mrco.viewmodel.product

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import java.text.SimpleDateFormat
import java.util.Date

class RowProductQnaViewModel: ViewModel() {
    // 문의 답변 상태
    val item_product_qna_status_text = MutableLiveData<String>()

    // 문의 내용
    val item_product_question_content = MutableLiveData<String>()

    // 문의 작성자 닉네임
    val item_product_question_owner = MutableLiveData<String>()


    val simpleDateFormat = SimpleDateFormat("yyyy.MM.dd")
    val question_date = simpleDateFormat.format(Date())

    // 문의 작성 날짜
    val item_product_question_date = MutableLiveData<String>(question_date)

    // 답변 내용
    val item_product_answer_content = MutableLiveData<String>()

    // 답변 작성자(코디네이터명)
    val item_product_answer_owner = MutableLiveData<String>()


}