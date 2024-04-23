package kr.co.lion.team4.mrco.fragment.mypage

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
import kr.co.lion.team4.mrco.databinding.FragmentCoordinatorMyPageBinding
import kr.co.lion.team4.mrco.viewmodel.mypage.CoordinatorMyPageViewModel

class CoordinatorMyPageFragment : Fragment() {

    lateinit var fragmentCoordinatorMyPageBinding: FragmentCoordinatorMyPageBinding
    lateinit var mainActivity: MainActivity
    lateinit var coordinatorMyPageViewModel: CoordinatorMyPageViewModel
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        fragmentCoordinatorMyPageBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_coordinator_my_page, container, false)
        coordinatorMyPageViewModel = CoordinatorMyPageViewModel()
        fragmentCoordinatorMyPageBinding.coordinatorMyPageViewModel = coordinatorMyPageViewModel
        fragmentCoordinatorMyPageBinding.lifecycleOwner = this

        mainActivity = activity as MainActivity

        // 탭바, 하단바 설정
        settingTabs()
        settingMypagetab()
        bottomSheetSetting()
        settingBottomTabs()

        settingCoordiMyPageMenuClickEvent()

        return fragmentCoordinatorMyPageBinding.root
    }


    // 탭바 위치 설정
    fun settingTabs(){
        fragmentCoordinatorMyPageBinding.apply {
            val tabLayout = tabsMypage
            tabLayout.getTabAt(1)?.select()
        }
    }

    // 탭 이동 설정
    fun settingMypagetab() {
        CoroutineScope(Dispatchers.Main).launch {
            fragmentCoordinatorMyPageBinding.apply {
                val tabLayout = tabsMypage

                // 탭 선택 리스너 설정
                tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
                    override fun onTabSelected(tab: TabLayout.Tab?) {
                        // 선택된 탭이 첫 번째 탭인 경우
                        if (tab?.position == 0) {
                            mainActivity.removeFragment(MainFragmentName.COORDINATOR_MYPAGE_FRAGMENT)
                            mainActivity.replaceFragment(MainFragmentName.USER_MYPAGE_FRAGMENT, false, false, null)
                        } else {
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

    // 하단 바 설정
    fun bottomSheetSetting() {
        fragmentCoordinatorMyPageBinding.apply {
            mainBottomNavi.setOnItemSelectedListener { item ->
                when(item.itemId) {
                    R.id.main_bottom_navi_home -> {
                        mainActivity.replaceFragment(MainFragmentName.HOME_MAIN_FULL, false, false, mainActivity.bundle)
                    }
                    R.id.main_bottom_navi_category -> {
                        mainActivity.replaceFragment(MainFragmentName.CATEGORY_MAIN_FRAGMENT, false, false, null)
                    }
                    R.id.main_bottom_navi_like -> {
                        mainActivity.replaceFragment(MainFragmentName.LIKE, false, false, null)
                    }
                    else -> {
                        mainActivity.replaceFragment(MainFragmentName.USER_MYPAGE_FRAGMENT, false, false, null)
                    }
                }
                true
            }
        }
    }

    // 하단 바 마이 페이지로 체크 표시 설정
    fun settingBottomTabs() {
        fragmentCoordinatorMyPageBinding.apply {
            val menuItemId = R.id.main_bottom_navi_my
            fragmentCoordinatorMyPageBinding.mainBottomNavi.menu.findItem(menuItemId)?.isChecked = true
        }
    }

    fun settingCoordiMyPageMenuClickEvent(){
        fragmentCoordinatorMyPageBinding.apply {
            // 코디 상품 등록
            textViewMenuCoordinatorMyPage1.setOnClickListener {
                mainActivity.replaceFragment(MainFragmentName.ADD_PRODUCT_FRAGMENT, true, true, null)
            }

            // 등록 상품 관리
            textViewMenuCoordinatorMyPage2.setOnClickListener {
                mainActivity.replaceFragment(MainFragmentName.FRAGMENT_PRODUCT_MANAGEMENT, true, true, null)
            }

            // 배송 관리
            textViewMenuCoordinatorMyPage3.setOnClickListener {
                mainActivity.replaceFragment(MainFragmentName.MANAGE_SHIPMENT_FRAGMENT, true, true, null)
            }

            // 상품 문의 관리
            textViewMenuCoordinatorMyPage4.setOnClickListener {
                mainActivity.replaceFragment(MainFragmentName.PRODUCT_QNA_LIST_FRAGMENT, true, true, null)
            }

            // 상품 판매 내역
            textViewMenuCoordinatorMyPage5.setOnClickListener {
                mainActivity.replaceFragment(MainFragmentName.SALES_LIST_FRAGMENT, true, true, null)
            }

            // 매출/정산 관리
            textViewMenuCoordinatorMyPage6.setOnClickListener {
                mainActivity.replaceFragment(MainFragmentName.SALES_MANAGEMENT, true, true, null)
            }

            // 로그아웃
            textViewMenuCoordinatorMyPage7.setOnClickListener {
                // 임시
                mainActivity.replaceFragment(MainFragmentName.LOGIN_FRAGMENT, false, false, null)
            }

            // 코디네이터 정보 수정
            iconButtonEditCoordinatorMyPage.setOnClickListener {
                mainActivity.replaceFragment(MainFragmentName.MODIFY_COORDINATOR_FRAGMENT, true, true, null)
            }
        }
    }
}