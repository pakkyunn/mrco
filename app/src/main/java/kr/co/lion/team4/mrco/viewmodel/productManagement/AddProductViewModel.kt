package kr.co.lion.team4.mrco.viewmodel.productManagement

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kr.co.lion.team4.mrco.CategoryIdSubMOOD
import kr.co.lion.team4.mrco.CategoryIdSubSEASON
import kr.co.lion.team4.mrco.CategoryIdSubTPO
import kr.co.lion.team4.mrco.CodiMbti
import kr.co.lion.team4.mrco.Gender
import kr.co.lion.team4.mrco.databinding.FragmentAddProductBinding

/* (판매자) 코디 상품 등록 화면(AddProductFragment)의 View Model */
class AddProductViewModel : ViewModel() {
    // 코디의 성별 - to do Binding Adapter
    val chipgroupAddProductGender = MutableLiveData<Int>()
    // 코디의 카테고리(대분류) - to do Binding Adapter
    val chipgroupAddProductCatogory = MutableLiveData<Int>()
    // 코디 MBTI
    val chipgroupAddProductMbti = MutableLiveData<CodiMbti>()
    // 하위 카테고리 1. TPO
    val chipgroupAddProductTpoSub = MutableLiveData<CategoryIdSubTPO>()
    // 하위 카테고리 2. SEASON
    val chipgroupAddProductSeasonSub = MutableLiveData<CategoryIdSubSEASON>()
    // 하위 카테고리 3. MOOD
    val chipgroupAddProductMoodSub = MutableLiveData<CategoryIdSubMOOD>()

    // 코디 상품명
    val edittextAddProductName = MutableLiveData<String>()
    // 코디 소개(아이템 및 스타일 소개)
    val edittextAddProductComments = MutableLiveData<String>()
    // 코디 상품 가격
    val edittextAddProductPrice = MutableLiveData<String>()

//
//    // ChipGroupProductGender의 체크상태를 받아와서 값을 적용하는 메서드
//    fun gettingChipCheckedStateProductGender( checkedId: FragmentAddProductBinding.){
//        chipgroupAddProductGender.value =
//    }
}