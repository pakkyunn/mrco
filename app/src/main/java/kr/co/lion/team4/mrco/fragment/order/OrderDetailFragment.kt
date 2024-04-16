package kr.co.lion.team4.mrco.fragment.order

import android.content.Intent
import android.net.Uri
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

    // 원빈 - 주문 상세 정보 화면

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

        // 툴바 세팅
        toolbarSetting()

        // 버튼 세팅
        settingButton()

        return fragmentOrderDetailBinding.root
    }

    // 툴바 설정
    fun toolbarSetting(){
        fragmentOrderDetailBinding.toolbarOrderDetail.apply {
            title = "주문 상세 정보"
            // 네비게이션
            setNavigationIcon(R.drawable.arrow_back_24px)
            setNavigationOnClickListener {
                backProcesss()
            }
        }
    }

    // 버튼 설정
    fun settingButton(){
        fragmentOrderDetailBinding.apply {

            // 반품하기 버튼
            buttonOrderDetail1.setOnClickListener {
                mainActivity.replaceFragment(MainFragmentName.CUSTOMER_INQUIRY_FRAGMENT, true, true, null)
            }

            // 교환하기 버튼
            buttonOrderDetail2.setOnClickListener {
                mainActivity.replaceFragment(MainFragmentName.CUSTOMER_INQUIRY_FRAGMENT, true, true, null)
            }

            // 배송현황 버튼
            buttonOrderDetail3.setOnClickListener {
                val url = "https://www.cjlogistics.com/ko/tool/parcel/tracking" // 대한통운 배송 조회 페이지 URL
                val intent = Intent(Intent.ACTION_VIEW)
                intent.data = Uri.parse(url)
                startActivity(intent)
            }
            
            // 문의하기 버튼
            buttonOrderDetail4.setOnClickListener {
                mainActivity.replaceFragment(MainFragmentName.REGISTER_PRODUCT_QNA_FRAGMENT, true, true, null)
            }
        }
    }

    // 뒤로가기 처리
    fun backProcesss(){
        mainActivity.removeFragment(MainFragmentName.ORDER_DETAIL)
    }
}