package kr.co.lion.team4.mrco.fragment.productQna

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import kr.co.lion.team4.mrco.viewmodel.productQna.RegisterProductQnaViewModel
import kr.co.lion.team4.mrco.R
import kr.co.lion.team4.mrco.databinding.FragmentRegisterProductQnaBinding

class RegisterProductQnaFragment : Fragment() {
    lateinit var fragmetRegisterProductQnaBinding : FragmentRegisterProductQnaBinding
    lateinit var registerProductQnaViewModel: RegisterProductQnaViewModel
    override fun onCreateView( inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle? ): View? {

        fragmetRegisterProductQnaBinding = DataBindingUtil.inflate(inflater,
            R.layout.fragment_register_product_qna, container, false)
        registerProductQnaViewModel = RegisterProductQnaViewModel()
        fragmetRegisterProductQnaBinding.registerProductQnaViewModel = registerProductQnaViewModel
        fragmetRegisterProductQnaBinding.lifecycleOwner = this

        settingButtonPproductqnaSubmit()

        return fragmetRegisterProductQnaBinding.root
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