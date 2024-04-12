package kr.co.lion.team4.mrco.fragment.mypage

import android.app.AlertDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.google.android.material.tabs.TabLayout
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
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

        // 탭바 설정
        settingTabs()
        settingMypagetab()

        settingUserMyPageMenuClickEvent()
        mainActivity.viewBottomSheet()

        return fragmentUserMyPageBinding.root
    }

    // 탭바 위치 설정
    fun settingTabs(){
        fragmentUserMyPageBinding.apply {
            val tabLayout = tabsMypage
            tabLayout.getTabAt(0)?.select()
        }
    }

    // 탭 이동 설정
    fun settingMypagetab() {
        CoroutineScope(Dispatchers.Main).launch {
            fragmentUserMyPageBinding.apply {
                val tabLayout = tabsMypage

                // 탭 선택 리스너 설정
                tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
                    override fun onTabSelected(tab: TabLayout.Tab?) {
                        // 선택된 탭이 첫 번째 탭인 경우
                        if (tab?.position == 0) {
                            mainActivity.removeFragment(MainFragmentName.COORDINATOR_MYPAGE_FRAGMENT)
                            mainActivity.replaceFragment(MainFragmentName.USER_MYPAGE_FRAGMENT, false, false, null)
                        } else {
                            showAlert("코디네이터 등록이 허가된 사용자만 이용 가능합니다")
                            mainActivity.removeFragment(MainFragmentName.USER_MYPAGE_FRAGMENT)
                            mainActivity.replaceFragment(MainFragmentName.COORDINATOR_MYPAGE_FRAGMENT, false, false, null)
                        }
                    }

                    override fun onTabUnselected(tab: TabLayout.Tab?) {
                    }

                    override fun onTabReselected(tab: TabLayout.Tab?) {
                    }
                })
            }
        }
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
                mainActivity.replaceFragment(MainFragmentName.MY_REVIEW, true, true, null)
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
                // 임시
                mainActivity.replaceFragment(MainFragmentName.LOGIN_FRAGMENT, false, false, null)
            }

            // 내 정보 변경
            iconButtonEditUserMyPage.setOnClickListener {
                mainActivity.replaceFragment(MainFragmentName.MODIFY_USER_FRAGMENT, true, true, null)
            }

        }
    }

    // 경고 메시지 표시하는 함수
    fun showAlert(message: String) {
        val builder = AlertDialog.Builder(context)
        builder.setMessage(message)
            .setPositiveButton("확인") { dialog, _ ->
                dialog.dismiss()
            }
        val alertDialog = builder.create()
        alertDialog.show()
    }
}