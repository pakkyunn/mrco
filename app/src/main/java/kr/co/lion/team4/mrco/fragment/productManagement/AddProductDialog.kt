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

    // Activity 실행을 위한 런처
    lateinit var albumLauncher: ActivityResultLauncher<Intent>

    // 리스너 선언
    private lateinit var listener: AddProductDialogListener

    lateinit var dialogAddProductBinding: DialogAddProductBinding
    lateinit var dialogAddProductViewModel : DialogAddProductViewModel

    lateinit var mainActivity: MainActivity

    // 스피너로 고른 상품 타입(예: 상의/하의/신발/카테고리)
    var productType = ""

    // 이미지를 첨부한 적이 있는지...
    var isAddPicture = false

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        dialogAddProductBinding = DataBindingUtil.inflate(inflater, R.layout.dialog_add_product, null, false)
        dialogAddProductViewModel = DialogAddProductViewModel()
        dialogAddProductBinding.dialogAddProductViewModel = dialogAddProductViewModel
        dialogAddProductBinding.lifecycleOwner = this

        mainActivity = activity as MainActivity

        // Type(분류) 스피너 세팅
        settingSpinner()

        // 카메라 앨범 사용
        settingAlbumLauncher()

        // 입력 초기화
        settingInputForm()

        // 사진 추가 버튼
        settingAddPhotoButton()

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

    // 사진 추가 버튼
    private fun settingAddPhotoButton() {
        dialogAddProductBinding.imageviewDialogAddProduct.setOnClickListener {
            // 사진 추가 버튼
            startAlbumLauncher()
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
                    if(isAddPicture == true) {
                        // 이미지의 뷰의 이미지 데이터를 파일로 저장한다.
                        Tools.saveImageViewData(mainActivity, dialogAddProductBinding.imageviewDialogAddProduct, "uploadTemp.jpg")
                        // 서버에서의 파일 이름
                        serverFileName = "image_${System.currentTimeMillis()}.jpg"
                        // 서버로 업로드한다.
                        ProductDao.uploadItemsImage(mainActivity, "uploadTemp.jpg", serverFileName!!)
                    }

                    val productData = mapOf(
                        "0" to dialogAddProductBinding.edittextDialogAddProductName.text.toString(),
                        "1" to dialogAddProductBinding.edittextDialogAddProductSize.text.toString(),
                        "2" to dialogAddProductBinding.edittextDialogAddProductStock.text.toString(),
                        "3" to productType,
                        "4" to dialogAddProductBinding.edittextDialogAddProductColor.text.toString(),
                        "5" to serverFileName.toString()
                    )

                    // 리스너 실행 및 데이터 전달
                    listener.onAddProductClicked(productData, isAddPicture)

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

    // 앨범 런처 설정
    fun settingAlbumLauncher() {
        // 앨범 실행을 위한 런처
        val contract2 = ActivityResultContracts.StartActivityForResult()
        albumLauncher = registerForActivityResult(contract2){
            // 사진 선택을 완료한 후 돌아왔다면
            if(it.resultCode == AppCompatActivity.RESULT_OK){
                // 선택한 이미지의 경로 데이터를 관리하는 Uri 객체를 추출한다.
                val uri = it.data?.data
                if(uri != null){
                    // 안드로이드 Q(10) 이상이라면
                    val bitmap = if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q){
                        // 이미지를 생성할 수 있는 객체를 생성한다.
                        val source = ImageDecoder.createSource(mainActivity.contentResolver, uri)
                        // Bitmap을 생성한다.
                        ImageDecoder.decodeBitmap(source)
                    } else {
                        // 컨텐츠 프로바이더를 통해 이미지 데이터에 접근한다.
                        val cursor = mainActivity.contentResolver.query(uri, null, null, null, null)
                        if(cursor != null){
                            cursor.moveToNext()

                            // 이미지의 경로를 가져온다.
                            val idx = cursor.getColumnIndex(MediaStore.Images.Media.DATA)
                            val source = cursor.getString(idx)

                            // 이미지를 생성한다
                            BitmapFactory.decodeFile(source)
                        }  else {
                            null
                        }
                    }

                    // 회전 각도값을 가져온다.
                    val degree = Tools.getDegree(mainActivity, uri)
                    // 회전 이미지를 가져온다
                    val bitmap2 = Tools.rotateBitmap(bitmap!!, degree.toFloat())
                    // 크기를 줄인 이미지를 가져온다.
                    val bitmap3 = Tools.resizeBitmap(bitmap2, 256)

                    dialogAddProductBinding.imageviewDialogAddProduct.setImageBitmap(bitmap3)
                    isAddPicture = true
                }
            }
        }
    }

    // 앨범 런처를 실행하는 메서드
    fun startAlbumLauncher() {
        // 앨범에서 사진을 선택할 수 있도록 셋팅된 인텐트를 생성한다.
        val albumIntent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        // 실행할 액티비티의 타입을 설정(이미지를 선택할 수 있는 것이 뜨게 한다)
        albumIntent.setType("image/*")
        // 선택할 수 있는 파들의 MimeType을 설정한다.
        // 여기서 선택한 종류의 파일만 선택이 가능하다. 모든 이미지로 설정한다.
        val mimeType = arrayOf("image/*")
        albumIntent.putExtra(Intent.EXTRA_MIME_TYPES, mimeType)
        // 액티비티를 실행한다.
        albumLauncher.launch(albumIntent)
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