package kr.co.lion.team4.mrco

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import kr.co.lion.team4.mrco.databinding.FragmentCartBinding

/* (구매자) 장바구니 화면 */
class CartFragment : Fragment() {
    lateinit var fragmentCartBinding: FragmentCartBinding
    lateinit var cartViewModel : CartViewModel

    lateinit var mainActivity: MainActivity
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        fragmentCartBinding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_cart, container, false)
        cartViewModel = CartViewModel()
        fragmentCartBinding.cartViewModel = cartViewModel
        fragmentCartBinding.lifecycleOwner = this

        mainActivity = activity as MainActivity

        return fragmentCartBinding.root
    }

}