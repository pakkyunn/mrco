package kr.co.lion.team4.mrco.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kr.co.lion.team4.mrco.databinding.FragmentMbtiBottomSheetBinding
import kr.co.lion.team4.mrco.MainActivity
import kr.co.lion.team4.mrco.Tools
import kr.co.lion.team4.mrco.viewmodel.JoinCoordinatorViewModel
import kr.co.lion.team4.mrco.viewmodel.MbtiBottomSheetViewModel


class MbtiBottomSheetFragment : BottomSheetDialogFragment() {

    lateinit var fragmentMbtiBottomSheetBinding: FragmentMbtiBottomSheetBinding
    lateinit var mainActivity: MainActivity
    lateinit var mbtiBottomSheetViewModel: MbtiBottomSheetViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        fragmentMbtiBottomSheetBinding = FragmentMbtiBottomSheetBinding.inflate(layoutInflater)
        mainActivity = activity as MainActivity
        mbtiBottomSheetViewModel = MbtiBottomSheetViewModel()
        fragmentMbtiBottomSheetBinding.mbtiBottomSheetViewModel = mbtiBottomSheetViewModel

//        selectMbti()

        return fragmentMbtiBottomSheetBinding.root
    }

//    fun selectMbti(){
//        fragmentMbtiBottomSheetBinding.apply {
//            buttonMbtiBottomSheetCancel.setOnClickListener {
//                dismiss()
//            }
//            buttonMbtiBottomSheetSubmit.setOnClickListener {
//                val selectedEI = mbtiBottomSheetViewModel?.settingMbtiEI(mbtiBottomSheetViewModel.gettingMbtiEI()).toString()
//                val selectedSN = mbtiBottomSheetViewModel?.settingMbtiSN(mbtiBottomSheetViewModel.gettingMbtiSN()).toString()
//                val selectedTF = mbtiBottomSheetViewModel?.settingMbtiTF(mbtiBottomSheetViewModel.gettingMbtiTF()).toString()
//                val selectedPJ = mbtiBottomSheetViewModel?.settingMbtiPJ(mbtiBottomSheetViewModel.gettingMbtiPJ()).toString()
//
//                if (selectedEI.isNotEmpty()&&selectedSN.isNotEmpty()&&selectedTF.isNotEmpty()&&selectedPJ.isNotEmpty()){
//
//                    val selectedMbti = selectedEI + selectedSN + selectedTF + selectedPJ
//
//                    JoinCoordinatorFragment().fragmentJoinCoordinatorBinding.textFieldJoinCoordinatorMBTI.setText(selectedMbti)
//                    dismiss()
//                }else{
//                    Tools.showErrorDialog(mainActivity,toggleButtonEI,"MBTI 입력 오류","MBTI 선택이 완료되지 않았습니다.")
//                }
//            }
//        }
//    }

}