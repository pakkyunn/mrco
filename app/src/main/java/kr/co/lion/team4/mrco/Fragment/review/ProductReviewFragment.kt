package kr.co.lion.team4.mrco.Fragment.review

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import kr.co.lion.team4.mrco.MainActivity
import kr.co.lion.team4.mrco.R
import kr.co.lion.team4.mrco.databinding.ActivityMainBinding
import kr.co.lion.team4.mrco.databinding.FragmentProductReviewBinding
import kr.co.lion.team4.mrco.viewmodel.ProductReviewViewModel

class ProductReviewFragment : Fragment() {
    private lateinit var binding:FragmentProductReviewBinding
    private lateinit var viewModel: ProductReviewViewModel
    private lateinit var mainActivity: MainActivity

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_product_review, container, false)
        viewModel = ViewModelProvider(this).get(ProductReviewViewModel::class.java)
        mainActivity = activity as MainActivity
        binding.lifecycleOwner = this


        return binding.root
    }
}