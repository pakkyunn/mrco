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
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kr.co.lion.team4.mrco.MainActivity
import kr.co.lion.team4.mrco.MainFragmentName
import kr.co.lion.team4.mrco.R
import kr.co.lion.team4.mrco.dao.CoordinatorDao
import kr.co.lion.team4.mrco.dao.LikeDao
import kr.co.lion.team4.mrco.databinding.FragmentLikeCoordinatorBinding
import kr.co.lion.team4.mrco.databinding.RowLikeCoordinator2Binding
import kr.co.lion.team4.mrco.databinding.RowLikeCoordinatorBinding
import kr.co.lion.team4.mrco.model.CoordinatorModel
import kr.co.lion.team4.mrco.model.LikeModel
import kr.co.lion.team4.mrco.viewmodel.like.RowLikeCoordinator2ViewModel
import kr.co.lion.team4.mrco.viewmodel.like.RowLikeCoordinatorViewModel
import java.text.NumberFormat
import java.util.Locale

class LikeCoordinatorFragment : Fragment() {

    // 원빈 - 좋아요 화면(코디네이터)
    lateinit var fragmentLikeCoordinatorBinding: FragmentLikeCoordinatorBinding
    lateinit var mainActivity: MainActivity

    // 모든 회원의 코디네이터 팔로우 정보를 담고 있을 리스트
    var coordinatorsList = mutableListOf<LikeModel>()

    // 로그인한 회원이 팔로우 한 코디네이터의 인덱스 번호
    var coordinatorsFollowArray = mutableListOf<Int>()

    // 팔로우 한 코디네이터의 정보를 담고 있을 리스트
    var coordinatorsFollowList = mutableListOf<CoordinatorModel>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        fragmentLikeCoordinatorBinding = FragmentLikeCoordinatorBinding.inflate(inflater)
        mainActivity = activity as MainActivity

        // 리사이클러 뷰
        settingRecyclerViewLikeCoordinator()

        // 데이터를 가져온다.
        gettingCoordinatorsFollowData()

        return fragmentLikeCoordinatorBinding.root
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
            return coordinatorsFollowArray.size
        }

        override fun onBindViewHolder(holder: LikeCorrdinatorViewHolder, position: Int) {
            // 내부 리사이클러 뷰 설정
            val innerRecyclerView = holder.rowLikeCoordinatorBinding.recyclerViewLikeCoordinator2
            innerRecyclerView.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            innerRecyclerView.adapter = InnerRecyclerViewAdapter() // 내부 리사이클러 뷰 어댑터 설정

            CoroutineScope(Dispatchers.Main).launch {
                CoordinatorDao.getCoordinatorImage(mainActivity, coordinatorsFollowList[position].coordi_photo, holder.rowLikeCoordinatorBinding.imageViewRowLikeCoordinatorProfile)
            }

            holder.rowLikeCoordinatorBinding.textViewRowLikeCoordinatorName.text = coordinatorsFollowList[position].coordi_name
            holder.rowLikeCoordinatorBinding.textViewRowLikeCoordinatorFollower.text =
                "${NumberFormat.getNumberInstance(Locale.getDefault()).format(coordinatorsFollowList[position].coordi_followers)}"

            // 프로필 사진 클릭 시
            val bundle = Bundle()
            bundle.putInt("coordi_idx", coordinatorsFollowList[position].coordi_idx)
            holder.rowLikeCoordinatorBinding.imageViewRowLikeCoordinatorProfile.setOnClickListener {
                mainActivity.replaceFragment(MainFragmentName.COORDINATOR_MAIN, true, true, bundle)
            }


            for (i in 0 until coordinatorsList.size) {
                for (j in 0 until (coordinatorsList[i].like_coordinator_idx).size) {
                    if (coordinatorsList[i].like_coordinator_idx[j] == coordinatorsFollowList[position].coordi_idx) {
                        holder.rowLikeCoordinatorBinding.buttonRowLikeCoordinatorFollower.apply {
                            text = "팔로잉"
                            backgroundTintList = ContextCompat.getColorStateList(context, R.color.buttonFollowing)
                        }
                    }
                }
            }

            // (팔로우/팔로잉) 버튼 클릭 시
            holder.rowLikeCoordinatorBinding.buttonRowLikeCoordinatorFollower.setOnClickListener {
                holder.rowLikeCoordinatorBinding.buttonRowLikeCoordinatorFollower.apply {
                    if (text == "팔로우") {
                        text = "팔로잉"
                        backgroundTintList = ContextCompat.getColorStateList(context, R.color.buttonFollowing)
                        CoroutineScope(Dispatchers.Main).launch {
                            LikeDao.insertLikeCoordinatorData(mainActivity.loginUserIdx, coordinatorsFollowList[position].coordi_idx)
                        }
                        Log.d("test1234", "인기 코디네이터 화면 : ${coordinatorsFollowList[position].coordi_idx} 팔로우 추가")
                    } else {
                        text = "팔로우"
                        backgroundTintList = ContextCompat.getColorStateList(context, R.color.buttonFollow)
                        CoroutineScope(Dispatchers.Main).launch {
                            LikeDao.deleteLikeCoordinatorData(mainActivity.loginUserIdx, coordinatorsFollowList[position].coordi_idx)
                        }
                        Log.d("test1234", "인기 코디네이터 화면 : ${coordinatorsFollowList[position].coordi_idx} 팔로우 삭제")
                    }
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
            return 12
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

    // 모든 코디네이터의 팔로우 상태 데이터를 가져와 메인 화면의 RecyclerView를 갱신한다.
    fun gettingCoordinatorsFollowData() {
        CoroutineScope(Dispatchers.Main).launch {
            // 모든 코디네이터의 팔로우 상태 정보를 가져온다. (연동 On)
            coordinatorsList = LikeDao.getLikeData(mainActivity.loginUserIdx)
            for (i in 0 until coordinatorsList.size) {
                for (j in 0 until (coordinatorsList[i].like_coordinator_idx).size) {
                    coordinatorsFollowArray.add(coordinatorsList[i].like_coordinator_idx[j])
                }
            }
            coordinatorsFollowList = LikeDao.getCoordinatorInfo(coordinatorsFollowArray)
            Log.d("test1234", "Like 페이지(코디네이터) - 팔로우 한 코디네이터 정보 : ${coordinatorsFollowList}")
            fragmentLikeCoordinatorBinding.recyclerViewLikeCoordinator.adapter?.notifyDataSetChanged()
        }
    }
}