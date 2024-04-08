package kr.co.lion.team4.mrco.fragment.home.mbti

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.tabs.TabLayout
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kr.co.lion.team4.mrco.MainActivity
import kr.co.lion.team4.mrco.MainFragmentName
import kr.co.lion.team4.mrco.R
import kr.co.lion.team4.mrco.databinding.FragmentCoordinatorRankBinding
import kr.co.lion.team4.mrco.databinding.FragmentHomeMbtiBinding
import kr.co.lion.team4.mrco.databinding.RowCoordinatorRankBinding
import kr.co.lion.team4.mrco.databinding.RowHomeMbtiBinding
import kr.co.lion.team4.mrco.databinding.RowHomeRecommendBinding
import kr.co.lion.team4.mrco.viewmodel.coordinator.CoordinatorRankViewModel
import kr.co.lion.team4.mrco.viewmodel.coordinator.RowCoordinatorRankViewModel
import kr.co.lion.team4.mrco.viewmodel.home.mbti.HomeMbtiViewModel
import kr.co.lion.team4.mrco.viewmodel.home.mbti.RowHomeMbtiViewModel

class HomeMbtiFragment : Fragment() {

    lateinit var fragmentHomeMbtiBinding: FragmentHomeMbtiBinding
    lateinit var mainActivity: MainActivity

    lateinit var homeMbtiViewModel: HomeMbtiViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        fragmentHomeMbtiBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_home_mbti, container, false)
        homeMbtiViewModel = HomeMbtiViewModel()
        fragmentHomeMbtiBinding.homeMbtiViewModel = HomeMbtiViewModel()
        fragmentHomeMbtiBinding.lifecycleOwner = this

        mainActivity = activity as MainActivity

        // 툴바, 하단바, 탭 관련
        settingTabs()
        settingMainTab()
        settingToolbar()
        mainActivity.viewBottomSheet()

        // 리사이클러 뷰
        settingRecyclerViewHomeMBTI()
        settingRecyclerViewHomeMBTI2()

        // 버튼(더보기)
        settingContentMoreButton()

        return fragmentHomeMbtiBinding.root
    }

    // 툴바 세팅(메인 / 검색, 알림, 장바구니)
    fun settingToolbar() {
        fragmentHomeMbtiBinding.apply {
            toolbarMain.apply {
                setOnMenuItemClickListener {

                    when (it.itemId) {
                        // 검색 클릭 시
                        R.id.home_toolbar_search -> {

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

    // 탭바 위치 설정
    fun settingTabs(){
        fragmentHomeMbtiBinding.apply {
            val tabLayout = tabsMain
            tabLayout.getTabAt(1)?.select()
        }
    }

    fun settingMainTab(){
        CoroutineScope(Dispatchers.Main).launch {
            fragmentHomeMbtiBinding.apply {
                val tabLayout = tabsMain

                // 탭 선택 리스너 설정
                tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
                    override fun onTabSelected(tab: TabLayout.Tab?) {
                        // 선택된 탭이 첫 번째 탭인 경우
                        if (tab?.position == 0) {
                            mainActivity.removeFragment(MainFragmentName.HOME_MBTI)
                            mainActivity.removeFragment(MainFragmentName.HOME_COORDINATOR_RANK)
                            mainActivity.removeFragment(MainFragmentName.HOME_COORDINATOR_INFO)
                            mainActivity.replaceFragment(MainFragmentName.HOME_RECOMMEND, false, false, null)
                        }
                        else if (tab?.position == 1) {
                            mainActivity.removeFragment(MainFragmentName.HOME_RECOMMEND)
                            mainActivity.removeFragment(MainFragmentName.HOME_COORDINATOR_RANK)
                            mainActivity.removeFragment(MainFragmentName.HOME_COORDINATOR_INFO)
                            mainActivity.replaceFragment(MainFragmentName.HOME_MBTI, false, false, null)
                        } else {
                            mainActivity.removeFragment(MainFragmentName.HOME_RECOMMEND)
                            mainActivity.removeFragment(MainFragmentName.HOME_MBTI)
                            mainActivity.removeFragment(MainFragmentName.HOME_COORDINATOR_INFO)
                            mainActivity.replaceFragment(MainFragmentName.HOME_COORDINATOR_RANK, false, false, null)
                        }
                    }

                    override fun onTabUnselected(tab: TabLayout.Tab?) {
                        // Not implemented
                    }

                    override fun onTabReselected(tab: TabLayout.Tab?) {
                        // Not implemented
                    }
                })
            }
        }
    }

    // 더보기 버튼
    fun settingContentMoreButton(){
        fragmentHomeMbtiBinding.apply {
            homeMbtiContent1MoreButton.setOnClickListener {
                mainActivity.replaceFragment(MainFragmentName.MBTI_PRODUCT_MAIN, true, true, null)
            }

            homeMbtiContent2MoreButton.setOnClickListener {
                mainActivity.replaceFragment(MainFragmentName.MBTI_PRODUCT_MAIN, true, true, null)
            }
        }
    }

    // 홈(추천) - 신규 코디 리사이클러 뷰 설정
    fun settingRecyclerViewHomeMBTI() {
        fragmentHomeMbtiBinding.apply {
            homeMbtiContent1Recycler.apply {
                // 어뎁터 및 레이아웃 매니저 설정
                adapter = HomeMBTIRecyclerViewAdapter()
                // layoutManager = LinearLayoutManager(mainActivity, LinearLayoutManager.HORIZONTAL, false)
            }
        }
    }

    // 홈(추천) - 신규 코디 리사이클러 뷰 설정
    fun settingRecyclerViewHomeMBTI2() {
        fragmentHomeMbtiBinding.apply {
            homeMbtiContent2Recycler.apply {
                // 어뎁터 및 레이아웃 매니저 설정
                adapter = HomeMBTIRecyclerViewAdapter()
                // layoutManager = LinearLayoutManager(mainActivity, LinearLayoutManager.HORIZONTAL, false)
            }
        }
    }

    // 홈(MBTI) - MBTI @@에게 잘 어울리는 코디 리사이클러 뷰 어뎁터
    inner class HomeMBTIRecyclerViewAdapter: RecyclerView.Adapter<HomeMBTIRecyclerViewAdapter.HomeMBTIViewHolder>(){
        inner class HomeMBTIViewHolder(rowHomeMbtiBinding: RowHomeMbtiBinding): RecyclerView.ViewHolder(rowHomeMbtiBinding.root){
            val rowHomeMbtiBinding: RowHomeMbtiBinding

            init {
                this.rowHomeMbtiBinding = rowHomeMbtiBinding

                this.rowHomeMbtiBinding.root.layoutParams = ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT
                )
            }
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeMBTIViewHolder {
            val rowHomeMbtiBinding = DataBindingUtil.inflate<RowHomeMbtiBinding>(
                layoutInflater, R.layout.row_home_mbti, parent, false
            )
            val rowHomeMbtiViewModel = RowHomeMbtiViewModel()
            rowHomeMbtiBinding.rowHomeMbtiViewModel = rowHomeMbtiViewModel
            rowHomeMbtiBinding.lifecycleOwner = this@HomeMbtiFragment

            val homeMBTIViewHolder = HomeMBTIViewHolder(rowHomeMbtiBinding)

            return homeMBTIViewHolder
        }

        override fun getItemCount(): Int {
            return 6
        }

        override fun onBindViewHolder(holder: HomeMBTIViewHolder, position: Int) {

        }
    }
}