package kr.co.lion.team4.mrco.viewmodel.like

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class RowLikeCoordinatorViewModel: ViewModel() {
    val textViewRowLikeCoordinatorName = MutableLiveData<String>("아이유")
    val textViewRowLikeCoordinatorFollower = MutableLiveData<String>("2,751")
}