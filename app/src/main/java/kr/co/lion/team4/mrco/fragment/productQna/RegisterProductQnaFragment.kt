package kr.co.lion.team4.mrco.fragment.productQna

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import kr.co.lion.team4.mrco.MainActivity
import kr.co.lion.team4.mrco.MainFragmentName
import kr.co.lion.team4.mrco.viewmodel.productQna.RegisterProductQnaViewModel
import kr.co.lion.team4.mrco.R
import kr.co.lion.team4.mrco.databinding.FragmentRegisterProductQnaBinding

class RegisterProductQnaFragment : Fragment() {
    lateinit var fragmetRegisterProductQnaBinding : FragmentRegisterProductQnaBinding
    lateinit var registerProductQnaViewModel: RegisterProductQnaViewModel

    lateinit var mainActivity: MainActivity
    override fun onCreateView( inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle? ): View? {

        fragmetRegisterProductQnaBinding = DataBindingUtil.inflate(inflater,
            R.layout.fragment_register_product_qna, container, false)
        registerProductQnaViewModel = RegisterProductQnaViewModel()
        fragmetRegisterProductQnaBinding.registerProductQnaViewModel = registerProductQnaViewModel
        fragmetRegisterProductQnaBinding.lifecycleOwner = this

        mainActivity = activity as MainActivity

        settingToolbarRegisterProductQna() // 상단 툴바
        mainActivity.removeBottomSheet() // 하단 메뉴 없애기

        settingButtonPproductqnaSubmit()

        return fragmetRegisterProductQnaBinding.root
    }

    // 상단 툴바 설정
    fun settingToolbarRegisterProductQna(){
        fragmetRegisterProductQnaBinding.toolbarRegisterProductQna.apply {
            setNavigationIcon(R.drawable.arrow_back_24px)
            setNavigationOnClickListener {
                mainActivity.removeFragment(MainFragmentName.REGISTER_PRODUCT_QNA_FRAGMENT)
            }
        }
    }

    fun settingButtonPproductqnaSubmit(){
        // 문의 내용 등록
        fragmetRegisterProductQnaBinding.buttonProductqnaSubmit.setOnClickListener {
            // 비밀글로 문의하는 지 여부
            val isSecret = registerProductQnaViewModel.checkboxProductqnaSecret.value
            // 문의 내용
            val content = registerProductQnaViewModel.edittextProductqnaContent.value
        }
    }
}