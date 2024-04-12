package kr.co.lion.team4.mrco.fragment.product.buy

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import kr.co.lion.team4.mrco.MainActivity
import kr.co.lion.team4.mrco.R
import kr.co.lion.team4.mrco.databinding.FragmentProductQnaBinding
import kr.co.lion.team4.mrco.viewmodel.product.ProductQnaViewModel

class ProductQnaFragment : Fragment() {
    lateinit var fragmentProductQnaBinding: FragmentProductQnaBinding
    lateinit var mainActivity: MainActivity
    lateinit var productQnaViewModel: ProductQnaViewModel
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        fragmentProductQnaBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_product_qna, container, false)
        productQnaViewModel = ProductQnaViewModel()
        fragmentProductQnaBinding.productQnaViewModel = productQnaViewModel
        fragmentProductQnaBinding.lifecycleOwner = this

        mainActivity = activity as MainActivity

        return fragmentProductQnaBinding.root
    }
}