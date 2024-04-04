package kr.co.lion.team4.mrco.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import java.text.SimpleDateFormat
import java.util.Date

class CoordinatorRankViewModel: ViewModel() {
    val simpleDateFormat = SimpleDateFormat("yyyy.MM.dd 기준")
    val coordinatorRankDate = simpleDateFormat.format(Date())
    
    val textViewCoordinatorRankDate = MutableLiveData<String>(coordinatorRankDate)
}