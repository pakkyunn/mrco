package kr.co.lion.team4.mrco.Fragment.review

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kr.co.lion.team4.mrco.R
import kr.co.lion.team4.mrco.databinding.ActivityMainBinding
import kr.co.lion.team4.mrco.databinding.FragmentProductReviewBinding

class ProductReviewFragment : Fragment() {
    lateinit var fragmentProductReviewBinding: FragmentProductReviewBinding
    lateinit var activityMainBinding: ActivityMainBinding
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        fragmentProductReviewBinding = FragmentProductReviewBinding.inflate(inflater)
        activityMainBinding = activity as ActivityMainBinding

        return fragmentProductReviewBinding.root
    }

    fun setEvent(){
        fragmentProductReviewBinding.apply {

        }
    }
}