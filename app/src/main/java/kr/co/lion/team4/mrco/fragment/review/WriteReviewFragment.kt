package kr.co.lion.team4.mrco.fragment.review

import android.os.Bundle
import android.view.ContextThemeWrapper
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import kr.co.lion.team4.mrco.MainActivity
import kr.co.lion.team4.mrco.MainFragmentName
import kr.co.lion.team4.mrco.R
import kr.co.lion.team4.mrco.databinding.FragmentWriteReviewBinding
import kr.co.lion.team4.mrco.viewmodel.review.WriteReviewViewModel

class WriteReviewFragment : Fragment() {

    lateinit var fragmentWriteReviewBinding: FragmentWriteReviewBinding
    lateinit var mainActivity: MainActivity

    lateinit var writeReviewViewModel: WriteReviewViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        fragmentWriteReviewBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_write_review, container, false)
        writeReviewViewModel = WriteReviewViewModel()
        fragmentWriteReviewBinding.writeReviewViewModel = WriteReviewViewModel()
        fragmentWriteReviewBinding.lifecycleOwner = this

        mainActivity = activity as MainActivity

        // 툴바, 하단바, 탭 관련
        mainActivity.removeBottomSheet()
        settingToolbarWriteReview()
        settingButtonEvent()

        return fragmentWriteReviewBinding.root
    }

    // 툴바 설정
    fun settingToolbarWriteReview(){
        fragmentWriteReviewBinding.apply {
            toolbarWriteReview.apply {
                title = "스타일 리뷰 작성"
                // 네비게이션
                setNavigationIcon(R.drawable.arrow_back_24px)
                setNavigationOnClickListener {
                    backProcesss()
                }
                // 메뉴
                inflateMenu(R.menu.menu_write_review)
                setOnMenuItemClickListener {

                    when (it.itemId) {
                        // 드롭다운 클릭 시
                        R.id.menuItemWriteReviewDropdown -> {
                            showDropdownMenu(toolbarWriteReview.findViewById(R.id.menuItemWriteReviewDropdown)) // 팝업 메뉴 표시
                        }
                    }
                    true
                }
            }

            
        }
    }

    // 버튼 설정
    fun settingButtonEvent(){
        fragmentWriteReviewBinding.apply {
            buttonWriteReviewCancel.setOnClickListener {
                backProcesss()
            }
        }
    }

    // 뒤로가기 처리
    fun backProcesss(){
        mainActivity.removeFragment(MainFragmentName.WRITE_REVIEW)
    }

    // 팝업 메뉴 표시 함수
    private fun showDropdownMenu(anchorView: View) {
        // 팝업 메뉴에 적용할 Style
        val contextThemeWrapper = ContextThemeWrapper(requireContext(), R.style.PopupMenuStyle)

        // 팝업 메뉴 생성
        val popupMenu = PopupMenu(contextThemeWrapper, anchorView)

        popupMenu.inflate(R.menu.popup_menu_write_review) // 팝업 메뉴에 사용할 XML 리소스 파일 지정

        // 팝업 메뉴 아이템 클릭 이벤트 처리
        popupMenu.setOnMenuItemClickListener { menuItem ->
            when (menuItem.itemId) {
                // 스타일 리뷰 작성 클릭
                R.id.popup_write_review_Style -> {
                    fragmentWriteReviewBinding.apply {
                        toolbarWriteReview.title = "스타일 리뷰 작성"
                        textViewWriteReviewPhoto.isVisible = true
                        linearLayoutWriteReviewPhoto.isVisible = true
                    }
                }
                // 일반 리뷰 작성 클릭
                R.id.popup_write_review_Normal -> {
                    fragmentWriteReviewBinding.apply {
                        toolbarWriteReview.title = "일반 리뷰 작성"
                        textViewWriteReviewPhoto.isVisible = false
                        linearLayoutWriteReviewPhoto.isVisible = false
                    }
                }
            }
            true
        }

        // 팝업 메뉴 표시
        popupMenu.show()
    }

}
