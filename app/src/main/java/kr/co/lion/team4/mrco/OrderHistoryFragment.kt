package kr.co.lion.team4.mrco

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import kr.co.lion.team4.mrco.databinding.FragmentOrderHistoryBinding

/* (구매자) 주문 내역 화면 */
class OrderHistoryFragment : Fragment() {
    lateinit var fragmentOrderHistoryBinding: FragmentOrderHistoryBinding
    lateinit var orderHistoryViewModel : OrderHistoryViewModel

    lateinit var mainActivity: MainActivity
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        fragmentOrderHistoryBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_order_history, container, false)
        orderHistoryViewModel = OrderHistoryViewModel()
        fragmentOrderHistoryBinding.orderHistoryViewModel = orderHistoryViewModel
        fragmentOrderHistoryBinding.lifecycleOwner = this

        mainActivity = activity as MainActivity

        return fragmentOrderHistoryBinding.root
    }


}