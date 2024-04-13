package kr.co.lion.team4.mrco.viewmodel.product

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ProductPurchaseBottomViewModel: ViewModel() {
    // 상품구매 바텀시트 상품명
    val textViewBottomProductPurchase1 = MutableLiveData<String>("상품명1")
    val textViewBottomProductPurchase2 = MutableLiveData<String>("상품명2")
    val textViewBottomProductPurchase3 = MutableLiveData<String>("상품명3")
    val textViewBottomProductPurchase4 = MutableLiveData<String>("상품명4")
    val textViewBottomProductPurchase5 = MutableLiveData<String>("상품명5")

    // 상품구매 바텀시트 선택완료된 코디 정보
    val textViewCoordiBottomProductPurchase1 = MutableLiveData<String>("멋쟁이코디세트")
    val textViewCoordiBottomProductPurchase2 = MutableLiveData<String>("178,000원")
    val textViewCoordiBottomProductPurchase3 = MutableLiveData<String>("코트 · M / 셔츠ㆍM / 바지ㆍ28 / 신발ㆍ230mm")

    // 상품구매 바텀시트 상품별 옵션(1_사이즈,2_색상)
    val spinnerSortProductPurchase1_1 = MutableLiveData<Int>()
    val spinnerSortProductPurchase1_2 = MutableLiveData<Int>()
    val spinnerSortProductPurchase2_1 = MutableLiveData<Int>()
    val spinnerSortProductPurchase2_2 = MutableLiveData<Int>()
    val spinnerSortProductPurchase3_1 = MutableLiveData<Int>()
    val spinnerSortProductPurchase3_2 = MutableLiveData<Int>()
    val spinnerSortProductPurchase4_1 = MutableLiveData<Int>()
    val spinnerSortProductPurchase4_2 = MutableLiveData<Int>()
    val spinnerSortProductPurchase5_1 = MutableLiveData<Int>()
    val spinnerSortProductPurchase5_2 = MutableLiveData<Int>()
}