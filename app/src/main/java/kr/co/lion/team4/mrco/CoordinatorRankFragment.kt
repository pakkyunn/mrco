package kr.co.lion.team4.mrco

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kr.co.lion.team4.mrco.databinding.FragmentCoordinatorRankBinding
import kr.co.lion.team4.mrco.databinding.RowCoordinatorInfoBinding
import kr.co.lion.team4.mrco.databinding.RowCoordinatorRankBinding

class CoordinatorRankFragment : Fragment() {

    lateinit var fragmentCoordinatorRankBinding: FragmentCoordinatorRankBinding
    lateinit var mainActivity: MainActivity

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment

        fragmentCoordinatorRankBinding = FragmentCoordinatorRankBinding.inflate(layoutInflater)
        mainActivity = activity as MainActivity

        settingToolbarCoordinatorRank()
        settingRecyclerViewCoordinatorInfo()

        return fragmentCoordinatorRankBinding.root
    }

    // 툴바 설정
    fun settingToolbarCoordinatorRank(){
        fragmentCoordinatorRankBinding.apply {
            toolbarCoordinatorRank.apply {
                title = "인기 코디네이터"
            }
        }
    }

    // 코디네이터 소개 리사클러뷰 설정
    fun settingRecyclerViewCoordinatorInfo() {
        fragmentCoordinatorRankBinding.apply {
            recyclerViewCoordinatorRank.apply {
                // 어뎁터 및 레이아웃 매니저 설정
                adapter = CoordinatorRankRecyclerViewAdapter()
                layoutManager = LinearLayoutManager(mainActivity)
            }
        }
    }

    // 코디네이터 소개 리사이클러 뷰 어뎁터
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
            val rowCoordinatorRankBinding = RowCoordinatorRankBinding.inflate(layoutInflater)
            val coordinatorRankViewHolder = CorrdinatorRankViewHolder(rowCoordinatorRankBinding)

            return coordinatorRankViewHolder
        }

        override fun getItemCount(): Int {
            return 6
        }

        override fun onBindViewHolder(holder: CorrdinatorRankViewHolder, position: Int) {
            holder.rowCoordinatorRankBinding.textViewRowCoordinatorRankRank.text = "${position + 1}"
        }
    }


}