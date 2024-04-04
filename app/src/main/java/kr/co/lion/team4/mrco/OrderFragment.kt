package kr.co.lion.team4.mrco

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import kr.co.lion.team4.mrco.databinding.FragmentOrderBinding

/* (구매자) 상품 주문 화면 */
class OrderFragment : Fragment() {
    lateinit var fragmentOrderBinding: FragmentOrderBinding
    lateinit var orderViewModel: OrderViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        fragmentOrderBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_order, container, false)
        orderViewModel = OrderViewModel()
        fragmentOrderBinding.orderViewModel = orderViewModel
        fragmentOrderBinding.lifecycleOwner = this

        return fragmentOrderBinding.root
    }
}