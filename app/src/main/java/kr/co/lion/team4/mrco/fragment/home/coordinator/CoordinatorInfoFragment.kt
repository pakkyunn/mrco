package kr.co.lion.team4.mrco.fragment.home.coordinator

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.tabs.TabLayout
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kr.co.lion.team4.mrco.MainActivity
import kr.co.lion.team4.mrco.MainFragmentName
import kr.co.lion.team4.mrco.R
import kr.co.lion.team4.mrco.databinding.FragmentCoordinatorInfoBinding
import kr.co.lion.team4.mrco.databinding.RowCoordinatorInfoBinding
import kr.co.lion.team4.mrco.viewmodel.coordinator.RowCoordinatorInfoViewModel

class CoordinatorInfoFragment : Fragment() {
    
    // 원빈 - 코디네이터 정보 화면

    lateinit var fragmentCoordinatorInfoBinding: FragmentCoordinatorInfoBinding
    lateinit var mainActivity: MainActivity

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment

        fragmentCoordinatorInfoBinding = FragmentCoordinatorInfoBinding.inflate(inflater)

        mainActivity = activity as MainActivity
        
        // 툴바, 하단바, 탭 관련
        settingTabs()
        settingCoorditab()
        settingMainTab()
        settingToolbar()
        mainActivity.viewBottomSheet()

        // 리사이클러 뷰
        settingRecyclerViewCoordinatorInfo()

        return fragmentCoordinatorInfoBinding.root
    }

    // 툴바 세팅(메인 / 검색, 알림, 장바구니)
    fun settingToolbar() {
        fragmentCoordinatorInfoBinding.apply {
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

    // 코디네이터 소개 리사이클러 뷰 설정
    fun settingRecyclerViewCoordinatorInfo() {
        fragmentCoordinatorInfoBinding.apply {
            recyclerViewCoordinatorInfo.apply {
                // 어뎁터 및 레이아웃 매니저 설정
                adapter = CoordinatorInfoRecyclerViewAdapter()
                layoutManager = GridLayoutManager(mainActivity, 2)
            }
        }
    }

    // 탭바 위치 설정
    fun settingTabs(){
        fragmentCoordinatorInfoBinding.apply {
            val tabLayout = tabsMain
            tabLayout.getTabAt(2)?.select()
        }
        fragmentCoordinatorInfoBinding.apply {
            val tabLayout = tabs
            tabLayout.getTabAt(1)?.select()
        }
    }

    // 코디네이터 소개 리사이클러 뷰 어뎁터
    inner class CoordinatorInfoRecyclerViewAdapter: RecyclerView.Adapter<CoordinatorInfoRecyclerViewAdapter.CorrdinatorInfoViewHolder>(){
        inner class CorrdinatorInfoViewHolder(rowCoordinatorInfoBinding: RowCoordinatorInfoBinding): RecyclerView.ViewHolder(rowCoordinatorInfoBinding.root){
            val rowCoordinatorInfoBinding: RowCoordinatorInfoBinding

            init {
                this.rowCoordinatorInfoBinding = rowCoordinatorInfoBinding

                this.rowCoordinatorInfoBinding.root.layoutParams = ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT
                )
            }
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CorrdinatorInfoViewHolder {
            // val rowCoordinatorInfoBinding = RowCoordinatorInfoBinding.inflate(layoutInflater)

            val rowCoordinatorInfoBinding = DataBindingUtil.inflate<RowCoordinatorInfoBinding>(
                layoutInflater, R.layout.row_coordinator_info, parent, false
            )
            val rowCoordinatorInfoViewModel = RowCoordinatorInfoViewModel()
            rowCoordinatorInfoBinding.rowCoordinatorInfoViewModel = rowCoordinatorInfoViewModel
            rowCoordinatorInfoBinding.lifecycleOwner = this@CoordinatorInfoFragment

            val coordinatorInfoViewHolder = CorrdinatorInfoViewHolder(rowCoordinatorInfoBinding)

            return coordinatorInfoViewHolder
        }

        override fun getItemCount(): Int {
            return 8
        }

        override fun onBindViewHolder(holder: CorrdinatorInfoViewHolder, position: Int) {
            holder.rowCoordinatorInfoBinding.root.setOnClickListener {
                mainActivity.replaceFragment(MainFragmentName.COORDINATOR_MAIN, true, true, null)
            }

            // position 값에 따라 다른 이미지 설정
            val imageResource = when (position % 5) {
                0 -> R.drawable.iu_image
                1 -> R.drawable.iu_image2
                2 -> R.drawable.iu_image3
                3 -> R.drawable.iu_image4
                else -> R.drawable.iu_image5
            }
            holder.rowCoordinatorInfoBinding.imageViewCoordinatorInfo.setImageResource(imageResource)
        }
    }

    fun settingCoorditab(){
        CoroutineScope(Dispatchers.Main).launch {
            fragmentCoordinatorInfoBinding.apply {
                val tabLayout = tabs

                // 탭 선택 리스너 설정
                tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
                    override fun onTabSelected(tab: TabLayout.Tab?) {
                        // 선택된 탭이 첫 번째 탭인 경우
                        if (tab?.position == 0) {
                            mainActivity.removeFragment(MainFragmentName.HOME_COORDINATOR_INFO)
                            mainActivity.replaceFragment(MainFragmentName.HOME_COORDINATOR_RANK, false, false, null)
                        }
                        else {
                            mainActivity.removeFragment(MainFragmentName.HOME_COORDINATOR_RANK)
                            mainActivity.replaceFragment(MainFragmentName.HOME_COORDINATOR_INFO, false, false, null)
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

    fun settingMainTab(){
        CoroutineScope(Dispatchers.Main).launch {
            fragmentCoordinatorInfoBinding.apply {
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
}