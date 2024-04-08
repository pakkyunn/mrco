package kr.co.lion.team4.mrco.viewmodel.coordinator

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class RowCoordinatorRankViewModel: ViewModel() {
    val textViewRowCoordinatorRankName = MutableLiveData<String>("아이유")
    val textViewRowCoordinatorRankFollower = MutableLiveData<String>("2,751")
}