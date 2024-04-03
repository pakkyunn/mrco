package kr.co.lion.team4.mrco.ViewModel

import android.view.View
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

// Model : 프로젝트에서 사용하는 모든 데이터를 관리
class ProductReviewViewModel: ViewModel(){
    // UI 요소에 셋팅할 데이터들을 관리하는 데이터
    // Activity or Fragment와 XML 파일의 오작교 역할

    // View에 설정할 값을 담을 객체
    // 제네릭은 반드시 View의 속성에 대한 값의 타입으로 맞춰줘야 한다
    val editData1 = MutableLiveData<String>()
    val editData2 = MutableLiveData<String>()
    val textViewData = MutableLiveData<String>()

    // 버튼을 누르면 동작하는 메서드
    fun buttonClick(view: View){
        // 입력한 값을 가져온다.
        val data1 = editData1.value?.toInt()!!
        val data2 = editData2.value?.toInt()!!

        val r1 = data1 + data2

        // 출력한다
        textViewData.value = r1.toString()
    }

}