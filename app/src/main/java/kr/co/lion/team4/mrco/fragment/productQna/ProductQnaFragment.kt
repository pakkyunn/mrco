package kr.co.lion.team4.mrco.fragment.productQna

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import kr.co.lion.team4.mrco.viewmodel.productQna.ProductQnaViewModel
import kr.co.lion.team4.mrco.R
import kr.co.lion.team4.mrco.databinding.FragmentProductQnaBinding

class ProductQnaFragment : Fragment() {
    lateinit var fragmetProductQnaBinding : FragmentProductQnaBinding
    lateinit var productQnaViewModel: ProductQnaViewModel
    override fun onCreateView( inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle? ): View? {

        fragmetProductQnaBinding = DataBindingUtil.inflate(inflater,
            R.layout.fragment_product_qna, container, false)
        productQnaViewModel = ProductQnaViewModel()
        fragmetProductQnaBinding.productQnaViewModel = productQnaViewModel
        fragmetProductQnaBinding.lifecycleOwner = this

        settingButtonPproductqnaSubmit()

        return fragmetProductQnaBinding.root
    }

    fun settingButtonPproductqnaSubmit(){
        // 문의 내용 등록
        fragmetProductQnaBinding.buttonProductqnaSubmit.setOnClickListener {
            // 비밀글로 문의하는 지 여부
            val isSecret = productQnaViewModel.checkboxProductqnaSecret.value
            // 문의 내용
            val content = productQnaViewModel.edittextProductqnaContent.value
        }
    }
}