package kr.co.lion.team4.mrco

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ProductQnaListViewModel : ViewModel() {
    // 정렬 방식 spinner -> string-array의 순서값(Int)을 전달해준다
    val spinnerQnaListSort = MutableLiveData<Int>()
}