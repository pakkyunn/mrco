package kr.co.lion.team4.mrco.model

import com.google.firebase.Timestamp

data class OrderModel(
    var order_idx : Int, // 주문번호
    var order_user_idx : Int, // 구매자 인덱스
    // 구매자에게 보여주기위한 주문번호 (주문년월일시분초+랜덤숫자4개 임시로 넣어둠 -> 예: 202404241340411258 )
    var order_number : String, // 주문번호
    var order_date : Timestamp, // 주문일자 - 주문일자 검색을 위해 Firebase에서 제공하는 Timestamp 타입으로 설정
    var order_product : ArrayList<OrderProduct>, // 주문상품 리스트
    var payment_method : String, // 결제방법
    var payment_amount : Int, // 최종 결제금액
    var original_price : Int, // 상품가격
    var discount_price : Int, // 할인내역
    var order_state : Int, // 주문상태(확정, 취소 등)
    var shipping_address : String, // 배송 주소
    var shipping_address_detail : String, // 상세 배송 주소
    var shipping_name : String, // 수령인
    var shipping_phone_number : String, // 연락처
    var shipping_memo : String?, // 배송 메모
) {
    constructor() : this(0, 0, "", Timestamp.now(), ArrayList(),
        "", 0, 0,0, 0,
        "", "", "", "", "")
}

data class OrderProduct(
    var order_product_idx: Int, // 상품 idx
    var coordinator_idx: Int, // 코디네이터 idx
    var product_coordi_option: String, // 상품 옵션
    var product_quantity: Int, // 주문 수량
    var courier: String?, // 택배사
    var tracking_number: String?, // 운송장번호
    var tracking_state: Int, // 배송 상태
    var review_idx: Int?, // 리뷰 번호
    var review_state: Int, // 리뷰 상태
) {
    constructor() : this(0,0, "", 0,
        null, null, 0, null, 0)
}

data class OrderedProductInfoModel(
    var ordered_product_idx : Int,
    var ordered_product_name : String, // 상품명
    var ordered_product_price : Int, // 상품의 가격
    var ordered_product_thumbnail_filename : String, // 상품 사진
)


/* 참고 정보 : 주문 상태, 배송 상태, 리뷰 작성 Enum Class  (Tools.kt)

// 주문 상태
enum class OrderState(var str: String, var num:Int){
    ORDERED("주문 접수", 0),
    IN_TRANSIT("배송중", 1),
    DELIVERED("배송완료", 2),
    ORDER_CONFIRM("구매 확정", 3),
    RETURN("반품 접수", 4),
    EXCHANGE("교환 접수", 5),
    CANCEL("주문 취소", 6),
}

// 배송 상태
enum class ShippingState(var str: String, var num:Int){
    READY_TO_SHIP("발송준비", 0),
    SHIPPED("배송시작", 1),
    IN_TRANSIT("배송중", 2),
    ARRIVE_SOON("도착예정", 3),
    DELIVERED("도착", 4),
}

// 리뷰 작성 상태
enum class ReviewState(var buttonText: String, var num: Int) {
    WAITING("리뷰 작성", 0), // 리뷰 작성 대기중
    COMPLETE("리뷰 보기", 1) // 리뷰 작성 완료
}
*/