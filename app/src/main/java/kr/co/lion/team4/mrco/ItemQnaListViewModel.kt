package kr.co.lion.team4.mrco

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ItemQnaListViewModel : ViewModel() {
    // 코디 상품명
    val textviewQnaListCoordiName = MutableLiveData<String>()
    // 코디 상품번호
    val textviewQnaListCoordiIndex = MutableLiveData<String>()
    // 답변 등록여부 (버튼에 표시될 텍스트)
    val buttonQnaListCoordiAnswer = MutableLiveData<String>()
    // 작성자명 | 작성일
    val textviewQnaListWriter = MutableLiveData<String>()
    // 문의 내용
    val textviewQnaListContent = MutableLiveData<String>()
}