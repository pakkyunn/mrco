package kr.co.lion.team4.mrco.fragment.like

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kr.co.lion.team4.mrco.MainActivity
import kr.co.lion.team4.mrco.R
import kr.co.lion.team4.mrco.databinding.FragmentLikeCoordinatorBinding
import kr.co.lion.team4.mrco.databinding.RowLikeCoordinator2Binding
import kr.co.lion.team4.mrco.databinding.RowLikeCoordinatorBinding
import kr.co.lion.team4.mrco.viewmodel.LikeCoordinatorViewModel
import kr.co.lion.team4.mrco.viewmodel.RowLikeCoordinator2ViewModel
import kr.co.lion.team4.mrco.viewmodel.RowLikeCoordinatorViewModel

class LikeCoordinatorFragment : Fragment() {

    lateinit var fragmentLikeCoordinatorBinding: FragmentLikeCoordinatorBinding
    lateinit var mainActivity: MainActivity

    lateinit var likeCoordinatorViewModel: LikeCoordinatorViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        fragmentLikeCoordinatorBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_like_coordinator, container, false)
        likeCoordinatorViewModel = LikeCoordinatorViewModel()
        fragmentLikeCoordinatorBinding.likeCoordinatorViewModel = LikeCoordinatorViewModel()
        fragmentLikeCoordinatorBinding.lifecycleOwner = this

        mainActivity = activity as MainActivity

        // 툴바, 하단바, 탭 관련
        settingTabs()
        toolbarSetting()
        mainActivity.removeTabsBar()

        // 리사이클러 뷰
        settingRecyclerViewLikeCoordinator()

        return fragmentLikeCoordinatorBinding.root
    }

    fun toolbarSetting(){
        mainActivity.activityMainBinding.apply {
            toolbarMain.apply {
                title = null
            }
        }
    }

    // 인기 코디네이터 리사이클러 뷰 설정
    fun settingRecyclerViewLikeCoordinator() {
        fragmentLikeCoordinatorBinding.apply {
            recyclerViewLikeCoordinator.apply {
                // 어뎁터 및 레이아웃 매니저 설정
                adapter = LikeCoordinatorRecyclerViewAdapter()
                layoutManager = LinearLayoutManager(mainActivity)
            }
        }
    }

    // 탭바 및 바텀바 위치 설정
    fun settingTabs(){
        fragmentLikeCoordinatorBinding.apply {
            val tabLayout = tabs
            tabLayout.getTabAt(1)?.select()
        }
        mainActivity.activityMainBinding.apply {
            val bottomBar = mainBottomNavi
            bottomBar.selectedItemId = R.id.main_bottom_navi_like
        }
    }

    // 인기 코디네이터 리사이클러 뷰 어뎁터
    inner class LikeCoordinatorRecyclerViewAdapter: RecyclerView.Adapter<LikeCoordinatorRecyclerViewAdapter.LikeCorrdinatorViewHolder>(){
        inner class LikeCorrdinatorViewHolder(rowLikeCoordinatorBinding: RowLikeCoordinatorBinding): RecyclerView.ViewHolder(rowLikeCoordinatorBinding.root){
            val rowLikeCoordinatorBinding: RowLikeCoordinatorBinding

            init {
                this.rowLikeCoordinatorBinding = rowLikeCoordinatorBinding

                this.rowLikeCoordinatorBinding.root.layoutParams = ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT
                )
            }
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LikeCorrdinatorViewHolder {
            val rowLikeCoordinatorBinding = DataBindingUtil.inflate<RowLikeCoordinatorBinding>(
                layoutInflater, R.layout.row_like_coordinator, parent, false
            )
            val rowLikeCoordinatorViewModel = RowLikeCoordinatorViewModel()
            rowLikeCoordinatorBinding.rowLikeCoordinatorViewModel = rowLikeCoordinatorViewModel
            rowLikeCoordinatorBinding.lifecycleOwner = this@LikeCoordinatorFragment

            val likeCorrdinatorViewHolder = LikeCorrdinatorViewHolder(rowLikeCoordinatorBinding)

            return likeCorrdinatorViewHolder
        }

        override fun getItemCount(): Int {
            return 6
        }

        override fun onBindViewHolder(holder: LikeCorrdinatorViewHolder, position: Int) {
            // 내부 리사이클러 뷰 설정
            val innerRecyclerView = holder.rowLikeCoordinatorBinding.recyclerViewLikeCoordinator2
            innerRecyclerView.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            innerRecyclerView.adapter = InnerRecyclerViewAdapter() // 내부 리사이클러 뷰 어댑터 설정
        }
    }

    // 내부 리사이클러 뷰 어댑터
    inner class InnerRecyclerViewAdapter : RecyclerView.Adapter<InnerRecyclerViewAdapter.InnerViewHolder>() {
        inner class InnerViewHolder(rowLikeCoordinator2Binding: RowLikeCoordinator2Binding) : RecyclerView.ViewHolder(rowLikeCoordinator2Binding.root) {
            // 내부 리사이클러 뷰의 ViewHolder 내용 정의
            val rowLikeCoordinator2Binding: RowLikeCoordinator2Binding

            init {
                this.rowLikeCoordinator2Binding = rowLikeCoordinator2Binding

                this.rowLikeCoordinator2Binding.root.layoutParams = ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT
                )
            }
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): InnerViewHolder {
            // val rowLikeCoordinator2Binding = RowLikeCoordinator2Binding.inflate(layoutInflater)

            val rowLikeCoordinator2Binding = DataBindingUtil.inflate<RowLikeCoordinator2Binding>(
                layoutInflater, R.layout.row_like_coordinator2, parent, false
            )
            val rowLikeCoordinator2ViewModel = RowLikeCoordinator2ViewModel()
            rowLikeCoordinator2Binding.rowLikeCoordinator2ViewModel = rowLikeCoordinator2ViewModel
            rowLikeCoordinator2Binding.lifecycleOwner = this@LikeCoordinatorFragment

            val innerViewHolder = InnerViewHolder(rowLikeCoordinator2Binding)

            return innerViewHolder
        }

        override fun getItemCount(): Int {
            return 10
        }

        override fun onBindViewHolder(holder: InnerViewHolder, position: Int) {

        }
    }

}