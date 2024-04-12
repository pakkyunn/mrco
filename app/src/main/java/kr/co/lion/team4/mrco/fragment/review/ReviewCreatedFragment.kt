package kr.co.lion.team4.mrco.fragment.review

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kr.co.lion.team4.mrco.R
import kr.co.lion.team4.mrco.databinding.FragmentReviewCreatedBinding

class ReviewCreatedFragment : Fragment() {

    lateinit var binding: FragmentReviewCreatedBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        binding = FragmentReviewCreatedBinding.inflate(inflater)

        return binding.root
    }
}