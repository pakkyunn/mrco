package kr.co.lion.team4.mrco.model

/*
위에서 부터 순서대로

코디네이터 인덱스, 사용자 인덱스, 코디네이터 활동명, 코디네이터 소개글,
코디네이터 사진, 코디네이터 자격증 사진, 자격증 번호, 포토폴리오 파일,
사업자 등록증 파일, 사업자 등록증 번호
MBTI, 핸드폰, 고객용 오픈 핸드폰, 출고지,
출고지 상세주소, 반품/교환지, 반품/교환지 상세주소,은행명,
예금주명, 계좌번호, 팔로워수, 코디네이터 등록 허가 상태,
코디네이터 등록 요청일, 동의여부
*/

data class CoordinatorModel(var coordi_idx: Int, var coordi_user_idx: Int, var coordi_name: String, var coordi_intro_text: String,
    var coordi_photo: String, var coordi_license: String, var coordi_license_num: String, var coordi_portfolio: String,
    var coordi_business_license : String, var coordi_business_license_num : String, var coordi_mbti: String, var coordi_business_phone: String, var coordi_forwarding_loc: String,
    var coordi_forwarding_loc_detail: String, var coordi_return_loc: String, var coordi_return_loc_detail: String,var coordi_bank: String,
    var coordi_account_holder: String, var coordi_account: String, var coordi_followers: Int, var coordi_permission: Boolean,
    var coordi_request_date: String, var coordi_consent: Boolean) {

    // 매개 변수가 없는 생성자 (FireStore 연동 후 사용)
    constructor(): this(0, 0, "", "",
        "", "", "", "",
        "", "","", "", "",
        "", "", "", "",
        "", "", 0, false,
        "", true)
}