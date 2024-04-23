package kr.co.lion.team4.mrco.fragment.home.coordinator

import android.os.Bundle
import android.util.Log
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
import kr.co.lion.team4.mrco.dao.CoordinatorDao
import kr.co.lion.team4.mrco.databinding.FragmentCoordinatorInfoBinding
import kr.co.lion.team4.mrco.databinding.RowCoordinatorInfoBinding
import kr.co.lion.team4.mrco.model.CoordinatorModel
import kr.co.lion.team4.mrco.model.UserModel
import kr.co.lion.team4.mrco.viewmodel.coordinator.RowCoordinatorInfoViewModel

class CoordinatorInfoFragment : Fragment() {
    
    // 원빈 - 코디네이터 정보 화면

    lateinit var fragmentCoordinatorInfoBinding: FragmentCoordinatorInfoBinding
    lateinit var mainActivity: MainActivity

    // 모든 사용자 정보를 담고 있을 리스트
    var coordinatorList = mutableListOf<CoordinatorModel>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment

        fragmentCoordinatorInfoBinding = FragmentCoordinatorInfoBinding.inflate(inflater)

        mainActivity = activity as MainActivity

        // 데이터 설정
        gettingMainData()

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
            return coordinatorList.size
        }

        override fun onBindViewHolder(holder: CorrdinatorInfoViewHolder, position: Int) {
            val bundle = Bundle()
            bundle.putInt("coordi_idx", coordinatorList[position].coordi_idx)

            holder.rowCoordinatorInfoBinding.root.setOnClickListener {
                mainActivity.replaceFragment(MainFragmentName.COORDINATOR_MAIN, true, true, bundle)
            }

            holder.rowCoordinatorInfoBinding.textViewRowInfoMBTI.text = coordinatorList[position].coordi_mbti
            holder.rowCoordinatorInfoBinding.textViewRowInfoName.text = coordinatorList[position].coordi_name

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

    // 현재 게시판의 데이터를 가져와 메인 화면의 RecyclerView를 갱신한다.
    fun gettingMainData(){
        CoroutineScope(Dispatchers.Main).launch {
            // 모든 코디네이터의 정보를 가져온다. (연동 On)
            coordinatorList = CoordinatorDao.getCoordinatorAll()
            Log.d("test1234", "코디네이터 정보 페이지 - coordinatorList: ${coordinatorList.size}명")
            fragmentCoordinatorInfoBinding.recyclerViewCoordinatorInfo.adapter?.notifyDataSetChanged()
        }
    }
}