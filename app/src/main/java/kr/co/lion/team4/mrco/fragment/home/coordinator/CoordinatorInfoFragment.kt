package kr.co.lion.team4.mrco.fragment.home.coordinator

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.tabs.TabLayout
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kr.co.lion.team4.mrco.MainActivity
import kr.co.lion.team4.mrco.MainFragmentName
import kr.co.lion.team4.mrco.R
import kr.co.lion.team4.mrco.databinding.FragmentCoordinatorInfoBinding
import kr.co.lion.team4.mrco.databinding.RowCoordinatorInfoBinding
import kr.co.lion.team4.mrco.viewmodel.coordinator.RowCoordinatorInfoViewModel

class CoordinatorInfoFragment : Fragment() {
    
    // 원빈 - 코디네이터 정보 화면

    lateinit var fragmentCoordinatorInfoBinding: FragmentCoordinatorInfoBinding
    lateinit var mainActivity: MainActivity

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment

        fragmentCoordinatorInfoBinding = FragmentCoordinatorInfoBinding.inflate(inflater)

        mainActivity = activity as MainActivity

        // 리사이클러 뷰
        settingRecyclerViewCoordinatorInfo()

        return fragmentCoordinatorInfoBinding.root
    }


    // 코디네이터 소개 리사이클러 뷰 설정
    fun settingRecyclerViewCoordinatorInfo() {
        fragmentCoordinatorInfoBinding.apply {

            val screenWidthDp = resources.configuration.screenWidthDp

            if (screenWidthDp >= 600) {
                // 너비가 600dp 이상인 디바이스에서 실행될 동작
                recyclerViewCoordinatorInfo.apply {
                    // 어뎁터 및 레이아웃 매니저 설정
                    adapter = CoordinatorInfoRecyclerViewAdapter()
                    layoutManager = GridLayoutManager(mainActivity, 3)
                }
            }
            else {
                // 너비가 600dp 미만인 디바이스에서 실행될 동작
                recyclerViewCoordinatorInfo.apply {
                    // 어뎁터 및 레이아웃 매니저 설정
                    adapter = CoordinatorInfoRecyclerViewAdapter()
                    layoutManager = GridLayoutManager(mainActivity, 2)
                }
            }
        }
    }

    // 코디네이터 소개 리사이클러 뷰 어뎁터
    inner class CoordinatorInfoRecyclerViewAdapter: RecyclerView.Adapter<CoordinatorInfoRecyclerViewAdapter.CorrdinatorInfoViewHolder>(){
        inner class CorrdinatorInfoViewHolder(rowCoordinatorInfoBinding: RowCoordinatorInfoBinding): RecyclerView.ViewHolder(rowCoordinatorInfoBinding.root){
            val rowCoordinatorInfoBinding: RowCoordinatorInfoBinding

            init {
                this.rowCoordinatorInfoBinding = rowCoordinatorInfoBinding

                this.rowCoordinatorInfoBinding.root.layoutParams = ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT
                )
            }
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CorrdinatorInfoViewHolder {
            // val rowCoordinatorInfoBinding = RowCoordinatorInfoBinding.inflate(layoutInflater)

            val rowCoordinatorInfoBinding = DataBindingUtil.inflate<RowCoordinatorInfoBinding>(
                layoutInflater, R.layout.row_coordinator_info, parent, false
            )
            val rowCoordinatorInfoViewModel = RowCoordinatorInfoViewModel()
            rowCoordinatorInfoBinding.rowCoordinatorInfoViewModel = rowCoordinatorInfoViewModel
            rowCoordinatorInfoBinding.lifecycleOwner = this@CoordinatorInfoFragment

            val coordinatorInfoViewHolder = CorrdinatorInfoViewHolder(rowCoordinatorInfoBinding)

            return coordinatorInfoViewHolder
        }

        override fun getItemCount(): Int {
            return 8
        }

        override fun onBindViewHolder(holder: CorrdinatorInfoViewHolder, position: Int) {
            holder.rowCoordinatorInfoBinding.root.setOnClickListener {
                mainActivity.replaceFragment(MainFragmentName.COORDINATOR_MAIN, true, true, null)
            }

            // position 값에 따라 다른 이미지 설정
            val imageResource = when (position % 8) {
                0 -> R.drawable.iu_image
                1 -> R.drawable.iu_image2
                2 -> R.drawable.iu_image8
                3 -> R.drawable.iu_image7
                4 -> R.drawable.iu_image3
                5 -> R.drawable.iu_image4
                6 -> R.drawable.iu_image5
                else -> R.drawable.iu_image6
            }
            holder.rowCoordinatorInfoBinding.imageViewCoordinatorInfo.setImageResource(imageResource)
        }
    }
}