package kr.co.lion.team4.mrco.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kr.co.lion.mrco_01.MainActivity
import kr.co.lion.mrco_01.R
import kr.co.lion.mrco_01.databinding.FragmentModifyCoordinatorBinding
import kr.co.lion.mrco_01.databinding.FragmentModifyUserBinding


class ModifyCoordinatorFragment : Fragment() {

    lateinit var fragmentModifyCoordinatorBinding: FragmentModifyCoordinatorBinding
    lateinit var mainActivity: MainActivity

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        fragmentModifyCoordinatorBinding = FragmentModifyCoordinatorBinding.inflate(inflater)
        mainActivity = activity as MainActivity

        // 테스트용 바텀시트 노출 버튼
        fragmentModifyCoordinatorBinding.buttonModifyCoordinatorSubmit.setOnClickListener {
            showPwChangeBottomSheet()
        }

        return fragmentModifyCoordinatorBinding.root
    }

    // 댓글을 보여불 BottomSheet를 띄워준다.
    fun showPwChangeBottomSheet(){
        val pwChangeBottomSheetFragment = PwChangeBottomSheetFragment()
        pwChangeBottomSheetFragment.show(mainActivity.supportFragmentManager, "PwChangeBottomSheet")
    }



}