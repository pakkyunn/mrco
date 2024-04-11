package kr.co.lion.team4.mrco.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ModifyUserViewModel : ViewModel(){
    //이름
    val textFieldModifyUserUserName = MutableLiveData<String>()
    //이메일
    val textFieldModifyUserUserEmail = MutableLiveData<String>()
    //MBTI
    val textFieldModifyUserUserMBTI = MutableLiveData<String>()
    //휴대폰 번호
    val textFieldModifyUserUserPhone = MutableLiveData<String>()
    //휴대폰 인증번호
    val textFieldModifyUserUserAuthNumber = MutableLiveData<String>()
    //주소
    val textFieldModifyUserUserAddress = MutableLiveData<String>()
    //상세주소
    val textFieldModifyUserUserAddressDetail = MutableLiveData<String>()
    //환불계좌 은행명
    val textFieldModifyUserUserRefundBankName = MutableLiveData<String>()
    //은행계좌 예금주
    val textFieldModifyUserUserRefundBankAccountHolder = MutableLiveData<String>()
    //계좌번호
    val textFieldModifyUserUserRefundBankAccountNumber = MutableLiveData<String>()
    //키
    val textFieldModifyUserUserHeight = MutableLiveData<String>()
    //몸무게
    val textFieldModifyUserUserWeight = MutableLiveData<String>()
    //앱 알림 설정
    val switchModifyUserNotification = MutableLiveData<Boolean>()

    //앱알림설정 스위치 토글
    fun toggleNotificationSwitch(){
        switchModifyUserNotification.value = !switchModifyUserNotification.value!!

    }

}