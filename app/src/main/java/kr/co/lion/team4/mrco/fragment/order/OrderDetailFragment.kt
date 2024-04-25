package kr.co.lion.team4.mrco.fragment.order

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kr.co.lion.team4.mrco.MainActivity
import kr.co.lion.team4.mrco.MainFragmentName
import kr.co.lion.team4.mrco.R
import kr.co.lion.team4.mrco.databinding.FragmentOrderDetailBinding
import kr.co.lion.team4.mrco.databinding.ItemOrderDetailProductBinding
import kr.co.lion.team4.mrco.viewmodel.order.OrderDetailProductViewModel
import kr.co.lion.team4.mrco.viewmodel.order.OrderDetailViewModel

class OrderDetailFragment : Fragment() {

    // 원빈 - 주문 상세 정보 화면

    lateinit var fragmentOrderDetailBinding: FragmentOrderDetailBinding
    lateinit var mainActivity: MainActivity

    lateinit var orderDetailViewModel: OrderDetailViewModel

    var orderIdx = -1

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        fragmentOrderDetailBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_order_detail, container, false)
        orderDetailViewModel = OrderDetailViewModel()
        fragmentOrderDetailBinding.orderDetailViewModel = OrderDetailViewModel()
        fragmentOrderDetailBinding.lifecycleOwner = this

        mainActivity = activity as MainActivity

        // 툴바 세팅
        toolbarSetting()

        // 리사이클러뷰 세팅
        settingOrderDetailRecyclerView()

        // 주문 번호
        orderIdx = arguments?.getInt("orderIdx")!!

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

    // 리사이클러뷰
    fun settingOrderDetailRecyclerView(){
        fragmentOrderDetailBinding.recyclerViewOrderDetailProducts.apply {
            adapter = OrderDetailProductAdapter()
            layoutManager = LinearLayoutManager(mainActivity)
        }
    }

    // 뒤로가기 처리
    fun backProcesss(){
        mainActivity.removeFragment(MainFragmentName.ORDER_DETAIL)
    }

    inner class OrderDetailProductAdapter : RecyclerView.Adapter<OrderDetailProductAdapter.OrderDetailProductViewHolder>(){
        inner class OrderDetailProductViewHolder(productBinding : ItemOrderDetailProductBinding): RecyclerView.ViewHolder(productBinding.root){
            val productBinding : ItemOrderDetailProductBinding
            init {
                this.productBinding = productBinding
                this.productBinding.root.layoutParams = ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT
                )
            }
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrderDetailProductViewHolder {
            val productBinding = DataBindingUtil.inflate<ItemOrderDetailProductBinding>(
                layoutInflater, R.layout.item_order_detail_product, parent, false)
            val orderDetailProductViewModel = OrderDetailProductViewModel()
            productBinding.orderDetailProductViewModel = orderDetailProductViewModel
            productBinding.lifecycleOwner = this@OrderDetailFragment

            val orderDetailProductViewHolder = OrderDetailProductViewHolder(productBinding)

            return orderDetailProductViewHolder
        }

        override fun onBindViewHolder(holder: OrderDetailProductViewHolder, position: Int) {
            holder.productBinding.orderDetailProductViewModel?.apply {
                // 주문 상태
                textviewOrderDetailProductState.value = "상품 준비중"
                // 코디 상품명
                textviewOrderDetailProductCoordiName.value = "영앤리치"
                // 주문 옵션
                textviewOrderDetailProductOption.value = "주문옵션 상품 종류 사이즈 색상 등등"
                // 가격
                textviewOrderDetailProductPrice.value = "000원"
            }


            holder.productBinding.apply {
                // 반품하기 버튼
                buttonOrderdetailProductReturn.setOnClickListener {
                    mainActivity.replaceFragment(
                        MainFragmentName.CUSTOMER_INQUIRY_FRAGMENT, true, true, null
                    )
                }

                // 교환하기 버튼
                buttonOrderdetailProductExchange.setOnClickListener {
                    mainActivity.replaceFragment(
                        MainFragmentName.CUSTOMER_INQUIRY_FRAGMENT, true, true, null
                    )
                }

                // 배송현황 버튼
                buttonOrderdetailProductShipping.setOnClickListener {
                    val url =
                        "https://www.cjlogistics.com/ko/tool/parcel/tracking" // 대한통운 배송 조회 페이지 URL
                    val intent = Intent(Intent.ACTION_VIEW)
                    intent.data = Uri.parse(url)
                    startActivity(intent)
                }

                // 문의하기 버튼
                buttonOrderdetailProductInquiry.setOnClickListener {
                    mainActivity.replaceFragment(
                        MainFragmentName.CUSTOMER_INQUIRY_FRAGMENT, true, true, null
                    )

                }
            }

            // to do ImageView
        }

        override fun getItemCount(): Int {
            return 2
        }
    }
}