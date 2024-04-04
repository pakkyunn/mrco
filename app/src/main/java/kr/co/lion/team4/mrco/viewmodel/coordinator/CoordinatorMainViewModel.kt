package kr.co.lion.team4.mrco.viewmodel.coordinator

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class CoordinatorMainViewModel: ViewModel() {
    val textViewCoordinatorMainIntro = MutableLiveData<String>("( 코디네이터 한 줄 소개 )\n안녕하세요!! 저는 @@@ 코디네이터입니다!!")
}