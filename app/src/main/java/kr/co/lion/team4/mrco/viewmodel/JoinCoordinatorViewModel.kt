package kr.co.lion.team4.mrco.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class JoinCoordinatorViewModel : ViewModel() {

    //활동명
    val textFieldJoinCoordinatorName = MutableLiveData<String>()
    //소개글
    val textFieldJoinCoordinatorIntro = MutableLiveData<String>()
    //자격증번호
    val textFieldJoinCoordinatorCertificationNumber = MutableLiveData<String>()
    //사업자번호
    val textFieldJoinCoordinatorBizLicenseNumber = MutableLiveData<String>()
    //MBTI
    val textFieldJoinCoordinatorMBTI = MutableLiveData<String>()
    //고객노출전화번호
    val textFieldJoinCoordinatorContactNumber = MutableLiveData<String>()
    //약관동의
    val checkBoxJoinCoordinatorConsent = MutableLiveData<Boolean>(false)

    //약관동의 체크박스 토글
    fun toggleCoordinatorConsentCheckBox() {
        checkBoxJoinCoordinatorConsent.value = !checkBoxJoinCoordinatorConsent.value!!
    }

}