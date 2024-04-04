package kr.co.lion.team4.mrco.fragment.order

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import kr.co.lion.team4.mrco.MainActivity
import kr.co.lion.team4.mrco.MainFragmentName
import kr.co.lion.team4.mrco.R
import kr.co.lion.team4.mrco.databinding.FragmentOrderDetailBinding
import kr.co.lion.team4.mrco.viewmodel.order.OrderDetailViewModel

class OrderDetailFragment : Fragment() {

    lateinit var fragmentOrderDetailBinding: FragmentOrderDetailBinding
    lateinit var mainActivity: MainActivity

    lateinit var orderDetailViewModel: OrderDetailViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        fragmentOrderDetailBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_order_detail, container, false)
        orderDetailViewModel = OrderDetailViewModel()
        fragmentOrderDetailBinding.orderDetailViewModel = OrderDetailViewModel()
        fragmentOrderDetailBinding.lifecycleOwner = this

        mainActivity = activity as MainActivity

        // 툴바, 하단바, 탭 관련
        mainActivity.removeTabsBar()
        mainActivity.removeBottomSheet()

        toolbarSetting()

        return fragmentOrderDetailBinding.root
    }

    // 툴바 설정
    fun toolbarSetting(){
        mainActivity.activityMainBinding.apply {
            toolbarMain.apply {
                title = "주문 상세 정보"
                // 네비게이션
                setNavigationIcon(R.drawable.arrow_back_24px)
                setNavigationOnClickListener {
                    backProcesss()
                }
                // 메뉴제거
                menu.clear()
            }
        }
    }

    // 뒤로가기 처리
    fun backProcesss(){
        mainActivity.removeFragment(MainFragmentName.ORDER_DETAIL)
    }
}