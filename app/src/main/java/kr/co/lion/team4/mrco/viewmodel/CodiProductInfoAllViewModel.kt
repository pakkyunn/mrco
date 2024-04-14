package kr.co.lion.team4.mrco.viewmodel

import android.graphics.drawable.Drawable
import android.media.Image
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import kr.co.lion.team4.mrco.fragment.product.codi.CodiProductInfoAllFragment

class CodiProductInfoAllViewModel: ViewModel() {
    var productName = MutableLiveData("나는 아이유다!!")

    var productImage = MutableLiveData<Drawable>()
}