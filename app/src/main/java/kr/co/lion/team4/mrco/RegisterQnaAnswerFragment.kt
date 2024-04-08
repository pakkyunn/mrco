package kr.co.lion.team4.mrco

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import kr.co.lion.team4.mrco.databinding.FragmentRegisterQnaAnswerBinding

/* (판매자) 상품 문의 답변 등록 화면 */
class RegisterQnaAnswerFragment : Fragment() {
    lateinit var fragmentRegisterQnaAnswerBinding: FragmentRegisterQnaAnswerBinding
    lateinit var registerQnaAnswerViewModel: RegisterQnaAnswerViewModel
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        fragmentRegisterQnaAnswerBinding = DataBindingUtil.inflate(inflater,R.layout.fragment_register_qna_answer, container, false)
        registerQnaAnswerViewModel = RegisterQnaAnswerViewModel()
        fragmentRegisterQnaAnswerBinding.registerQnaAnswerViewModel = registerQnaAnswerViewModel
        fragmentRegisterQnaAnswerBinding.lifecycleOwner = this

        return fragmentRegisterQnaAnswerBinding.root
    }
}