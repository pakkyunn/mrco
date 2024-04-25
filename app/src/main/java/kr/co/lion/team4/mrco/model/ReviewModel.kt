package kr.co.lion.team4.mrco.model

data class ReviewModel(
    val reviewIdx: Int, // review 인덱스
    val reviewUserIdx: Int, // review 작성자 인덱스
    val reviewUserNickname: String, // review 작성자 인덱스
    val reviewProductIdx: Int, // review 상품 인덱스
    val reviewType: ReviewType, // review 타입
    val reviewDate: String, // review 타입

    val reviewUserGender: Int, // review 작성자 선택정보(성별)
    val reviewUserHeight: Int, // review 작성자 선택정보(키)
    val reviewUserWeight: Int, // review 작성자 선택정보(체중)

    val reviewStarRating: ReviewStarRating, // review 별점
    val reviewRateConcept: ReviewRateConcept, // review 컨셉매칭 평가
    val reviewRateShipping: ReviewRateShipping, // review 배송 평가
    val reviewRateQuality: ReviewRateQuality, // review 품질 평가
    val reviewRateCost: ReviewRateCost, // review 가성비 평가

    val reviewText: String, // review 텍스트
    val reviewImage: Int, // review 사진
) {
    constructor() : this(0, 0, "",0, ReviewType.STYLE_REVIEW, "",0, 0, 0, ReviewStarRating.FIVE_STAR, ReviewRateConcept.RATE_HIGH, ReviewRateShipping.RATE_HIGH, ReviewRateQuality.RATE_HIGH, ReviewRateCost.RATE_HIGH, "", 0)
}

enum class ReviewType(var str: String){
    STYLE_REVIEW("스타일 리뷰"),
    TEXT_REVIEW("일반 리뷰")
}

enum class ReviewStarRating(var str: String, var num:Int){
    ONE_STAR("★☆☆☆☆",1),
    TWO_STAR("★★☆☆☆",2),
    THREE_STAR("★★★☆☆",3),
    FOUR_STAR("★★★★☆",4),
    FIVE_STAR("★★★★★",5),
}

enum class RateOption(var num: Int){
    RATE_CONCEPT(1),
    RATE_SHIPPING(2),
    RATE_QUALITY(3),
    RATE_COST(4),
}

enum class ReviewRateConcept(var str: String){
    RATE_HIGH("잘 어울려요"),
    RATE_MID("무난해요"),
    RATE_LOW("안 어울려요"),
}

enum class ReviewRateShipping(var str: String){
    RATE_HIGH("빨라요"),
    RATE_MID("적당해요"),
    RATE_LOW("느려요"),
}

enum class ReviewRateQuality(var str: String){
    RATE_HIGH("좋아요"),
    RATE_MID("적당해요"),
    RATE_LOW("별로예요"),
}

enum class ReviewRateCost(var str: String){
    RATE_HIGH("좋아요"),
    RATE_MID("적당해요"),
    RATE_LOW("별로예요"),
}
