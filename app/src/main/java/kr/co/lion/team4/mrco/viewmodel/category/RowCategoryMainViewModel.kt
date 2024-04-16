package kr.co.lion.team4.mrco.viewmodel.category

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class RowCategoryMainViewModel: ViewModel() {
    var textViewRowCategoryMainCoordinatorName = MutableLiveData<String>("MRCO 코디네이터")
    var textViewRowCategoryMainProductName = MutableLiveData<String>("호불호 없는 출근룩 코디 SET")
    var textViewRowCategoryMainProductMBTI = MutableLiveData<String>("ISFP")
    var textViewRowCategoryMainProductDiscountPrice = MutableLiveData<String>("30%")
    var textViewRowCategoryMainProductPrice = MutableLiveData<String>("178,000원")
}