package kr.co.lion.team4.mrco.fragment.coordinatormain

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kr.co.lion.team4.mrco.MainActivity
import kr.co.lion.team4.mrco.MainFragmentName
import kr.co.lion.team4.mrco.R
import kr.co.lion.team4.mrco.databinding.FragmentCoordinatorMainBinding
import kr.co.lion.team4.mrco.databinding.RowCoordinatorMainBinding
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
        
        // 리사이클러 뷰
        settingRecyclerViewCoordinatorInfo()

        return fragmentCoordinatorMainBinding.root
    }

    // 툴바 설정
    fun toolbarSetting(){
        fragmentCoordinatorMainBinding.toolbarCoordinatorMain.apply {
                title = "(스타일리스트 명)"
                // 네비게이션
                setNavigationIcon(R.drawable.arrow_back_24px)
                setNavigationOnClickListener {
                    backProcesss()
            }
        }
    }

    // 코디네이터 메인 리사클러뷰 설정
    fun settingRecyclerViewCoordinatorInfo() {
        fragmentCoordinatorMainBinding.apply {
            recyclerViewCoordinatorMain.apply {
                // 어뎁터 및 레이아웃 매니저 설정
                adapter = CoordinatorMainRecyclerViewAdapter()
                layoutManager = GridLayoutManager(mainActivity, 2)
            }
        }
    }

    // 코디네이터 메인 리사이클러 뷰 어뎁터
    inner class CoordinatorMainRecyclerViewAdapter: RecyclerView.Adapter<CoordinatorMainRecyclerViewAdapter.CorrdinatorMainViewHolder>(){
        inner class CorrdinatorMainViewHolder(rowCoordinatorMainBinding: RowCoordinatorMainBinding): RecyclerView.ViewHolder(rowCoordinatorMainBinding.root){
            val rowCoordinatorMainBinding: RowCoordinatorMainBinding

            init {
                this.rowCoordinatorMainBinding = rowCoordinatorMainBinding

                this.rowCoordinatorMainBinding.root.layoutParams = ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT
                )
            }
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CorrdinatorMainViewHolder {
            val rowCoordinatorMainBinding = DataBindingUtil.inflate<RowCoordinatorMainBinding>(
                layoutInflater, R.layout.row_coordinator_main, parent, false
            )
            val rowCoordinatorMainViewModel = RowCoordinatorMainViewModel()
            rowCoordinatorMainBinding.rowCoordinatorMainViewModel = rowCoordinatorMainViewModel
            rowCoordinatorMainBinding.lifecycleOwner = this@CoordinatorMainFragment

            val coordinatorMainViewHolder = CorrdinatorMainViewHolder(rowCoordinatorMainBinding)

            return coordinatorMainViewHolder
        }

        override fun getItemCount(): Int {
            return 8
        }

        override fun onBindViewHolder(holder: CorrdinatorMainViewHolder, position: Int) {

        }
    }

    // 뒤로가기 처리
    fun backProcesss(){
        mainActivity.removeFragment(MainFragmentName.COORDINATOR_MAIN)
        mainActivity.viewBottomSheet()
    }

}