package kr.co.lion.team4.mrco.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ModifyCoordinatorViewModel : ViewModel() {

    //활동명
    val textFieldModifyCoordinatorName = MutableLiveData<String>()
    //소개글
    val textFieldModifyCoordinatorIntroText = MutableLiveData<String>()
    //이메일
    val textFieldModifyCoordinatorEmail = MutableLiveData<String>()
    //고객노출전화번호
    val textFieldModifyCoordinatorCustomerRelationPhone = MutableLiveData<String>()

    //휴대폰 번호
    val textFieldModifyCoordinatorOfficialPhone = MutableLiveData<String>()
    //휴대폰 인증번호
    val textFieldModifyCoordinatorAuthNumber = MutableLiveData<String>()
    //주소
    val textFieldModifyCoordinatorAddress = MutableLiveData<String>()
    //상세주소
    val textFieldModifyCoordinatorAddressDetail = MutableLiveData<String>()

    //출고지 주소
    val textFieldModifyCoordinatorWarehouseAddress = MutableLiveData<String>()

    //출고지 상세 주소
    val textFieldModifyCoordinatorWarehouseAddressDetail = MutableLiveData<String>()

    //반품지 주소
    val textFieldModifyCoordinatorReturnAddress = MutableLiveData<String>()

    //반품지 상세 주소
    val textFieldJModifyCoordinatorReturnAddressDetail = MutableLiveData<String>()

    //은행
    val textFieldModifyCoordinatorSettlementBank = MutableLiveData<String>()

    //예금주
    val textFieldModifyCoordinatorSettlementAccountHolder = MutableLiveData<String>()

    //계좌번호
    val textFieldModifyCoordinatorSettlementAccountNumber = MutableLiveData<String>()

}