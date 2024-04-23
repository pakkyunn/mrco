package kr.co.lion.team4.mrco.model

/* 고객센터 1:1 문의 */
data class CustomerInquiryModel(
    var inquiry_idx : Int, // 문의 번호
    var inquiry_user_idx: Int, // 문의 남긴 유저의 user idx
    var inquiry_type : String, // 문의 유형
    var inquiry_order_number: String?, // 주문 번호
    var inquiry_title : String, // 문의 제목
    var inquiry_content : String, // 문의 내용
    var inquiry_filename : String?, // 문의 시 첨부한 사진의 파일명
    var inquiry_answer_way : String, // 답변 방식
    var inquiry_date : String, // 문의 작성일
    var inquiry_state : Int // 문의 처리 상태
)