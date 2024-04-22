package kr.co.lion.team4.mrco.viewmodel.mbtiproductmain

import androidx.databinding.BindingAdapter
import androidx.databinding.InverseBindingAdapter
import androidx.databinding.InverseBindingListener
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.android.material.button.MaterialButtonToggleGroup
import kr.co.lion.team4.mrco.MbtiEI
import kr.co.lion.team4.mrco.MbtiPJ
import kr.co.lion.team4.mrco.MbtiSN
import kr.co.lion.team4.mrco.MbtiTF
import kr.co.lion.team4.mrco.R

class MbtiProductBottomSheetViewModel: ViewModel() {

    //
    val toggleButtonEI = MutableLiveData<Int>()
    //
    val toggleButtonSN = MutableLiveData<Int>()
    //
    val toggleButtonTF = MutableLiveData<Int>()
    //
    val toggleButtonPJ = MutableLiveData<Int>()

    fun settingMbtiEI(mbtiEI: MbtiEI){
        when(mbtiEI){
            MbtiEI.E -> {
                toggleButtonEI.value = R.id.mbti_selector_e_button
            }
            MbtiEI.I -> {
                toggleButtonEI.value = R.id.mbti_selector_i_button
            }
        }
    }

    fun settingMbtiSN(mbtiSN: MbtiSN){
        when(mbtiSN){
            MbtiSN.S -> {
                toggleButtonSN.value = R.id.mbti_selector_s_button
            }
            MbtiSN.N -> {
                toggleButtonSN.value = R.id.mbti_selector_n_button
            }
        }
    }

    fun settingMbtiTF(mbtiTF: MbtiTF){
        when(mbtiTF){
            MbtiTF.T -> {
                toggleButtonTF.value = R.id.mbti_selector_t_button
            }
            MbtiTF.F -> {
                toggleButtonTF.value = R.id.mbti_selector_f_button
            }
        }
    }

    fun settingMbtiPJ(mbtiPJ: MbtiPJ){
        when(mbtiPJ){
            MbtiPJ.P -> {
                toggleButtonPJ.value = R.id.mbti_selector_p_button
            }
            MbtiPJ.J -> {
                toggleButtonPJ.value = R.id.mbti_selector_j_button
            }
        }
    }

    // MBTI값을 반환하는 메서드
    fun gettingMbtiEI(): MbtiEI? = when(toggleButtonEI.value){
        R.id.mbti_selector_e_button -> MbtiEI.E
        R.id.mbti_selector_i_button -> MbtiEI.I
        else -> null
    }
    fun gettingMbtiSN(): MbtiSN? = when(toggleButtonSN.value){
        R.id.mbti_selector_s_button -> MbtiSN.S
        R.id.mbti_selector_n_button -> MbtiSN.N
        else -> null
    }
    fun gettingMbtiTF(): MbtiTF? = when(toggleButtonTF.value){
        R.id.mbti_selector_t_button -> MbtiTF.T
        R.id.mbti_selector_f_button -> MbtiTF.F
        else -> null
    }
    fun gettingMbtiPJ(): MbtiPJ? = when(toggleButtonPJ.value){
        R.id.mbti_selector_p_button -> MbtiPJ.P
        R.id.mbti_selector_j_button -> MbtiPJ.J
        else -> null
    }

    companion object {

        // ViewModel에 값을 설정하여 화면에 반영하는 작업을 할 때 호출된다.
        // () 안에는 속성의 이름을 넣어준다. 속성의 이름은 자유롭게 해주면 되지만 기존의 속성 이름과 중복되지 않게
        // 해줘야 한다.
        // 매개변수 : 값이 설정된 View 객체, ViewModel을 통해 설정되는 값
        @BindingAdapter("android:checkedButtonId")
        @JvmStatic
        fun setCheckedButtonId(group: MaterialButtonToggleGroup, buttonId:Int){
            group.check(buttonId)
        }

        // 값을 속성에 넣어주는 것을 순방향이라고 부른다. 반대로 속성의 값이 변경되어 MutableLive 데이터로
        // 전달되는 것을 역방향이라고 한다.
        // 순방향만 구현해주면 단방향이 되는 것이고 순방향과 역방향을 모두 구현해주면 양방향
        // 화면 요소가 가진 속성애 새로운 값이 설정되면 ViewModel의 변수에 값이 설정될 때 호출된다.
        // 리스너 역할을 할 속성을 만들어준다.
        @BindingAdapter("checkedButtonChangeListener")
        @JvmStatic
        fun checkedButtonChangeListener(group: MaterialButtonToggleGroup, inverseBindingListener: InverseBindingListener){
            group.addOnButtonCheckedListener { group, checkedId, isChecked ->
                inverseBindingListener.onChange()
            }
        }
        // 역방향 바인딩이 벌어질 때 호출된다.
        @InverseBindingAdapter(attribute = "android:checkedButtonId", event="checkedButtonChangeListener")
        @JvmStatic
        fun getCheckedButtonId(group: MaterialButtonToggleGroup):Int{
            return group.checkedButtonId
        }
    }
}