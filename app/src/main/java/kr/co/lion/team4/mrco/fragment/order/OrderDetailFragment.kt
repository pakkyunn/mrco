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
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kr.co.lion.team4.mrco.MainActivity
import kr.co.lion.team4.mrco.MainFragmentName
import kr.co.lion.team4.mrco.R
import kr.co.lion.team4.mrco.ShippingState
import kr.co.lion.team4.mrco.Tools
import kr.co.lion.team4.mrco.dao.OrderHistoryDao
import kr.co.lion.team4.mrco.databinding.FragmentOrderDetailBinding
import kr.co.lion.team4.mrco.databinding.ItemOrderDetailProductBinding
import kr.co.lion.team4.mrco.model.OrderModel
import kr.co.lion.team4.mrco.model.OrderedProductInfoModel
import kr.co.lion.team4.mrco.viewmodel.order.OrderDetailProductViewModel
import kr.co.lion.team4.mrco.viewmodel.order.OrderDetailViewModel
import java.text.SimpleDateFormat
import java.util.*

class OrderDetailFragment : Fragment() {

    // 원빈 - 주문 상세 정보 화면
    lateinit var fragmentOrderDetailBinding: FragmentOrderDetailBinding
    lateinit var mainActivity: MainActivity

    lateinit var orderDetailViewModel: OrderDetailViewModel

    // 주문한 상품의 정보(상품명, 옵션, 가격)들을 담고 있는 객체
    var orderedProductsDetail = mutableListOf<OrderedProductInfoModel>()
    lateinit var orderDetail : OrderModel

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
        val orderIdx = arguments?.getInt("orderIdx")!!
        // 주문 번호와 일치하는 주문 정보를 불러온다
        gettingOrderDetailData(orderIdx)

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

    fun gettingOrderDetailData(orderIdx : Int){
        CoroutineScope(Dispatchers.Main).launch {
            orderDetail = OrderHistoryDao.selectOrderData(orderIdx)!!
            if(orderDetail!=null){
                // 서버에서 받아온 정보를 화면에 보여준다.
                settingOrderData(orderDetail!!)
                // 주문한 상품 정보도 서버에서 받아온 뒤, 화면에 보여준다.
                settingOrderedProductInfo(orderDetail!!)
            }
        }
    }

    fun settingOrderData(orderDetail : OrderModel){
        orderDetailViewModel.apply {
            // 주문 정보 (주문 번호, 주문 일자)
            textViewOrderDetailOrderNumber.value = orderDetail.order_number
            val orderedDate = orderDetail.order_date.toDate()
            val simpleDateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.KOREA)
            val orderedDateParse = simpleDateFormat.format(orderedDate) // 년-월-일 형태로 날짜만 표기해준다.
            textViewOrderDetailOrderDate.value = orderedDateParse

            // 배송 정보 (수령인, 연락처, 주소, 배송 메모)
            textViewOrderDetailUserName.value = orderDetail.shipping_name
            textViewOrderDetailUserPhone.value = orderDetail.shipping_phone_number
            val shippingAddress = "${orderDetail.shipping_address} ${orderDetail.shipping_address_detail}"
            textViewOrderDetailUserAddress.value = shippingAddress
            // 배송 메모를 작성한 경우
            if(orderDetail.shipping_memo != null){
                textViewOrderDetailOrderMemo.value = orderDetail.shipping_memo
            }

            // 결제 내역 (상품 금액, 할인 금액, 쿠폰명, 쿠폰 할인 금액)
            textViewOrderDetailOrderPrice.value = Tools.gettingPriceDecimalFormat(orderDetail.original_price)
            textViewOrderDetailOrderDiscountPrice.value = Tools.gettingPriceDecimalFormat(orderDetail.discount_price)
            textViewOrderDetailOrderTotalPrice.value = Tools.gettingPriceDecimalFormat(orderDetail.payment_amount)
        }

        // MutableLiveData를 View에 반영해준다.
        orderDetailViewModel.apply {
            // 주문 정보 (주문 번호, 주문 일자)
            textViewOrderDetailOrderNumber.observe(viewLifecycleOwner){ orderNum ->
                fragmentOrderDetailBinding.textViewOrderDetailOrderNumber.text = orderNum
            }
            textViewOrderDetailOrderDate.observe(viewLifecycleOwner){ orderDate ->
                fragmentOrderDetailBinding.textViewOrderDetailOrderDate.text = orderDate
            }

            // 배송 정보 (수령인, 연락처, 주소, 배송 메모)
            textViewOrderDetailUserName.observe(viewLifecycleOwner){ shippingName ->
                fragmentOrderDetailBinding.textViewOrderDetailUserName.text = shippingName
            }
            textViewOrderDetailUserPhone.observe(viewLifecycleOwner){ phoneNumber ->
                fragmentOrderDetailBinding.textViewOrderDetailUserPhone.text = phoneNumber
            }
            textViewOrderDetailUserAddress.observe(viewLifecycleOwner){ address ->
                fragmentOrderDetailBinding.textViewOrderDetailUserAddress.text = address
            }
            textViewOrderDetailOrderMemo.observe(viewLifecycleOwner){ memo ->
                fragmentOrderDetailBinding.textViewOrderDetailOrderMemo.text = memo
            }

            // 결제 내역 (상품 금액, 할인 금액, 최종 결제 금액)
            textViewOrderDetailOrderPrice.observe(viewLifecycleOwner){ originalPrice ->
                fragmentOrderDetailBinding.textViewOrderDetailOrderPrice.text = originalPrice
            }
            textViewOrderDetailOrderDiscountPrice.observe(viewLifecycleOwner){ discountPrice ->
                fragmentOrderDetailBinding.textViewOrderDetailOrderDiscountPrice.text = discountPrice
            }
            textViewOrderDetailOrderTotalPrice.observe(viewLifecycleOwner){ paymentAmount  ->
                fragmentOrderDetailBinding.textViewOrderDetailOrderTotalPrice.text = paymentAmount
            }
        }
    }

    // 주문한 상품들의 정보를 불러와서 View에 세팅해준다.
    fun settingOrderedProductInfo(orderDetail: OrderModel){
        CoroutineScope(Dispatchers.Main).launch {
            orderedProductsDetail.addAll(OrderHistoryDao.selectOrderProductInfo(orderDetail))
            fragmentOrderDetailBinding.recyclerViewOrderDetailProducts.adapter?.notifyDataSetChanged()
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
            // 주문 상태
            val orderProductState = orderDetail!!.order_product[position].tracking_state
            // 주문 상태가 구매확정 상태라면, 반품,교환 버튼을 숨겨준다.
            if(orderProductState == ShippingState.ORDER_CONFIRM.num){
                holder.productBinding.apply {
                    buttonOrderdetailProductReturn.visibility = View.GONE
                    dividerReturn.visibility = View.GONE
                    buttonOrderdetailProductExchange.visibility = View.GONE
                    dividerExchange.visibility = View.GONE
                }
            }

            holder.productBinding.orderDetailProductViewModel?.apply {
                // 주문 상태
                textviewOrderDetailProductState.value = Tools.getOrderItemStateStringValue(orderProductState)
                // 코디 상품명
                textviewOrderDetailProductCoordiName.value = orderedProductsDetail[position].ordered_product_name
                // 주문 옵션
                textviewOrderDetailProductOption.value = orderDetail!!.order_product[position].product_coordi_option
                // 가격
                textviewOrderDetailProductPrice.value = Tools.gettingPriceDecimalFormat(orderedProductsDetail[position].ordered_product_price)
            }
            // ProductData에 상품 이미지가 등록되면 주석을 해제하고, 상품 이미지를 보여준다.
            /*val thumbnailFileName = orderedProductsDetail[position].ordered_product_thumbnail_filename
            CoroutineScope(Dispatchers.Main).launch {
                ProductDao.gettingProductImage(mainActivity, thumbnailFileName, holder.productBinding.imageviewOrderdetailProductThumbnail )
            }*/

            holder.productBinding.apply {
                // 반품하기 버튼
                buttonOrderdetailProductReturn.setOnClickListener {
                    leaveOrderInquiry()
                }

                // 교환하기 버튼
                buttonOrderdetailProductExchange.setOnClickListener {
                    leaveOrderInquiry()
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
                    leaveOrderInquiry()
                }
            }
        }

        fun leaveOrderInquiry(){
            val orderDetailBundle = Bundle()
            // 주문번호를 전달
            orderDetailBundle.putString("orderNumber", orderDetail.order_number)

            mainActivity.replaceFragment(
                MainFragmentName.CUSTOMER_INQUIRY_FRAGMENT, true, true, orderDetailBundle)
        }

        override fun getItemCount(): Int {
            return orderedProductsDetail.size
        }
    }
}