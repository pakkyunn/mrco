package kr.co.lion.team4.mrco.fragment.product.purchase

import android.graphics.Color
import android.graphics.Typeface
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.PopupMenu
import androidx.core.view.isGone
import androidx.core.view.isInvisible
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kr.co.lion.team4.mrco.MainActivity
import kr.co.lion.team4.mrco.MainFragmentName
import kr.co.lion.team4.mrco.R
import kr.co.lion.team4.mrco.SubFragmentName
import kr.co.lion.team4.mrco.Tools
import kr.co.lion.team4.mrco.dao.ProductDao
import kr.co.lion.team4.mrco.dao.ReviewDao
import kr.co.lion.team4.mrco.databinding.FragmentProductReviewBinding
import kr.co.lion.team4.mrco.databinding.RowProductReviewUserBinding
import kr.co.lion.team4.mrco.databinding.RowProductReviewUserImageBinding
import kr.co.lion.team4.mrco.model.FaqModel
import kr.co.lion.team4.mrco.model.ProductModel
import kr.co.lion.team4.mrco.model.RateOption
import kr.co.lion.team4.mrco.model.ReviewModel
import kr.co.lion.team4.mrco.model.ReviewRateConcept
import kr.co.lion.team4.mrco.model.ReviewRateCost
import kr.co.lion.team4.mrco.model.ReviewRateQuality
import kr.co.lion.team4.mrco.model.ReviewRateShipping
import kr.co.lion.team4.mrco.viewmodel.product.ProductReviewViewModel
import kr.co.lion.team4.mrco.viewmodel.product.RowProductReviewUserViewModel
import java.text.NumberFormat
import java.util.Locale
import kotlin.math.round

class ProductReviewFragment : Fragment() {
    lateinit var fragmentProductReviewBinding: FragmentProductReviewBinding
    lateinit var mainActivity: MainActivity
    lateinit var productReviewViewModel: ProductReviewViewModel

    val productIdx = 0

    var progressResultHigh = 0
    var progressResultMid = 0
    var progressResultLow = 0

    // 모든 review 목록을 담을 리스트
    var reviewAllList = mutableListOf<ReviewModel>()

    // 해당 상품의 idx와 일치하는 리뷰 목록을 담을 리스트
    val reviewThisProductList = mutableListOf<ReviewModel>()
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


        gettingReviewData()

        settingRecyclerViewReviewUser()

        settingRecyclerViewRepresentImage()

        settingButtonImageMore()

        settingToggle()

        return fragmentProductReviewBinding.root
    }

     fun settingProductRate() {
        fragmentProductReviewBinding.apply {
            ratingBarProductReview.rating = gettingStarRatingAvg()
            if(reviewThisProductList.size !=0){
                textViewRatingBarProductReview.text = gettingStarRatingAvg().toString()
            }else{
                textViewRatingBarProductReview.text = "아직 작성된 리뷰가 없습니다."
            }

            textViewRatingConceptProductReview.setOnClickListener {
                resetRateOptionTextButton()
                textViewRatingConceptProductReview.setTypeface(null, Typeface.BOLD)

                textViewProductReviewRatingOption1.text = ReviewRateConcept.RATE_HIGH.str
                textViewProductReviewRatingOption2.text = ReviewRateConcept.RATE_MID.str
                textViewProductReviewRatingOption3.text = ReviewRateConcept.RATE_LOW.str
                if (reviewThisProductList.size != 0) {
                    gettingProgressBarResult(RateOption.RATE_CONCEPT.num)
                    progressBarProductReviewRatingOption1.progress = progressResultHigh
                    progressBarProductReviewRatingOption1.progress = progressResultMid
                    progressBarProductReviewRatingOption1.progress = progressResultLow
                }
            }
            textViewRatingShippingProductReview.setOnClickListener {
                resetRateOptionTextButton()
                textViewRatingShippingProductReview.setTypeface(null, Typeface.BOLD)

                textViewProductReviewRatingOption1.text = ReviewRateShipping.RATE_HIGH.str
                textViewProductReviewRatingOption2.text = ReviewRateShipping.RATE_MID.str
                textViewProductReviewRatingOption3.text = ReviewRateShipping.RATE_LOW.str
                if (reviewThisProductList.size != 0) {
                    gettingProgressBarResult(RateOption.RATE_SHIPPING.num)
                    progressBarProductReviewRatingOption1.progress = progressResultHigh
                    progressBarProductReviewRatingOption1.progress = progressResultMid
                    progressBarProductReviewRatingOption1.progress = progressResultLow
                }
            }
            textViewRatingQualityProductReview.setOnClickListener {
                resetRateOptionTextButton()
                textViewRatingQualityProductReview.setTypeface(null, Typeface.BOLD)

                textViewProductReviewRatingOption1.text = ReviewRateQuality.RATE_HIGH.str
                textViewProductReviewRatingOption2.text = ReviewRateQuality.RATE_MID.str
                textViewProductReviewRatingOption3.text = ReviewRateQuality.RATE_LOW.str
                if (reviewThisProductList.size != 0) {
                    gettingProgressBarResult(RateOption.RATE_QUALITY.num)
                    progressBarProductReviewRatingOption1.progress = progressResultHigh
                    progressBarProductReviewRatingOption1.progress = progressResultMid
                    progressBarProductReviewRatingOption1.progress = progressResultLow
                }
            }
            textViewRatingCostProductReview.setOnClickListener {
                resetRateOptionTextButton()
                textViewRatingCostProductReview.setTypeface(null, Typeface.BOLD)

                textViewProductReviewRatingOption1.text = ReviewRateCost.RATE_HIGH.str
                textViewProductReviewRatingOption2.text = ReviewRateCost.RATE_MID.str
                textViewProductReviewRatingOption3.text = ReviewRateCost.RATE_LOW.str
                if (reviewThisProductList.size != 0) {
                    gettingProgressBarResult(RateOption.RATE_COST.num)
                    progressBarProductReviewRatingOption1.progress = progressResultHigh
                    progressBarProductReviewRatingOption1.progress = progressResultMid
                    progressBarProductReviewRatingOption1.progress = progressResultLow
                }
            }
        }
    }


    fun gettingStarRatingAvg(): Float {
        var productRatingStarSum = 0f
        for (i in 0 until reviewThisProductList.size) {
            productRatingStarSum += reviewThisProductList[i].reviewStarRating.num
        }
        val productRatingAvg = round(productRatingStarSum / reviewThisProductList.size * 100) / 100
        return productRatingAvg
    }

    fun resetRateOptionTextButton() {
        fragmentProductReviewBinding.apply {
            textViewRatingConceptProductReview.setTypeface(null, Typeface.NORMAL)
            textViewRatingShippingProductReview.setTypeface(null, Typeface.NORMAL)
            textViewRatingQualityProductReview.setTypeface(null, Typeface.NORMAL)
            textViewRatingCostProductReview.setTypeface(null, Typeface.NORMAL)
        }
    }

    fun gettingProgressBarResult(rateOptionNum:Int){
        var progressOption1 = 0
        var progressOption2 = 0
        var progressOption3 = 0

        when(rateOptionNum){
            1 -> {
                for (i in 0 until reviewThisProductList.size) {
                    if(reviewThisProductList[i].reviewRateConcept == ReviewRateConcept.RATE_HIGH){
                        progressOption1++
                    }
                    if(reviewThisProductList[i].reviewRateConcept == ReviewRateConcept.RATE_MID){
                        progressOption2++
                    }
                    if(reviewThisProductList[i].reviewRateConcept == ReviewRateConcept.RATE_LOW){
                        progressOption3++
                    }
            }}
            2 -> {
                for (i in 0 until reviewThisProductList.size) {
                if(reviewThisProductList[i].reviewRateShipping == ReviewRateShipping.RATE_HIGH){
                    progressOption1++
                }
                if(reviewThisProductList[i].reviewRateShipping == ReviewRateShipping.RATE_MID){
                    progressOption2++
                }
                if(reviewThisProductList[i].reviewRateShipping == ReviewRateShipping.RATE_LOW){
                    progressOption3++
                }
            }}
            3 -> {for (i in 0 until reviewThisProductList.size) {
                if(reviewThisProductList[i].reviewRateQuality == ReviewRateQuality.RATE_HIGH){
                    progressOption1++
                }
                if(reviewThisProductList[i].reviewRateQuality == ReviewRateQuality.RATE_MID){
                    progressOption2++
                }
                if(reviewThisProductList[i].reviewRateQuality == ReviewRateQuality.RATE_LOW){
                    progressOption3++
                }
            }}
            4 -> {for (i in 0 until reviewThisProductList.size) {
                if(reviewThisProductList[i].reviewRateCost == ReviewRateCost.RATE_HIGH){
                    progressOption1++
                }
                if(reviewThisProductList[i].reviewRateCost == ReviewRateCost.RATE_MID){
                    progressOption2++
                }
                if(reviewThisProductList[i].reviewRateCost == ReviewRateCost.RATE_LOW){
                    progressOption3++
                }
            }}
        }
        progressResultHigh = progressOption1/reviewThisProductList.size
        progressResultHigh = round(progressResultHigh.toDouble()).toInt()
        progressResultMid = progressOption2/reviewThisProductList.size
        progressResultMid = round(progressResultMid.toDouble()).toInt()
        progressResultLow = progressOption3/reviewThisProductList.size
        progressResultLow = round(progressResultLow.toDouble()).toInt()
    }

    fun settingButtonImageMore() {
        fragmentProductReviewBinding.apply {
            buttonImageMore.setOnClickListener {
                mainActivity.replaceFragment(
                    MainFragmentName.REVIEW_IMAGE_MORE_FRAGMENT,
                    true,
                    true,
                    null
                )
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

    fun settingToggle() {
        fragmentProductReviewBinding.apply {
            toggleGroupProductReview.check(R.id.toggleButtonProductReviewStyle)
            toggleButtonProductReviewStyle.setOnClickListener {
                for (i in 0 until reviewAllList.size) {
                    if (reviewAllList[i].reviewProductIdx == productIdx && reviewAllList[i].reviewImage != null) {
                        reviewThisProductList.add(reviewAllList[i])
                    }
                }
                fragmentProductReviewBinding.recyclerViewProductReviewUser?.adapter?.notifyDataSetChanged()
            }
            toggleButtonProductReviewStyle.setOnClickListener {
                for (i in 0 until reviewAllList.size) {
                    if (reviewAllList[i].reviewProductIdx == productIdx && reviewAllList[i].reviewImage == null) {
                        reviewThisProductList.add(reviewAllList[i])
                    }
                }
                fragmentProductReviewBinding.recyclerViewProductReviewUser?.adapter?.notifyDataSetChanged()
            }
        }
    }

    fun reviewAllListFilter(): MutableList<ReviewModel> {
        reviewThisProductList.clear()
        if (reviewAllList.size != 0) {
            for (i in 0 until reviewAllList.size) {
                if (reviewAllList[i].reviewProductIdx == productIdx && reviewAllList[i].reviewImage != null) {
                    reviewThisProductList.add(reviewAllList[i])
                }
            }
        }
        Log.d("againagain","리스트 리턴")
        return reviewThisProductList
    }

    inner class ReviewRepresentImageRecyclerViewAdapter :
        RecyclerView.Adapter<ReviewRepresentImageRecyclerViewAdapter.ReviewRepresentImageViewHolder>() {
        inner class ReviewRepresentImageViewHolder(rowProductReviewUserImageBinding: RowProductReviewUserImageBinding) :
            RecyclerView.ViewHolder(rowProductReviewUserImageBinding.root) {
            val rowProductReviewUserImageBinding: RowProductReviewUserImageBinding

            init {
                this.rowProductReviewUserImageBinding = rowProductReviewUserImageBinding

                this.rowProductReviewUserImageBinding.root.layoutParams =
                    ViewGroup.LayoutParams(
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
            val imageResource = when (position % 6) {
                0 -> R.drawable.iu_image2
                1 -> R.drawable.iu_image3
                2 -> R.drawable.iu_image4
                3 -> R.drawable.iu_image5
                4 -> R.drawable.iu_image6
                else -> R.drawable.iu_image7
            }
            holder.rowProductReviewUserImageBinding.imageViewReviewRepresent.setImageResource(
                imageResource
            )
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

        override fun onCreateViewHolder(
            parent: ViewGroup,
            viewType: Int
        ): ReviewUserViewHolder {
            val rowProductReviewUserBinding =
                DataBindingUtil.inflate<RowProductReviewUserBinding>(
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
            return reviewThisProductList.size
        }

        override fun onBindViewHolder(holder: ReviewUserViewHolder, position: Int) {
            // position 값에 따라 다른 이미지 설정
            val imageResource = when (position % 6) {
                0 -> R.drawable.iu_image2
                1 -> R.drawable.iu_image3
                2 -> R.drawable.iu_image4
                3 -> R.drawable.iu_image5
                4 -> R.drawable.iu_image6
                else -> R.drawable.iu_image7
            }
            holder.rowProductReviewUserBinding.imageViewReviewUser.setImageResource(
                imageResource
            )

            holder.rowProductReviewUserBinding.textViewReviewUserNickname.text =
                "${reviewThisProductList[position].reviewUserNickname}"
            holder.rowProductReviewUserBinding.textViewReviewDate.text =
                "${reviewThisProductList[position].reviewDate}"
            holder.rowProductReviewUserBinding.textViewUserInfo.text =
                "${reviewThisProductList[position].reviewUserGender}ㆍ${reviewThisProductList[position].reviewUserHeight}ㆍ${reviewThisProductList[position].reviewUserWeight}"
            holder.rowProductReviewUserBinding.textViewRatingStars.text =
                "${reviewThisProductList[position].reviewStarRating.str}"
            holder.rowProductReviewUserBinding.textViewRateConcept.text =
                "${reviewThisProductList[position].reviewRateConcept.str}"
            holder.rowProductReviewUserBinding.textViewRateShipping.text =
                "${reviewThisProductList[position].reviewRateShipping.str}"
            holder.rowProductReviewUserBinding.textViewRateQuality.text =
                "${reviewThisProductList[position].reviewRateQuality.str}"
            holder.rowProductReviewUserBinding.textViewRateCost.text =
                "${reviewThisProductList[position].reviewRateCost.str}"
            holder.rowProductReviewUserBinding.textViewReviewText.text =
                "${reviewThisProductList[position].reviewText}"
//            holder.rowProductReviewUserBinding.imageViewReviewUser.setImageResource(reviewThisProductList[position].reviewImage)

        }
    }

    fun gettingReviewData() {
        CoroutineScope(Dispatchers.Main).launch {
            // 모든 리뷰 목록 정보를 가져온다.
            reviewAllList = ReviewDao.gettingReviewAllList()

            reviewAllListFilter()

            settingProductRate()

            settingRecyclerViewReviewUser()
        }
    }
}