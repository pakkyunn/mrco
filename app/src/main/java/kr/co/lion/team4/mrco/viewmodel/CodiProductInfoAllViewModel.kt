package kr.co.lion.team4.mrco.viewmodel

import android.media.Image
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class CodiProductInfoAllViewModel: ViewModel() {
    var productName = MutableLiveData<String>()

    var productImage = MutableLiveData<Image>()
}