package kr.co.lion.team4.mrco.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kr.co.lion.team4.mrco.databinding.FragmentMbtiBottomSheetBinding
import kr.co.lion.team4.mrco.MainActivity



class MbtiBottomSheetFragment : BottomSheetDialogFragment() {

    lateinit var fragmentMbtiBottomSheetBinding: FragmentMbtiBottomSheetBinding
    lateinit var mainActivity: MainActivity

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        fragmentMbtiBottomSheetBinding = FragmentMbtiBottomSheetBinding.inflate(layoutInflater)
        mainActivity = activity as MainActivity
        return fragmentMbtiBottomSheetBinding.root
    }


}