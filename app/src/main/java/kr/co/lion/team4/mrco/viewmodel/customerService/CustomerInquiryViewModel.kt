package kr.co.lion.team4.mrco.viewmodel.customerService

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class CustomerInquiryViewModel : ViewModel() {
    // 문의 유형
    val autotextviewCustomerInquiryType = MutableLiveData<String>()
    // 주문 번호
    val textinputCustomerInquiryOrderNumber = MutableLiveData<String>()
    // 문의 제목
    val textinputCustomerInquiryTitle = MutableLiveData<String>()
    // 문의 내용
    val textinputCustomerInquiryContent = MutableLiveData<String>()
    // 첨부파일명
    val textviewCustomerInquiryFilename = MutableLiveData<String>()
    // 답변 방식
    val textviewCustomerInquiryAnswerWay = MutableLiveData<String>()
}