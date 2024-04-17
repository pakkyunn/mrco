package kr.co.lion.team4.mrco.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import kr.co.lion.team4.mrco.databinding.FragmentModifyUserBinding
import kr.co.lion.team4.mrco.MainActivity
import kr.co.lion.team4.mrco.MainFragmentName
import kr.co.lion.team4.mrco.R
import kr.co.lion.team4.mrco.viewmodel.ModifyUserViewModel


class ModifyUserFragment : Fragment() {

    lateinit var fragmentModifyUserBinding: FragmentModifyUserBinding
    lateinit var modifyUserViewModel : ModifyUserViewModel
    lateinit var mainActivity: MainActivity

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment

        fragmentModifyUserBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_modify_user, container, false)
        modifyUserViewModel = ModifyUserViewModel()
        fragmentModifyUserBinding.modifyUserViewModel = modifyUserViewModel
        fragmentModifyUserBinding.lifecycleOwner = this

        mainActivity = activity as MainActivity

        settingToolbar()

        // 테스트용 바텀시트 노출 버튼
        fragmentModifyUserBinding.textFieldModifyUserUserMBTI.setOnClickListener {
            showMbtiBottomSheet()
        }

        return fragmentModifyUserBinding.root
    }

    // 툴바 설정
    fun settingToolbar() {
        fragmentModifyUserBinding.apply {
            toolbarModifyUser.apply {
                // 네비게이션
                setNavigationIcon(R.drawable.arrow_back_24px)
                setNavigationOnClickListener {
                    backProcesss()
                }
            }
        }
    }

    // Mbti 바텀 시트
    fun showMbtiBottomSheet(){
        val mbtiBottomSheetFragment = MbtiBottomSheetFragment(modifyUserViewModel.textFieldModifyUserUserMBTI)
        mbtiBottomSheetFragment.show(mainActivity.supportFragmentManager, "MbtiBottomSheet")
    }

    // 뒤로가기 처리
    fun backProcesss(){
        mainActivity.removeFragment(MainFragmentName.MODIFY_USER_FRAGMENT)
    }

}