package kr.co.lion.team4.mrco.fragment.mypage

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import kr.co.lion.team4.mrco.MainActivity
import kr.co.lion.team4.mrco.MainFragmentName
import kr.co.lion.team4.mrco.R
import kr.co.lion.team4.mrco.databinding.FragmentCoordinatorMyPageBinding
import kr.co.lion.team4.mrco.viewmodel.mypage.CoordinatorMyPageViewModel

class CoordinatorMyPageFragment : Fragment() {

    lateinit var fragmentCoordinatorMyPageBinding: FragmentCoordinatorMyPageBinding
    lateinit var mainActivity: MainActivity
    lateinit var coordinatorMyPageViewModel: CoordinatorMyPageViewModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        fragmentCoordinatorMyPageBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_coordinator_my_page, container, false)
        coordinatorMyPageViewModel = CoordinatorMyPageViewModel()
        fragmentCoordinatorMyPageBinding.coordinatorMyPageViewModel = coordinatorMyPageViewModel
        fragmentCoordinatorMyPageBinding.lifecycleOwner = this

        mainActivity = activity as MainActivity

        settingCoordiMyPageMenuClickEvent()

        return fragmentCoordinatorMyPageBinding.root
    }

    fun settingCoordiMyPageMenuClickEvent(){
        fragmentCoordinatorMyPageBinding.apply {
            // 코디 상품 등록
            textViewMenuCoordinatorMyPage1.setOnClickListener {
                mainActivity.replaceFragment(MainFragmentName.ADD_PRODUCT_FRAGMENT, true, true, null)
            }

            // 등록 상품 관리

            // 배송 관리
            textViewMenuCoordinatorMyPage3.setOnClickListener {
                mainActivity.replaceFragment(MainFragmentName.MANAGE_SHIPMENT_FRAGMENT, true, true, null)
            }

            // 상품 문의 관리
            textViewMenuCoordinatorMyPage4.setOnClickListener {
                mainActivity.replaceFragment(MainFragmentName.PRODUCT_QNA_LIST_FRAGMENT, true, true, null)
            }

            // 상품 판매 내역
            textViewMenuCoordinatorMyPage5.setOnClickListener {
                mainActivity.replaceFragment(MainFragmentName.SALES_LIST_FRAGMENT, true, true, null)
            }
            // 매출/정산 관리
        }
    }
}