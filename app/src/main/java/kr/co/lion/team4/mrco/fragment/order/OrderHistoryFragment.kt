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
import kr.co.lion.team4.mrco.MainActivity
import kr.co.lion.team4.mrco.MainFragmentName
import kr.co.lion.team4.mrco.viewmodel.order.OrderHistoryItemViewModel
import kr.co.lion.team4.mrco.viewmodel.order.OrderHistoryProductViewModel
import kr.co.lion.team4.mrco.viewmodel.order.OrderHistoryViewModel
import kr.co.lion.team4.mrco.R
import kr.co.lion.team4.mrco.databinding.FragmentOrderHistoryBinding
import kr.co.lion.team4.mrco.databinding.ItemOrderhistoryItemBinding
import kr.co.lion.team4.mrco.databinding.ItemOrderhistoryProductBinding
import java.text.SimpleDateFormat
import java.util.*

/* (구매자) 주문 내역 화면 */
class OrderHistoryFragment : Fragment() {
    lateinit var fragmentOrderHistoryBinding: FragmentOrderHistoryBinding
    lateinit var orderHistoryViewModel : OrderHistoryViewModel

    lateinit var mainActivity: MainActivity
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        fragmentOrderHistoryBinding = DataBindingUtil.inflate(inflater,
            R.layout.fragment_order_history, container, false)
        orderHistoryViewModel = OrderHistoryViewModel()
        fragmentOrderHistoryBinding.orderHistoryViewModel = orderHistoryViewModel
        fragmentOrderHistoryBinding.lifecycleOwner = this

        mainActivity = activity as MainActivity

        // 하단 바 안보이게
        mainActivity.removeBottomSheet()

        // 주문 내역 목록 리사이클러뷰
        settingOrderHistoryRecyclerView()
        settingOrderHistoryPeriodButtonClickListener()

        return fragmentOrderHistoryBinding.root
    }

    // 조회기간 버튼 클릭 이벤트
    fun settingOrderHistoryPeriodButtonClickListener(){
        fragmentOrderHistoryBinding.apply {
            // 조회 기간 1개월
            buttonOrderHistoryOneMonth.setOnClickListener {
                settingOrderHistoryPeriod(R.id.button_order_history_one_month)
            }
            buttonOrderHistoryThreeMonths.setOnClickListener {
                settingOrderHistoryPeriod(R.id.button_order_history_three_months)
            }
            buttonOrderHistorySixMonths.setOnClickListener {
                settingOrderHistoryPeriod(R.id.button_order_history_six_months)
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
        val date = SimpleDateFormat("yyyy-MM-dd").format(calendar.time)

        return date
    }

    // 현재 날짜로부터 1개월, 3개월, 6개월 조회 기간을 설정
    fun settingOrderHistoryPeriod(periodType: Int){
        val calendar = Calendar.getInstance()
        calendar.time = Date()
        val simpleDateFormat = SimpleDateFormat("yyyy-MM-dd")
        // 종료일 (현재)
        orderHistoryViewModel.periodEnd.value = simpleDateFormat.format(calendar.time)

        when(periodType){
            // 조회기간 1개월
            R.id.button_order_history_one_month -> {
                calendar.add(Calendar.MONTH, -1)
                // 1개월 전의 날짜로 설정
                orderHistoryViewModel.periodStart.value = simpleDateFormat.format(calendar.time)
            }
            // 조회기간 3개월
            R.id.button_order_history_three_months -> {
                calendar.add(Calendar.MONTH, -3)
                // 3개월 전의 날짜로 설정
                orderHistoryViewModel.periodStart.value = simpleDateFormat.format(calendar.time)
            }
            // 조회기간 6개월
            R.id.button_order_history_six_months -> {
                calendar.add(Calendar.MONTH, -6)
                // 6개월 전의 날짜로 설정
                orderHistoryViewModel.periodStart.value = simpleDateFormat.format(calendar.time)
            }
        }
        // to do - 조회 기간에 맞는 주문 배송 내역 불러오기
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
            holder.itemOrderhistoryItemBinding.orderHistoryItemViewModel?.textViewOrderedItemDate?.value = "2024.01.01"

            // 내부 리사이클러뷰 (주문 상품 목록)
            val productRecyclerView = holder.itemOrderhistoryItemBinding.recyclerviewOrderedItems
            productRecyclerView.apply {
                adapter = OrderHistoryProductRecyclerViewAdapter()
                layoutManager = LinearLayoutManager(mainActivity)
                val deco = MaterialDividerItemDecoration(mainActivity, MaterialDividerItemDecoration.VERTICAL)
                deco.isLastItemDecorated = false // 마지막 아이템에는 MaterialDividerItemDecoration 제거
                addItemDecoration(deco)
            }

            // 주문상세 > 이거 눌렀을 때
            holder.itemOrderhistoryItemBinding.buttonOrderHistory.setOnClickListener {
                mainActivity.replaceFragment(MainFragmentName.ORDER_DETAIL, true, true, null)
            }
        }

        override fun getItemCount(): Int {
            return 5
        }
    }

    // 주문 내역 목록에 표시되는 주문 상품 목록의 RecyclerView Adapter
    inner class OrderHistoryProductRecyclerViewAdapter : RecyclerView.Adapter<OrderHistoryProductRecyclerViewAdapter.OrderHistoryProductViewHolder>(){
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
            // 주문 상태
            holder.itemOrderhistoryProductBinding.orderHistoryProductViewModel?.textViewOrderedItemState?.value = "상품 준비중"
            // 주문 상품명
            holder.itemOrderhistoryProductBinding.orderHistoryProductViewModel?.textViewOrderedItemCoordiName?.value = "ㅇㅇㅇㅇ룩 코디 SET"
            // 주문 옵션
            holder.itemOrderhistoryProductBinding.orderHistoryProductViewModel?.textViewOrderedItemOption?.value = "상의 / 아우터 / 하의"
            // 주문 가격
            holder.itemOrderhistoryProductBinding.orderHistoryProductViewModel?.textViewOrderedItemPrice?.value = "100,000원"

            val state = position%4
            // 주문 상태 텍스트 아이콘 + 프로그레스바 설정
            settingOrderState(state, holder)
        }

        override fun getItemCount(): Int {
            return 4
        }

        fun settingOrderState(state: Int, holder: OrderHistoryProductViewHolder){
            // to do 데이터 연결 후 state 값은 enum class로 수정할 것

            // 텍스트 아이콘 상태 초기화
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