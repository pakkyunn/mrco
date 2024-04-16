package kr.co.lion.team4.mrco.viewmodel.salesManagement

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

/* (판매자) 배송관리 화면(ManageShipmentsFragment)의 ViewModel */
class ManageShipmentsViewModel : ViewModel(){
    // 상품명 입력창
    val edittextManageShipmentsKeyword = MutableLiveData<String>()

    // 조회 기간 - 시작일
    val shipmentsPeriodStart = MutableLiveData<String>()
    // 조회 기간 - 종료일
    val shipmentsPeriodEnd = MutableLiveData<String>()
}