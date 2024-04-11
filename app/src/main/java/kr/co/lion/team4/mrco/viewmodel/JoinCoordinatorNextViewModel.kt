package kr.co.lion.team4.mrco.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class JoinCoordinatorNextViewModel : ViewModel() {
    //출고지 주소
    val textFieldJoinCoordinatorNextWarehouseAddress = MutableLiveData<String>()

    //출고지 상세 주소
    val textFieldJoinCoordinatorNextWarehouseAddressDetail = MutableLiveData<String>()

    //반품지 주소
    val textFieldJoinCoordinatorNextReturnAddress = MutableLiveData<String>()

    //반품지 상세 주소
    val textFieldJoinCoordinatorNextReturnAddressDetail = MutableLiveData<String>()

    //은행
    val textFieldJoinCoordinatorNextSettlementBank = MutableLiveData<String>()

    //예금주
    val textFieldJoinCoordinatorNextSettlementAccountHolder = MutableLiveData<String>()

    //계좌번호
    val textFieldJoinCoordinatorNextSettlementAccountNumber = MutableLiveData<String>()

}