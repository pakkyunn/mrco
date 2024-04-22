package kr.co.lion.team4.mrco.fragment.home.recommend

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.PagerSnapHelper
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.SnapHelper
import com.google.android.material.tabs.TabLayout
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kr.co.lion.team4.mrco.MainActivity
import kr.co.lion.team4.mrco.MainFragmentName
import kr.co.lion.team4.mrco.R
import kr.co.lion.team4.mrco.databinding.FragmentHomeRecommendBinding
import kr.co.lion.team4.mrco.databinding.RowHomeRecommendBannerBinding
import kr.co.lion.team4.mrco.databinding.RowHomeRecommendBinding
import kr.co.lion.team4.mrco.databinding.RowHomeRecommendNewCoordiBinding
import kr.co.lion.team4.mrco.fragment.home.coordinator.HomeMainFullFragment
import kr.co.lion.team4.mrco.viewmodel.coordinator.CoordinatorRankViewModel
import kr.co.lion.team4.mrco.viewmodel.home.recommend.HomeRecommendViewModel
import kr.co.lion.team4.mrco.viewmodel.home.recommend.RowHomeRecommendBannerViewModel
import kr.co.lion.team4.mrco.viewmodel.home.recommend.RowHomeRecommendNewCoordiViewModel
import kr.co.lion.team4.mrco.viewmodel.home.recommend.RowHomeRecommendViewModel

class HomeRecommendFragment : Fragment() {

    lateinit var fragmentHomeRecommendBinding: FragmentHomeRecommendBinding
    lateinit var mainActivity: MainActivity

    lateinit var homeRecommendViewModel: HomeRecommendViewModel

    lateinit var snapHelper: SnapHelper

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment

        fragmentHomeRecommendBinding = FragmentHomeRecommendBinding.inflate(inflater)
        fragmentHomeRecommendBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_home_recommend, container, false)
        homeRecommendViewModel = HomeRecommendViewModel()
        fragmentHomeRecommendBinding.homeRecommendViewModel = HomeRecommendViewModel()
        fragmentHomeRecommendBinding.lifecycleOwner = this

        mainActivity = activity as MainActivity

        // 버튼
        settingButton()

        // 리사이클러 뷰
        settingRecyclerViewHomeRecommendBanner()
        settingRecyclerViewHomeRecommend()
        settingRecyclerViewHomeRecommendNewCoordi()
        settingInit()

        // 추천 코디 상품(첫번째) TextView 관찰
        homeRecommendViewModel.textViewHomeRecommendContent1.observe(viewLifecycleOwner) { text ->
            // MBTI TextView 업데이트
            fragmentHomeRecommendBinding.homeRecommendContent1.text = text
        }

        return fragmentHomeRecommendBinding.root
    }

    fun settingInit() {
        if (mainActivity.loginUserIdx != -1){
            homeRecommendViewModel.textViewHomeRecommendContent1.value = "${mainActivity.loginUserName}(${mainActivity.loginUserMbti}"
        } else {
            homeRecommendViewModel.textViewHomeRecommendContent1.value = "ENFP"
            fragmentHomeRecommendBinding.textViewSideContent1.setText(" 추천코디")
        }
    }

    fun settingButton(){
        fragmentHomeRecommendBinding.apply {
            homeRecommendAllButton.apply {
                setOnClickListener {
                    mainActivity.replaceFragment(MainFragmentName.CATEGORY_MAIN_FRAGMENT, true, false, null)
                }
            }
        }
    }

    // 홈(추천) - 배너 리사이클러 뷰 설정
    fun settingRecyclerViewHomeRecommendBanner() {
        fragmentHomeRecommendBinding.apply {
            homeRecommendBanner.apply {
                // 어뎁터 및 레이아웃 매니저 설정
                adapter = HomeRecommendBannerRecyclerViewAdapter()

                snapHelper = PagerSnapHelper()
                snapHelper.attachToRecyclerView(this)
            }
        }
    }

    // 홈(추천) - 추천 코디 리사이클러 뷰 설정
    fun settingRecyclerViewHomeRecommend() {
        fragmentHomeRecommendBinding.apply {
            homeRecommendRecycler.apply {
                // 어뎁터 및 레이아웃 매니저 설정
                adapter = HomeRecommendRecyclerViewAdapter()
            }
        }
    }

    // 홈(추천) - 신규 코디 리사이클러 뷰 설정
    fun settingRecyclerViewHomeRecommendNewCoordi() {
        fragmentHomeRecommendBinding.apply {
            homeRecommendNewRecycler.apply {
                // 어뎁터 및 레이아웃 매니저 설정
                adapter = HomeRecommendNewCoordiRecyclerViewAdapter()
            }
        }
    }

    // 홈(추천) - 배너 리사이클러 뷰 어뎁터
    inner class HomeRecommendBannerRecyclerViewAdapter: RecyclerView.Adapter<HomeRecommendBannerRecyclerViewAdapter.HomeRecommendBannerViewHolder>(){
        inner class HomeRecommendBannerViewHolder(rowHomeRecommendBannerBinding: RowHomeRecommendBannerBinding): RecyclerView.ViewHolder(rowHomeRecommendBannerBinding.root){
            val rowHomeRecommendBannerBinding: RowHomeRecommendBannerBinding

            init {
                this.rowHomeRecommendBannerBinding = rowHomeRecommendBannerBinding

                this.rowHomeRecommendBannerBinding.root.layoutParams = ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT
                )
            }
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeRecommendBannerViewHolder {
            val rowHomeRecommendBannerBinding = DataBindingUtil.inflate<RowHomeRecommendBannerBinding>(
                layoutInflater, R.layout.row_home_recommend_banner, parent, false
            )
            val rowHomeRecommendBannerViewModel = RowHomeRecommendBannerViewModel()
            rowHomeRecommendBannerBinding.rowHomeRecommendBannerViewModel = rowHomeRecommendBannerViewModel
            rowHomeRecommendBannerBinding.lifecycleOwner = this@HomeRecommendFragment

            val homeRecommendBannerViewHolder = HomeRecommendBannerViewHolder(rowHomeRecommendBannerBinding)

            return homeRecommendBannerViewHolder
        }

        override fun getItemCount(): Int {
            return 6
        }

        override fun onBindViewHolder(holder: HomeRecommendBannerViewHolder, position: Int) {
            holder.rowHomeRecommendBannerBinding.textViewBannerPage.text = "${position+1}/6"
        }
    }

    // 홈(추천) - 추천 코디 리사이클러 뷰 어뎁터
    inner class HomeRecommendRecyclerViewAdapter: RecyclerView.Adapter<HomeRecommendRecyclerViewAdapter.HomeRecommendViewHolder>(){
        inner class HomeRecommendViewHolder(rowHomeRecommendBinding: RowHomeRecommendBinding): RecyclerView.ViewHolder(rowHomeRecommendBinding.root){
            val rowHomeRecommendBinding: RowHomeRecommendBinding

            init {
                this.rowHomeRecommendBinding = rowHomeRecommendBinding

                this.rowHomeRecommendBinding.root.layoutParams = ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT
                )
            }
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeRecommendViewHolder {
            val rowHomeRecommendBinding = DataBindingUtil.inflate<RowHomeRecommendBinding>(
                layoutInflater, R.layout.row_home_recommend, parent, false
            )
            val rowHomeRecommendViewModel = RowHomeRecommendViewModel()
            rowHomeRecommendBinding.rowHomeRecommendViewModel = rowHomeRecommendViewModel
            rowHomeRecommendBinding.lifecycleOwner = this@HomeRecommendFragment

            val homeRecommendViewHolder = HomeRecommendViewHolder(rowHomeRecommendBinding)

            return homeRecommendViewHolder
        }

        override fun getItemCount(): Int {
            return 12
        }

        override fun onBindViewHolder(holder: HomeRecommendViewHolder, position: Int) {
            // position 값에 따라 다른 이미지 설정
            val imageResource = when (position % 4) {
                0 -> R.drawable.iu_image4
                1 -> R.drawable.iu_image5
                2 -> R.drawable.iu_image6
                else -> R.drawable.iu_image7
            }
            holder.rowHomeRecommendBinding.itemMainProductThumbnail.setImageResource(imageResource)

            // position 값에 따라 다른 MBTI 색상 설정
            val colorResource = when (position % 4) {
                0 -> Color.parseColor("#13D4EF")
                1 -> Color.parseColor("#BDB14C")
                2 -> Color.parseColor("#B75AB6")
                else -> Color.parseColor("#36C87C")
            }
            holder.rowHomeRecommendBinding.textViewRowHomeRecommendMBTI.setBackgroundColor(colorResource)

            holder.rowHomeRecommendBinding.itemMainProductThumbnail.setOnClickListener {
                mainActivity.replaceFragment(MainFragmentName.PRODUCT_FRAGMENT,true,true,null)
            }
        }
    }

    // 홈(추천) - 신규 코디 리사이클러 뷰 어뎁터
    inner class HomeRecommendNewCoordiRecyclerViewAdapter: RecyclerView.Adapter<HomeRecommendNewCoordiRecyclerViewAdapter.HomeRecommendNewCoordiViewHolder>(){
        inner class HomeRecommendNewCoordiViewHolder(rowHomeRecommendNewCoordiBinding: RowHomeRecommendNewCoordiBinding): RecyclerView.ViewHolder(rowHomeRecommendNewCoordiBinding.root){
            val rowHomeRecommendNewCoordiBinding: RowHomeRecommendNewCoordiBinding

            init {
                this.rowHomeRecommendNewCoordiBinding = rowHomeRecommendNewCoordiBinding

                this.rowHomeRecommendNewCoordiBinding.root.layoutParams = ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT
                )
            }
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeRecommendNewCoordiViewHolder {
            // val rowCoordinatorInfoBinding = RowCoordinatorInfoBinding.inflate(layoutInflater)

            val rowHomeRecommendNewCoordiBinding = DataBindingUtil.inflate<RowHomeRecommendNewCoordiBinding>(
                layoutInflater, R.layout.row_home_recommend_new_coordi, parent, false
            )
            val rowHomeRecommendNewCoordiViewModel = RowHomeRecommendNewCoordiViewModel()
            rowHomeRecommendNewCoordiBinding.rowHomeRecommendNewCoordiViewModel = rowHomeRecommendNewCoordiViewModel
            rowHomeRecommendNewCoordiBinding.lifecycleOwner = this@HomeRecommendFragment


            val homeRecommendNewCoordiViewHolder = HomeRecommendNewCoordiViewHolder(rowHomeRecommendNewCoordiBinding)

            return homeRecommendNewCoordiViewHolder
        }

        override fun getItemCount(): Int {
            return 12
        }

        override fun onBindViewHolder(holder: HomeRecommendNewCoordiViewHolder, position: Int) {
            // position 값에 따라 다른 이미지 설정
            val imageResource = when (position % 4) {
                0 -> R.drawable.iu_image2
                1 -> R.drawable.iu_image3
                2 -> R.drawable.iu_image5
                else -> R.drawable.iu_image8
            }
            holder.rowHomeRecommendNewCoordiBinding.itemMainProductThumbnail3.setImageResource(imageResource)

            // position 값에 따라 다른 MBTI 색상 설정
            val colorResource = when (position % 4) {
                0 -> Color.parseColor("#13D4EF")
                1 -> Color.parseColor("#BDB14C")
                2 -> Color.parseColor("#B75AB6")
                else -> Color.parseColor("#36C87C")
            }
            holder.rowHomeRecommendNewCoordiBinding.itemMainProductMbti3.setBackgroundColor(colorResource)

            holder.rowHomeRecommendNewCoordiBinding.itemMainProductThumbnail3.setOnClickListener {
                mainActivity.replaceFragment(MainFragmentName.PRODUCT_FRAGMENT,true,true,null)
            }
        }
    }
}