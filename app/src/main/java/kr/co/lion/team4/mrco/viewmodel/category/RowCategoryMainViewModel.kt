package kr.co.lion.team4.mrco.viewmodel.category

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class RowCategoryMainViewModel: ViewModel() {
    var textViewCategoryMainCoordinatorName = MutableLiveData<String>()
    var textViewCategoryMainProductName = MutableLiveData<String>()
    var textViewCategoryMainProductMbti = MutableLiveData<String>()
    var textViewCategoryMainProductPrice = MutableLiveData<String>()
}