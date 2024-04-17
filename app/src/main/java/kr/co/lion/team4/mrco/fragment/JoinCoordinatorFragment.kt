package kr.co.lion.team4.mrco.fragment

import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isGone
import androidx.core.view.isVisible
import androidx.core.view.size
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kr.co.lion.team4.mrco.MainActivity
import kr.co.lion.team4.mrco.MainFragmentName
import kr.co.lion.team4.mrco.R
import kr.co.lion.team4.mrco.Tools
import kr.co.lion.team4.mrco.dao.CoordinatorDao
import kr.co.lion.team4.mrco.databinding.FragmentJoinCoordinatorBinding
import kr.co.lion.team4.mrco.viewmodel.JoinCoordinatorViewModel


class JoinCoordinatorFragment : Fragment() {

    lateinit var fragmentJoinCoordinatorBinding: FragmentJoinCoordinatorBinding
    lateinit var mainActivity: MainActivity
    lateinit var joinCoordinatorViewModel: JoinCoordinatorViewModel

    var coordiNameChk: Boolean = false

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        fragmentJoinCoordinatorBinding = FragmentJoinCoordinatorBinding.inflate(inflater)
        mainActivity = activity as MainActivity
        joinCoordinatorViewModel = JoinCoordinatorViewModel()
        fragmentJoinCoordinatorBinding.joinCoordinatorViewModel = joinCoordinatorViewModel

        // 툴바 및 하단 바 세팅
        settingToolbar()

        initInput()

        settingButtonCheckName()

        showMbtiBottomSheet()

        settingButtonNext()

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
        fragmentJoinCoordinatorBinding.buttonCoordinatorImage.setOnClickListener {
            // 코디네이터 메인 사진 첨부
        }
    }

    fun settingButtonCoordinatorCertification() {
        fragmentJoinCoordinatorBinding.buttonJoinCoordinatorCertificationSubmit.setOnClickListener {
            // 스타일리스트 자격증 사진 첨부
        }
    }

    fun settingButtonCoordinatorPortfolio() {
        fragmentJoinCoordinatorBinding.buttonJoinCoordinatorPortfolioSubmit.setOnClickListener {
            // 포트폴리오 사진 첨부
        }
    }

    fun settingButtonCoordinatorBizLicenseSubmit() {
        fragmentJoinCoordinatorBinding.buttonJoinCoordinatorBizLicenseSubmit.setOnClickListener {
            // 사업자 등록 증명서 사진 첨부
        }
    }

    fun showMbtiBottomSheet() {
        fragmentJoinCoordinatorBinding.apply {
            textFieldJoinCoordinatorMBTI.setOnClickListener {
                val bottomSheet = MbtiBottomSheetFragment()
                bottomSheet.show(mainActivity.supportFragmentManager, "MbtiBottomSheet")
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

                    //코디 메인 사진은 파이어스토어에 파일 업로드 후 접근할 수 있는 파일명만 넘겨준다.
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
        val coordiCertifiPhoto = fragmentJoinCoordinatorBinding.imageJoinCoordinatorCertification
        val coordiCertifiNum =
            joinCoordinatorViewModel.textFieldJoinCoordinatorCertificationNumber.value!!
        val coordiPortfolio =
            fragmentJoinCoordinatorBinding.recyclerViewJoinCoordinatorPortfolio.size
        val coordiBizLicensePhoto = fragmentJoinCoordinatorBinding.imageJoinCoordinatorBizLicense
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
}