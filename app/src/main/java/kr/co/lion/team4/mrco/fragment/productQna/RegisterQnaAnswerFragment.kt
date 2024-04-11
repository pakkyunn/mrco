package kr.co.lion.team4.mrco.fragment.productQna

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import kr.co.lion.team4.mrco.MainActivity
import kr.co.lion.team4.mrco.MainFragmentName
import kr.co.lion.team4.mrco.R
import kr.co.lion.team4.mrco.viewmodel.productQna.RegisterQnaAnswerViewModel
import kr.co.lion.team4.mrco.databinding.FragmentRegisterQnaAnswerBinding

/* (판매자) 상품 문의 답변 등록 화면 */
class RegisterQnaAnswerFragment : Fragment() {
    lateinit var fragmentRegisterQnaAnswerBinding: FragmentRegisterQnaAnswerBinding
    lateinit var registerQnaAnswerViewModel: RegisterQnaAnswerViewModel

    lateinit var mainActivity: MainActivity

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        fragmentRegisterQnaAnswerBinding = DataBindingUtil.inflate(inflater,
            R.layout.fragment_register_qna_answer, container, false)
        registerQnaAnswerViewModel = RegisterQnaAnswerViewModel()
        fragmentRegisterQnaAnswerBinding.registerQnaAnswerViewModel = registerQnaAnswerViewModel
        fragmentRegisterQnaAnswerBinding.lifecycleOwner = this

        mainActivity = activity as MainActivity

        // 툴바, 하단바 설정
        settingToolbarRegisterQnaAnswer()
        mainActivity.removeBottomSheet()
        return fragmentRegisterQnaAnswerBinding.root
    }

    fun settingToolbarRegisterQnaAnswer(){
        fragmentRegisterQnaAnswerBinding.toolbarRegisterQnaAnswer.apply {
            setNavigationIcon(R.drawable.arrow_back_24px)
            setNavigationOnClickListener {
                backProcess()
            }
        }
    }

    fun backProcess(){
        mainActivity.removeFragment(MainFragmentName.REGISTER_QNA_ANSWER_FRAGMENT)
    }
}