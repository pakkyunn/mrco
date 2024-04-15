package kr.co.lion.team4.mrco.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class CreateReviewViewModel: ViewModel() {
    val reviewContentInfo = MutableLiveData("(이용안내) Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo ") // 후기 작성 안내 내용

    val reviewCodiProductName = MutableLiveData<String>()

    val reviewCodiProductMBTI = MutableLiveData<String>()

    val reviewCodiProductPrice = MutableLiveData<String>()

    val reviewCodiProductPurchaseDate = MutableLiveData<String>()



}