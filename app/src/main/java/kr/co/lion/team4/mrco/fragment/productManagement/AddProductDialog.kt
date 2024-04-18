package kr.co.lion.team4.mrco.fragment.productManagement

import android.content.Intent
import android.graphics.BitmapFactory
import android.graphics.ImageDecoder
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager.LayoutParams
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.DialogFragment
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kr.co.lion.team4.mrco.MainActivity
import kr.co.lion.team4.mrco.R
import kr.co.lion.team4.mrco.Tools
import kr.co.lion.team4.mrco.dao.ProductDao
import kr.co.lion.team4.mrco.databinding.DialogAddProductBinding
import kr.co.lion.team4.mrco.viewmodel.productManagement.DialogAddProductViewModel


class AddProductDialog(val deviceWidth : Int) : DialogFragment() {

    // 리스너 선언
    private lateinit var listener: AddProductDialogListener

    lateinit var dialogAddProductBinding: DialogAddProductBinding
    lateinit var dialogAddProductViewModel : DialogAddProductViewModel

    lateinit var mainActivity: MainActivity

    // 스피너로 고른 상품 타입(예: 상의/하의/신발/카테고리)
    var productType = ""

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        dialogAddProductBinding = DataBindingUtil.inflate(inflater, R.layout.dialog_add_product, null, false)
        dialogAddProductViewModel = DialogAddProductViewModel()
        dialogAddProductBinding.dialogAddProductViewModel = dialogAddProductViewModel
        dialogAddProductBinding.lifecycleOwner = this

        mainActivity = activity as MainActivity

        // Type(분류) 스피너 세팅
        settingSpinner()

        // 입력 초기화
        settingInputForm()

        // 취소, 등록 버튼
        settingButtonCancel() // 취소
        settingButtonSubmit() // 등록

        return dialogAddProductBinding.root
    }

    // 분류 스피너 설정
    private fun settingSpinner(){
        // Spinner 데이터 설정
        val spinner: Spinner = dialogAddProductBinding.spinnerDialogAddProductType
        val items = arrayOf("상의", "하의", "신발", "악세사리") // 드롭다운 항목 리스트
        val adapter = ArrayAdapter(mainActivity, android.R.layout.simple_spinner_dropdown_item, items)
        spinner.adapter = adapter

        // Spinner 선택 이벤트 처리
        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parentView: AdapterView<*>, selectedItemView: View, position: Int, id: Long) {
                productType = items[position]
            }

            override fun onNothingSelected(parentView: AdapterView<*>) {
                // 아무것도 선택되지 않았을 때의 작업 수행
                productType = "상의"
            }
        }
    }

    // 취소 버튼
    private fun settingButtonCancel() {
        dialogAddProductBinding.buttonDialogAddProductCancel.setOnClickListener {
            dismiss()
        }
    }

    // 등록 버튼
    private fun settingButtonSubmit() {
        dialogAddProductBinding.buttonDialogAddProductSubmit.setOnClickListener {

            // 서버에서의 첨부 이미지 파일 이름
            var serverFileName:String? = null

            // 입력 요소 유효성 검사
            val chk = checkInputForm()

            if(chk == true) {
                CoroutineScope(Dispatchers.Main).launch {
                    // 첨부된 이미지가 있다면
//                    if(isAddPicture == true) {
//                        // 이미지의 뷰의 이미지 데이터를 파일로 저장한다.
//                        Tools.saveImageViewData(mainActivity, dialogAddProductBinding.imageviewDialogAddProduct, "uploadTemp.jpg")
//                        // 서버에서의 파일 이름
//                        serverFileName = "image_${System.currentTimeMillis()}.jpg"
//                        // 서버로 업로드한다.
//                        ProductDao.uploadItemsImage(mainActivity, "uploadTemp.jpg", serverFileName!!)
//                        Log.d("test1234", "DialogFragment - 사진 서버로 업로드 완료")
//                    }

                    val productData = mutableMapOf(
                        "0" to dialogAddProductBinding.edittextDialogAddProductName.text.toString(),
                        "1" to dialogAddProductBinding.edittextDialogAddProductSize.text.toString(),
                        "2" to dialogAddProductBinding.edittextDialogAddProductStock.text.toString(),
                        "3" to productType,
                        "4" to dialogAddProductBinding.edittextDialogAddProductColor.text.toString(),
                        "5" to "test"
                    )

                    // 리스너 실행 및 데이터 전달
                    // Log.d("test1234", "DialogFragment - 리스너 실행 및 데이터 전달")
                    listener.onAddProductClicked(productData)

                    dismiss()
                }
            }
        }
    }

    // Dialog 가로 길이 설정
    override fun onResume() {
        super.onResume()

        val layoutParams : ViewGroup.LayoutParams? = dialog?.window?.attributes
        // 디바이스의 100% 크기로 설정
        layoutParams?.width = (deviceWidth * 1).toInt()
        dialog?.window?.attributes = layoutParams as LayoutParams
    }

    // 리스너 세팅
    fun setListener(listener: AddProductDialogListener) {
        this.listener = listener
    }

    // 입력 요소 초기화
    fun settingInputForm() {
        dialogAddProductBinding.edittextDialogAddProductName.setText("")
        dialogAddProductBinding.edittextDialogAddProductSize.setText("")
        dialogAddProductBinding.edittextDialogAddProductStock.setText("")
        dialogAddProductBinding.edittextDialogAddProductColor.setText("")
    }

    // 입력 요소 유효성 검사
    fun checkInputForm():Boolean{
        // 입력한 내용을 가져온다.
        val productName = dialogAddProductBinding.edittextDialogAddProductName.text.toString()
        val productSize =  dialogAddProductBinding.edittextDialogAddProductSize.text.toString()
        val productStock =  dialogAddProductBinding.edittextDialogAddProductStock.text.toString()
        val productColor =  dialogAddProductBinding.edittextDialogAddProductColor.text.toString()

        if(productName.isEmpty()){
            Tools.showErrorDialog(mainActivity, dialogAddProductBinding.edittextDialogAddProductName,
                "상품명 입력 오류", "해당 상품의 상품명을 입력해주세요")
            return false
        }

        if(productSize.isEmpty()){
            Tools.showErrorDialog(mainActivity, dialogAddProductBinding.edittextDialogAddProductSize,
                "사이즈 입력 오류", "해당 상품의 사이즈를 입력해주세요")
            return false
        }

        if(productStock.isEmpty()){
            Tools.showErrorDialog(mainActivity, dialogAddProductBinding.edittextDialogAddProductStock,
                "재고 입력 오류", "해당 상품의 재고를 입력해주세요")
            return false
        }

        if(productColor.isEmpty()){
            Tools.showErrorDialog(mainActivity, dialogAddProductBinding.edittextDialogAddProductColor,
                "색상 입력 오류", "해당 상품의 색상을 입력해주세요")
            return false
        }
        return true
    }

    /*
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        settingButtonCancel() // 취소
        settingButtonSubmit() // 등록
    }
    */
}