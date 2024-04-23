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
import kr.co.lion.team4.mrco.dao.CoordinatorDao
import kr.co.lion.team4.mrco.dao.ProductDao
import kr.co.lion.team4.mrco.databinding.FragmentCoordinatorRankBinding
import kr.co.lion.team4.mrco.databinding.RowCoordinatorRank2Binding
import kr.co.lion.team4.mrco.databinding.RowCoordinatorRankBinding
import kr.co.lion.team4.mrco.model.CoordinatorModel
import kr.co.lion.team4.mrco.model.ProductModel
import kr.co.lion.team4.mrco.viewmodel.coordinator.CoordinatorRankViewModel
import kr.co.lion.team4.mrco.viewmodel.coordinator.RowCoordinatorRank2ViewModel
import kr.co.lion.team4.mrco.viewmodel.coordinator.RowCoordinatorRankViewModel
import java.text.NumberFormat
import java.util.Locale

class CoordinatorRankFragment : Fragment() {

    // 원빈 - 인기 코디네이터 화면
    lateinit var fragmentCoordinatorRankBinding: FragmentCoordinatorRankBinding
    lateinit var mainActivity: MainActivity

    lateinit var coordinatorRankViewModel: CoordinatorRankViewModel

    var coordinatorPosition = -1

    // 모든 코디네이터 정보를 담고 있을 리스트
    var coordinatorList = mutableListOf<CoordinatorModel>()
    
    // 모든 코디네이터의 상품들 정보를 담고 있을 리스트
    var productList = mutableListOf<ProductModel>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment

        //fragmentCoordinatorRankBinding = FragmentCoordinatorRankBinding.inflate(layoutInflater)
        fragmentCoordinatorRankBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_coordinator_rank, container, false)
        coordinatorRankViewModel = CoordinatorRankViewModel()
        fragmentCoordinatorRankBinding.coordinatorRankViewModel = CoordinatorRankViewModel()
        fragmentCoordinatorRankBinding.lifecycleOwner = this

        mainActivity = activity as MainActivity

        // 데이터 가져오기
        gettingCoordinatorData()

        // 리사이클러 뷰
        settingRecyclerViewCoordinatorRank()

        return fragmentCoordinatorRankBinding.root
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
            return coordinatorList.size
        }

        override fun onBindViewHolder(holder: CorrdinatorRankViewHolder, position: Int) {
            gettingProductData(position)

            holder.rowCoordinatorRankBinding.textViewRowCoordinatorRankNumber.text = "${position + 1}"

            CoroutineScope(Dispatchers.Main).launch {
                CoordinatorDao.getCoordinatorImage(mainActivity, coordinatorList[position].coordi_photo, holder.rowCoordinatorRankBinding.imageViewRowCoordinatorRankProfile)
            }

            holder.rowCoordinatorRankBinding.textViewRowCoordinatorRankName.text = coordinatorList[position].coordi_name
            holder.rowCoordinatorRankBinding.textViewRowCoordinatorRankFollower.text =
                "${NumberFormat.getNumberInstance(Locale.getDefault()).format(coordinatorList[position].coordi_followers)}"

            val bundle = Bundle()
            bundle.putInt("coordi_idx", coordinatorList[position].coordi_idx)

            holder.rowCoordinatorRankBinding.imageViewRowCoordinatorRankProfile.setOnClickListener {
                mainActivity.replaceFragment(MainFragmentName.COORDINATOR_MAIN, true, true, bundle)
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
            coordinatorPosition = position
        }
    }

    // 내부 리사이클러 뷰 어댑터
    inner class InnerRecyclerViewAdapter() : RecyclerView.Adapter<InnerRecyclerViewAdapter.InnerViewHolder>() {
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

            holder.rowCoordinatorRank2Binding.root.setOnClickListener {
                mainActivity.replaceFragment(MainFragmentName.PRODUCT_FRAGMENT, true, true, null)
            }
        }
    }

    // 모든 코디네이터의 데이터를 가져와 메인 화면의 RecyclerView를 갱신한다.
    fun gettingCoordinatorData() {
        CoroutineScope(Dispatchers.Main).launch {
            // 모든 코디네이터의 정보를 가져온다. (연동 On)
            coordinatorList = CoordinatorDao.getCoordinatorAllRank()
            Log.d("test1234", "인기 코디네이터 페이지 - coordinatorList: ${coordinatorList.size}명")
            fragmentCoordinatorRankBinding.recyclerViewCoordinatorRank.adapter?.notifyDataSetChanged()
        }
    }

    // 모든 코디네이터의 데이터를 가져와 메인 화면의 RecyclerView를 갱신한다.
    fun gettingProductData(coordinatorIdx: Int){
        CoroutineScope(Dispatchers.Main).launch {
            // 모든 코디네이터의 정보를 가져온다. (연동 On)
            productList = ProductDao.gettingProductListOneCoordinator(coordinatorIdx)
            Log.d("test1234", "코디네이터 정보 페이지 - 상품들: ${productList.size}개")
        }
    }
}