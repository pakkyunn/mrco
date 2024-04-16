package kr.co.lion.team4.mrco.fragment.product.purchase

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
import kr.co.lion.team4.mrco.SubFragmentName
import kr.co.lion.team4.mrco.databinding.FragmentReviewImageMoreBinding
import kr.co.lion.team4.mrco.databinding.RowProductReviewUserImageBinding

class ReviewImageMoreFragment : Fragment() {

    lateinit var fragmentReviewImageMoreBinding: FragmentReviewImageMoreBinding
    lateinit var mainActivity: MainActivity
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        fragmentReviewImageMoreBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_review_image_more, container, false)
        fragmentReviewImageMoreBinding.lifecycleOwner = this

        mainActivity = activity as MainActivity

        settingRecyclerViewImageMore()
//        settingToolbar()

        return fragmentReviewImageMoreBinding.root
    }

//    fun settingToolbar(){
//        fragmentReviewImageMoreBinding.apply {
//            toolbarReviewImageMore.apply {
//                setNavigationOnClickListener {
//                    mainActivity.removeFragment(MainFragmentName.REVIEW_IMAGE_MORE_FRAGMENT)
//                    ProductFragment().replaceFragment(SubFragmentName.PRODUCT_REVIEW_FRAGMENT,false,true,null)
//                }
//            }
//        }
//    }

    // 상품 후기 사진 전체 RecyclerView 구성
    fun settingRecyclerViewImageMore() {
        fragmentReviewImageMoreBinding.apply {
            recyclerViewReviewImageMore.apply {
                // 어뎁터
                adapter = ReviewImageMoreRecyclerViewAdapter()
                // 레이아웃 매니저
                layoutManager = GridLayoutManager(mainActivity, 4)
            }
        }
    }

    inner class ReviewImageMoreRecyclerViewAdapter :
        RecyclerView.Adapter<ReviewImageMoreRecyclerViewAdapter.ReviewImageMoreViewHolder>() {
        inner class ReviewImageMoreViewHolder(rowProductReviewUserImageBinding: RowProductReviewUserImageBinding) :
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
        ): ReviewImageMoreViewHolder {
            val rowProductReviewUserImageBinding =
                DataBindingUtil.inflate<RowProductReviewUserImageBinding>(
                    layoutInflater,
                    R.layout.row_product_review_user_image,
                    parent,
                    false
                )
            rowProductReviewUserImageBinding.lifecycleOwner = this@ReviewImageMoreFragment

            val ReviewImageMoreViewHolder =
                ReviewImageMoreViewHolder(rowProductReviewUserImageBinding)
            return ReviewImageMoreViewHolder
        }

        override fun getItemCount(): Int {
            return 50
        }

        override fun onBindViewHolder(holder: ReviewImageMoreViewHolder, position: Int) {

        }
    }
}