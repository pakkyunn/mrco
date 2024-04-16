package kr.co.lion.team4.mrco.model

// 회원번호, 아이디, 비밀번호, 이름, 성별, 이메일, MBTI, 개인정보 제공동의, 상품정보 수신동의, 회원상태

// 닉네임, 전화번호, 주소, 상세주소, 가입일, 키, 체중, 앱 알림 설정, 코디네이터 등록 여부 필요함
data class UserModel(var userIdx:Int, var userId:String,
                     var userPw:String, var userName:String, var userGender:Int,
                     var userEmail:String, var userMBTI:String, var userConsent01: Boolean,
                     var userConsent02: Boolean, var userState:Int,){

    // 매개 변수가 없는 생성자
    // fireStore 를 사용할 때 데이터를 담을 클래스 타입을 지정하게 되면
    // 매개 변수가 없는 생성자를 사용해 객체 생성해주기 때문에 만들어줘야 한다.
    constructor() : this(0, "", "", "", 0, "", "", false, false, 0)
}