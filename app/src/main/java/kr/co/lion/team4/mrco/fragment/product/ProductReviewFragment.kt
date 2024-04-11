package kr.co.lion.team4.mrco.fragment.product

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import kr.co.lion.team4.mrco.MainActivity
import kr.co.lion.team4.mrco.R
import kr.co.lion.team4.mrco.databinding.FragmentProductReviewBinding
import kr.co.lion.team4.mrco.viewmodel.product.ProductReviewViewModel

class ProductReviewFragment : Fragment() {
    lateinit var fragmentProductReviewBinding: FragmentProductReviewBinding
    lateinit var mainActivity: MainActivity
    lateinit var productReviewViewModel: ProductReviewViewModel
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        fragmentProductReviewBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_product_review, container, false)
        productReviewViewModel = ProductReviewViewModel()
        fragmentProductReviewBinding.productReviewViewModel = productReviewViewModel
        fragmentProductReviewBinding.lifecycleOwner = this

        mainActivity = activity as MainActivity

        return fragmentProductReviewBinding.root
    }
}