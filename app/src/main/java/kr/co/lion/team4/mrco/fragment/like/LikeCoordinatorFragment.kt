package kr.co.lion.team4.mrco.fragment.like

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
import kr.co.lion.team4.mrco.MainActivity
import kr.co.lion.team4.mrco.MainFragmentName
import kr.co.lion.team4.mrco.R
import kr.co.lion.team4.mrco.databinding.FragmentLikeCoordinatorBinding
import kr.co.lion.team4.mrco.databinding.RowLikeCoordinator2Binding
import kr.co.lion.team4.mrco.databinding.RowLikeCoordinatorBinding
import kr.co.lion.team4.mrco.viewmodel.like.RowLikeCoordinator2ViewModel
import kr.co.lion.team4.mrco.viewmodel.like.RowLikeCoordinatorViewModel

class LikeCoordinatorFragment : Fragment() {

    // 원빈 - 좋아요 화면(코디네이터)

    lateinit var fragmentLikeCoordinatorBinding: FragmentLikeCoordinatorBinding
    lateinit var mainActivity: MainActivity

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        fragmentLikeCoordinatorBinding = FragmentLikeCoordinatorBinding.inflate(inflater)
        mainActivity = activity as MainActivity

        // 리사이클러 뷰
        settingRecyclerViewLikeCoordinator()

        return fragmentLikeCoordinatorBinding.root
    }

    // 하단 바 홈으로 체크 표시 설정
    fun settingBottomTabs() {
        mainActivity.activityMainBinding.apply {
            val menuItemId = R.id.main_bottom_navi_like
            mainActivity.activityMainBinding.mainBottomNavi.menu.findItem(menuItemId)?.isChecked = true
        }
    }

    // 리사이클러 뷰 설정
    fun settingRecyclerViewLikeCoordinator() {
        fragmentLikeCoordinatorBinding.apply {
            recyclerViewLikeCoordinator.apply {
                // 어뎁터 및 레이아웃 매니저 설정
                adapter = LikeCoordinatorRecyclerViewAdapter()
                layoutManager = LinearLayoutManager(mainActivity)
            }
        }
    }

    // 리사이클러 뷰 어뎁터
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

            // position 값에 따라 다른 이미지 설정
            val imageResource = when (position % 5) {
                0 -> R.drawable.iu_image
                1 -> R.drawable.iu_image2
                2 -> R.drawable.iu_image3
                3 -> R.drawable.iu_image4
                else -> R.drawable.iu_image5
            }
            holder.rowLikeCoordinatorBinding.imageViewRowLikeCoordinatorProfile.setImageResource(imageResource)

            holder.rowLikeCoordinatorBinding.imageViewRowLikeCoordinatorProfile.setOnClickListener {
                mainActivity.replaceFragment(MainFragmentName.COORDINATOR_MAIN, true, true, null)
                Log.d("test1234", "좋아요(코디네이터) 화면 : imageView - Click / 코디네이터 메인으로 이동")
            }

            // (팔로우/팔로잉) 버튼 클릭 시
            holder.rowLikeCoordinatorBinding.buttonRowLikeCoordinatorFollower.setOnClickListener {
                holder.rowLikeCoordinatorBinding.buttonRowLikeCoordinatorFollower.apply {
                    val newTintList = if (text == "팔로우") {
                        Log.d("test1234", "좋아요(코디네이터) 화면 : button - Click / 다시 팔로잉")
                        text = "팔로잉"
                        ContextCompat.getColorStateList(context, R.color.buttonFollowing)
                    } else {
                        Log.d("test1234", "좋아요(코디네이터) 화면 : button - Click / 팔로잉 취소")
                        text = "팔로우"
                        ContextCompat.getColorStateList(context, R.color.buttonFollow)
                    }
                    backgroundTintList = newTintList
                }
            }
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
            val rowLikeCoordinator2ViewModel = RowLikeCoordinator2ViewModel()

            // position 값에 따라 다른 이미지 설정
            when (position % 5) {
                0 -> holder.rowLikeCoordinator2Binding.imageViewCoordinatorInfo.setImageResource(R.drawable.iu_image)
                1 -> holder.rowLikeCoordinator2Binding.imageViewCoordinatorInfo.setImageResource(R.drawable.iu_image2)
                2 -> holder.rowLikeCoordinator2Binding.imageViewCoordinatorInfo.setImageResource(R.drawable.iu_image3)
                3 -> holder.rowLikeCoordinator2Binding.imageViewCoordinatorInfo.setImageResource(R.drawable.iu_image4)
                else -> holder.rowLikeCoordinator2Binding.imageViewCoordinatorInfo.setImageResource(R.drawable.iu_image5)
            }

            holder.rowLikeCoordinator2Binding.root.setOnClickListener {
                mainActivity.replaceFragment(MainFragmentName.PRODUCT_FRAGMENT, true, true, null)
            }

        }
    }
}