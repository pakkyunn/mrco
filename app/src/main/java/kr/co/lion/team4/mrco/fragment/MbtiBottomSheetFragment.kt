package kr.co.lion.team4.mrco.fragment

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.MutableLiveData
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kr.co.lion.team4.mrco.databinding.FragmentMbtiBottomSheetBinding
import kr.co.lion.team4.mrco.MainActivity
import kr.co.lion.team4.mrco.R
import kr.co.lion.team4.mrco.Tools
import kr.co.lion.team4.mrco.viewmodel.MbtiBottomSheetViewModel


class MbtiBottomSheetFragment(var mbtiEditText: MutableLiveData<String>) : BottomSheetDialogFragment() {

    lateinit var fragmentMbtiBottomSheetBinding: FragmentMbtiBottomSheetBinding
    lateinit var mainActivity: MainActivity

    lateinit var mbtiBottomSheetViewModel: MbtiBottomSheetViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment

        fragmentMbtiBottomSheetBinding = FragmentMbtiBottomSheetBinding.inflate(layoutInflater)
        mainActivity = activity as MainActivity
        mbtiBottomSheetViewModel = MbtiBottomSheetViewModel()
        fragmentMbtiBottomSheetBinding.mbtiBottomSheetViewModel = mbtiBottomSheetViewModel

        selectMbti()
        return fragmentMbtiBottomSheetBinding.root
    }

    // 다이얼로그가 만들어질 때 자동으로 호출되는 메서드
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        // 다이얼로그를 받는다
        val dialog = super.onCreateDialog(savedInstanceState)
        // 다이얼로그가 보일때 동작하는 리스너
        dialog.setOnShowListener {

            val bottomSheetDialog = it as BottomSheetDialog
            // 바텀시트 설정
            settingBottomSheet(bottomSheetDialog)
        }

        return dialog
    }

    fun settingBottomSheet(bottomSheetDialog: BottomSheetDialog){
        // BottomSheet의 기본 뷰 객체를 가져온다
        val bottomSheet = bottomSheetDialog.findViewById<View>(com.google.android.material.R.id.design_bottom_sheet)!!

        // 바텀시트의 배경색을 변경한다 (둥근 모서리를 유지하기 위해 drawable 지정)
        // 흰색 바탕에 둥그렇게
        bottomSheet.setBackgroundResource(R.drawable.bottom_sheet_background)
    }

    fun selectMbti(){
        fragmentMbtiBottomSheetBinding.apply {
            buttonMbtiBottomSheetCancel.setOnClickListener {
                dismiss()
            }
            buttonMbtiBottomSheetSubmit.setOnClickListener {
                val selectedEI = mbtiBottomSheetViewModel?.gettingMbtiEI()?.str
                val selectedSN = mbtiBottomSheetViewModel?.gettingMbtiSN()?.str
                val selectedTF = mbtiBottomSheetViewModel?.gettingMbtiTF()?.str
                val selectedPJ = mbtiBottomSheetViewModel?.gettingMbtiPJ()?.str

                if (!selectedEI.isNullOrEmpty() && !selectedSN.isNullOrEmpty() && !selectedTF.isNullOrEmpty() && !selectedPJ.isNullOrEmpty()){
                    val selectedMbti = selectedEI + selectedSN + selectedTF + selectedPJ
                    mbtiEditText.value = selectedMbti

                    dismiss()
                }else{
                    Tools.showErrorDialog(mainActivity,toggleButtonEI,"MBTI 입력 오류","MBTI 선택이 완료되지 않았습니다.")
                }
            }
        }
    }

}