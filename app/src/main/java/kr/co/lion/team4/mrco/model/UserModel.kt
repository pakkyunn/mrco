package kr.co.lion.team4.mrco.model

// 기존 필수 항목 회원번호, 아이디, 비밀번호, 이름, 성별, 이메일, MBTI,
// 개인정보 제공동의, 상품정보 수신동의, 회원상태에
// 추가 선택정보로 휴대폰 번호, 주소, 상세주소, 환불계좌 은행,
// 환불계좌 예금주, 환불계좌 계좌번호, 키, 체중, 앱 알림설정 추가

data class UserModel(var userIdx:Int, var userId:String,
                     var userPw:String, var userName:String, var userGender:Int,
                     var userEmail:String, var userMBTI:String, var userConsent01: Boolean,
                     var userConsent02: Boolean, var userState:Int,
                     var userPhone: String = "", var userAddress: String = "",
                     var userAddressDetail: String = "", var userRefundBankName: String = "",
                     var userRefundBankAccountHolder: String = "",
                     var userRefundBankAccountNumber: String = "",
                     var userHeight: String = "", var userWeight: String = "",
                     var userNotification: Boolean = false,){

    // 매개 변수가 없는 생성자
    // fireStore 를 사용할 때 데이터를 담을 클래스 타입을 지정하게 되면
    // 매개 변수가 없는 생성자를 사용해 객체 생성해주기 때문에 만들어줘야 한다.
    constructor() : this(0, "", "", "", 0, "",
        "", false, false, 0,
        "","", "", "", "",
        "", "", "", false)
}


// 회원번호, 아이디, 비밀번호, 이름, 성별, 이메일, MBTI, 개인정보 제공동의, 상품정보 수신동의, 회원상태
//data class UserModel(var userIdx:Int, var userId:String,
//                     var userPw:String, var userName:String, var userGender:Int,
//                     var userEmail:String, var userMBTI:String, var userConsent01: Boolean,
//                     var userConsent02: Boolean, var userState:Int,){
//
//    // 매개 변수가 없는 생성자
//    // fireStore 를 사용할 때 데이터를 담을 클래스 타입을 지정하게 되면
//    // 매개 변수가 없는 생성자를 사용해 객체 생성해주기 때문에 만들어줘야 한다.
//    constructor() : this(0, "", "", "", 0, "", "", false, false, 0)
//}




