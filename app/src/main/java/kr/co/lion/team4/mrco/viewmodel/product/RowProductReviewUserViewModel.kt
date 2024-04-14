package kr.co.lion.team4.mrco.viewmodel.product

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class RowProductReviewUserViewModel: ViewModel() {
    // 리뷰 유저 닉네임
    val textViewReviewUserNickname = MutableLiveData<String>("UserNickname")
    // 리뷰 작성 날짜
    val textViewReviewDate = MutableLiveData<String>("2024.04.14")
    // 리뷰 유저 신체정보
    val textViewUserInfo = MutableLiveData<String>("남성ㆍ185cmㆍ75kg")
    // 리뷰 상품 옵션
    val textViewPurchaseOption = MutableLiveData<String>("셔츠ㆍM / 베스트ㆍM / 바지ㆍ28 / 가방ㆍfree / 신발ㆍ230mm")
    // 리뷰 상품 평점
    val textViewRatingStars = MutableLiveData<String>("★★★★☆")
    // 리뷰 상품 평가항목1
    val textViewRatingOption1 = MutableLiveData<String>("잘 어울려요")
    // 리뷰 상품 평가항목1
    val textViewRatingOption2 = MutableLiveData<String>("적당해요")
    // 리뷰 상품 평가항목1
    val textViewRatingOption3 = MutableLiveData<String>("적당해요")
    // 리뷰 상품 평가항목1
    val textViewRatingOption4 = MutableLiveData<String>("적당해요")
    // 리뷰 상품 평가항목1
    val textViewReviewComment = MutableLiveData<String>("유저후기 Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.")
}