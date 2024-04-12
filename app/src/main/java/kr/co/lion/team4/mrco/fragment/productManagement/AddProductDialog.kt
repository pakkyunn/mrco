package kr.co.lion.team4.mrco.fragment.productManagement

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager.LayoutParams
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.DialogFragment
import kr.co.lion.team4.mrco.R
import kr.co.lion.team4.mrco.databinding.DialogAddProductBinding
import kr.co.lion.team4.mrco.viewmodel.productManagement.DialogAddProductViewModel

class AddProductDialog(val deviceWidth : Int) : DialogFragment() {

    lateinit var dialogAddProductBinding: DialogAddProductBinding
    lateinit var dialogAddProductViewModel : DialogAddProductViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        dialogAddProductBinding = DataBindingUtil.inflate(inflater, R.layout.dialog_add_product, null, false)
        dialogAddProductViewModel = DialogAddProductViewModel()
        dialogAddProductBinding.dialogAddProductViewModel = dialogAddProductViewModel
        dialogAddProductBinding.lifecycleOwner = this

        return dialogAddProductBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        settingButtonCancel() // 취소
        settingButtonSubmit() // 등록
    }

    // 취소 버튼
    fun settingButtonCancel(){
        dialogAddProductBinding.buttonDialogAddProductCancel.setOnClickListener {
            dismiss()
        }
    }

    // 상품 등록 버튼
    fun settingButtonSubmit(){
        dialogAddProductBinding.buttonDialogAddProductSubmit.setOnClickListener {
            // to do 데이터 전달
            dismiss()
        }
    }

    // Dialog 가로 길이 설정
    override fun onResume() {
        super.onResume()

        val layoutParams : ViewGroup.LayoutParams? = dialog?.window?.attributes
        // 디바이스의 90% 크기로 설정
        layoutParams?.width = (deviceWidth * 0.9).toInt()
        dialog?.window?.attributes = layoutParams as LayoutParams
    }
}