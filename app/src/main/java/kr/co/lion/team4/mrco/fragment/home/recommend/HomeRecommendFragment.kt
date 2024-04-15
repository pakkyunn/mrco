package kr.co.lion.team4.mrco.fragment.home.recommend

import android.os.Bundle
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

        return fragmentHomeRecommendBinding.root
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
                    ViewGroup.LayoutParams.MATCH_PARENT,
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
            return 6
        }

        override fun onBindViewHolder(holder: HomeRecommendViewHolder, position: Int) {
            holder.rowHomeRecommendBinding.itemMainProductThumbnail.setOnClickListener {
                mainActivity.replaceFragment(MainFragmentName.PRODUCT_FRAGMENT,true,true,null)
            }
            holder.rowHomeRecommendBinding.itemMainProductThumbnail2.setOnClickListener {
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
                    ViewGroup.LayoutParams.MATCH_PARENT,
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
            return 6
        }

        override fun onBindViewHolder(holder: HomeRecommendNewCoordiViewHolder, position: Int) {
            holder.rowHomeRecommendNewCoordiBinding.itemMainProductThumbnail3.setOnClickListener {
                mainActivity.replaceFragment(MainFragmentName.PRODUCT_FRAGMENT,true,true,null)
            }
            holder.rowHomeRecommendNewCoordiBinding.itemMainProductThumbnail4.setOnClickListener {
                mainActivity.replaceFragment(MainFragmentName.PRODUCT_FRAGMENT,true,true,null)
            }
        }
    }
}