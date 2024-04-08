package kr.co.lion.team4.mrco.fragment.mbtiproductmain

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
import kr.co.lion.team4.mrco.databinding.FragmentLikeProductBinding
import kr.co.lion.team4.mrco.databinding.FragmentMbtiProductMainBinding
import kr.co.lion.team4.mrco.databinding.RowLikeProductBinding
import kr.co.lion.team4.mrco.databinding.RowMbtiProductMainBinding
import kr.co.lion.team4.mrco.viewmodel.like.RowLikeProductViewModel

class MbtiProductMainFragment : Fragment() {

    lateinit var fragmentMbtiProductMainBinding: FragmentMbtiProductMainBinding
    lateinit var mainActivity: MainActivity

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment

        fragmentMbtiProductMainBinding = FragmentMbtiProductMainBinding.inflate(inflater)
        mainActivity = activity as MainActivity

        // 툴바, 하단바, 탭 관련
        mainActivity.removeBottomSheet()

        // 리사이클러 뷰
        settingRecyclerViewMbtiProductMain()

        return fragmentMbtiProductMainBinding.root
    }


    // 리사이클러 뷰 설정
    fun settingRecyclerViewMbtiProductMain() {
        fragmentMbtiProductMainBinding.apply {
            recyclerViewMbtiProductMain.apply {
                // 어뎁터 및 레이아웃 매니저 설정
                adapter = MbtiProductMainRecyclerViewAdapter()
            }
        }
    }

    // 리사이클러 뷰 어뎁터
    inner class MbtiProductMainRecyclerViewAdapter: RecyclerView.Adapter<MbtiProductMainRecyclerViewAdapter.MbtiProductMainViewHolder>(){
        inner class MbtiProductMainViewHolder(rowMbtiProductMainBinding: RowMbtiProductMainBinding): RecyclerView.ViewHolder(rowMbtiProductMainBinding.root){
            val rowMbtiProductMainBinding: RowMbtiProductMainBinding

            init {
                this.rowMbtiProductMainBinding = rowMbtiProductMainBinding

                this.rowMbtiProductMainBinding.root.layoutParams = ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT
                )
            }
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MbtiProductMainViewHolder {
            val rowMbtiProductMainBinding = RowMbtiProductMainBinding.inflate(layoutInflater)
            val mbtiProductMainViewHolder = MbtiProductMainViewHolder(rowMbtiProductMainBinding)

            return mbtiProductMainViewHolder
        }

        override fun getItemCount(): Int {
            return 20
        }

        override fun onBindViewHolder(holder: MbtiProductMainViewHolder, position: Int) {

        }
    }

    // 뒤로가기 처리
    fun backProcesss(){
        mainActivity.removeFragment(MainFragmentName.MBTI_PRODUCT_MAIN)
    }

}