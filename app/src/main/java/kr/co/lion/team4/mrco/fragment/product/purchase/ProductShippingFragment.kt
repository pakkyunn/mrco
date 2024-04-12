package kr.co.lion.team4.mrco.fragment.product.purchase

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import kr.co.lion.team4.mrco.MainActivity
import kr.co.lion.team4.mrco.R
import kr.co.lion.team4.mrco.databinding.FragmentProductShippingBinding

class ProductShippingFragment : Fragment() {
    lateinit var fragmentProductShippingBinding: FragmentProductShippingBinding
    lateinit var mainActivity: MainActivity
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        fragmentProductShippingBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_product_shipping, container, false)
        fragmentProductShippingBinding.lifecycleOwner = this

        mainActivity = activity as MainActivity

        return fragmentProductShippingBinding.root
    }
}