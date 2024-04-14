package kr.co.lion.team4.mrco.fragment.product.purchase

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kr.co.lion.team4.mrco.MainActivity
import kr.co.lion.team4.mrco.R
import kr.co.lion.team4.mrco.databinding.FragmentProductReviewBinding
import kr.co.lion.team4.mrco.databinding.RowProductReviewUserBinding
import kr.co.lion.team4.mrco.viewmodel.product.ProductReviewViewModel
import kr.co.lion.team4.mrco.viewmodel.product.RowProductReviewUserViewModel

class ProductReviewFragment : Fragment() {
    lateinit var fragmentProductReviewBinding: FragmentProductReviewBinding
    lateinit var mainActivity: MainActivity
    lateinit var productReviewViewModel: ProductReviewViewModel
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        fragmentProductReviewBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_product_review, container, false)
        productReviewViewModel = ProductReviewViewModel()
        fragmentProductReviewBinding.productReviewViewModel = productReviewViewModel
        fragmentProductReviewBinding.lifecycleOwner = this

        mainActivity = activity as MainActivity

        settingRecyclerViewReviewUser()

        return fragmentProductReviewBinding.root
    }


    // RecyclerView 구성
    fun settingRecyclerViewReviewUser(){
        fragmentProductReviewBinding.apply {
            recyclerViewProductReviewUser.apply {
                // 어뎁터
                adapter = ReviewUserRecyclerViewAdapter()
                // 레이아웃 매니저
                layoutManager = LinearLayoutManager(mainActivity)
            }
        }
    }

    // 댓글 목록을 보여줄 RecyclerView의 어뎁터
    inner class ReviewUserRecyclerViewAdapter : RecyclerView.Adapter<ReviewUserRecyclerViewAdapter.ReviewUserViewHolder>(){

        inner class ReviewUserViewHolder(rowProductReviewUserBinding: RowProductReviewUserBinding) : RecyclerView.ViewHolder(rowProductReviewUserBinding.root){
            val rowProductReviewUserBinding : RowProductReviewUserBinding

            init {
                this.rowProductReviewUserBinding = rowProductReviewUserBinding

                rowProductReviewUserBinding.root.layoutParams = ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT
                )
            }
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReviewUserViewHolder {
            val rowProductReviewUserBinding = DataBindingUtil.inflate<RowProductReviewUserBinding>(layoutInflater, R.layout.row_product_review_user, parent, false)
            val rowProductReviewUserViewModel = RowProductReviewUserViewModel()
            rowProductReviewUserBinding.rowProductReviewUserViewModel = rowProductReviewUserViewModel
            rowProductReviewUserBinding.lifecycleOwner = this@ProductReviewFragment

            val reviewUserViewHolder = ReviewUserViewHolder(rowProductReviewUserBinding)
            return reviewUserViewHolder
        }

        override fun getItemCount(): Int {
            return 10
        }

        override fun onBindViewHolder(holder: ReviewUserViewHolder, position: Int) {

        }
    }
}