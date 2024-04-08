package kr.co.lion.team4.mrco.fragment.product

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import kr.co.lion.team4.mrco.MainActivity
import kr.co.lion.team4.mrco.R
import kr.co.lion.team4.mrco.databinding.FragmentProductDetailBinding
import kr.co.lion.team4.mrco.viewmodel.product.ProductDetailViewModel

class ProductDetailFragment : Fragment() {
    lateinit var fragmentProductDetailBinding: FragmentProductDetailBinding
    lateinit var mainActivity: MainActivity
    lateinit var productDetailViewModel: ProductDetailViewModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        fragmentProductDetailBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_product_detail, container, false)
        productDetailViewModel = ProductDetailViewModel()
        fragmentProductDetailBinding.productDetailViewModel = productDetailViewModel
        fragmentProductDetailBinding.lifecycleOwner = this

        mainActivity = activity as MainActivity

        return fragmentProductDetailBinding.root
    }
}