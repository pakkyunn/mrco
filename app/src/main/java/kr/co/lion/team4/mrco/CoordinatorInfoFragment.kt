package kr.co.lion.team4.mrco

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kr.co.lion.team4.mrco.databinding.FragmentCoordinatorInfoBinding
import kr.co.lion.team4.mrco.databinding.RowCoordinatorInfoBinding

class CoordinatorInfoFragment : Fragment() {

    lateinit var fragmentCoordinatorInfoBinding: FragmentCoordinatorInfoBinding
    lateinit var mainActivity: MainActivity

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment

        fragmentCoordinatorInfoBinding = FragmentCoordinatorInfoBinding.inflate(inflater)
        mainActivity = activity as MainActivity

        settingToolbarCoordinatorInfo()
        settingRecyclerViewCoordinatorInfo()


        return fragmentCoordinatorInfoBinding.root
    }

    // 툴바 설정
    fun settingToolbarCoordinatorInfo(){
        fragmentCoordinatorInfoBinding.apply {
            toolbarCoordinatorInfo.apply {
                title = "코디네이터 소개"
            }
        }
    }

    // 코디네이터 소개 리사클러뷰 설정
    fun settingRecyclerViewCoordinatorInfo() {
        fragmentCoordinatorInfoBinding.apply {
            recyclerViewCoordinatorInfo.apply {
                // 어뎁터 및 레이아웃 매니저 설정
                adapter = CoordinatorInfoRecyclerViewAdapter()
                layoutManager = GridLayoutManager(mainActivity, 2)
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
            val rowCoordinatorInfoBinding = RowCoordinatorInfoBinding.inflate(layoutInflater)
            val coordinatorInfoViewHolder = CorrdinatorInfoViewHolder(rowCoordinatorInfoBinding)

            return coordinatorInfoViewHolder
        }

        override fun getItemCount(): Int {
            return 6
        }

        override fun onBindViewHolder(holder: CorrdinatorInfoViewHolder, position: Int) {

        }
    }
}