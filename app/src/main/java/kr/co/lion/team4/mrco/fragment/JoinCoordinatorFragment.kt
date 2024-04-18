package kr.co.lion.team4.mrco.fragment

import android.content.DialogInterface
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Color
import android.graphics.ImageDecoder
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.isGone
import androidx.core.view.isVisible
import androidx.core.view.size
import androidx.core.widget.addTextChangedListener
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import kr.co.lion.team4.mrco.MainActivity
import kr.co.lion.team4.mrco.MainFragmentName
import kr.co.lion.team4.mrco.R
import kr.co.lion.team4.mrco.Tools
import kr.co.lion.team4.mrco.dao.CoordinatorDao
import kr.co.lion.team4.mrco.databinding.FragmentJoinCoordinatorBinding
import kr.co.lion.team4.mrco.databinding.RowJoinCoordinatorPortfolioBinding
import kr.co.lion.team4.mrco.viewmodel.JoinCoordinatorViewModel
import java.io.File

class JoinCoordinatorFragment : Fragment() {

    lateinit var fragmentJoinCoordinatorBinding: FragmentJoinCoordinatorBinding
    lateinit var mainActivity: MainActivity
    lateinit var joinCoordinatorViewModel: JoinCoordinatorViewModel

    // 이미지를 첨부한 적이 있는지...
    var isAddPicture = false

    var coordiNameChk: Boolean = false

    // Activity 실행을 위한 런처
    lateinit var albumLauncher: ActivityResultLauncher<Intent>

    // 버튼 분기를 위한 객체
    var button = 0

    // 포트폴리오 리싸이클러뷰 구성을 위한 리스트 객체
    var portfolioImageList = mutableListOf<Bitmap>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        fragmentJoinCoordinatorBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_join_coordinator, container, false)
        joinCoordinatorViewModel = JoinCoordinatorViewModel()
        fragmentJoinCoordinatorBinding.joinCoordinatorViewModel = joinCoordinatorViewModel
        fragmentJoinCoordinatorBinding.lifecycleOwner = this

        mainActivity = activity as MainActivity

        
        initInput()

        settingToolbar()

        settingButtonCheckName()

        showMbtiBottomSheet()

        settingButtonNext()

        settingAlbumLauncher()

        settingRecyclerViewPortfolio()

        settingButtonCoordinatorPhoto()
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
                    Tools.hideSoftInput(mainActivity)
                    backProcess()
                }
            }
        }
    }

    fun initInput() {
        fragmentJoinCoordinatorBinding.apply {
            textFieldJoinCoordinatorName.addTextChangedListener {
                coordiNameChk = false
                buttonJoinCoordinatorCheckName.apply {
                    isEnabled = true
                    backgroundTintList = ContextCompat.getColorStateList(mainActivity,R.color.buttonFollow)
                    setTextColor(Color.parseColor("#FFFFFF"))
                }
            }
        }
        joinCoordinatorViewModel.textFieldJoinCoordinatorIntro.value = ""
        joinCoordinatorViewModel.textFieldJoinCoordinatorCertificationNumber.value = ""
        joinCoordinatorViewModel.textFieldJoinCoordinatorBizLicenseNumber.value = ""
        joinCoordinatorViewModel.textFieldJoinCoordinatorMBTI.value = ""
        joinCoordinatorViewModel.textFieldJoinCoordinatorContactNumber.value = ""
        joinCoordinatorViewModel.checkBoxJoinCoordinatorConsent.value = false
    }

    // 활동명 중복확인
    fun settingButtonCheckName() {
        fragmentJoinCoordinatorBinding.apply {
            buttonJoinCoordinatorCheckName.setOnClickListener {
                Tools.hideSoftInput(mainActivity)
                val coordiName = joinCoordinatorViewModel!!.textFieldJoinCoordinatorName.value

                if (coordiName.isNullOrEmpty()) { // 활동명이 입력되지 않은 경우)
                    Tools.showErrorDialog(
                        mainActivity, fragmentJoinCoordinatorBinding.textFieldJoinCoordinatorName,
                        "활동명 입력 오류", "활동명을 입력해주세요"
                    )
                    coordiNameChk = false
                    return@setOnClickListener
                } else {
                    CoroutineScope(Dispatchers.Main).launch {
                        val coordiNameDuplication =
                            CoordinatorDao.checkCoordiName(joinCoordinatorViewModel?.textFieldJoinCoordinatorName?.value!!)
                        if (coordiNameDuplication == false) {  // 입력한 활동명이 중복된 경우
                            joinCoordinatorViewModel?.textFieldJoinCoordinatorName?.value = ""
                            Tools.showErrorDialog(
                                mainActivity,
                                fragmentJoinCoordinatorBinding.textFieldJoinCoordinatorName,
                                "활동명 중복",
                                "존재하는 활동명입니다\n다른 활동명을 입력해주세요"
                            )
                            coordiNameChk = false
                        }
                        // 중복되지 않은 활동명을 입력한 경우
                        else {
                            val materialAlertDialogBuilder = MaterialAlertDialogBuilder(
                                mainActivity,
                                R.style.MaterialAlertDialog_Theme
                            )
                            materialAlertDialogBuilder.setMessage("사용 가능한 활동명 입니다")
                            materialAlertDialogBuilder.setNegativeButton("확인") { dialogInterface: DialogInterface, i: Int ->
                                Tools.hideSoftInput(mainActivity)
                                coordiNameChk = true
                                buttonJoinCoordinatorCheckName.apply {
                                    isEnabled = false
                                    backgroundTintList = ContextCompat.getColorStateList(mainActivity, R.color.gray)
                                    setTextColor(Color.parseColor("#E1E3E5"))
                                }
                            }
                            materialAlertDialogBuilder.show()
                        }
                    }
                }
            }
        }
    }

    fun settingButtonCoordinatorPhoto() {
        fragmentJoinCoordinatorBinding.apply {
            buttonCoordinatorPhoto.setOnClickListener {
                Tools.hideSoftInput(mainActivity)
                // 코디네이터 소개 사진 첨부
                button = R.id.buttonCoordinatorPhoto
                startAlbumLauncher()
            }
        }
    }

    fun settingButtonCoordinatorCertification() {
        fragmentJoinCoordinatorBinding.apply {
            buttonCoordinatorCertification.setOnClickListener {
                Tools.hideSoftInput(mainActivity)
                // 스타일리스트 자격증 사진 첨부
                button = R.id.buttonCoordinatorCertification
                startAlbumLauncher()
            }
        }
    }

    fun settingButtonCoordinatorPortfolio() {
        fragmentJoinCoordinatorBinding.apply {
            buttonCoordinatorPortfolio.setOnClickListener {
                Tools.hideSoftInput(mainActivity)
                // 포트폴리오 사진 첨부
                button = R.id.buttonCoordinatorPortfolio
                startAlbumLauncher()
            }
        }
    }

    fun settingButtonCoordinatorBizLicenseSubmit() {
        fragmentJoinCoordinatorBinding.apply {
            buttonCoordinatorBizLicense.setOnClickListener {
                Tools.hideSoftInput(mainActivity)
                // 사업자 등록 증명서 사진 첨부
                button = R.id.buttonCoordinatorBizLicense
                startAlbumLauncher()
            }
        }
    }

    fun showMbtiBottomSheet() {
        fragmentJoinCoordinatorBinding.apply {
            textFieldJoinCoordinatorMBTI.setOnClickListener {
                Tools.hideSoftInput(mainActivity)
                val bottomSheet =
                    MbtiBottomSheetFragment(joinCoordinatorViewModel?.textFieldJoinCoordinatorMBTI!!)
                bottomSheet.show(mainActivity.supportFragmentManager, "MbtiBottomSheet")
            }
        }
    }

    fun settingButtonNext() {
        fragmentJoinCoordinatorBinding.apply {
            buttonJoinCoordinatorNext.setOnClickListener {
                Tools.hideSoftInput(mainActivity)
//                if (checkInput()) {
//                    // JoinCoordinatorNextFragment를 보여준다.
//                    val joinCoordinatorBundle = Bundle()
//
//                    joinCoordinatorBundle.putString(
//                        "coordiName",
//                        textFieldJoinCoordinatorName.text.toString()
//                    )
//                    joinCoordinatorBundle.putString(
//                        "coordIntro",
//                        textFieldJoinCoordinatorIntro.text.toString()
//                    )
//
//                    //코디네이터 소개 사진은 파이어스토어에 파일 업로드 후 접근할 수 있는 파일명만 넘겨준다.
//                    joinCoordinatorBundle.putString(
//                        "coordiMainImage",
//                        textFieldJoinCoordinatorIntro.text.toString()
//                    )
//
//                    //스타일리스트 자격증 파일은 파이어스토어에 파일 업로드 후 접근할 수 있는 파일명만 넘겨준다.
//                    joinCoordinatorBundle.putString(
//                        "coordiCertification",
//                        textFieldJoinCoordinatorIntro.text.toString()
//                    )
//                    joinCoordinatorBundle.putString(
//                        "coordiCertificationNumber",
//                        textFieldJoinCoordinatorCertificationNumber.text.toString()
//                    )
//
//                    //포트폴리오 파일은 파이어스토어에 파일 업로드 후 접근할 수 있는 파일명만 넘겨준다.
//                    joinCoordinatorBundle.putString(
//                        "coordiPortfolio",
//                        textFieldJoinCoordinatorIntro.text.toString()
//                    )
//
//                    //사업자 등록 증명 파일은 파이어스토어에 파일 업로드 후 접근할 수 있는 파일명만 넘겨준다.
//                    joinCoordinatorBundle.putString(
//                        "coordiBizLicense",
//                        textFieldJoinCoordinatorIntro.text.toString()
//                    )
//                    joinCoordinatorBundle.putString(
//                        "coordiBizLicenseNumber",
//                        textFieldJoinCoordinatorBizLicenseNumber.text.toString()
//                    )
//
//                    joinCoordinatorBundle.putString(
//                        "coordiMBTI",
//                        textFieldJoinCoordinatorMBTI.text.toString()
//                    )
//
//                    joinCoordinatorBundle.putString(
//                        "coordiContactNumber",
//                        textFieldJoinCoordinatorContactNumber.text.toString()
//                    )
//
//                    mainActivity.replaceFragment(
//                        MainFragmentName.JOIN_COORDINATOR_NEXT_FRAGMENT,
//                        true,
//                        true,
//                        joinCoordinatorBundle
//                    )
//            }
//        }
                // 화면 이동을 위한 임시 메서드
                mainActivity.replaceFragment(
                    MainFragmentName.JOIN_COORDINATOR_NEXT_FRAGMENT,
                    true,
                    true,
                    null
                )
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
        val coordiPhoto = fragmentJoinCoordinatorBinding.imageViewJoinCoordinatorPhoto
        val coordiCertifiPhoto =
            fragmentJoinCoordinatorBinding.imageViewJoinCoordinatorCertification
        val coordiCertifiNum =
            joinCoordinatorViewModel.textFieldJoinCoordinatorCertificationNumber.value!!
        val coordiPortfolio =
            fragmentJoinCoordinatorBinding.recyclerViewJoinCoordinatorPortfolio.size
        val coordiBizLicensePhoto =
            fragmentJoinCoordinatorBinding.imageViewJoinCoordinatorBizLicense
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

        if (coordiIntro.isEmpty()) {
            Tools.showErrorDialog(
                mainActivity, fragmentJoinCoordinatorBinding.textFieldJoinCoordinatorIntro,
                "소개글 입력 오류", "소개글을 입력해주세요"
            )
            return false
        }

        if (coordiPhoto.isGone) {
            Tools.showErrorDialog(
                mainActivity, fragmentJoinCoordinatorBinding.textFieldJoinCoordinatorIntro,
                "코디네이터 소개 사진 제출 오류", "코디네이터 소개 사진을 제출해주세요"
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

        if (coordiContactNum.isEmpty()) {
            Tools.showErrorDialog(
                mainActivity, fragmentJoinCoordinatorBinding.textFieldJoinCoordinatorMBTI,
                "고객 노출 연락처 입력 오류", "고객 노출 연락처를 입력해주세요."
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


    // 앨범 런처 설정
    fun settingAlbumLauncher() {
        // 앨범 실행을 위한 런처
        val contract = ActivityResultContracts.StartActivityForResult()
        albumLauncher = registerForActivityResult(contract) {
            fragmentJoinCoordinatorBinding.apply {
                // 사진 선택을 완료한 후 돌아왔다면
                if (it.resultCode == AppCompatActivity.RESULT_OK) {
                    when (button) {
                        R.id.buttonCoordinatorPhoto -> imageViewJoinCoordinatorPhoto.isVisible =
                            true

                        R.id.buttonCoordinatorCertification -> imageViewJoinCoordinatorCertification.isVisible =
                            true

                        R.id.buttonCoordinatorBizLicense -> imageViewJoinCoordinatorBizLicense.isVisible =
                            true
                    }

                    // 선택한 이미지의 경로 데이터를 관리하는 Uri 객체를 추출한다.
                    val uri = it.data?.data
                    if (uri != null) {
                        // 안드로이드 Q(10) 이상이라면
                        val bitmap = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                            // 이미지를 생성할 수 있는 객체를 생성한다.
                            val source =
                                ImageDecoder.createSource(mainActivity.contentResolver, uri)
                            // Bitmap을 생성한다.
                            ImageDecoder.decodeBitmap(source)
                        } else {
                            // 컨텐츠 프로바이더를 통해 이미지 데이터에 접근한다.
                            val cursor =
                                mainActivity.contentResolver.query(uri, null, null, null, null)
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
                        val bitmap3 = Tools.resizeBitmap(bitmap2, 256)

                        when (button) {
                            R.id.buttonCoordinatorPhoto -> imageViewJoinCoordinatorPhoto.setImageBitmap(
                                bitmap3
                            )

                            R.id.buttonCoordinatorCertification -> imageViewJoinCoordinatorCertification.setImageBitmap(
                                bitmap3
                            )

                            R.id.buttonCoordinatorPortfolio -> {
                                portfolioImageList.add(bitmap3)
                                recyclerViewJoinCoordinatorPortfolio.adapter?.notifyDataSetChanged()
                                Log.d("test1234", "rr")
                                Log.d("test1234", "$portfolioImageList")

                            }

                            R.id.buttonCoordinatorBizLicense -> imageViewJoinCoordinatorBizLicense.setImageBitmap(
                                bitmap3
                            )
                        }
                        isAddPicture = true
                    }
                }
            }
        }
    }

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


    // 유저 후기 RecyclerView 구성
    fun settingRecyclerViewPortfolio() {
        fragmentJoinCoordinatorBinding.apply {
            if (portfolioImageList.size != 0) {
                recyclerViewJoinCoordinatorPortfolio.apply {
                    // 어뎁터
                    adapter = PortfolioRecyclerViewAdapter()
                    // 레이아웃 매니저
                    layoutManager =
                        LinearLayoutManager(mainActivity, LinearLayoutManager.HORIZONTAL, false)
                }
            }
        }
    }

    inner class PortfolioRecyclerViewAdapter :
        RecyclerView.Adapter<PortfolioRecyclerViewAdapter.PortfolioViewHolder>() {

        inner class PortfolioViewHolder(rowJoinCoordinatorPortfolioBinding: RowJoinCoordinatorPortfolioBinding) :
            RecyclerView.ViewHolder(rowJoinCoordinatorPortfolioBinding.root) {
            val rowJoinCoordinatorPortfolioBinding: RowJoinCoordinatorPortfolioBinding

            init {
                this.rowJoinCoordinatorPortfolioBinding = rowJoinCoordinatorPortfolioBinding

                rowJoinCoordinatorPortfolioBinding.root.layoutParams = ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT
                )
            }
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PortfolioViewHolder {
            val rowJoinCoordinatorPortfolioBinding =
                DataBindingUtil.inflate<RowJoinCoordinatorPortfolioBinding>(
                    layoutInflater,
                    R.layout.row_join_coordinator_portfolio,
                    parent,
                    false
                )
            rowJoinCoordinatorPortfolioBinding.lifecycleOwner = this@JoinCoordinatorFragment

            val PortfolioViewHolder = PortfolioViewHolder(rowJoinCoordinatorPortfolioBinding)
            return PortfolioViewHolder
        }

        override fun getItemCount(): Int {
            return portfolioImageList.size
        }

        override fun onBindViewHolder(holder: PortfolioViewHolder, position: Int) {
//            // position 값에 따라 다른 이미지 설정
//            val imageResource = when (position % 10) {
//                1 -> portfolioImageList[position]
//                2 -> portfolioImageList[position]
//                3 -> portfolioImageList[position]
//                4 -> portfolioImageList[position]
//                5 -> portfolioImageList[position]
//                6 -> portfolioImageList[position]
//                7 -> portfolioImageList[position]
//                8 -> portfolioImageList[position]
//                9 -> portfolioImageList[position]
//                else -> portfolioImageList[position]
//            }

            holder.rowJoinCoordinatorPortfolioBinding.imageViewPortfolio.setImageBitmap(
                portfolioImageList[position]
            )
        }
    }
}