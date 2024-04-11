package kr.co.lion.team4.mrco.viewmodel.coordinator

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class CoordinatorMainViewModel: ViewModel() {
    val textViewCoordinatorMainNickname = MutableLiveData<String>("Nickname IU")
    val textViewCoordinatorMainNameMbti = MutableLiveData<String>("아이유 | ISFP")
    val textViewCoordinatorMainIntro = MutableLiveData<String>("여러분의 패션에 자신감을 더해 드리겠습니다.\n" +
            "어느 자리에서든 돋보이고 트렌디한 스타일로 변화된 자신을 느끼실 수 있도록 제가 도와드릴게요! \n" +
            "앞으로 스타일링 걱정은 저에게 맡겨주세요.")
}