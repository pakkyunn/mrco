package kr.co.lion.team4.mrco.fragment.like

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.tabs.TabLayout
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kr.co.lion.team4.mrco.MainActivity
import kr.co.lion.team4.mrco.MainFragmentName
import kr.co.lion.team4.mrco.R
import kr.co.lion.team4.mrco.databinding.FragmentLikeProductBinding
import kr.co.lion.team4.mrco.databinding.RowLikeCoordinatorBinding
import kr.co.lion.team4.mrco.databinding.RowLikeProductBinding
import kr.co.lion.team4.mrco.viewmodel.like.LikeCoordinatorViewModel
import kr.co.lion.team4.mrco.viewmodel.like.LikeProductViewModel
import kr.co.lion.team4.mrco.viewmodel.like.RowLikeCoordinatorViewModel

class LikeProductFragment : Fragment() {

    // 원빈 - 좋아요 화면(코디네이터)

    lateinit var fragmentLikeProductBinding: FragmentLikeProductBinding
    lateinit var mainActivity: MainActivity

    lateinit var likeProductViewModel: LikeProductViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        fragmentLikeProductBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_like_product, container, false)
        likeProductViewModel = LikeProductViewModel()
        fragmentLikeProductBinding.likeProductViewModel = LikeProductViewModel()
        fragmentLikeProductBinding.lifecycleOwner = this

        mainActivity = activity as MainActivity

        // 툴바, 하단바, 탭 관련
        settingTabs()
        settingLikeTab()

        // 리사이클러 뷰
        settingRecyclerViewLikeProduct()

        return fragmentLikeProductBinding.root
    }

    // 인기 코디네이터 리사이클러 뷰 설정
    fun settingRecyclerViewLikeProduct() {
        fragmentLikeProductBinding.apply {
            recyclerViewLikeCoordi.apply {
                // 어뎁터 및 레이아웃 매니저 설정
                adapter = LikeProductRecyclerViewAdapter()
                layoutManager = GridLayoutManager(mainActivity, 2)
            }
        }
    }

    // 탭바 및 바텀바 위치 설정
    fun settingTabs() {
        fragmentLikeProductBinding.apply {
            val tabLayout = tabsLike
            tabLayout.getTabAt(0)?.select()
        }
        mainActivity.activityMainBinding.apply {
            val bottomBar = mainBottomNavi
            bottomBar.selectedItemId = R.id.main_bottom_navi_like
        }
    }

    // 인기 코디네이터 리사이클러 뷰 어뎁터
    inner class LikeProductRecyclerViewAdapter: RecyclerView.Adapter<LikeProductRecyclerViewAdapter.LikeProductViewHolder>(){
        inner class LikeProductViewHolder(rowLikeProductBinding: RowLikeProductBinding): RecyclerView.ViewHolder(rowLikeProductBinding.root){
            val rowLikeProductBinding: RowLikeProductBinding

            init {
                this.rowLikeProductBinding = rowLikeProductBinding

                this.rowLikeProductBinding.root.layoutParams = ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT
                )
            }
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LikeProductViewHolder {
            val rowLikeProductBinding = RowLikeProductBinding.inflate(layoutInflater)
            val likeProductViewHolder = LikeProductViewHolder(rowLikeProductBinding)

            return likeProductViewHolder
        }

        override fun getItemCount(): Int {
            return 20
        }

        override fun onBindViewHolder(holder: LikeProductViewHolder, position: Int) {

        }
    }


    // Like 상단 탭 설정
    fun settingLikeTab(){
        CoroutineScope(Dispatchers.Main).launch {
            fragmentLikeProductBinding.apply {
                val tabLayout = tabsLike

                // 탭 선택 리스너 설정
                tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
                    override fun onTabSelected(tab: TabLayout.Tab?) {
                        // 선택된 탭이 첫 번째 탭인 경우
                        if (tab?.position == 0) {
                            mainActivity.removeFragment(MainFragmentName.LIKE_COORDINATOR)
                            mainActivity.replaceFragment(MainFragmentName.LIKE_PRODUCT, false, false, null)
                        }
                        else {
                            mainActivity.removeFragment(MainFragmentName.LIKE_PRODUCT)
                            mainActivity.replaceFragment(MainFragmentName.LIKE_COORDINATOR, false, false, null)
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