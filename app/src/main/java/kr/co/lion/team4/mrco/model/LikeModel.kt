package kr.co.lion.team4.mrco.model

/*
위에서 부터 순서대로

좋아요 인덱스, 회원 인덱스, 상품 인덱스, 좋아요 여부(O, X)
*/

data class LikeModel(var like_idx: Int, var like_user_idx: Int,
                     var like_product_idx: MutableList<Int> = mutableListOf(),
                     var like_coordinator_idx: MutableList<Int> = mutableListOf()) {

    // 매개 변수가 없는 생성자 (FireStore 연동 후 사용)
    constructor(): this(0, 0, mutableListOf(), mutableListOf())
}