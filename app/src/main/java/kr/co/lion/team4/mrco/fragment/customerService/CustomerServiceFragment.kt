package kr.co.lion.team4.mrco.fragment.customerService

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import kr.co.lion.team4.mrco.MainActivity
import kr.co.lion.team4.mrco.MainFragmentName
import kr.co.lion.team4.mrco.R
import kr.co.lion.team4.mrco.databinding.FragmentCustomerServiceBinding
import kr.co.lion.team4.mrco.viewmodel.customerService.CustomerServiceViewModel

class CustomerServiceFragment : Fragment() {

    lateinit var fragmentCustomerServiceBinding: FragmentCustomerServiceBinding
    lateinit var mainActivity: MainActivity
    lateinit var customerServiceViewModel: CustomerServiceViewModel
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        fragmentCustomerServiceBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_customer_service, container, false)
        customerServiceViewModel = CustomerServiceViewModel()
        fragmentCustomerServiceBinding.customerServiceViewModel = customerServiceViewModel
        fragmentCustomerServiceBinding.lifecycleOwner = this

        mainActivity = activity as MainActivity

        // 하단 바 안보이게
        mainActivity.removeBottomSheet()

        leaveCustomerInquiry()

        return fragmentCustomerServiceBinding.root
    }

    // '1:1 문의하기 버튼' 클릭이벤트
    fun leaveCustomerInquiry(){
        fragmentCustomerServiceBinding.apply {
            button.setOnClickListener {
                mainActivity.replaceFragment(MainFragmentName.CUSTOMER_INQUIRY_FRAGMENT, true, true, null)
            }
        }
    }
}