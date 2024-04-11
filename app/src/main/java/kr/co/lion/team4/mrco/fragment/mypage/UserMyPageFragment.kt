package kr.co.lion.team4.mrco.fragment.mypage

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import kr.co.lion.team4.mrco.MainActivity
import kr.co.lion.team4.mrco.MainFragmentName
import kr.co.lion.team4.mrco.R
import kr.co.lion.team4.mrco.databinding.FragmentUserMyPageBinding
import kr.co.lion.team4.mrco.viewmodel.mypage.UserMyPageViewModel

class UserMyPageFragment : Fragment() {

    lateinit var fragmentUserMyPageBinding: FragmentUserMyPageBinding
    lateinit var mainActivity: MainActivity
    lateinit var userMyPageViewModel: UserMyPageViewModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        fragmentUserMyPageBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_user_my_page, container, false)
        userMyPageViewModel = UserMyPageViewModel()
        fragmentUserMyPageBinding.userMyPageViewModel = userMyPageViewModel
        fragmentUserMyPageBinding.lifecycleOwner = this

        mainActivity = activity as MainActivity

        settingUserMyPageMenuClickEvent()
        mainActivity.viewBottomSheet()

        return fragmentUserMyPageBinding.root
    }

    // 마이페이지 메뉴 클릭 이벤트
    fun settingUserMyPageMenuClickEvent(){
        fragmentUserMyPageBinding.apply {
            // 주문/배송 조회
            textViewMenuUserMyPage1.setOnClickListener {
                mainActivity.replaceFragment(MainFragmentName.ORDER_HISTORY_FRAGMENT, true, true, null)
            }
            // 나의 리뷰
            textViewMenuUserMyPage2.setOnClickListener {
                mainActivity.replaceFragment(MainFragmentName.WRITE_REVIEW, true, true, null)
            }

            // 고객센터
            textViewMenuUserMyPage3.setOnClickListener {
                mainActivity.replaceFragment(MainFragmentName.CUSTOMER_SERVICE_FRAGMENT, true, true, null)
            }

            // 코디네이터 등록 신청
            textViewMenuUserMyPage4.setOnClickListener {
                mainActivity.replaceFragment(MainFragmentName.JOIN_COORDINATOR_FRAGMENT, true, true, null)
            }
            // 코디네이터 등록 신청
            textViewMenuUserMyPage5.setOnClickListener {
                mainActivity.replaceFragment(MainFragmentName.LOGIN_FRAGMENT, true, true, null)
            }

            // 로그아웃
            textViewMenuUserMyPage5.setOnClickListener {
                mainActivity.replaceFragment(MainFragmentName.LOGIN_FRAGMENT, false, false, null)
            }

            // 내 정보 변경
            iconButtonEditUserMyPage.setOnClickListener {
                mainActivity.replaceFragment(MainFragmentName.MODIFY_USER_FRAGMENT, true, true, null)
            }

        }
    }
}