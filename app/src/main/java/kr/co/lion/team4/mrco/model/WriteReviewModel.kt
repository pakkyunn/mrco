package kr.co.lion.team4.mrco.model

/*
위에서 부터 순서대로

리뷰 인덱스, 회원 인덱스, 상품 인덱스, 리뷰 작성일
리뷰 별점, 리뷰 평가 항목1, 2, 3, 리뷰 성별
리뷰 작성 키, 리뷰 작성 체중, 리뷰 내용, 리뷰 사진, 리뷰 타입(스타일 리뷰 or 일반 리뷰)
*/

data class WriteReviewModel (var review_idx: Int, var review_user_idx: Int, var review_product_idx: Int, var review_date: String,
    var review_rate_stars: Int, var review_rate_1: Int, var review_rate_2: Int, var review_rate_3: Int, var review_gender: Int,
    var review_height: Int, var review_weight: Int, var review_content: String, var review_images: String, var review_type: Int
)   {

    // 매개 변수가 없는 생성자 (FireStore 연동 후 사용)
    constructor(): this(0, 0, 0, "",
        0, 0, 0, 0, 0,
        0, 0, "", "", 0)
}