package kr.co.lion.team4.mrco.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class RowCoordinatorInfoViewModel: ViewModel() {
    val textViewRowInfoMBTI = MutableLiveData<String>("ISFP")
    val textViewRowInfoName = MutableLiveData<String>("아이유 >")
    val textViewRowInfoFollower = MutableLiveData<String>("1,361")
}