package kr.co.lion.team4.mrco.Fragment.review

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import kr.co.lion.team4.mrco.Activity.MainActivity
import kr.co.lion.team4.mrco.R
import kr.co.lion.team4.mrco.databinding.FragmentCreatedReviewBinding
import kr.co.lion.team4.mrco.viewmodel.CreateReviewViewModel

class CreateReviewFragment : Fragment() {
    private lateinit var binding: FragmentCreatedReviewBinding
    private lateinit var viewModel: CreateReviewViewModel
    private lateinit var mainActivity: MainActivity
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_created_review, container, false)
        viewModel = ViewModelProvider(this).get(CreateReviewViewModel::class.java)
        mainActivity = activity as MainActivity


        return binding.root
    }
}