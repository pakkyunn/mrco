package kr.co.lion.team4.mrco.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class JoinViewModel : ViewModel() {
    //아이디
    val textFieldJoinUserId = MutableLiveData<String>()
    //비번
    val textFieldJoinUserPw = MutableLiveData<String>()
    //비번2
    val textFieldJoinUserPw2 = MutableLiveData<String>()
    //이름
    val textFieldJoinUserName = MutableLiveData<String>()
    //성별
    val checkBoxJoinMale = MutableLiveData<Boolean>()
    val checkBoxJoinFemale = MutableLiveData<Boolean>()
    //이메일
    val textFieldJoinUserEmail = MutableLiveData<String>()
    //MBTI
    val textFieldJoinUserMBTI = MutableLiveData<String>()
    //동의 선택01
    val checkBoxJoinUserConsent01 = MutableLiveData<Boolean>()
    //동의 선택02
    val checkBoxJoinUserConsent02 = MutableLiveData<Boolean>()


    fun toggleMaleCheckBox(){
        //checkBoxJoinMale.value = !checkBoxJoinMale.value!!
        checkBoxJoinFemale.value = !checkBoxJoinFemale.value!!
    }

    fun toggleFemaleCheckBox(){
        checkBoxJoinMale.value = !checkBoxJoinMale.value!!
        //checkBoxJoinFemale.value = !checkBoxJoinFemale.value!!
    }

    //선택동의01 체크박스 토글
    fun toggleUserConsent01CheckBox(){
        checkBoxJoinUserConsent01.value = !checkBoxJoinUserConsent01.value!!

    }

    //선택동의02 체크박스 토글
    fun toggleUserConsent02CheckBox(){
        checkBoxJoinUserConsent02.value = !checkBoxJoinUserConsent02.value!!
    }


}