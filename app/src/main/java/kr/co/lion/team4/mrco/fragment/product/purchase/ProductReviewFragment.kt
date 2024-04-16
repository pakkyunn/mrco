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
import kr.co.lion.team4.mrco.MainFragmentName
import kr.co.lion.team4.mrco.R
import kr.co.lion.team4.mrco.SubFragmentName
import kr.co.lion.team4.mrco.databinding.FragmentProductReviewBinding
import kr.co.lion.team4.mrco.databinding.RowProductReviewUserBinding
import kr.co.lion.team4.mrco.databinding.RowProductReviewUserImageBinding
import kr.co.lion.team4.mrco.viewmodel.product.ProductReviewViewModel
import kr.co.lion.team4.mrco.viewmodel.product.RowProductReviewUserViewModel

class ProductReviewFragment : Fragment() {
    lateinit var fragmentProductReviewBinding: FragmentProductReviewBinding
    lateinit var mainActivity: MainActivity
    lateinit var productReviewViewModel: ProductReviewViewModel
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        fragmentProductReviewBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_product_review, container, false)
        productReviewViewModel = ProductReviewViewModel()
        fragmentProductReviewBinding.productReviewViewModel = productReviewViewModel
        fragmentProductReviewBinding.lifecycleOwner = this

        mainActivity = activity as MainActivity

        settingRecyclerViewReviewUser()

        settingRecyclerViewRepresentImage()

        settingButtonImageMore()

        return fragmentProductReviewBinding.root
    }

    fun settingButtonImageMore() {
        fragmentProductReviewBinding.apply {
            buttonImageMore.setOnClickListener {
                mainActivity.replaceFragment(MainFragmentName.REVIEW_IMAGE_MORE_FRAGMENT,true,true,null)
            }
        }
    }

    // 상품 후기 대표사진(6개) RecyclerView 구성
    fun settingRecyclerViewRepresentImage() {
        fragmentProductReviewBinding.apply {
            recyclerViewReviewRepresentImage.apply {
                // 어뎁터
                adapter = ReviewRepresentImageRecyclerViewAdapter()
                // 레이아웃 매니저
                layoutManager = GridLayoutManager(mainActivity, 3)
            }
        }
    }

    inner class ReviewRepresentImageRecyclerViewAdapter :
        RecyclerView.Adapter<ReviewRepresentImageRecyclerViewAdapter.ReviewRepresentImageViewHolder>() {
        inner class ReviewRepresentImageViewHolder(rowProductReviewUserImageBinding: RowProductReviewUserImageBinding) :
            RecyclerView.ViewHolder(rowProductReviewUserImageBinding.root) {
            val rowProductReviewUserImageBinding: RowProductReviewUserImageBinding

            init {
                this.rowProductReviewUserImageBinding = rowProductReviewUserImageBinding

                this.rowProductReviewUserImageBinding.root.layoutParams = ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT
                )
            }
        }

        override fun onCreateViewHolder(
            parent: ViewGroup,
            viewType: Int
        ): ReviewRepresentImageViewHolder {
            val rowProductReviewUserImageBinding =
                DataBindingUtil.inflate<RowProductReviewUserImageBinding>(
                    layoutInflater,
                    R.layout.row_product_review_user_image,
                    parent,
                    false
                )
            rowProductReviewUserImageBinding.lifecycleOwner = this@ProductReviewFragment

            val reviewRepresentImageViewHolder =
                ReviewRepresentImageViewHolder(rowProductReviewUserImageBinding)
            return reviewRepresentImageViewHolder
        }

        override fun getItemCount(): Int {
            return 6
        }

        override fun onBindViewHolder(holder: ReviewRepresentImageViewHolder, position: Int) {

        }
    }


    // 유저 후기 RecyclerView 구성
    fun settingRecyclerViewReviewUser() {
        fragmentProductReviewBinding.apply {
            recyclerViewProductReviewUser.apply {
                // 어뎁터
                adapter = ReviewUserRecyclerViewAdapter()
                // 레이아웃 매니저
                layoutManager = LinearLayoutManager(mainActivity)
            }
        }
    }

    inner class ReviewUserRecyclerViewAdapter :
        RecyclerView.Adapter<ReviewUserRecyclerViewAdapter.ReviewUserViewHolder>() {

        inner class ReviewUserViewHolder(rowProductReviewUserBinding: RowProductReviewUserBinding) :
            RecyclerView.ViewHolder(rowProductReviewUserBinding.root) {
            val rowProductReviewUserBinding: RowProductReviewUserBinding

            init {
                this.rowProductReviewUserBinding = rowProductReviewUserBinding

                rowProductReviewUserBinding.root.layoutParams = ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT
                )
            }
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReviewUserViewHolder {
            val rowProductReviewUserBinding = DataBindingUtil.inflate<RowProductReviewUserBinding>(
                layoutInflater,
                R.layout.row_product_review_user,
                parent,
                false
            )
            val rowProductReviewUserViewModel = RowProductReviewUserViewModel()
            rowProductReviewUserBinding.rowProductReviewUserViewModel =
                rowProductReviewUserViewModel
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