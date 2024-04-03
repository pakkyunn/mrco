package kr.co.lion.team4.mrco.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class CoordinatorRankViewModel: ViewModel() {
    val textViewCoordinatorRankDate = MutableLiveData<String>("2024.04.02 기준")
}