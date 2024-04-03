package kr.co.lion.team4.mrco.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kr.co.lion.mrco_01.MainActivity
import kr.co.lion.mrco_01.databinding.FragmentPwChangeBottomSheetBinding


class PwChangeBottomSheetFragment : BottomSheetDialogFragment() {

    lateinit var fragmentPwChangeBottomSheetBinding: FragmentPwChangeBottomSheetBinding
    lateinit var mainActivity: MainActivity

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        fragmentPwChangeBottomSheetBinding = FragmentPwChangeBottomSheetBinding.inflate(layoutInflater)
        mainActivity = activity as MainActivity
        return fragmentPwChangeBottomSheetBinding.root
    }


}