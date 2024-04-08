package kr.co.lion.team4.mrco.fragment.appNotice

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kr.co.lion.team4.mrco.MainActivity
import kr.co.lion.team4.mrco.MainFragmentName
import kr.co.lion.team4.mrco.R
import kr.co.lion.team4.mrco.databinding.FragmentAppNoticeBinding

class AppNoticeFragment : Fragment() {

    lateinit var fragmentAppNoticeBinding: FragmentAppNoticeBinding
    lateinit var mainActivity: MainActivity
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        fragmentAppNoticeBinding = FragmentAppNoticeBinding.inflate(inflater)
        mainActivity = activity as MainActivity

        // 하단 바 안보이게
        mainActivity.removeBottomSheet()
        // 툴바
        toolbarSetting()

        return fragmentAppNoticeBinding.root
    }

    // 툴바 설정
    fun toolbarSetting() {
        fragmentAppNoticeBinding.toolbarAppNotice.apply {
            setOnMenuItemClickListener {

                when (it.itemId) {
                    // 드롭다운 클릭 시
                    R.id.main_toolbar_close -> {
                        backProcesss()
                    }
                }
                true
            }
        }
    }

    // 뒤로가기 처리
    fun backProcesss(){
        mainActivity.removeFragment(MainFragmentName.APP_NOTICE_FRAGMENT)
        mainActivity.viewBottomSheet()
    }
}