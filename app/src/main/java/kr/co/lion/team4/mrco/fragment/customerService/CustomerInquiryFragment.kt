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

        return fragmentCustomerInquiryBinding.root
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