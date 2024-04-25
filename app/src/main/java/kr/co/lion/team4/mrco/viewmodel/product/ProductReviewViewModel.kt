package kr.co.lion.team4.mrco.viewmodel.product

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kr.co.lion.team4.mrco.R

class ProductReviewViewModel: ViewModel() {
    // 상품페이지 상품 별점
    var ratingBarProductReview = MutableLiveData<Int>()
    // 상품페이지 리뷰탭 평점평균
    var textViewRatingBarProductReview = MutableLiveData<String>()

    // 상품페이지 리뷰탭 평가항목1
    val textViewProductReviewRatingOption1 = MutableLiveData<String>()
    // 상품페이지 리뷰탭 평가항목1 프로그레스
    val progressBarProductReviewRatingOption1 = MutableLiveData<Int>()
    // 상품페이지 리뷰탭 평가항목1 비율
    val progressRatioProductReviewRatingOption1 = MutableLiveData<String>()

    // 상품페이지 리뷰탭 평가항목2
    val textViewProductReviewRatingOption2 = MutableLiveData<String>()
    // 상품페이지 리뷰탭 평가항목2 프로그레스
    val progressBarProductReviewRatingOption2 = MutableLiveData<Int>()
    // 상품페이지 리뷰탭 평가항목2 비율
    val progressRatioProductReviewRatingOption2 = MutableLiveData<String>()

    // 상품페이지 리뷰탭 평가항목3
    val textViewProductReviewRatingOption3 = MutableLiveData<String>()
    // 상품페이지 리뷰탭 평가항목3 프로그레스
    val progressBarProductReviewRatingOption3 = MutableLiveData<Int>()
    // 상품페이지 리뷰탭 평가항목3 비율
    val progressRatioProductReviewRatingOption3 = MutableLiveData<String>()

    // 정렬 방식 spinner -> string-array의 순서값(Int)을 전달해준다
    val spinnerSortProductReview = MutableLiveData<Int>()




    // 성별을 셋팅하는 메서드
    /*fun settingReviewType(reviewType:ReviewType){
        // 성별로 분기한다.
        when(reviewType){
            ReviewType.SyleReview -> {
                toggleGroupProductReview.value = R.id.toggleButtonProductReview1
            }
            ReviewType.TextReview -> {
                toggleGroupProductReview.value = R.id.toggleButtonProductReview2
            }
        }
    }*/

    // 성별값을 반환하는 메서드
    /*fun gettinggReviewType():ReviewType = when(toggleGroupProductReview.value){
        R.id.toggleButtonProductReview1 -> ReviewType.MALE
        else -> ReviewType.FEMALE
    }*/




}