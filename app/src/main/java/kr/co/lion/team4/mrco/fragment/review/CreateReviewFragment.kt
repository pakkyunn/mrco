package kr.co.lion.team4.mrco.fragment.review

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kr.co.lion.team4.mrco.MainActivity
import kr.co.lion.team4.mrco.MainFragmentName
import kr.co.lion.team4.mrco.R
import kr.co.lion.team4.mrco.databinding.FragmentCreatedReviewBinding
import kr.co.lion.team4.mrco.databinding.FragmentReviewCreatedBinding
import kr.co.lion.team4.mrco.databinding.RowLikeProductBinding
import kr.co.lion.team4.mrco.databinding.RowReviewCreateCardviewBinding
import kr.co.lion.team4.mrco.viewmodel.CreateReviewViewModel
import kr.co.lion.team4.mrco.viewmodel.like.RowLikeProductViewModel

class CreateReviewFragment : Fragment() {
    lateinit var binding: FragmentReviewCreatedBinding
    lateinit var viewModel: CreateReviewViewModel
    lateinit var mainActivity: MainActivity

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_review_created, container, false)
        viewModel = ViewModelProvider(this).get(CreateReviewViewModel::class.java)
        mainActivity = activity as MainActivity

        // 리사이클러 뷰 설정
        settingRecyclerViewLikeProduct()

        return binding.root
    }

    // 리사이클러 뷰 설정
    fun settingRecyclerViewLikeProduct() {
        binding.apply {
            recyclerViewReviewCreateCardView.apply {
                // 어뎁터 및 레이아웃 매니저 설정
                adapter = CreateReviewRecyclerViewAdapter()
                layoutManager = LinearLayoutManager(mainActivity)
            }
        }
    }

    // 리사이클러 뷰 어뎁터
    inner class CreateReviewRecyclerViewAdapter: RecyclerView.Adapter<CreateReviewRecyclerViewAdapter.CreateReviewViewHolder>(){
        inner class CreateReviewViewHolder(rowReviewCreateCardViewBinding: RowReviewCreateCardviewBinding): RecyclerView.ViewHolder(rowReviewCreateCardViewBinding.root){
            val rowReviewCreateCardViewBinding: RowReviewCreateCardviewBinding

            init {
                this.rowReviewCreateCardViewBinding = rowReviewCreateCardViewBinding

                this.rowReviewCreateCardViewBinding.root.layoutParams = ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT
                )
            }
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CreateReviewViewHolder {
            val rowReviewCreateCardViewBinding = RowReviewCreateCardviewBinding.inflate(layoutInflater)
            val createReviewViewHolder = CreateReviewViewHolder(rowReviewCreateCardViewBinding)

            return createReviewViewHolder
        }

        override fun getItemCount(): Int {
            return 20
        }

        override fun onBindViewHolder(holder: CreateReviewViewHolder, position: Int) {
            holder.rowReviewCreateCardViewBinding.buttonRowReviewCreatedWriteReview.setOnClickListener {
                mainActivity.replaceFragment(MainFragmentName.WRITE_REVIEW, true, true, null)
            }
        }
    }

//    fun settingClickEvent(){
//        binding.apply {
//            // 전체 클릭시
//            tabItemCreatedReviewAll.setOnClickListener {
//                // 프래그먼트 컨테이너에 전체 리스트를 다 보여줌
//            }
//
//            // 최근 1년 클릭시
//            tabItemCreatedReviewOneYear.setOnClickListener {
//                // 프래그먼트 컨테이너에 최근 1년 리스트만 보여줌
//            }
//            // 기간 버튼 클릭시 -> 바텀시트를 띄운다
//            iconButtonCreatedReviewCalendarDialog.setOnClickListener {
//                // 바텀시트를 띄우고 바텀시트에서 기간설정 후 완료 버튼 클릭시 그 기간에 맞는 리스트를 띄워줌
//
//            }
//        }
//    }
}