package kr.co.lion.team4.mrco.fragment

import android.content.DialogInterface
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kr.co.lion.team4.mrco.MainActivity
import kr.co.lion.team4.mrco.MainFragmentName
import kr.co.lion.team4.mrco.R
import kr.co.lion.team4.mrco.Tools
import kr.co.lion.team4.mrco.dao.CoordinatorDao
import kr.co.lion.team4.mrco.databinding.FragmentJoinCoordinatorNextBinding
import kr.co.lion.team4.mrco.model.CoordinatorModel
import kr.co.lion.team4.mrco.viewmodel.JoinCoordinatorNextViewModel
import java.text.SimpleDateFormat
import java.util.*

class JoinCoordinatorNextFragment : Fragment() {
    lateinit var fragmentJoinCoordinatorNextBinding: FragmentJoinCoordinatorNextBinding
    lateinit var mainActivity: MainActivity
    lateinit var joinCoordinatorNextViewModel: JoinCoordinatorNextViewModel
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        fragmentJoinCoordinatorNextBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_join_coordinator_next, container, false)
        joinCoordinatorNextViewModel = JoinCoordinatorNextViewModel()
        fragmentJoinCoordinatorNextBinding.joinCoordinatorNextViewModel = joinCoordinatorNextViewModel
        fragmentJoinCoordinatorNextBinding.lifecycleOwner = this

        settingButtonPrevious() // 뒤로가기 버튼
        // settingButtonJoinCoordinatorNextSubmit() // 신청완료 버튼

        return fragmentJoinCoordinatorNextBinding.root
    }

    // 신청완료 버튼
    fun settingButtonJoinCoordinatorNextSubmit(){
        fragmentJoinCoordinatorNextBinding.buttonJoinCoordinatorNextSubmit.setOnClickListener {
            // 입력 요소 검사 수행
            val check = checkTextInput()

            // 모든 입력 요소가 입력되었다면
            if(check){
                submitCoordinatorRegistration() // 입력한 신청 정보를 서버에 저장
            }
        }
    }

    // 데이터를 저장하고, 이전 화면으로 이동한다.
    fun submitCoordinatorRegistration(){
        CoroutineScope(Dispatchers.Main).launch {
            val coordinatorData = CoordinatorModel()
            // 사용자 번호 시퀀스 값을 가져온다.
            val coordiSequence = CoordinatorDao.getCoordinatorSequence()

            // 시퀀스 값을 1 증가시켜 업데이트해준다.
            CoordinatorDao.updateCoordinatorSequence(coordiSequence+1)

            val TEMP_IDX = -1

            // 저장할 데이터를 가져온다.
            coordinatorData.coordi_idx = coordiSequence+1 // 코디네이터 인덱스 번호
            coordinatorData.coordi_user_idx = TEMP_IDX // todo 로그인한 사용자의 user 인덱스 번호

            // 이전 화면에서 작성한 코디네이터 정보
            coordinatorData.coordi_name = arguments?.getString("coordiName")!! // 코디네이터
            coordinatorData.coordi_intro_text = arguments?.getString("coordiIntroText")!! // 코디네이터 소개글
            coordinatorData.coordi_photo = arguments?.getString("coordiPhoto")!! // 코디네이터 사진
            coordinatorData.coordi_license = arguments?.getString("coordiLicense")!! // 코디네이터 자격증 파일명
            coordinatorData.coordi_license_num = arguments?.getString("coordiLicenseNum")!! // 코디네이터 자격증 번호
            coordinatorData.coordi_portfolio = arguments?.getString("coordiPortfolio")!! // 코디네이터 포트폴리오 파일명
            coordinatorData.coordi_business_license = arguments?.getString("coordiBusinessLicense")!! // 사업자 등록증 파일명
            coordinatorData.coordi_mbti = arguments?.getInt("coordiMbti")!! // MBTI
            coordinatorData.coordi_business_phone = arguments?.getString("coordiBusinessPhone")!! // 고객 노출 연락처

            // 출고지 주소
            val coordiWarehouseAddress = joinCoordinatorNextViewModel.textFieldJoinCoordinatorNextWarehouseAddress.value!!
            // 출고지 상세 주소
            val coordiWarehouseAddressDetail = joinCoordinatorNextViewModel.textFieldJoinCoordinatorNextWarehouseAddressDetail.value!!
            // 출고지 전체 주소
            coordinatorData.coordi_forwarding_loc = "$coordiWarehouseAddress $coordiWarehouseAddressDetail"// 출고지

            // 반품지 주소
            val coordiReturnAddress = joinCoordinatorNextViewModel.textFieldJoinCoordinatorNextReturnAddress.value!!
            // 반품지 상세 주소
            val coordiReturnAddressDetail = joinCoordinatorNextViewModel.textFieldJoinCoordinatorNextReturnAddressDetail.value!!
            // 반품지 전체 주소
            coordinatorData.coordi_return_loc = "$coordiReturnAddress $coordiReturnAddressDetail"// 반품지

            // 은행
            coordinatorData.coordi_bank = joinCoordinatorNextViewModel.textFieldJoinCoordinatorNextSettlementBank.value!!
            // 예금주
            coordinatorData.coordi_account_holder = joinCoordinatorNextViewModel.textFieldJoinCoordinatorNextSettlementAccountHolder.value!!
            // 계좌번호
            coordinatorData.coordi_account = joinCoordinatorNextViewModel.textFieldJoinCoordinatorNextSettlementAccountNumber.value!!

            // 코디네이터 등록 신청일
            val calendar = Calendar.getInstance()
            calendar.time = Date()
            val simpleDateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.KOREA)
            // 종료일 (현재)
            coordinatorData.coordi_request_date = simpleDateFormat.format(calendar.time)

            // 코디네이터 등록 신청 정보를 저장
            CoordinatorDao.insertCoordinatorData(coordinatorData)

            // 신청 완료 팝업
            val materialAlertDialogBuilder = MaterialAlertDialogBuilder(mainActivity)
            materialAlertDialogBuilder.setTitle("신청 완료")
            materialAlertDialogBuilder.setMessage("감사합니다.\n코디네이터 신청이 완료되었습니다.\n심사 결과는 3일 이내에 이메일에서 확인 가능합니다. ")
            materialAlertDialogBuilder.setPositiveButton("확인") { dialogInterface: DialogInterface, i: Int ->
                // 로그인 화면으로 돌아간다
                mainActivity.removeFragment(MainFragmentName.JOIN_COORDINATOR_FRAGMENT)
                mainActivity.removeFragment(MainFragmentName.JOIN_COORDINATOR_NEXT_FRAGMENT)
            }
            materialAlertDialogBuilder.show()
        }
    }


    // 모든 항목을 입력했는 지 확인
    fun checkTextInput() : Boolean {
        // 입력된 내용을 가져온다.
        // 출고지 주소
        val coordiWarehouseAddress = joinCoordinatorNextViewModel.textFieldJoinCoordinatorNextWarehouseAddress.value!!
        // 출고지 상세 주소
        val coordiWarehouseAddressDetail = joinCoordinatorNextViewModel.textFieldJoinCoordinatorNextWarehouseAddressDetail.value!!
        // 반품지 주소
        val coordiReturnAddress = joinCoordinatorNextViewModel.textFieldJoinCoordinatorNextReturnAddress.value!!
        // 반품지 상세 주소
        val coordiReturnAddressDetail = joinCoordinatorNextViewModel.textFieldJoinCoordinatorNextReturnAddressDetail.value!!
        // 은행
        val coordiBank = joinCoordinatorNextViewModel.textFieldJoinCoordinatorNextSettlementBank.value!!
        // 예금주
        val coordiAccountHolder = joinCoordinatorNextViewModel.textFieldJoinCoordinatorNextSettlementAccountHolder.value!!
        // 계좌번호
        val coordiAccountNumber = joinCoordinatorNextViewModel.textFieldJoinCoordinatorNextSettlementAccountNumber.value!!

        // 출고지 주소가 입력되지 않았다면
        if(coordiWarehouseAddress.isEmpty()){
            Tools.showErrorDialog(
                mainActivity, fragmentJoinCoordinatorNextBinding.textFieldJoinCoordinatorNextWarehouseAddress,
                "출고지 주소 입력 오류", "출고지 주소를 입력해주세요"
            )
            return false
        }

        // 출고지 상세 주소가 입력되지 않았다면
        if(coordiWarehouseAddressDetail.isEmpty()){
            Tools.showErrorDialog(
                mainActivity, fragmentJoinCoordinatorNextBinding.textFieldJoinCoordinatorNextWarehouseAddressDetail,
                "출고지 상세 주소 입력 오류", "출고지 상세 주소를 입력해주세요"
            )
            return false
        }

        // 반품지 주소가 입력되지 않았다면
        if(coordiReturnAddress.isEmpty()){
            Tools.showErrorDialog(
                mainActivity, fragmentJoinCoordinatorNextBinding.textFieldJoinCoordinatorNextReturnAddress,
                "반품지 주소 입력 오류", "반품지 주소를 입력해주세요"
            )
            return false
        }

        // 반품지 상세 주소가 입력되지 않았다면
        if(coordiReturnAddressDetail.isEmpty()){
            Tools.showErrorDialog(
                mainActivity, fragmentJoinCoordinatorNextBinding.textFieldJoinCoordinatorNextReturnAddressDetail,
                "반품지 주소 입력 오류", "반품지 상세 주소를 입력해주세요"
            )
            return false
        }

        // 은행이 입력되지 않았다면
        if(coordiBank.isEmpty()){
            Tools.showErrorDialog(
                mainActivity, fragmentJoinCoordinatorNextBinding.textFieldJoinCoordinatorNextSettlementBank,
                "은행 입력 오류", "은행을 입력해주세요"
            )
            return false
        }

        // 예금주가 입력되지 않았다면
        if(coordiAccountHolder.isEmpty()){
            Tools.showErrorDialog(
                mainActivity, fragmentJoinCoordinatorNextBinding.textFieldJoinCoordinatorNextSettlementAccountHolder,
                "예금주 입력 오류", "예금주를 입력해주세요"
            )
            return false
        }

        // 계좌번호가 입력되지 않았다면
        if(coordiAccountNumber.isEmpty()){
            Tools.showErrorDialog(
                mainActivity, fragmentJoinCoordinatorNextBinding.textFieldJoinCoordinatorNextSettlementAccountNumber,
                "계좌번호 입력 오류", "계좌번호를 입력해주세요"
            )
            return false
        }

        return true // 모든 입력요소 입력 확인
    }

    // 뒤로 가기 버튼
    fun settingButtonPrevious(){
        fragmentJoinCoordinatorNextBinding.buttonJoinCoordinatorNextPrevious.setOnClickListener {
            backProcess()
        }
    }

    fun backProcess(){
        mainActivity.removeFragment(MainFragmentName.JOIN_COORDINATOR_NEXT_FRAGMENT)
    }
}