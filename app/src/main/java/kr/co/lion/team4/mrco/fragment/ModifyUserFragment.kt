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
import kr.co.lion.team4.mrco.dao.UserDao
import kr.co.lion.team4.mrco.databinding.FragmentModifyUserBinding
import kr.co.lion.team4.mrco.model.UserModel
import kr.co.lion.team4.mrco.viewmodel.ModifyUserViewModel


class ModifyUserFragment : Fragment() {

    lateinit var fragmentModifyUserBinding: FragmentModifyUserBinding
    lateinit var modifyUserViewModel : ModifyUserViewModel
    lateinit var mainActivity: MainActivity

    // 서버에서 가져올 로그인 사용자 정보를 담을 객체
    lateinit var originalUserModel: UserModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment

        fragmentModifyUserBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_modify_user, container, false)
        modifyUserViewModel = ModifyUserViewModel()
        fragmentModifyUserBinding.modifyUserViewModel = modifyUserViewModel
        fragmentModifyUserBinding.lifecycleOwner = this

        mainActivity = activity as MainActivity

        settingToolbar()

        // 테스트용 바텀시트 노출 버튼
        fragmentModifyUserBinding.textFieldModifyUserUserMBTI.setOnClickListener {
            showMbtiBottomSheet()
        }

        return fragmentModifyUserBinding.root
    }

    // 툴바 설정
    fun settingToolbar() {
        fragmentModifyUserBinding.apply {
            toolbarModifyUser.apply {
                // 네비게이션
                setNavigationIcon(R.drawable.arrow_back_24px)
                setNavigationOnClickListener {
                    backProcesss()
                }
            }
        }
    }

    // Mbti 바텀 시트
    fun showMbtiBottomSheet(){
        val mbtiBottomSheetFragment = MbtiBottomSheetFragment(modifyUserViewModel.textFieldModifyUserUserMBTI)
        mbtiBottomSheetFragment.show(mainActivity.supportFragmentManager, "MbtiBottomSheet")
    }

    // 뒤로가기 처리
    fun backProcesss(){
        mainActivity.removeFragment(MainFragmentName.MODIFY_USER_FRAGMENT)
    }


    // 서버로부터 로그인한 사용자의 (loginUserIdx 또는 loginUserId) 정보를 가져와 입력 요소들에 설정해주는 메서드
    fun gettingUserDataFromServer(){
        CoroutineScope(Dispatchers.Main).launch{
            // 입력 요소들에 공백 문자열을 넣어준다.
            modifyUserViewModel.textFieldModifyUserUserName.value = " "
            modifyUserViewModel.textFieldModifyUserUserEmail.value = " "
            modifyUserViewModel.textFieldModifyUserUserMBTI.value = " "
            modifyUserViewModel.textFieldModifyUserUserPhone.value = " "

            modifyUserViewModel.textFieldModifyUserUserAddress.value = " "
            modifyUserViewModel.textFieldModifyUserUserAddressDetail.value = " "
            modifyUserViewModel.textFieldModifyUserUserRefundBankName.value = " "
            modifyUserViewModel.textFieldModifyUserUserRefundBankAccountHolder.value = " "

            modifyUserViewModel.textFieldModifyUserUserRefundBankAccountNumber.value = " "
            modifyUserViewModel.textFieldModifyUserUserHeight.value = " "
            modifyUserViewModel.textFieldModifyUserUserWeight.value = " "
            modifyUserViewModel.switchModifyUserNotification.value = false

            // 서버에서 로그인한 사용자인 loginUserIdx 의 데이터를 가져와 originalUserModel 에 담는다.
            //originalUserModel = UserDao.gettingUserInfoByUserIdx(mainActivity.loginUserIdx)!!
            originalUserModel = UserDao.gettingUserInfoByUserIdx(1)!!

            // 사용자 정보들을 뷰에 넣어준다.
            modifyUserViewModel.textFieldModifyUserUserName.value = originalUserModel.userName
            modifyUserViewModel.textFieldModifyUserUserEmail.value = originalUserModel.userEmail
            modifyUserViewModel.textFieldModifyUserUserMBTI.value = originalUserModel.userMBTI
            modifyUserViewModel.textFieldModifyUserUserPhone.value = originalUserModel.userPhone

            modifyUserViewModel.textFieldModifyUserUserAddress.value = originalUserModel.userAddress
            modifyUserViewModel.textFieldModifyUserUserAddressDetail.value = originalUserModel.userAddressDetail
            modifyUserViewModel.textFieldModifyUserUserRefundBankName.value = originalUserModel.userRefundBankName
            modifyUserViewModel.textFieldModifyUserUserRefundBankAccountHolder.value = originalUserModel.userRefundBankAccountHolder

            modifyUserViewModel.textFieldModifyUserUserRefundBankAccountNumber.value = originalUserModel.userRefundBankAccountNumber
            modifyUserViewModel.textFieldModifyUserUserHeight.value = originalUserModel.userHeight
            modifyUserViewModel.textFieldModifyUserUserWeight.value = originalUserModel.userWeight
            modifyUserViewModel.switchModifyUserNotification.value = originalUserModel.userNotification
        }
    }

    // 수정된 로그인 사용자의 (loginUserIdx 또는 loginUserId) 입력 요소들의 입력값들을 가져오고,
    // modifiedUserModel 에 담아주고, UserDao 의 정보수정 메서드에 modifiedUserModel 를 넘겨주고 호출
    // 유효성 검사는???

    fun updateUserData() {
        // 1. 사용자가 입력한 수정 데이터를 가져온다
        val userName = modifyUserViewModel.textFieldModifyUserUserName.value!!
        val userEmail = modifyUserViewModel.textFieldModifyUserUserEmail.value!!
        val userMBTI = modifyUserViewModel.textFieldModifyUserUserMBTI.value!!
        val userPhone = modifyUserViewModel.textFieldModifyUserUserPhone.value!!

        val userAddress = modifyUserViewModel.textFieldModifyUserUserAddress.value!!
        val userAddressDetail = modifyUserViewModel.textFieldModifyUserUserAddressDetail.value!!
        val userRefundBankName = modifyUserViewModel.textFieldModifyUserUserRefundBankName.value!!
        val userRefundBankAccountHolder =
            modifyUserViewModel.textFieldModifyUserUserRefundBankAccountHolder.value!!

        val userRefundBankAccountNumber =
            modifyUserViewModel.textFieldModifyUserUserRefundBankAccountNumber.value!!
        val userHeight = modifyUserViewModel.textFieldModifyUserUserHeight.value!!
        val userWeight = modifyUserViewModel.textFieldModifyUserUserWeight.value!!
        val userNotification = modifyUserViewModel.switchModifyUserNotification.value!!

        // originalUserModel 수정한 modifiedUserModel에 담는다.
        val modifiedUserModel = UserModel(
            originalUserModel.userIdx,
            originalUserModel.userId,
            originalUserModel.userPw,
            userName,
            originalUserModel.userGender,
            userEmail,
            userMBTI,
            originalUserModel.userConsent01,
            originalUserModel.userConsent02,
            originalUserModel.userState,
            userPhone,
            userAddress,
            userAddressDetail,
            userRefundBankName,
            userRefundBankAccountHolder,
            userRefundBankAccountNumber,
            userHeight,
            userWeight,
            userNotification,
        )

//        UserModel(var userIdx:Int, var userId:String,
//        var userPw:String, var userName:String, var userGender:Int,
//        var userEmail:String, var userMBTI:String, var userConsent01: Boolean,
//        var userConsent02: Boolean, var userState:Int,
//        var userPhone: String = "", var userAddress: String = "",
//        var userAddressDetail: String = "", var userRefundBankName: String = "",
//        var userRefundBankAccountHolder: String = "",
//        var userRefundBankAccountNumber: String = "",
//        var userHeight: String = "", var userWeight: String = "",
//        var userNotification: Boolean = false,){

        CoroutineScope(Dispatchers.Main).launch {
            // 수정메서드를 호출한다.
            UserDao.updateUserData(modifiedUserModel)

            Snackbar.make(fragmentModifyUserBinding.root, "수정되었습니다", Snackbar.LENGTH_SHORT).show()
            Tools.hideSoftInput(mainActivity)
        }
    }

}