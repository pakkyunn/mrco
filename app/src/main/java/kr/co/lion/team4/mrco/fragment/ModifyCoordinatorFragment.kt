package kr.co.lion.team4.mrco.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kr.co.lion.team4.mrco.MainActivity
import kr.co.lion.team4.mrco.MainFragmentName
import kr.co.lion.team4.mrco.R
import kr.co.lion.team4.mrco.Tools
import kr.co.lion.team4.mrco.dao.CoordinatorDao
import kr.co.lion.team4.mrco.databinding.FragmentModifyCoordinatorBinding
import kr.co.lion.team4.mrco.model.CoordinatorModel
import kr.co.lion.team4.mrco.viewmodel.ModifyCoordinatorViewModel


class ModifyCoordinatorFragment : Fragment() {

    lateinit var fragmentModifyCoordinatorBinding: FragmentModifyCoordinatorBinding
    lateinit var modifyCoordinatorViewModel: ModifyCoordinatorViewModel
    lateinit var mainActivity: MainActivity

    // 서버에서 가져올 로그인한 코디네이터 정보를 담을 객체
    lateinit var originalCoordinatorModel: CoordinatorModel


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment

        fragmentModifyCoordinatorBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_modify_coordinator, container, false)
        modifyCoordinatorViewModel = ModifyCoordinatorViewModel()
        fragmentModifyCoordinatorBinding.modifyCoordinatorViewModel = modifyCoordinatorViewModel
        fragmentModifyCoordinatorBinding.lifecycleOwner = this

        mainActivity = activity as MainActivity

        gettingCoordinatorDataFromServer()

        settingButtonModifyCoordinatorSubmit()


        // 테스트용 바텀시트 노출 버튼
        fragmentModifyCoordinatorBinding.buttonModifyCoordinatorSubmit.setOnClickListener {
            showPwChangeBottomSheet()
        }

        return fragmentModifyCoordinatorBinding.root
    }

    fun settingButtonModifyCoordinatorSubmit() {
        fragmentModifyCoordinatorBinding.buttonModifyCoordinatorSubmit.setOnClickListener {
            updateCoordinatorData()
        }
    }


    // 댓글을 보여불 BottomSheet를 띄워준다.
    fun showPwChangeBottomSheet(){
        val pwChangeBottomSheetFragment = PwChangeBottomSheetFragment()
        pwChangeBottomSheetFragment.show(mainActivity.supportFragmentManager, "PwChangeBottomSheet")
    }

    // 서버로부터 로그인한 코디네이터의 (loginUserIdx 또는 loginUserId) 정보를 가져와 입력 요소들에 설정해주는 메서드
    fun gettingCoordinatorDataFromServer(){
        CoroutineScope(Dispatchers.Main).launch{
            // 입력 요소들에 공백 문자열을 넣어준다.
            modifyCoordinatorViewModel.textFieldModifyCoordinatorName.value = " "
            modifyCoordinatorViewModel.textFieldModifyCoordinatorIntroText.value = " "
            //modifyCoordinatorViewModel.textFieldModifyCoordinatorEmail.value = " "
            modifyCoordinatorViewModel.textFieldModifyCoordinatorCustomerRelationPhone.value = " "

//            modifyCoordinatorViewModel.textFieldModifyCoordinatorPhone.value = " "
//            modifyCoordinatorViewModel.textFieldModifyCoordinatorAddress.value = " "
//            modifyCoordinatorViewModel.textFieldModifyCoordinatorAddressDetail.value = " "
            modifyCoordinatorViewModel.textFieldModifyCoordinatorWarehouseAddress.value = " "

            modifyCoordinatorViewModel.textFieldModifyCoordinatorWarehouseAddressDetail.value = " "
            modifyCoordinatorViewModel.textFieldModifyCoordinatorReturnAddress.value = " "
            modifyCoordinatorViewModel.textFieldJModifyCoordinatorReturnAddressDetail.value = " "
            modifyCoordinatorViewModel.textFieldModifyCoordinatorSettlementBank.value = " "

            modifyCoordinatorViewModel.textFieldModifyCoordinatorSettlementAccountHolder.value = " "
            modifyCoordinatorViewModel.textFieldModifyCoordinatorSettlementAccountNumber.value = " "

            // 서버에서 로그인한 코디네이터인 loginUserIdx 의 데이터를 가져와 originalCoordinatorModel 에 담는다.
            originalCoordinatorModel = CoordinatorDao.gettingCoordinatorInfoByCoordiIdx(mainActivity.loginUserIdx)!!
            //originalCoordinatorModel = CoordinatorDao.gettingCoordinatorInfoByCoordiIdx(1)!!

//            data class CoordinatorModel(var coordi_idx: Int, var coordi_user_idx: Int, var coordi_name: String, var coordi_intro_text: String,
//                                        var coordi_photo: String, var coordi_license: String, var coordi_license_num: String, var coordi_portfolio: String, var coordi_business_license : String,
//                                        var coordi_mbti: String, var coordi_business_phone: String, var coordi_forwarding_loc: String,
//                                        var coordi_return_loc: String, var coordi_bank: String, var coordi_account_holder: String, var coordi_account: String,
//                                        var coordi_followers: Int, var coordi_permission: Boolean, var coordi_request_date: String) {

            // 코디네이터 정보들을 뷰에 넣어준다.
            modifyCoordinatorViewModel.textFieldModifyCoordinatorName.value = originalCoordinatorModel.coordi_name
            modifyCoordinatorViewModel.textFieldModifyCoordinatorIntroText.value = originalCoordinatorModel.coordi_intro_text
            //modifyCoordinatorViewModel.textFieldModifyCoordinatorEmail.value = originalCoordinatorModel.
            modifyCoordinatorViewModel.textFieldModifyCoordinatorCustomerRelationPhone.value = originalCoordinatorModel.coordi_business_phone

            //modifyCoordinatorViewModel.textFieldModifyCoordinatorPhone.value = originalCoordinatorModel.
            //modifyCoordinatorViewModel.textFieldModifyCoordinatorAddress.value = originalCoordinatorModel.
            //modifyCoordinatorViewModel.textFieldModifyCoordinatorAddressDetail.value = originalCoordinatorModel.
            modifyCoordinatorViewModel.textFieldModifyCoordinatorWarehouseAddress.value = originalCoordinatorModel.coordi_forwarding_loc

            //modifyCoordinatorViewModel.textFieldModifyCoordinatorWarehouseAddressDetail.value = originalCoordinatorModel.
            modifyCoordinatorViewModel.textFieldModifyCoordinatorReturnAddress.value = originalCoordinatorModel.coordi_return_loc
            //modifyCoordinatorViewModel.textFieldJModifyCoordinatorReturnAddressDetail.value = originalCoordinatorModel.
            modifyCoordinatorViewModel.textFieldModifyCoordinatorSettlementBank.value = originalCoordinatorModel.coordi_bank

            modifyCoordinatorViewModel.textFieldModifyCoordinatorSettlementAccountHolder.value = originalCoordinatorModel.coordi_account_holder
            modifyCoordinatorViewModel.textFieldModifyCoordinatorSettlementAccountNumber.value = originalCoordinatorModel.coordi_account
        }
    }

    // 수정된 로그인 코디네이터의 (loginUserIdx 또는 loginUserId) 입력 요소들의 입력값들을 가져오고,
    // modifiedCoordinatorModel 에 담아주고, CoordinatorDao 의 정보수정 메서드에 modified CoordinatorModel 를 넘겨주고 호출
    // 유효성 검사는??? 코디네이터의 경우는 사용자 정보수정과는 달리 유효성 검사 해야

    fun updateCoordinatorData() {
        // 1. 사용자가 입력한 수정 데이터를 가져온다
        val coordinatorName = modifyCoordinatorViewModel.textFieldModifyCoordinatorName.value!!
        val coordinatorIntroText = modifyCoordinatorViewModel.textFieldModifyCoordinatorIntroText.value!!
        //val coordinatorEmail = modifyCoordinatorViewModel.textFieldModifyCoordinatorEmail.value!!
        val coordinatorCustomerRelationPhone = modifyCoordinatorViewModel.textFieldModifyCoordinatorCustomerRelationPhone.value!!

//        val coordinatorPhone = modifyCoordinatorViewModel.textFieldModifyCoordinatorPhone.value!!
//        val coordinatorAddress = modifyCoordinatorViewModel.textFieldModifyCoordinatorAddress.value!!
//        val coordinatorAddressDetail = modifyCoordinatorViewModel.textFieldModifyCoordinatorAddressDetail.value!!
        val coordinatorWarehouseAddress = modifyCoordinatorViewModel.textFieldModifyCoordinatorWarehouseAddress.value!!

        val coordinatorWarehouseAddressDetail = modifyCoordinatorViewModel.textFieldModifyCoordinatorWarehouseAddressDetail.value!!
        val coordinatorReturnAddress = modifyCoordinatorViewModel.textFieldModifyCoordinatorReturnAddress.value!!
        val coordinatorReturnAddressDetail = modifyCoordinatorViewModel.textFieldJModifyCoordinatorReturnAddressDetail.value!!
        val coordinatorSettlementBank = modifyCoordinatorViewModel.textFieldModifyCoordinatorSettlementBank.value!!
        val coordinatorSettlementAccountHolder = modifyCoordinatorViewModel.textFieldModifyCoordinatorSettlementAccountHolder.value!!
        val coordinatorSettlementAccountNumber = modifyCoordinatorViewModel.textFieldModifyCoordinatorSettlementAccountNumber.value!!


        // originalCoordinatorModel 수정한 modifiedCoordinatorModel에 담는다.
        /*
        val modifiedCoordinatorModel = CoordinatorModel(
            originalCoordinatorModel.coordi_idx,
            originalCoordinatorModel.coordi_user_idx,
            coordinatorName,
            coordinatorIntroText,
            originalCoordinatorModel.coordi_photo,
            originalCoordinatorModel.coordi_license,
            originalCoordinatorModel.coordi_license_num,
            originalCoordinatorModel.coordi_portfolio,
            originalCoordinatorModel.coordi_business_license,
            originalCoordinatorModel.coordi_mbti,
            coordinatorCustomerRelationPhone,
            coordinatorWarehouseAddress,
            coordinatorWarehouseAddressDetail,
            coordinatorReturnAddress,
            coordinatorReturnAddressDetail,
            coordinatorSettlementBank,
            coordinatorSettlementAccountHolder,
            coordinatorSettlementAccountNumber,
            originalCoordinatorModel.coordi_followers,
            originalCoordinatorModel.coordi_permission,
            originalCoordinatorModel.coordi_request_date,
        )

        CoroutineScope(Dispatchers.Main).launch {
            // 수정메서드를 호출한다.
            CoordinatorDao.updateCoordinatorData(modifiedCoordinatorModel)

            Snackbar.make(fragmentModifyCoordinatorBinding.root, "수정되었습니다", Snackbar.LENGTH_SHORT).show()
            Tools.hideSoftInput(mainActivity)

            // 0.5초 정도 딜레이 주면 좋을 듯
            mainActivity.removeFragment(MainFragmentName.MODIFY_COORDINATOR_FRAGMENT)
        }
        */

    }




}
