package kr.co.lion.team4.mrco.fragment.customerService

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kr.co.lion.team4.mrco.MainActivity
import kr.co.lion.team4.mrco.MainFragmentName
import kr.co.lion.team4.mrco.R
import kr.co.lion.team4.mrco.Tools
import kr.co.lion.team4.mrco.dao.CustomerInquiryDao
import kr.co.lion.team4.mrco.databinding.FragmentCustomerInquiryBinding
import kr.co.lion.team4.mrco.model.CustomerInquiryModel
import kr.co.lion.team4.mrco.viewmodel.customerService.CustomerInquiryViewModel
import java.text.SimpleDateFormat
import java.util.*


/* (구매자) 고객센터 - 1:1 문의 작성 화면 */
class CustomerInquiryFragment : Fragment() {
    lateinit var fragmentCustomerInquiryBinding: FragmentCustomerInquiryBinding
    lateinit var customerInquiryViewModel: CustomerInquiryViewModel

    lateinit var mainActivity: MainActivity

    lateinit var albumLauncher: ActivityResultLauncher<Intent>

    // 첨부한 파일
    var attachedFile : Bitmap? = null
    // 첨부한 파일의 파일명
    lateinit var attachedImageName : String

    // 이미지를 첨부한 적이 있는지..
    var isAddPicture = false

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        fragmentCustomerInquiryBinding = DataBindingUtil.inflate(inflater,
            R.layout.fragment_customer_inquiry, container, false)
        customerInquiryViewModel = CustomerInquiryViewModel()
        fragmentCustomerInquiryBinding.customerInquiryViewModel = customerInquiryViewModel
        fragmentCustomerInquiryBinding.lifecycleOwner = this

        mainActivity = activity as MainActivity

        // 툴바, bottom navigation 설정
        settingCustomerInquiryToolbar()

        settingAlbumLauncher()
        attachInquiryFile()

        submitCustomerInquiry()

        removeAttachedFile()

        return fragmentCustomerInquiryBinding.root
    }

    // 문의 내용 제출
    fun submitCustomerInquiry(){
        fragmentCustomerInquiryBinding.apply {
            buttonCustomerInquirySubmit.setOnClickListener {
                val validation = checkInquiryFormValid()
                if(validation){
                    // 문의 내용을 서버에 업로드한다.
                    saveCustomerInquiry()
                }
            }
        }

    }

    // 파일 첨부
    fun attachInquiryFile(){
        fragmentCustomerInquiryBinding.apply {
            // 파일 첨부 버튼
            buttonCustomerInquiryAttachFile.setOnClickListener {
                startAlbumLauncher()
            }
        }
    }

    // 앨범 런처를 실행하는 메서드
    fun startAlbumLauncher(){
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

    // 앨범 런처 설정
    fun settingAlbumLauncher(){
        val albumContract = ActivityResultContracts.StartActivityForResult()
        albumLauncher = registerForActivityResult(albumContract){
            // 사진 선택을 완료한 후 돌아왔다면
            if(it.resultCode == AppCompatActivity.RESULT_OK){
                // 선택한 이미지의 경로 데이터를 관리하는 Uri 객체를 추출한다.
                val uri = it.data?.data
                if(uri!=null) {
                    attachedImageName = getFileName(uri)

                    attachedFile = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                        // 이미지를 생성할 수 있는 객체를 생성한다.
                        val source = ImageDecoder.createSource(mainActivity.contentResolver, uri)
                        // Bitmap을 생성한다.
                        ImageDecoder.decodeBitmap(source)
                    } else {
                        // 컨텐츠 프로바이더를 통해 이미지 데이터에 접근한다.
                        val cursor = mainActivity.contentResolver.query(uri, null, null, null, null)
                        if (cursor != null) {
                            cursor.moveToNext()

                            // 이미지의 경로를 가져온다.
                            val idx = cursor.getColumnIndex(MediaStore.Images.Media.DATA)
                            val source = cursor.getString(idx)

                            cursor.close()

                            // 이미지를 생성한다
                            BitmapFactory.decodeFile(source)
                        } else {
                            null
                        }
                    }

                    if(attachedImageName != ""){
                        // 첨부한 파일명을 화면에 표시해준다.
                        fragmentCustomerInquiryBinding.customerInquiryViewModel?.textviewCustomerInquiryFilename?.value = attachedImageName
                        fragmentCustomerInquiryBinding.textviewCustomerInquiryFilename.visibility = View.VISIBLE
                        fragmentCustomerInquiryBinding.buttonCustomerInquiryDeleteFile.visibility = View.VISIBLE
                        isAddPicture = true
                    }
                }
            }
        }
    }

    // Uri로부터 첨부한 사진의 파일명만 읽어오기
    fun getFileName(uri:Uri) : String {
        // content resolver : uri를 읽어오기 위해 필요한 객체. query를 통해 Content Provider와 통신
        // uri : 갤러리에서 선택한 사진의 uri. Content Provider가 관리하는 uri로, 이걸 통해 CP 데이터에 접근할 수 있다
        // cursor = 데이터에 접근하는 포인터
        val cursor = mainActivity.contentResolver.query(uri, null, null, null, null)
        if(cursor != null){
            // 다음 행으로 이동
            cursor.moveToNext()
            // 원하는 컬럼(파일명)의 데이터를 받아온다.
            val fileName = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DISPLAY_NAME))
            cursor.close() // cursor 해제

            return fileName
        }

        return ""
    }

    fun saveCustomerInquiry(){
        CoroutineScope(Dispatchers.Main).launch {
            // 서버에서의 첨부 파일명
            var serverFileName : String? = null

            // 첨부된 이미지가 있다면
            if(isAddPicture == true && attachedFile != null){
                // 데이터 파일로 저장할 떄의 파일명
                val tempFileName = "inquiryAttachedImageTemp.jpg"
                // 서버에서의 첨부 파일명 설정
                serverFileName = "inqiry_${System.currentTimeMillis()}.jpg"

                // 첨부한 이미지 데이터를 파일로 저장한다.
                Tools.saveImageViewIndividualItemData(mainActivity, attachedFile!!, tempFileName)
                // 서버로 업로드한다.
                CustomerInquiryDao.uploadAttachedImage(mainActivity, tempFileName, serverFileName)
            }

            // 고객센터 문의 시퀀스 값을 가져온다.
            val customerInquirySequence = CustomerInquiryDao.getCustomerInquirySequence()
            // 고객센터 문의 시퀀스 값을 업데이트 한다.
            CustomerInquiryDao.updateCustomerInquirySequence(customerInquirySequence+1)

            // 업로드 할 정보를 담아준다.
            val inquiryIdx = customerInquirySequence+1 // 문의 번호
            val inquiryUserIdx = mainActivity.loginUserIdx // 문의 남긴 유저의 user idx
            val inquiryType = customerInquiryViewModel.autotextviewCustomerInquiryType.value!!  // 문의 유형
            val inquiryOrderNumber = customerInquiryViewModel.textinputCustomerInquiryOrderNumber.value // 주문 번호
            val inquiryTitle = customerInquiryViewModel.textinputCustomerInquiryTitle.value!! // 문의 제목
            val inquiryContent = customerInquiryViewModel.textinputCustomerInquiryContent.value!! // 문의 내용
            val inquiryFilename = serverFileName // // 문의 시 첨부한 사진의 서버 파일명
            val inquiryAnswerWay = customerInquiryViewModel.textviewCustomerInquiryAnswerWay.value!! // 답변 방식

            val simpleDateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.KOREA)
            val inquiryDate = simpleDateFormat.format(Date()) // 문의 작성일

            val inquiryModel = CustomerInquiryModel(inquiryIdx, inquiryUserIdx, inquiryType, inquiryOrderNumber,
                inquiryTitle, inquiryContent, inquiryFilename, inquiryAnswerWay, inquiryDate, 0)
            CustomerInquiryDao.insertCustomerInquiryData(inquiryModel)

            // 이전 화면으로 돌아간다
            Tools.hideSoftInput(mainActivity)
            mainActivity.removeFragment(MainFragmentName.CUSTOMER_INQUIRY_FRAGMENT)
        }
    }

    // 현재 첨부된 파일 삭제하는 기능
    fun removeAttachedFile(){
        fragmentCustomerInquiryBinding.apply {
            buttonCustomerInquiryDeleteFile.setOnClickListener {
                // 첨부 파일 초기화
                attachedFile = null
                // 첨부 파일명을 화면에서 숨긴다
                fragmentCustomerInquiryBinding.textviewCustomerInquiryFilename.visibility = View.GONE
                fragmentCustomerInquiryBinding.buttonCustomerInquiryDeleteFile.visibility = View.GONE
                // 파일 첨부 내역 초기화
                isAddPicture = false
            }
        }
    }

    // 주문 번호, 첨부파일을 제외한 입력 요소 유효성 검사
    fun checkInquiryFormValid() : Boolean {
        // 문의 유형
        val inquiryType = customerInquiryViewModel.autotextviewCustomerInquiryType.value
        // 문의 제목
        val inquiryTitle = customerInquiryViewModel.textinputCustomerInquiryTitle.value
        // 문의 내용
        val inquiryContent = customerInquiryViewModel.textinputCustomerInquiryContent.value
        // 답변 방식
        val inquiryAnswerWay = customerInquiryViewModel.textviewCustomerInquiryAnswerWay.value

        if(inquiryType == null){ // 문의 유형을 선택하지 않은 경우
            Tools.showErrorDialog(mainActivity, fragmentCustomerInquiryBinding.menuCustomerInquiryType,
                "제목 입력 오류", "제목을 입력해주세요")
            return false
        }

        if(inquiryTitle == null){
            Tools.showErrorDialog(mainActivity, fragmentCustomerInquiryBinding.textinputCustomerInquiryTitle,
                "제목 입력 오류", "제목을 입력해주세요")
            return false
        }

        if(inquiryContent == null){
            Tools.showErrorDialog(mainActivity, fragmentCustomerInquiryBinding.textinputCustomerInquiryContent,
                "제목 입력 오류", "제목을 입력해주세요")
            return false
        }

        if(inquiryAnswerWay == null){
            Tools.showErrorDialog(mainActivity, fragmentCustomerInquiryBinding.menuCustomerInquiryAnswerWay,
                "제목 입력 오류", "제목을 입력해주세요")
            return false
        }

        return true
    }

    // 툴바 설정
    fun settingCustomerInquiryToolbar(){
        fragmentCustomerInquiryBinding.toolbarCustomerInquiry.apply {
            setNavigationIcon(R.drawable.arrow_back_24px)
            setNavigationOnClickListener {
                backProcess()
            }
        }
    }

    // 뒤로가기
    fun backProcess(){
        mainActivity.removeFragment(MainFragmentName.CUSTOMER_INQUIRY_FRAGMENT)
    }
}