package kr.co.lion.team4.mrco.model

data class FaqModel (
    val faqIdx : Int, // faq 인덱스 번호
    val faqCategory : Int, // faq 카테고리
    val faqSubCategory : String, // subCategory
    val faqQuestion : String, // question
    val faqAnswer : String, // answer
)


// 자주 묻는 질문 카테고리
enum class FaqCategory(var str: String, var categoryNum : Int){
    USER("회원정보", 0),
    PRODUCT_AND_ORDER("상품/주문", 1),
    SHIPPING("배송", 2),
    RETURN_AND_EXCHANGE("교환/반품", 3)
}

// 자주 묻는 질문 하위 카테고리
enum class FaqSubCategory(var str:String){
    // 회원정보
    JOIN("가입"),
    LOGIN("로그인"),
    USER_INFO("회원정보"),
    COORDINATOR("코디네이터 입점"),

    // 상품/주문/결제
    INQUIRY("상품문의"),
    ORDER("주문"),
    PAYMENT("결제수단"),

    // 배송
    SHIPPING("배송"),

    // 교환/반품
    EXCHANGE("교환"),
    RETURN("반품")
}