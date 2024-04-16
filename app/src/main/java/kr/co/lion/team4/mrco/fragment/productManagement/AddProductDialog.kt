package kr.co.lion.team4.mrco.fragment.productManagement

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager.LayoutParams
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import kr.co.lion.team4.mrco.R
import kr.co.lion.team4.mrco.databinding.DialogAddProductBinding
import kr.co.lion.team4.mrco.viewmodel.productManagement.DialogAddProductViewModel



class AddProductDialog(val deviceWidth : Int) : DialogFragment() {

    private lateinit var listener: AddProductDialogListener
    lateinit var dialogAddProductBinding: DialogAddProductBinding
    lateinit var dialogAddProductViewModel : DialogAddProductViewModel

    lateinit var addProductFragment: AddProductFragment

    // EditText의 값을 저장할 변수들 정의
    var editTextValues: ArrayList<String> = arrayListOf()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        dialogAddProductBinding = DataBindingUtil.inflate(inflater, R.layout.dialog_add_product, null, false)
        dialogAddProductViewModel = DialogAddProductViewModel()
        dialogAddProductBinding.dialogAddProductViewModel = dialogAddProductViewModel
        dialogAddProductBinding.lifecycleOwner = this

        addProductFragment = AddProductFragment()

        return dialogAddProductBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        settingButtonCancel() // 취소
        settingButtonSubmit() // 등록
    }

    // 사진 추가 버튼
    private  fun settingAddPhoto() {
        dialogAddProductBinding.imageviewDialogAddProduct.setOnClickListener {
            // 사진 추가 버튼
        }
    }

    // 취소 버튼
    private fun settingButtonCancel() {
        dialogAddProductBinding.buttonDialogAddProductCancel.setOnClickListener {
            dismiss()
        }
    }

    private fun settingButtonSubmit() {
        dialogAddProductBinding.buttonDialogAddProductSubmit.setOnClickListener {
            val productData = arrayListOf(
                dialogAddProductBinding.edittextDialogAddProductName.text.toString(),
                dialogAddProductBinding.edittextDialogAddProductSize.text.toString(),
                dialogAddProductBinding.edittextDialogAddProductStock.text.toString(),
                dialogAddProductBinding.edittextDialogAddProductType.text.toString(),
                dialogAddProductBinding.edittextDialogAddProductColor.text.toString()
            )
            // 리스너 실행 및 데이터 전달
            listener.onAddProductClicked(productData)
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

    fun setListener(listener: AddProductDialogListener) {
        this.listener = listener
    }
}