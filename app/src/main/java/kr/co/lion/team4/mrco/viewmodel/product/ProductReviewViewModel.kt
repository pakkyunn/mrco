package kr.co.lion.team4.mrco.viewmodel.product

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kr.co.lion.team4.mrco.R

class ProductReviewViewModel: ViewModel() {
    // 상품페이지 상품 평점
    val ratingBarProductReview = MutableLiveData<Int>()
    // 상품페이지 리뷰탭 평점평균
    val textViewRatingBarProductReview = MutableLiveData<String>()

    // 상품페이지 리뷰탭 평가항목1
    val textViewRatingOptionProductReview1 = MutableLiveData<String>()
    // 상품페이지 리뷰탭 평가항목1 프로그레스
    val progressBarRatingOptionProductReview1 = MutableLiveData<Int>(66)
    // 상품페이지 리뷰탭 평가항목1 비율
    val progressRatioRatingOptionProductReview1 = MutableLiveData<String>()

    // 상품페이지 리뷰탭 평가항목2
    val textViewRatingOptionProductReview2 = MutableLiveData<String>()
    // 상품페이지 리뷰탭 평가항목2 프로그레스
    val progressBarRatingOptionProductReview2 = MutableLiveData<Int>(26)
    // 상품페이지 리뷰탭 평가항목2 비율
    val progressRatioRatingOptionProductReview2 = MutableLiveData<String>()

    // 상품페이지 리뷰탭 평가항목3
    val textViewRatingOptionProductReview3 = MutableLiveData<String>()
    // 상품페이지 리뷰탭 평가항목3 프로그레스
    val progressBarRatingOptionProductReview3 = MutableLiveData<Int>(8)
    // 상품페이지 리뷰탭 평가항목3 비율
    val progressRatioRatingOptionProductReview3 = MutableLiveData<String>()

    // 리뷰 타입
    val toggleGroupProductReview = MutableLiveData<Int>()

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