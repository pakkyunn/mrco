package kr.co.lion.team4.mrco.fragment.customerService

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import kr.co.lion.team4.mrco.MainActivity
import kr.co.lion.team4.mrco.MainFragmentName
import kr.co.lion.team4.mrco.viewmodel.customerService.CustomerInquiryViewModel
import kr.co.lion.team4.mrco.R
import kr.co.lion.team4.mrco.Tools
import kr.co.lion.team4.mrco.databinding.FragmentCustomerInquiryBinding

/* (구매자) 고객센터 - 1:1 문의 작성 화면 */
class CustomerInquiryFragment : Fragment() {
    lateinit var fragmentCustomerInquiryBinding: FragmentCustomerInquiryBinding
    lateinit var customerInquiryViewModel: CustomerInquiryViewModel

    lateinit var mainActivity: MainActivity
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        fragmentCustomerInquiryBinding = DataBindingUtil.inflate(inflater,
            R.layout.fragment_customer_inquiry, container, false)
        customerInquiryViewModel = CustomerInquiryViewModel()
        fragmentCustomerInquiryBinding.customerInquiryViewModel = customerInquiryViewModel
        fragmentCustomerInquiryBinding.lifecycleOwner = this

        mainActivity = activity as MainActivity

        // 툴바, bottom navigation 설정
        settingCustomerInquiryToolbar()

        submitCustomerInquiry()

        return fragmentCustomerInquiryBinding.root
    }

    // 문의 내용 제출
    fun submitCustomerInquiry(){
        fragmentCustomerInquiryBinding.apply {
            buttonCustomerInquirySubmit.setOnClickListener {
                val validation = checkInquiryFormValid()
                if(validation){

                }
            }
        }

    }

    // 주문 번호, 첨부파일을 제외한 입력 요소 유효성 검사
    fun checkInquiryFormValid() : Boolean {
        // 문의 유형
        val inquiryType = customerInquiryViewModel.autotextviewCustomerInquiryType.value
        // 문의 제목
        val inquiryTitle = customerInquiryViewModel.textinputCustomerInquiryTitle.value
        // 문의 내용
        val inquiryContent = customerInquiryViewModel.textinputCustomerInquiryContent.value
        // 답변 방식
        val inquiryAnswerWay = customerInquiryViewModel.textviewCustomerInquiryAnswerWay.value

        if(inquiryType == null){ // 문의 유형을 선택하지 않은 경우
            Tools.showErrorDialog(mainActivity, fragmentCustomerInquiryBinding.menuCustomerInquiryType,
                "제목 입력 오류", "제목을 입력해주세요")
            return false
        }

        if(inquiryTitle == null){
            Tools.showErrorDialog(mainActivity, fragmentCustomerInquiryBinding.textinputCustomerInquiryTitle,
                "제목 입력 오류", "제목을 입력해주세요")
            return false
        }

        if(inquiryContent == null){
            Tools.showErrorDialog(mainActivity, fragmentCustomerInquiryBinding.textinputCustomerInquiryContent,
                "제목 입력 오류", "제목을 입력해주세요")
            return false
        }

        if(inquiryAnswerWay == null){
            Tools.showErrorDialog(mainActivity, fragmentCustomerInquiryBinding.menuCustomerInquiryAnswerWay,
                "제목 입력 오류", "제목을 입력해주세요")
            return false
        }

        return true
    }

    // 툴바 설정
    fun settingCustomerInquiryToolbar(){
        fragmentCustomerInquiryBinding.toolbarCustomerInquiry.apply {
            setNavigationIcon(R.drawable.arrow_back_24px)
            setNavigationOnClickListener {
                backProcess()
            }
        }
    }

    // 뒤로가기
    fun backProcess(){
        mainActivity.removeFragment(MainFragmentName.CUSTOMER_INQUIRY_FRAGMENT)
    }
}