package kr.co.lion.team4.mrco.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kr.co.lion.team4.mrco.R

class CategoryViewModel: ViewModel() {
    val textFieldCategorySearch = MutableLiveData<String>()
    val chipGroupCategoryGender = MutableLiveData<Int>()



    // 성별을 세팅하는 메서드
    fun settingGender(gender:Gender){
        // 성별로 분기한다.
        when(gender){
            Gender.MEN -> {
                chipGroupCategoryGender.value = R.id.chipCategoryGenderMEN
            }
            Gender.WOMEN -> {
                chipGroupCategoryGender.value = R.id.chipCategoryGenderWOMEN
            }
        }
    }

    // 성별값을 반환하는 메서드
    fun gettingGender():Gender = when(chipGroupCategoryGender.value){
        R.id.chipCategoryGenderMEN -> Gender.MEN
        else -> Gender.WOMEN
    }
}