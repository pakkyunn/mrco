package kr.co.lion.team4.mrco.fragment.customerService

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import kr.co.lion.team4.mrco.viewmodel.customerService.CustomerInquiryViewModel
import kr.co.lion.team4.mrco.R
import kr.co.lion.team4.mrco.databinding.FragmentCustomerInquiryBinding

/* (구매자) 고객센터 - 1:1 문의 작성 화면 */
class CustomerInquiryFragment : Fragment() {
    lateinit var fragmentCustomerInquiryBinding: FragmentCustomerInquiryBinding
    lateinit var customerInquiryViewModel: CustomerInquiryViewModel
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        fragmentCustomerInquiryBinding = DataBindingUtil.inflate(inflater,
            R.layout.fragment_customer_inquiry, container, false)
        customerInquiryViewModel = CustomerInquiryViewModel()
        fragmentCustomerInquiryBinding.customerInquiryViewModel = customerInquiryViewModel
        fragmentCustomerInquiryBinding.lifecycleOwner = this

        return fragmentCustomerInquiryBinding.root
    }
}