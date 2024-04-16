package kr.co.lion.team4.mrco.fragment.mbtiproductmain

import android.app.Dialog
import android.graphics.Color
import android.os.Bundle
import android.util.DisplayMetrics
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kr.co.lion.team4.mrco.MainActivity
import kr.co.lion.team4.mrco.R
import kr.co.lion.team4.mrco.databinding.FragmentMbtiProductBottomBinding

class MbtiProductBottomFragment : BottomSheetDialogFragment() {

    lateinit var fragmentMbtiProductBottomBinding: FragmentMbtiProductBottomBinding
    lateinit var mainActivity: MainActivity

      override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
          fragmentMbtiProductBottomBinding = FragmentMbtiProductBottomBinding.inflate(inflater)
          mainActivity = activity as MainActivity

          return fragmentMbtiProductBottomBinding.root
    }

    // 다이얼로그가 만들어질 때 자동으로 호출되는 메서드
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        // 다이얼로그를 받는다
        val dialog = super.onCreateDialog(savedInstanceState)
        // 다이얼로그가 보일때 동작하는 리스너
        dialog.setOnShowListener {

            val bottomSheetDialog = it as BottomSheetDialog
            // 높이를 설정한다.
            setBottomSheetHeight(bottomSheetDialog)
        }

        return dialog
    }

    // BottomSheet의 높이를 설정해준다.
    fun setBottomSheetHeight(bottomSheetDialog: BottomSheetDialog) {

        // BottomSheet의 기본 뷰 객체를 가져온다
        val bottomSheet = bottomSheetDialog.findViewById<View>(com.google.android.material.R.id.design_bottom_sheet)!!

        // 바텀시트의 배경색을 변경한다 (둥근 모서리를 유지하기 위해 drawable 지정)
        bottomSheet.setBackgroundResource(R.drawable.bottom_sheet_background)

        // BottomSheet 높이를 설정할 수 있는 객체를 생성한다.
        val behavior = BottomSheetBehavior.from(bottomSheet)

        // 높이를 설정한다.
        val layoutParams = bottomSheet.layoutParams
        layoutParams.height = getBottomSheetDialogHeight()
        bottomSheet.layoutParams = layoutParams
        behavior.state = BottomSheetBehavior.STATE_EXPANDED
    }

    // BottomSheet의 높이를 구한다 (화면 액정의 51% 크기)
    fun getBottomSheetDialogHeight(): Int {
        return (getWindowHeight() * 0.51).toInt()
    }

    // 사용자 단말기 액정의 세로 길이를 구해 반환하는 메서드
    fun getWindowHeight(): Int{
        // 화면 크기 정보를 담을 배열 객체
        val displayMetrics = DisplayMetrics()
        // 액정의 가로 세러 길이 정보를 담아준다.
        mainActivity.windowManager.defaultDisplay.getMetrics(displayMetrics)
        // 세로 길이를 반환
        return displayMetrics.heightPixels
    }
}