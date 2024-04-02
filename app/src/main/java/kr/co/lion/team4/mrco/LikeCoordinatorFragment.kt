package kr.co.lion.team4.mrco

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.tabs.TabLayout
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kr.co.lion.team4.mrco.databinding.FragmentLikeCoordinatorBinding
import kr.co.lion.team4.mrco.databinding.RowLikeCoordinator2Binding
import kr.co.lion.team4.mrco.databinding.RowLikeCoordinatorBinding

class LikeCoordinatorFragment : Fragment() {

    lateinit var fragmentLikeCoordinatorBinding: FragmentLikeCoordinatorBinding
    lateinit var mainActivity: MainActivity

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment

        fragmentLikeCoordinatorBinding = FragmentLikeCoordinatorBinding.inflate(layoutInflater)
        mainActivity = activity as MainActivity

        settingRecyclerViewLikeCoordinator()
        settingTabs()
        toolbarSetting()
        mainActivity.removeTabsBar()

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
            val rowLikeCoordinatorBinding = RowLikeCoordinatorBinding.inflate(layoutInflater)
            val likeCorrdinatorViewHolder = LikeCorrdinatorViewHolder(rowLikeCoordinatorBinding)

            return likeCorrdinatorViewHolder
        }

        override fun getItemCount(): Int {
            return 10
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
            val rowLikeCoordinator2Binding = RowLikeCoordinator2Binding.inflate(layoutInflater)
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