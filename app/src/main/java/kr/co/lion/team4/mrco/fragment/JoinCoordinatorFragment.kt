package kr.co.lion.team4.mrco.fragment

import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isGone
import androidx.core.view.isVisible
import androidx.core.view.size
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import kr.co.lion.team4.mrco.MainActivity
import kr.co.lion.team4.mrco.MainFragmentName
import kr.co.lion.team4.mrco.R
import kr.co.lion.team4.mrco.Tools
import kr.co.lion.team4.mrco.dao.CoordinatorDao
import kr.co.lion.team4.mrco.databinding.FragmentCameraAlbumBottomSheetBinding
import kr.co.lion.team4.mrco.databinding.FragmentJoinCoordinatorBinding
import kr.co.lion.team4.mrco.viewmodel.JoinCoordinatorViewModel
import java.io.File

class JoinCoordinatorFragment : Fragment() {

    lateinit var fragmentJoinCoordinatorBinding: FragmentJoinCoordinatorBinding
    lateinit var mainActivity: MainActivity
    lateinit var joinCoordinatorViewModel: JoinCoordinatorViewModel
    lateinit var fragmentCameraAlbumBottomSheetBinding: FragmentCameraAlbumBottomSheetBinding

    var coordiNameChk: Boolean = false

    // Activity 실행을 위한 런처
    lateinit var cameraLauncher: ActivityResultLauncher<Intent>
    lateinit var albumLauncher: ActivityResultLauncher<Intent>

    // 촬영된 사진이 저장된 경로 정보를 가지고 있는 Uri 객체
    lateinit var contentUri: Uri

    // 이미지를 첨부한 적이 있는지..
    var isAddPicture = false

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        //fragmentJoinCoordinatorBinding = FragmentJoinCoordinatorBinding.inflate(inflater)
        fragmentJoinCoordinatorBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_join_coordinator, container, false)
        fragmentCameraAlbumBottomSheetBinding =
            DataBindingUtil.inflate(
                inflater,
                R.layout.fragment_camera_album_bottom_sheet,
                container,
                false
            )
        joinCoordinatorViewModel = JoinCoordinatorViewModel()
        fragmentJoinCoordinatorBinding.joinCoordinatorViewModel = joinCoordinatorViewModel
        fragmentJoinCoordinatorBinding.lifecycleOwner = this

        mainActivity = activity as MainActivity

        // 툴바 및 하단 바 세팅
        settingToolbar()

        initInput()

        settingButtonCheckName()

        showMbtiBottomSheet()

        settingButtonNext()

        settingButtonCoordinatorImage()
        settingButtonCoordinatorCertification()
        settingButtonCoordinatorPortfolio()
        settingButtonCoordinatorBizLicenseSubmit()

        return fragmentJoinCoordinatorBinding.root
    }

    // 툴바 설정
    fun settingToolbar() {
        fragmentJoinCoordinatorBinding.apply {
            toolbarJoinCoordinator.apply {
                // 네비게이션
                setNavigationIcon(R.drawable.arrow_back_24px)
                setNavigationOnClickListener {
                    backProcess()
                }
            }
        }
    }

    fun initInput() {
        joinCoordinatorViewModel.textFieldJoinCoordinatorName.value = ""
        joinCoordinatorViewModel.textFieldJoinCoordinatorIntro.value = ""
        joinCoordinatorViewModel.textFieldJoinCoordinatorCertificationNumber.value = ""
        joinCoordinatorViewModel.textFieldJoinCoordinatorBizLicenseNumber.value = ""
        joinCoordinatorViewModel.textFieldJoinCoordinatorMBTI.value = ""
        joinCoordinatorViewModel.textFieldJoinCoordinatorContactNumber.value = ""
        joinCoordinatorViewModel.checkBoxJoinCoordinatorConsent.value = false
    }

    fun settingButtonCheckName() {
        fragmentJoinCoordinatorBinding.apply {
            buttonJoinCoordinatorCheckName.setOnClickListener {
                val coordiName = joinCoordinatorViewModel!!.textFieldJoinCoordinatorName.value

                if (10 < coordiName!!.length) {
                    // 활동명은 10자 이내(공백포함)로 생성 가능합니다. 문구 출력
                } else {
                    CoroutineScope(Dispatchers.Main).launch {
                        coordiNameChk =
                            CoordinatorDao.checkCoordiName(joinCoordinatorViewModel?.textFieldJoinCoordinatorName?.value!!)
                        if (coordiNameChk == false) {
                            // 중복된 활동명이 있다는 문구 출력
                        } else {
                            // 사용 가능한 활동명이라는 문구 출력과 함께 editable과 clickable을 false로 설정 -> 중복확인 클릭 후 수정 방지
                        }
                    }
                }
            }
        }
    }

    fun settingButtonCoordinatorImage() {
        fragmentJoinCoordinatorBinding.apply {
            buttonCoordinatorImage.setOnClickListener {
                // 코디네이터 소개 사진 첨부
                showCameraAlbumBottomSheet()
                settingCameraAlbumBottomSheetButton(imageJoinCoordinatorPhoto)
            }
        }
    }

    fun settingButtonCoordinatorCertification() {
        fragmentJoinCoordinatorBinding.apply {
            buttonJoinCoordinatorCertificationSubmit.setOnClickListener {
                // 스타일리스트 자격증 사진 첨부
                showCameraAlbumBottomSheet()
                settingCameraAlbumBottomSheetButton(imageJoinCoordinatorCertification)
            }
        }
    }

    fun settingButtonCoordinatorPortfolio() {
        fragmentJoinCoordinatorBinding.apply {
            buttonJoinCoordinatorPortfolioSubmit.setOnClickListener {
                // 포트폴리오 사진 첨부
                showCameraAlbumBottomSheet()
                // 리싸이클러뷰 처리,,
            }
        }
    }

    fun settingButtonCoordinatorBizLicenseSubmit() {
        fragmentJoinCoordinatorBinding.apply {
            buttonJoinCoordinatorBizLicenseSubmit.setOnClickListener {
                // 사업자 등록 증명서 사진 첨부
                showCameraAlbumBottomSheet()
                settingCameraAlbumBottomSheetButton(imageJoinCoordinatorBizLicense)
            }
        }
    }

    fun showMbtiBottomSheet() {
        fragmentJoinCoordinatorBinding.apply {
            textFieldJoinCoordinatorMBTI.setOnClickListener {
                val bottomSheet = MbtiBottomSheetFragment(joinCoordinatorViewModel?.textFieldJoinCoordinatorMBTI!!)
                bottomSheet.show(mainActivity.supportFragmentManager, "MbtiBottomSheet")
            }
        }
    }


    fun showCameraAlbumBottomSheet() {
        val bottomSheet = CameraAlbumBottomSheetFragment()
        bottomSheet.show(mainActivity.supportFragmentManager, "CameraAlbumBottomSheet")
    }

    fun settingCameraAlbumBottomSheetButton(imageView: ImageView) {
        fragmentCameraAlbumBottomSheetBinding.apply {
            imageButtonCamera.setOnClickListener {
                settingCameraLauncher(imageView)
                Log.d("test1","클릭됨")
            }
            imageButtonAlbum.setOnClickListener {
                settingAlbumLauncher(imageView)
                Log.d("test2","클릭됨")

            }
        }
    }

    fun settingButtonNext() {
        fragmentJoinCoordinatorBinding.apply {
            buttonJoinCoordinatorNext.setOnClickListener {
                if (checkInput()) {
                    // JoinCoordinatorNextFragment를 보여준다.
                    val joinCoordinatorBundle = Bundle()

                    joinCoordinatorBundle.putString(
                        "coordiName",
                        textFieldJoinCoordinatorName.text.toString()
                    )
                    joinCoordinatorBundle.putString(
                        "coordIntro",
                        textFieldJoinCoordinatorIntro.text.toString()
                    )

                    //코디네이터 소개 사진은 파이어스토어에 파일 업로드 후 접근할 수 있는 파일명만 넘겨준다.
                    joinCoordinatorBundle.putString(
                        "coordiMainImage",
                        textFieldJoinCoordinatorIntro.text.toString()
                    )

                    //스타일리스트 자격증 파일은 파이어스토어에 파일 업로드 후 접근할 수 있는 파일명만 넘겨준다.
                    joinCoordinatorBundle.putString(
                        "coordiCertification",
                        textFieldJoinCoordinatorIntro.text.toString()
                    )
                    joinCoordinatorBundle.putString(
                        "coordiCertificationNumber",
                        textFieldJoinCoordinatorCertificationNumber.text.toString()
                    )

                    //포트폴리오 파일은 파이어스토어에 파일 업로드 후 접근할 수 있는 파일명만 넘겨준다.
                    joinCoordinatorBundle.putString(
                        "coordiPortfolio",
                        textFieldJoinCoordinatorIntro.text.toString()
                    )

                    //사업자 등록 증명 파일은 파이어스토어에 파일 업로드 후 접근할 수 있는 파일명만 넘겨준다.
                    joinCoordinatorBundle.putString(
                        "coordiBizLicense",
                        textFieldJoinCoordinatorIntro.text.toString()
                    )
                    joinCoordinatorBundle.putString(
                        "coordiBizLicenseNumber",
                        textFieldJoinCoordinatorBizLicenseNumber.text.toString()
                    )

                    joinCoordinatorBundle.putString(
                        "coordiMBTI",
                        textFieldJoinCoordinatorMBTI.text.toString()
                    )

                    joinCoordinatorBundle.putString(
                        "coordiContactNumber",
                        textFieldJoinCoordinatorContactNumber.text.toString()
                    )

                    mainActivity.replaceFragment(
                        MainFragmentName.JOIN_COORDINATOR_NEXT_FRAGMENT,
                        true,
                        true,
                        joinCoordinatorBundle
                    )
                }
            }
        }
    }


    // 뒤로가기 처리
    fun backProcess() {
        mainActivity.removeFragment(MainFragmentName.JOIN_COORDINATOR_FRAGMENT)
    }
      
    fun checkInput(): Boolean {
        // 입력을 체크할 항목 구성
        val coordiName = joinCoordinatorViewModel.textFieldJoinCoordinatorName.value!!
        // coordiNameChk
        val coordiIntro = joinCoordinatorViewModel.textFieldJoinCoordinatorIntro.value!!
        val coordiPhoto = fragmentJoinCoordinatorBinding.imageJoinCoordinatorPhoto
        val coordiCertifiPhoto =
            fragmentJoinCoordinatorBinding.imageJoinCoordinatorCertification
        val coordiCertifiNum =
            joinCoordinatorViewModel.textFieldJoinCoordinatorCertificationNumber.value!!
        val coordiPortfolio =
            fragmentJoinCoordinatorBinding.recyclerViewJoinCoordinatorPortfolio.size
        val coordiBizLicensePhoto =
            fragmentJoinCoordinatorBinding.imageJoinCoordinatorBizLicense
        val coordiBizLicenseNum =
            joinCoordinatorViewModel.textFieldJoinCoordinatorBizLicenseNumber.value!!
        val coordiMBTI = joinCoordinatorViewModel.textFieldJoinCoordinatorMBTI.value!!
        val coordiContactNum =
            joinCoordinatorViewModel.textFieldJoinCoordinatorContactNumber.value!!
        val checkBoxConsent = joinCoordinatorViewModel.checkBoxJoinCoordinatorConsent.value!!


        if (coordiName.isEmpty()) {
            Tools.showErrorDialog(
                mainActivity, fragmentJoinCoordinatorBinding.textFieldJoinCoordinatorName,
                "활동명 입력 오류", "활동명을 입력해주세요"
            )
            return false
        }

        if (coordiNameChk == false) {
            Tools.showErrorDialog(
                mainActivity, fragmentJoinCoordinatorBinding.textFieldJoinCoordinatorName,
                "활동명 입력 오류", "활동명 중복체크를 해주세요"
            )
            return false
        }

        if (coordiIntro.isEmpty() || coordiIntro.length > 50) {
            Tools.showErrorDialog(
                mainActivity, fragmentJoinCoordinatorBinding.textFieldJoinCoordinatorIntro,
                "소개글 입력 오류", "소개글을 입력해주세요(공백 포함 50자 이내)"
            )
            return false
        }

        if (coordiCertifiPhoto.isGone && coordiPortfolio == 0) {
            Tools.showErrorDialog(
                mainActivity,
                fragmentJoinCoordinatorBinding.textFieldJoinCoordinatorCertificationNumber,
                "자격 증명 오류",
                "자격증(사진 포함) 또는 포트폴리오 중 하나는 필수로 제출 되어야 합니다."
            )
            return false
        }

        if (coordiCertifiPhoto.isVisible && coordiCertifiNum.isEmpty()) {
            Tools.showErrorDialog(
                mainActivity,
                fragmentJoinCoordinatorBinding.textFieldJoinCoordinatorCertificationNumber,
                "자격 증명 오류",
                "자격증 사진과 번호 모두 제출 되어야 합니다."
            )
            return false
        }

        if (coordiBizLicensePhoto.isGone || coordiBizLicenseNum.isEmpty()) {
            Tools.showErrorDialog(
                mainActivity,
                fragmentJoinCoordinatorBinding.textFieldJoinCoordinatorBizLicenseNumber,
                "사업자 등록 입력 오류",
                "사업자 등록증 사진과 등록번호 모두 제출 되어야 합니다."
            )
            return false
        }

        if (coordiMBTI.isEmpty()) {
            Tools.showErrorDialog(
                mainActivity, fragmentJoinCoordinatorBinding.textFieldJoinCoordinatorMBTI,
                "MBTI 입력 오류", "MBTI를 선택해주세요."
            )
            return false
        }

        if (checkBoxConsent == false) {
            Tools.showErrorDialog(
                mainActivity, fragmentJoinCoordinatorBinding.checkBoxJoinCoordinatorConsent,
                "약관 동의", "약관 미동의시 등록 신청을 진행할 수 없습니다."
            )
            return false
        }
        return true
    }


    // 카메라 런처 설정
    fun settingCameraLauncher(imageView: ImageView) {
        val contract1 = ActivityResultContracts.StartActivityForResult()
        cameraLauncher = registerForActivityResult(contract1) {
            // 사진을 사용하겠다고 한 다음에 돌아왔을 경우
            if (it.resultCode == AppCompatActivity.RESULT_OK) {
                // 사진 객체를 생성한다.
                val bitmap = BitmapFactory.decodeFile(contentUri.path)

                // 회전 각도값을 구한다.
                val degree = Tools.getDegree(mainActivity, contentUri)
                // 회전된 이미지를 구한다.
                val bitmap2 = Tools.rotateBitmap(bitmap, degree.toFloat())
                // 크기를 조정한 이미지를 구한다.
                val bitmap3 = Tools.resizeBitmap(bitmap2, 1024)

                imageView.setImageBitmap(bitmap3)
                isAddPicture = true

                // 사진 파일을 삭제한다.
                val file = File(contentUri.path)
                file.delete()
            }
        }
    }

    // 카메라 런처를 실행하는 메서드
    fun startCameraLauncher() {
        // 촬영한 사진이 저장될 경로를 가져온다.
        contentUri = Tools.getPictureUri(mainActivity, "kr.co.lion.team4.mrco")

        if (contentUri != null) {
            val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            // 이미지가 저장될 경로를 가지고 있는 Uri 객체를 인텐트에 담아준다.
            cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, contentUri)
            cameraLauncher.launch(cameraIntent)
        }
    }

    // 앨범 런처 설정
    fun settingAlbumLauncher(imageView: ImageView) {
        val contract2 = ActivityResultContracts.StartActivityForResult()
        albumLauncher = registerForActivityResult(contract2) {
            if (it.resultCode == AppCompatActivity.RESULT_OK) {
                // 선택한 이미지의 경로 데이터를 관리하는 Uri 객체를 추출한다.
                val uri = it.data?.data
                if (uri != null) {
                    // 안드로이드 Q(10) 이상이라면
                    val bitmap = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
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

                            // 이미지를 생성한다
                            BitmapFactory.decodeFile(source)
                        } else {
                            null
                        }
                    }

                    // 회전 각도값을 가져온다.
                    val degree = Tools.getDegree(mainActivity, uri)
                    // 회전 이미지를 가져온다
                    val bitmap2 = Tools.rotateBitmap(bitmap!!, degree.toFloat())
                    // 크기를 줄인 이미지를 가져온다.
                    val bitmap3 = Tools.resizeBitmap(bitmap2, 1024)

                    imageView.setImageBitmap(bitmap3)
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
}