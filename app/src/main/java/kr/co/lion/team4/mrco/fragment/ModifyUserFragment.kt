package kr.co.lion.team4.mrco.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kr.co.lion.mrco_01.MainActivity
import kr.co.lion.mrco_01.R
import kr.co.lion.mrco_01.databinding.FragmentModifyUserBinding


class ModifyUserFragment : Fragment() {

    lateinit var fragmentModifyUserBinding: FragmentModifyUserBinding
    lateinit var mainActivity: MainActivity

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        fragmentModifyUserBinding = FragmentModifyUserBinding.inflate(inflater)
        mainActivity = activity as MainActivity

        // 테스트용 바텀시트 노출 버튼
        fragmentModifyUserBinding.buttonModifyUserSubmit.setOnClickListener {
            showMbtiBottomSheet()
        }

        return fragmentModifyUserBinding.root
    }

    // 댓글을 보여불 BottomSheet를 띄워준다.
    fun showMbtiBottomSheet(){
        val mbtiBottomSheetFragment = MbtiBottomSheetFragment()
        mbtiBottomSheetFragment.show(mainActivity.supportFragmentManager, "MbtiBottomSheet")
    }



}