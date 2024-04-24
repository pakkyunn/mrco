package kr.co.lion.team4.mrco.fragment.order

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.datepicker.CalendarConstraints
import com.google.android.material.datepicker.DateValidatorPointBackward
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.divider.MaterialDividerItemDecoration
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kr.co.lion.team4.mrco.InquiryPeriod
import kr.co.lion.team4.mrco.MainActivity
import kr.co.lion.team4.mrco.MainFragmentName
import kr.co.lion.team4.mrco.viewmodel.order.OrderHistoryItemViewModel
import kr.co.lion.team4.mrco.viewmodel.order.OrderHistoryProductViewModel
import kr.co.lion.team4.mrco.viewmodel.order.OrderHistoryViewModel
import kr.co.lion.team4.mrco.R
import kr.co.lion.team4.mrco.ShippingState
import kr.co.lion.team4.mrco.Tools
import kr.co.lion.team4.mrco.dao.OrderHistoryDao
import kr.co.lion.team4.mrco.databinding.FragmentOrderHistoryBinding
import kr.co.lion.team4.mrco.databinding.ItemOrderhistoryItemBinding
import kr.co.lion.team4.mrco.databinding.ItemOrderhistoryProductBinding
import kr.co.lion.team4.mrco.model.OrderModel
import kr.co.lion.team4.mrco.model.OrderedProductInfoModel
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

/* (구매자) 주문 내역 화면 */
class OrderHistoryFragment : Fragment() {
    lateinit var fragmentOrderHistoryBinding: FragmentOrderHistoryBinding
    lateinit var orderHistoryViewModel : OrderHistoryViewModel

    lateinit var mainActivity: MainActivity

    // 전체 주문내역을 담을 리스트
    var orderList = mutableListOf<OrderModel>()
    // 주문 상태별로 구분하여 구성하기 위한 주문 내역 리스트
    var filteredOrderList = mutableListOf<OrderModel>()

    // 주문 상품의 상품 정보를 담을 리스트
    var orderedProductInfoModelList = mutableListOf<ArrayList<OrderedProductInfoModel>>()
    // 주문 상태별로 구분된 상품들의 정보를 담을 리스트
    var filteredOrderedProductInfoModelList = mutableListOf<ArrayList<OrderedProductInfoModel>>()

    // 검색 화면의 RecyclerView 구성을 위한 리스트
    var searchResultOrderList = mutableListOf<OrderModel>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        fragmentOrderHistoryBinding = DataBindingUtil.inflate(inflater,
            R.layout.fragment_order_history, container, false)
        orderHistoryViewModel = OrderHistoryViewModel()
        fragmentOrderHistoryBinding.orderHistoryViewModel = orderHistoryViewModel
        fragmentOrderHistoryBinding.lifecycleOwner = this

        mainActivity = activity as MainActivity

        // 툴바 설정
        settingToolbarOrderHistory()

        // 주문 내역 목록 리사이클러뷰
        settingOrderHistoryRecyclerView()
        settingOrderHistoryPeriodButtonClickListener()

        // 전체 주문 내역 불러오기
        gettingOrderList()

        return fragmentOrderHistoryBinding.root
    }

    fun settingToolbarOrderHistory(){
        fragmentOrderHistoryBinding.toolbarOrderHistory.apply {
            setNavigationIcon(R.drawable.arrow_back_24px)
            setNavigationOnClickListener {
                mainActivity.removeFragment(MainFragmentName.ORDER_HISTORY_FRAGMENT)
            }
        }
    }

    // 주문 내역 추가하기 위한 임시 메서드
/*    fun sendTempData(){
        val product = OrderProduct(1, 4, "셔츠 pink M / 바지 red S / 아우터 white L / 가방 black",
            1, null, null, 4, null, 0)
        val product2 = OrderProduct(1, 4, "셔츠 blue L / 바지 skyblue S / 아우터 grey M / 가방 white",
            1, null, null, 2, null, 0)

        val orderedItem = arrayListOf<OrderProduct>(product, product2)

        val orderModel = OrderModel(_, 1, Timestamp.now(), orderedItem, "카드", 396000, 396000, 0, 0, "@@시 @@구", "@@아파트 @@동 @@호"
        , "홍길동", "01000000000", "부재시 경비실에 맡겨주세요")

        CoroutineScope(Dispatchers.Main).launch {
            OrderHistoryDao.insertOrderData(orderModel)
        }
    }*/

    // 조회기간 버튼 클릭 이벤트
    fun settingOrderHistoryPeriodButtonClickListener(){
        fragmentOrderHistoryBinding.apply {
            // 조회 기간 1개월
            buttonOrderHistoryOneMonth.setOnClickListener {
                settingOrderHistoryPeriod(InquiryPeriod.ONE_MONTH)
            }
            buttonOrderHistoryThreeMonths.setOnClickListener {
                settingOrderHistoryPeriod(InquiryPeriod.THREE_MONTHS)
            }
            buttonOrderHistorySixMonths.setOnClickListener {
                settingOrderHistoryPeriod(InquiryPeriod.SIX_MONTHS)
            }
            buttonOrderHistorySetPeriod.setOnClickListener {
                // 날짜는 최대 오늘까지 선택할 수 있도록 선택 가능한 기간 설정
                val calendarConstraints = CalendarConstraints.Builder().setValidator(DateValidatorPointBackward.now()).build()
                val dateRangePicker = MaterialDatePicker.Builder.dateRangePicker()
                    .setTitleText("조회기간을 설정해주세요")
                    .setCalendarConstraints(calendarConstraints)
                    .build()
                dateRangePicker.show(mainActivity.supportFragmentManager, "orderHistoryPeriod")
                dateRangePicker.addOnPositiveButtonClickListener {
                    // 시작일
                    orderHistoryViewModel?.periodStart?.value = getDateFromLongValue(it.first)
                    // 종료일
                    orderHistoryViewModel?.periodEnd?.value = getDateFromLongValue(it.second)
                }

                // to do - 조회 기간에 맞는 주문 배송 내역 불러오기
            }
        }
    }

    // dateRangePicker에서 선택된 날짜의 Long 값을 날짜로 변환
    fun getDateFromLongValue(date: Long) : String{
        val calendar = Calendar.getInstance()
        calendar.timeInMillis = date
        val date = SimpleDateFormat("yyyy-MM-dd", Locale.KOREA).format(calendar.time)

        return date
    }

    // 현재 날짜로부터 1개월, 3개월, 6개월 조회 기간을 설정
    fun settingOrderHistoryPeriod(periodType: InquiryPeriod){
        val calendar = Calendar.getInstance()
        calendar.time = Date()
        val simpleDateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.KOREA)
        // 종료일 (현재)
        orderHistoryViewModel.periodEnd.value = simpleDateFormat.format(calendar.time)

        when(periodType){
            // 조회기간 1개월
            InquiryPeriod.ONE_MONTH -> {
                calendar.add(Calendar.MONTH, InquiryPeriod.ONE_MONTH.num)
                // 1개월 전의 날짜로 설정
                orderHistoryViewModel.periodStart.value = simpleDateFormat.format(calendar.time)
            }
            // 조회기간 3개월
            InquiryPeriod.THREE_MONTHS -> {
                calendar.add(Calendar.MONTH, InquiryPeriod.THREE_MONTHS.num)
                // 3개월 전의 날짜로 설정
                orderHistoryViewModel.periodStart.value = simpleDateFormat.format(calendar.time)
            }
            // 조회기간 6개월
            InquiryPeriod.SIX_MONTHS -> {
                calendar.add(Calendar.MONTH, InquiryPeriod.SIX_MONTHS.num)
                // 6개월 전의 날짜로 설정
                orderHistoryViewModel.periodStart.value = simpleDateFormat.format(calendar.time)
            }
        }
        // to do - 조회 기간에 맞는 주문 배송 내역 불러오기
    }

    // 서버에서 로그인한 유저의 전체 주문 내역을 불러온다.
    fun gettingOrderList(){
        CoroutineScope(Dispatchers.Main).launch {
            filteredOrderList.clear()
            orderList = OrderHistoryDao.gettingOrderList(mainActivity.loginUserIdx)
            filteredOrderList.addAll(orderList)
//            fragmentOrderHistoryBinding.recyclerviewOrderHistory.adapter?.notifyDataSetChanged()
            gettingOrderItemInfoFromOrderList()
        }
    }

    fun gettingOrderItemInfoFromOrderList(){
        CoroutineScope(Dispatchers.Main).launch {
            filteredOrderedProductInfoModelList.clear() // 목록 초기화
            orderedProductInfoModelList = OrderHistoryDao.gettingOrderedItemInfo(orderList)
            filteredOrderedProductInfoModelList.addAll(orderedProductInfoModelList)
            fragmentOrderHistoryBinding.recyclerviewOrderHistory.adapter?.notifyDataSetChanged()
        }
    }

    fun settingOrderHistoryRecyclerView(){
        fragmentOrderHistoryBinding.recyclerviewOrderHistory.apply {
            adapter = OrderHistoryRecyclerViewAdapter()
            layoutManager = LinearLayoutManager(mainActivity)
        }
    }

    // 주문 내역 목록의 RecyclerView Adapter
    inner class OrderHistoryRecyclerViewAdapter : RecyclerView.Adapter<OrderHistoryRecyclerViewAdapter.OrderHistoryItemViewHolder>(){
        inner class OrderHistoryItemViewHolder(itemOrderhistoryItemBinding:ItemOrderhistoryItemBinding):
            RecyclerView.ViewHolder(itemOrderhistoryItemBinding.root){
                val itemOrderhistoryItemBinding : ItemOrderhistoryItemBinding
                init {
                    this.itemOrderhistoryItemBinding = itemOrderhistoryItemBinding
                    this.itemOrderhistoryItemBinding.root.layoutParams = ViewGroup.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT
                    )
                }
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrderHistoryItemViewHolder {
            val itemOrderHistoryItemBinding = DataBindingUtil.inflate<ItemOrderhistoryItemBinding>(
                layoutInflater, R.layout.item_orderhistory_item, parent, false
            )
            val orderHistoryItemViewModel = OrderHistoryItemViewModel()
            itemOrderHistoryItemBinding.orderHistoryItemViewModel = orderHistoryItemViewModel
            itemOrderHistoryItemBinding.lifecycleOwner = this@OrderHistoryFragment

            val orderHistoryItemViewHolder = OrderHistoryItemViewHolder(itemOrderHistoryItemBinding)

            return orderHistoryItemViewHolder
        }

        override fun onBindViewHolder(holder: OrderHistoryItemViewHolder, position: Int) {
            val orderedDate = filteredOrderList[position].order_date.toDate()
            val simpleDateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.KOREA)
            val orderedDateParse = simpleDateFormat.format(orderedDate) // 년-월-일 형태로 날짜만 표기해준다.

            holder.itemOrderhistoryItemBinding.orderHistoryItemViewModel?.textViewOrderedItemDate?.value = orderedDateParse

            // 내부 리사이클러뷰 (주문 상품 목록)
            val productRecyclerView = holder.itemOrderhistoryItemBinding.recyclerviewOrderedItems
            productRecyclerView.apply {
                adapter = OrderHistoryProductRecyclerViewAdapter(position)
                layoutManager = LinearLayoutManager(mainActivity)
                val deco = MaterialDividerItemDecoration(mainActivity, MaterialDividerItemDecoration.VERTICAL)
                deco.isLastItemDecorated = false // 마지막 아이템에는 MaterialDividerItemDecoration 제거
                addItemDecoration(deco)
            }

            // 주문상세 > 이거 눌렀을 때   to do data에 주문번호 넘겨줄 것
            holder.itemOrderhistoryItemBinding.buttonOrderHistory.setOnClickListener {
                mainActivity.replaceFragment(MainFragmentName.ORDER_DETAIL, true, true, null)
            }
        }

        override fun getItemCount(): Int {
            return filteredOrderList.size
        }
    }

    // 주문 내역 목록에 표시되는 주문 상품 목록의 RecyclerView Adapter
    inner class OrderHistoryProductRecyclerViewAdapter(var parentPosition: Int) : RecyclerView.Adapter<OrderHistoryProductRecyclerViewAdapter.OrderHistoryProductViewHolder>(){
        inner class OrderHistoryProductViewHolder(itemOrderhistoryProductBinding: ItemOrderhistoryProductBinding) :
            RecyclerView.ViewHolder(itemOrderhistoryProductBinding.root){
                val itemOrderhistoryProductBinding : ItemOrderhistoryProductBinding
                init {
                    this.itemOrderhistoryProductBinding = itemOrderhistoryProductBinding
                    this.itemOrderhistoryProductBinding.root.layoutParams = ViewGroup.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT
                    )
                }
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrderHistoryProductViewHolder {
            val itemOrderhistoryProductBinding = DataBindingUtil.inflate<ItemOrderhistoryProductBinding>(
                layoutInflater, R.layout.item_orderhistory_product, parent, false)
            val orderHistoryProductViewModel = OrderHistoryProductViewModel()
            itemOrderhistoryProductBinding.orderHistoryProductViewModel = orderHistoryProductViewModel
            itemOrderhistoryProductBinding.lifecycleOwner = this@OrderHistoryFragment

            val orderHistoryProductViewHolder = OrderHistoryProductViewHolder(itemOrderhistoryProductBinding)

            return orderHistoryProductViewHolder
        }

        override fun onBindViewHolder(holder: OrderHistoryProductViewHolder, position: Int) {
            // 주문 상품의 정보
            val orderedProduct = filteredOrderList[parentPosition].order_product[position]
            val orderedProductInfo = filteredOrderedProductInfoModelList[parentPosition][position]
            // 주문 상태
            holder.itemOrderhistoryProductBinding.orderHistoryProductViewModel?.textViewOrderedItemState?.value = getOrderItemStateStringValue(orderedProduct.tracking_state)
            // 주문 상품명
            holder.itemOrderhistoryProductBinding.orderHistoryProductViewModel?.textViewOrderedItemCoordiName?.value = orderedProductInfo.ordered_product_name
            // 주문 옵션
            holder.itemOrderhistoryProductBinding.orderHistoryProductViewModel?.textViewOrderedItemOption?.value = orderedProduct.product_coordi_option
            // 주문 가격
            holder.itemOrderhistoryProductBinding.orderHistoryProductViewModel?.textViewOrderedItemPrice?.value = Tools.gettingPriceDecimalFormat(orderedProductInfo.ordered_product_price)

            if(orderedProduct.tracking_state < ShippingState.DELIVERED.num){ // 아직 배송 진행 중인 경우
                // 주문 상태 텍스트 아이콘 + 프로그레스바 설정
                settingOrderState(orderedProduct.tracking_state, holder)
            }else{ // 배송 완료된 경우
                holder.itemOrderhistoryProductBinding.layoutOrderedItemStates.visibility = View.INVISIBLE
                holder.itemOrderhistoryProductBinding.buttonOrderedItemReview.visibility = View.VISIBLE
            }

            /*// 주문 상품 썸네일(대표사진)
            CoroutineScope(Dispatchers.Main).launch {
                ProductDao.gettingProductImage(mainActivity, orderedProductInfo.ordered_product_thumbnail_filename,
                    holder.itemOrderhistoryProductBinding.imageviewOrderedItemThumbnail)
            }*/

        }

        override fun getItemCount(): Int {
            return filteredOrderList[parentPosition].order_product.size
        }

        // 주문 상태 텍스트 표기
        fun getOrderItemStateStringValue(trackingState : Int) : String{
            return when(trackingState){
                ShippingState.READY_TO_SHIP.num -> ShippingState.READY_TO_SHIP.str
                ShippingState.SHIPPED.num -> ShippingState.SHIPPED.str
                ShippingState.IN_TRANSIT.num -> ShippingState.IN_TRANSIT.str
                ShippingState.ARRIVE_SOON.num -> ShippingState.ARRIVE_SOON.str
                else -> ShippingState.DELIVERED.str  // ShippingState.DELIVERED 인 경우
            }
        }

        // 주문 상태를 표기해주기 위한 메서드
        fun settingOrderState(state: Int, holder: OrderHistoryProductViewHolder){
            // to do 데이터 연결 후 state 값은 enum class로 수정할 것

            // 주문 상태의 텍스트, 아이콘 -> 상태 초기화
            initOrderState(holder)

            when(state){
                // 발송 준비 상태
                0 -> {
                    // 텍스트 아이콘으로 현재 상태 표기
                    holder.itemOrderhistoryProductBinding.orderHistoryProductViewModel?.radiobuttonOrderedItemStateReady?.value = true
                    // progressBar로 현재 상태 표시
                    holder.itemOrderhistoryProductBinding.orderHistoryProductViewModel?.progressbarOrderedItemState?.value = 2
                }
                // 배송 시작 상태
                1 -> {
                    // 텍스트 아이콘으로 현재 상태 표기
                    holder.itemOrderhistoryProductBinding.orderHistoryProductViewModel?.radiobuttonOrderedItemStateShipped?.value = true
                    // progressBar로 현재 상태 표시
                    holder.itemOrderhistoryProductBinding.orderHistoryProductViewModel?.progressbarOrderedItemState?.value = 34
                }
                // 배송중 상태
                2 -> {
                    // 텍스트 아이콘으로 현재 상태 표기
                    holder.itemOrderhistoryProductBinding.orderHistoryProductViewModel?.radiobuttonOrderedItemStateInTransit?.value = true
                    // progressBar로 현재 상태 표시
                    holder.itemOrderhistoryProductBinding.orderHistoryProductViewModel?.progressbarOrderedItemState?.value = 67

                }
                // 배송 완료 상태
                3 -> {
                    // 텍스트 아이콘으로 현재 상태 표기
                    holder.itemOrderhistoryProductBinding.orderHistoryProductViewModel?.radiobuttonOrderedItemStateDelivered?.value = true
                    // progressBar로 현재 상태 표시
                    holder.itemOrderhistoryProductBinding.orderHistoryProductViewModel?.progressbarOrderedItemState?.value = 100
                }
            }
        }

        fun initOrderState(holder: OrderHistoryProductViewHolder){
            // 배송 상태 표기해주는 텍스트 아이콘 상태 초기화
            holder.itemOrderhistoryProductBinding.orderHistoryProductViewModel?.radiobuttonOrderedItemStateReady?.value = false
            holder.itemOrderhistoryProductBinding.orderHistoryProductViewModel?.radiobuttonOrderedItemStateShipped?.value = false
            holder.itemOrderhistoryProductBinding.orderHistoryProductViewModel?.radiobuttonOrderedItemStateInTransit?.value = false
            holder.itemOrderhistoryProductBinding.orderHistoryProductViewModel?.radiobuttonOrderedItemStateDelivered?.value = false
        }
    }
}