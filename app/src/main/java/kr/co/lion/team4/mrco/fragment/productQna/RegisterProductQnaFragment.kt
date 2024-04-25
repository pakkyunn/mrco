package kr.co.lion.team4.mrco.fragment.productQna

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.FragmentManager
import kr.co.lion.team4.mrco.MainActivity
import kr.co.lion.team4.mrco.MainFragmentName
import kr.co.lion.team4.mrco.viewmodel.productQna.RegisterProductQnaViewModel
import kr.co.lion.team4.mrco.R
import kr.co.lion.team4.mrco.SubFragmentName
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

        settingButtonPproductqnaSubmit()

        return fragmetRegisterProductQnaBinding.root
    }

    // 상단 툴바 설정
    fun settingToolbarRegisterProductQna(){
        fragmetRegisterProductQnaBinding.toolbarRegisterProductQna.apply {
            inflateMenu(R.menu.menu_close_toolbar)
            setOnMenuItemClickListener {
                when (it.itemId) {
                    R.id.main_toolbar_close -> {
                        removeFragment(SubFragmentName.REGISTER_PRODUCT_QNA_FRAGMENT)
                    }
                }
                true
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

    fun removeFragment(name: SubFragmentName) {
        // 지정한 이름으로 있는 Fragment를 BackStack에서 제거한다.
        mainActivity.supportFragmentManager.popBackStack(
            name.str,
            FragmentManager.POP_BACK_STACK_INCLUSIVE
        )
    }
}