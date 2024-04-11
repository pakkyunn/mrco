package kr.co.lion.team4.mrco.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class PwChangeBottomSheetViewModel : ViewModel() {

    //기존 비번
    val textFieldPwChangeBottomSheetCurrentPw = MutableLiveData<String>()
    //새로운 비번
    val textFieldPwChangeBottomSheetNewPw = MutableLiveData<String>()
    //새로운 비번 확인
    val textFieldPwChangeBottomSheetNewPw2 = MutableLiveData<String>()
}