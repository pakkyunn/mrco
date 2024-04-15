package kr.co.lion.team4.mrco.fragment.product.purchase

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import kr.co.lion.team4.mrco.MainActivity
import kr.co.lion.team4.mrco.R
import kr.co.lion.team4.mrco.databinding.FragmentReviewImageMoreBinding

class ReviewImageMoreFragment : Fragment() {

    lateinit var fragmentReviewImageMoreBinding: FragmentReviewImageMoreBinding
    lateinit var mainActivity: MainActivity
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        fragmentReviewImageMoreBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_review_image_more, container, false)
        fragmentReviewImageMoreBinding.lifecycleOwner = this

        mainActivity = activity as MainActivity


        return fragmentReviewImageMoreBinding.root
    }


}