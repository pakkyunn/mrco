package kr.co.lion.team4.mrco.fragment.coordinatormain

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.api.Distribution.BucketOptions.Linear
import kr.co.lion.team4.mrco.MainActivity
import kr.co.lion.team4.mrco.MainFragmentName
import kr.co.lion.team4.mrco.R
import kr.co.lion.team4.mrco.databinding.FragmentCoordinatorMainBinding
import kr.co.lion.team4.mrco.databinding.RowCoordinatorMainBinding
import kr.co.lion.team4.mrco.databinding.RowCoordinatorMainItemBinding
import kr.co.lion.team4.mrco.viewmodel.coordinator.CoordinatorMainViewModel
import kr.co.lion.team4.mrco.viewmodel.coordinator.RowCoordinatorMainViewModel


class CoordinatorMainFragment : Fragment() {

    // 원빈 - 코디네이터 화면(메인)

    lateinit var fragmentCoordinatorMainBinding: FragmentCoordinatorMainBinding
    lateinit var mainActivity: MainActivity

    lateinit var coordinatorMainViewModel: CoordinatorMainViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        fragmentCoordinatorMainBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_coordinator_main, container, false)
        coordinatorMainViewModel = CoordinatorMainViewModel()
        fragmentCoordinatorMainBinding.coordinatorMainViewModel = CoordinatorMainViewModel()
        fragmentCoordinatorMainBinding.lifecycleOwner = this

        mainActivity = activity as MainActivity

        // 툴바, 하단바, 탭 관련
        toolbarSetting()
        mainActivity.removeBottomSheet()

        // 팔로우/팔로잉 버튼
        settingButton()
        
        // 리사이클러 뷰
        settingRecyclerViewCoordinatorInfo()

        return fragmentCoordinatorMainBinding.root
    }

    // 툴바 설정
    fun toolbarSetting() {
        fragmentCoordinatorMainBinding.toolbarCoordinatorMain.apply {
            // 네비게이션
            setNavigationOnClickListener {
                backProcesss()
            }
        }
    }
    
    // 팔로우/팔로잉 버튼 클릭 시
    fun settingButton() {
        fragmentCoordinatorMainBinding.buttonCoordinatorMainFollower.apply {
            setOnClickListener {
                val newTintList = if (text == "팔로우") {
                    text = "팔로잉"
                    ContextCompat.getColorStateList(context, R.color.buttonFollowing)
                } else {
                    text = "팔로우"
                    ContextCompat.getColorStateList(context, R.color.buttonFollow)
                }
                backgroundTintList = newTintList
            }
        }
    }

    // 코디네이터 메인 리사클러뷰 설정
    fun settingRecyclerViewCoordinatorInfo() {
        fragmentCoordinatorMainBinding.apply {
            recyclerViewCoordinatorMain.apply {
                // 어뎁터 및 레이아웃 매니저 설정
                adapter = CoordinatorMainRecyclerViewAdapter()
                // layoutManager = GridLayoutManager(mainActivity, 2)
                layoutManager = LinearLayoutManager(mainActivity)
            }
        }
    }

    // 코디네이터 메인 리사이클러 뷰 어뎁터
    inner class CoordinatorMainRecyclerViewAdapter: RecyclerView.Adapter<CoordinatorMainRecyclerViewAdapter.CorrdinatorMainViewHolder>(){
        inner class CorrdinatorMainViewHolder(rowCoordinatorMainItemBinding: RowCoordinatorMainItemBinding): RecyclerView.ViewHolder(rowCoordinatorMainItemBinding.root){
            val rowCoordinatorMainItemBinding: RowCoordinatorMainItemBinding

            init {
                this.rowCoordinatorMainItemBinding = rowCoordinatorMainItemBinding

                this.rowCoordinatorMainItemBinding.root.layoutParams = ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT
                )
            }
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CorrdinatorMainViewHolder {
            val rowCoordinatorMainItemBinding = DataBindingUtil.inflate<RowCoordinatorMainItemBinding>(
                layoutInflater, R.layout.row_coordinator_main_item, parent, false
            )
            val rowCoordinatorMainViewModel = RowCoordinatorMainViewModel()
            rowCoordinatorMainItemBinding.rowCoordinatorMainViewModel = rowCoordinatorMainViewModel
            rowCoordinatorMainItemBinding.lifecycleOwner = this@CoordinatorMainFragment

            val coordinatorMainViewHolder = CorrdinatorMainViewHolder(rowCoordinatorMainItemBinding)

            return coordinatorMainViewHolder
        }

        override fun getItemCount(): Int {
            return 6
        }

        override fun onBindViewHolder(holder: CorrdinatorMainViewHolder, position: Int) {
            // position 값에 따라 다른 이미지 설정
            val imageResource = when (position % 5) {
                0 -> R.drawable.iu_image7
                1 -> R.drawable.iu_image3
                2 -> R.drawable.iu_image6
                3 -> R.drawable.iu_image2
                else -> R.drawable.iu_image4
            }
            holder.rowCoordinatorMainItemBinding.itemCoordinatorMainProductThumbnail.setImageResource(imageResource)

            holder.rowCoordinatorMainItemBinding.root.setOnClickListener {
                mainActivity.replaceFragment(MainFragmentName.PRODUCT_FRAGMENT, true, true ,null)
            }
        }
    }

    // 뒤로가기 처리
    fun backProcesss(){
        mainActivity.removeFragment(MainFragmentName.COORDINATOR_MAIN)
    }
}