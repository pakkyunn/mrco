package kr.co.lion.team4.mrco.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kr.co.lion.team4.mrco.MainActivity
import kr.co.lion.team4.mrco.R
import kr.co.lion.team4.mrco.databinding.FragmentAppNoticeBinding
import kr.co.lion.team4.mrco.viewmodel.CategoryViewModel

class AppNoticeFragment : Fragment() {

    lateinit var fragmentAppNoticeBinding: FragmentAppNoticeBinding
    lateinit var mainActivity: MainActivity
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        fragmentAppNoticeBinding = FragmentAppNoticeBinding.inflate(inflater)
        mainActivity = activity as MainActivity


        return fragmentAppNoticeBinding.root
    }
}