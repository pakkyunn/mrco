package kr.co.lion.team4.mrco

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kr.co.lion.team4.mrco.databinding.FragmentWriteReviewBinding

class WriteReviewFragment : Fragment() {

    lateinit var fragmentWriteReviewBinding: FragmentWriteReviewBinding
    lateinit var mainActivity: MainActivity

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment

        fragmentWriteReviewBinding = FragmentWriteReviewBinding.inflate(layoutInflater)
        mainActivity = activity as MainActivity

        settingToolbarWriteReview()

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

                        }
                    }
                    true
                }
            }

            
        }
    }

    // 뒤로가기 처리
    fun backProcesss(){
        mainActivity.removeFragment(MainFragmentName.WRITE_REVIEW)
    }
}