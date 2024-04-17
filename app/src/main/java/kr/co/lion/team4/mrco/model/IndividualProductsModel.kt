package kr.co.lion.team4.mrco.model

/*
위에서 부터 순서대로

인덱스, 상품 카테고리(상의/하의 등), 상품명
재고 수량, 사이즈, 이미지 파일명, 색상
*/

data class IndividualProductsModel( var item_idx: Int, var name: String, var size: Int, var stock: Int,
                                    var item_category: String, var item_color: Int, var item_image_filename: String
) {
    // 매개 변수가 없는 생성자 (FireStore 연동 후 사용)
    constructor(): this(0, "",0,0, "", 0, "")
}