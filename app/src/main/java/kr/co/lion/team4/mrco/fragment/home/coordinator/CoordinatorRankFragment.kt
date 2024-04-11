package kr.co.lion.team4.mrco.fragment.home.coordinator

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
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
import kr.co.lion.team4.mrco.databinding.FragmentMainHomeBinding
import kr.co.lion.team4.mrco.databinding.RowCoordinatorRank2Binding
import kr.co.lion.team4.mrco.databinding.RowCoordinatorRankBinding
import kr.co.lion.team4.mrco.viewmodel.coordinator.CoordinatorRankViewModel
import kr.co.lion.team4.mrco.viewmodel.coordinator.RowCoordinatorRank2ViewModel
import kr.co.lion.team4.mrco.viewmodel.coordinator.RowCoordinatorRankViewModel

class CoordinatorRankFragment : Fragment() {

    // 원빈 - 인기 코디네이터 화면
    lateinit var fragmentCoordinatorRankBinding: FragmentCoordinatorRankBinding
    lateinit var mainActivity: MainActivity

    lateinit var coordinatorRankViewModel: CoordinatorRankViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment

        //fragmentCoordinatorRankBinding = FragmentCoordinatorRankBinding.inflate(layoutInflater)
        fragmentCoordinatorRankBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_coordinator_rank, container, false)
        coordinatorRankViewModel = CoordinatorRankViewModel()
        fragmentCoordinatorRankBinding.coordinatorRankViewModel = CoordinatorRankViewModel()
        fragmentCoordinatorRankBinding.lifecycleOwner = this

        val fragmentManager = activity?.supportFragmentManager
        val fragment = fragmentManager?.findFragmentById(R.id.containerMainHome)

        mainActivity = activity as MainActivity

        // 툴바, 하단바, 탭 관련
        settingTabs()
        settingCoorditab()
        settingMainTab()
        settingToolbar()
        settingBottomTabs()
        mainActivity.viewBottomSheet()

        // 리사이클러 뷰
        settingRecyclerViewCoordinatorRank()

        return fragmentCoordinatorRankBinding.root
    }

    // 툴바 세팅(메인 / 검색, 알림, 장바구니)
    fun settingToolbar() {
        fragmentCoordinatorRankBinding.apply {
            toolbarMain.apply {
                setOnMenuItemClickListener {

                    when (it.itemId) {
                        // 검색 클릭 시
                        R.id.home_toolbar_search -> {
                            mainActivity.replaceFragment(MainFragmentName.CATEGORY_FRAGMENT, false, false, null)
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

    // 하단 바 홈으로 체크 표시 설정
    fun settingBottomTabs() {
        mainActivity.activityMainBinding.apply {
            val menuItemId = R.id.main_bottom_navi_home
            mainActivity.activityMainBinding.mainBottomNavi.menu.findItem(menuItemId)?.isChecked = true
        }
    }

    // 인기 코디네이터 리사이클러 뷰 설정
    fun settingRecyclerViewCoordinatorRank() {
        fragmentCoordinatorRankBinding.apply {
            recyclerViewCoordinatorRank.apply {
                // 어뎁터 및 레이아웃 매니저 설정
                adapter = CoordinatorRankRecyclerViewAdapter()
                layoutManager = LinearLayoutManager(mainActivity)
            }
        }
    }

    // 탭바 위치 설정
    fun settingTabs(){
        fragmentCoordinatorRankBinding.apply {
            val tabLayout = tabsMain
            tabLayout.getTabAt(2)?.select()
        }
        fragmentCoordinatorRankBinding.apply {
            val tabLayout = tabs
            tabLayout.getTabAt(0)?.select()
        }
    }

    // 인기 코디네이터 리사이클러 뷰 어뎁터
    inner class CoordinatorRankRecyclerViewAdapter: RecyclerView.Adapter<CoordinatorRankRecyclerViewAdapter.CorrdinatorRankViewHolder>(){
        inner class CorrdinatorRankViewHolder(rowCoordinatorRankBinding: RowCoordinatorRankBinding): RecyclerView.ViewHolder(rowCoordinatorRankBinding.root){
            val rowCoordinatorRankBinding: RowCoordinatorRankBinding

            init {
                this.rowCoordinatorRankBinding = rowCoordinatorRankBinding

                this.rowCoordinatorRankBinding.root.layoutParams = ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT
                )
            }
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CorrdinatorRankViewHolder {
            val rowCoordinatorRankBinding = DataBindingUtil.inflate<RowCoordinatorRankBinding>(
                layoutInflater, R.layout.row_coordinator_rank, parent, false
            )
            val rowCoordinatorRankViewModel = RowCoordinatorRankViewModel()
            rowCoordinatorRankBinding.rowCoordinatorRankViewModel = rowCoordinatorRankViewModel
            rowCoordinatorRankBinding.lifecycleOwner = this@CoordinatorRankFragment

            val coordinatorRankViewHolder = CorrdinatorRankViewHolder(rowCoordinatorRankBinding)

            return coordinatorRankViewHolder
        }

        override fun getItemCount(): Int {
            return 10
        }

        override fun onBindViewHolder(holder: CorrdinatorRankViewHolder, position: Int) {
            holder.rowCoordinatorRankBinding.textViewRowCoordinatorRankNumber.text = "${position + 1}"

            // position 값에 따라 다른 이미지 설정
            val imageResource = when (position % 5) {
                0 -> R.drawable.iu_image
                1 -> R.drawable.iu_image2
                2 -> R.drawable.iu_image3
                3 -> R.drawable.iu_image4
                else -> R.drawable.iu_image5
            }
            holder.rowCoordinatorRankBinding.imageViewRowCoordinatorRankProfile.setImageResource(imageResource)

            holder.rowCoordinatorRankBinding.imageViewRowCoordinatorRankProfile.setOnClickListener {
                mainActivity.replaceFragment(MainFragmentName.COORDINATOR_MAIN, true, true, null)
                Log.d("test1234", "인기 코디네이터 화면 : imageView - Click / 코디네이터 메인으로 이동")
            }

            // (팔로우/팔로잉) 버튼 클릭 시
            holder.rowCoordinatorRankBinding.buttonRowCoordinatorRankFollower.setOnClickListener {
                holder.rowCoordinatorRankBinding.buttonRowCoordinatorRankFollower.apply {
                    val newTintList = if (text == "팔로우") {
                        text = "팔로잉"
                        ContextCompat.getColorStateList(context, R.color.buttonFollowing)
                    } else {
                        text = "팔로우"
                        ContextCompat.getColorStateList(context, R.color.buttonFollow)
                    }

                    backgroundTintList = newTintList
                }
                Log.d("test1234", "인기 코디네이터 화면 : button - Click / 팔로잉,팔로우 버튼")
            }

            // 내부 리사이클러 뷰 설정
            val innerRecyclerView = holder.rowCoordinatorRankBinding.recyclerViewCoordinatorRank2
            innerRecyclerView.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            innerRecyclerView.adapter  = InnerRecyclerViewAdapter() //내부 리사이클러 뷰 어댑터 설정

            // ItemDecoration 적용?
        }
    }

    // 내부 리사이클러 뷰 어댑터
    inner class InnerRecyclerViewAdapter : RecyclerView.Adapter<InnerRecyclerViewAdapter.InnerViewHolder>() {
        inner class InnerViewHolder(rowCoordinatorRank2Binding: RowCoordinatorRank2Binding) : RecyclerView.ViewHolder(rowCoordinatorRank2Binding.root) {
            // 내부 리사이클러 뷰의 ViewHolder 내용 정의
            val rowCoordinatorRank2Binding: RowCoordinatorRank2Binding

            init {
                this.rowCoordinatorRank2Binding = rowCoordinatorRank2Binding

                this.rowCoordinatorRank2Binding.root.layoutParams = ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT
                )
            }
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): InnerViewHolder {
            // val rowCoordinatorRank2Binding = RowCoordinatorRank2Binding.inflate(layoutInflater)

            val rowCoordinatorRank2Binding = DataBindingUtil.inflate<RowCoordinatorRank2Binding>(
                layoutInflater, R.layout.row_coordinator_rank2, parent, false
            )
            val rowCoordinatorRank2ViewModel = RowCoordinatorRank2ViewModel()
            rowCoordinatorRank2Binding.rowCoordinatorRank2ViewModel = rowCoordinatorRank2ViewModel
            rowCoordinatorRank2Binding.lifecycleOwner = this@CoordinatorRankFragment

            val innerViewHolder = InnerViewHolder(rowCoordinatorRank2Binding)

            return innerViewHolder
        }

        override fun getItemCount(): Int {
            return 10
        }

        override fun onBindViewHolder(holder: InnerViewHolder, position: Int) {
            // position 값에 따라 다른 이미지 설정
            val imageResource = when (position % 5) {
                0 -> R.drawable.iu_image5
                1 -> R.drawable.iu_image4
                2 -> R.drawable.iu_image3
                3 -> R.drawable.iu_image2
                else -> R.drawable.iu_image
            }
            holder.rowCoordinatorRank2Binding.imageViewRowCoordinatorRank2Photo.setImageResource(imageResource)
        }
    }

    fun settingCoorditab(){
        CoroutineScope(Dispatchers.Main).launch {
            fragmentCoordinatorRankBinding.apply {
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
            fragmentCoordinatorRankBinding.apply {
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