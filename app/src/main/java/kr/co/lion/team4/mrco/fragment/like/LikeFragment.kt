package kr.co.lion.team4.mrco.fragment.home.coordinator

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.google.android.material.tabs.TabLayoutMediator
import kr.co.lion.team4.mrco.MainActivity
import kr.co.lion.team4.mrco.MainFragmentName
import kr.co.lion.team4.mrco.R
import kr.co.lion.team4.mrco.databinding.FragmentLikeBinding
import kr.co.lion.team4.mrco.fragment.like.LikeCoordinatorFragment
import kr.co.lion.team4.mrco.fragment.like.LikeProductFragment
import kr.co.lion.team4.mrco.viewmodel.like.LikeViewModel

class LikeFragment : Fragment() {

    // 원빈 - 좋아요 화면(코디네이터)
    lateinit var fragmentLikeBinding: FragmentLikeBinding
    lateinit var mainActivity: MainActivity

    lateinit var likeViewModel: LikeViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        // Inflate the layout for this fragment
        fragmentLikeBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_like, container, false)
        likeViewModel = LikeViewModel()
        fragmentLikeBinding.likeViewModel = LikeViewModel()
        fragmentLikeBinding.lifecycleOwner = this

        mainActivity = activity as MainActivity

        // 툴바, 하단바, 탭 관련
        viewPagerActiviation()
        settingToolbar()
        bottomSheetSetting()
        settingBottomTabs()

        return fragmentLikeBinding.root
    }

    // 툴바 세팅(메인 / 검색, 알림, 장바구니)
    fun settingToolbar() {
        fragmentLikeBinding.apply {
            toolbarLike.apply {
                setOnMenuItemClickListener {
                    when (it.itemId) {
                        // 검색 클릭 시
                        R.id.home_toolbar_search -> {
                            mainActivity.replaceFragment(MainFragmentName.CATEGORY_SEARCH_FRAGMENT, true, false, null)
                        }
                        // 알람 클릭 시
                        R.id.home_toolbar_notification -> {
                            mainActivity.replaceFragment(MainFragmentName.APP_NOTICE_FRAGMENT, true, true, null)
                        }
                        // 장바구니 클릭 시
                        R.id.home_toolbar_shopping -> {
                            mainActivity.replaceFragment(MainFragmentName.CART_FRAGMENT, true, true, null)
                        }
                    }
                    true
                }
            }
        }
    }

    // 하단 바 설정
    fun bottomSheetSetting() {
        fragmentLikeBinding.apply {
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

    // 하단 바 홈으로 체크 표시 설정
    fun settingBottomTabs() {
        fragmentLikeBinding.apply {
            val menuItemId = R.id.main_bottom_navi_like
            fragmentLikeBinding.mainBottomNavi.menu.findItem(menuItemId)?.isChecked = true
        }
    }

    private fun viewPagerActiviation(){

        val coordicnt = 7
        val coordinatorcnt = 6

        fragmentLikeBinding.apply {
            // 1. 페이지 데이터를 로드
            val list = listOf(LikeProductFragment(), LikeCoordinatorFragment())
            // 2. Adapter 생성
            val pagerAdapter = FragmentPagerAdapter(list, mainActivity)
            // 3. Adapater와 Pager연결
            viewPagerLike.adapter = pagerAdapter
            // 4. 탭 메뉴의 갯수만큼 제목을 목록으로 생성
            val titles = listOf("코디 (${coordicnt})", "코디네이터 (${coordinatorcnt})")
            // 5. 탭 레이아웃과 뷰페이저 연결
            TabLayoutMediator(tabsLike, viewPagerLike) { tab, position ->
                tab.text = titles.get(position)
            }.attach()

            // ViewPager 드래그 비활성화
            viewPagerLike.isUserInputEnabled = false
        }
    }

    private inner class FragmentPagerAdapter(val fragmentList: List<Fragment>, fragmentActivity: FragmentActivity):
        FragmentStateAdapter(fragmentActivity){
        override fun getItemCount(): Int {
            return fragmentList.size
        }

        override fun createFragment(position: Int): Fragment {
            return fragmentList.get(position)
        }
    }

}