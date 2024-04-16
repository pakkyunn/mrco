package kr.co.lion.team4.mrco.fragment.salesManagement

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kr.co.lion.team4.mrco.InquiryPeriod
import kr.co.lion.team4.mrco.MainActivity
import kr.co.lion.team4.mrco.MainFragmentName
import kr.co.lion.team4.mrco.R
import kr.co.lion.team4.mrco.Tools
import kr.co.lion.team4.mrco.viewmodel.salesManagement.SalesListViewModel
import kr.co.lion.team4.mrco.viewmodel.salesManagement.SaleslistHeaderViewModel
import kr.co.lion.team4.mrco.viewmodel.salesManagement.SaleslistItemViewModel
import kr.co.lion.team4.mrco.databinding.FragmentSalesListBinding
import kr.co.lion.team4.mrco.databinding.ItemSaleslistBinding
import kr.co.lion.team4.mrco.databinding.ItemSaleslistHeaderBinding
import java.text.SimpleDateFormat
import java.util.*

/* (판매자) 판매내역 화면 */
class SalesListFragment : Fragment() {
    lateinit var fragmentSalesListBinding: FragmentSalesListBinding
    lateinit var salesListViewModel: SalesListViewModel

    lateinit var mainActivity: MainActivity
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        fragmentSalesListBinding = DataBindingUtil.inflate(inflater,
            R.layout.fragment_sales_list, container, false)
        salesListViewModel = SalesListViewModel()
        fragmentSalesListBinding.salesListViewModel = salesListViewModel
        fragmentSalesListBinding.lifecycleOwner = this

        mainActivity = activity as MainActivity

        // setting toolbar, bottom navigation
        settingToolbarSalesList()

        settingSalesListPeriodButtonClickListener()

        settingSalesListRecyclerView()

        return fragmentSalesListBinding.root
    }

    fun settingToolbarSalesList(){
        fragmentSalesListBinding.toolbarSalesList.apply {
            setNavigationOnClickListener {
                backProcess()
            }
        }
    }

    fun backProcess(){
        mainActivity.removeFragment(MainFragmentName.SALES_LIST_FRAGMENT)
    }

    fun settingSalesListRecyclerView(){
        fragmentSalesListBinding.apply {
            recyvlerviewSalesList.apply {
                adapter = SalesListRecyclerViewAdapter()
                layoutManager = LinearLayoutManager(mainActivity)
            }
        }
    }

    // 조회기간 설정
    fun settingSalesListPeriodButtonClickListener(){
        fragmentSalesListBinding.apply {
            // 조회 기간 1개월
            buttonSalesListOneMonth.setOnClickListener {
                settingOrderHistoryPeriod(InquiryPeriod.ONE_MONTH)
            }
            // 3개월
            buttonSalesListThreeMonths.setOnClickListener {
                settingOrderHistoryPeriod(InquiryPeriod.THREE_MONTHS)
            }
            // 6개월
            buttonSalesListSixMonths.setOnClickListener {
                settingOrderHistoryPeriod(InquiryPeriod.SIX_MONTHS)
            }
            // 조회 기간 직접 설정
            buttonSalesListSetPeriod.setOnClickListener {
                Tools.setPeriodFromDateRagnePicker(mainActivity.supportFragmentManager,
                    salesListViewModel!!.salesPeriodStart, salesListViewModel!!.salesPeriodEnd)
                // to do - 조회 기간에 맞는 주문 배송 내역 불러오기
            }
        }
    }

    // 현재 날짜로부터 1개월, 3개월, 6개월 조회 기간을 설정
    fun settingOrderHistoryPeriod(periodType: InquiryPeriod){
        val calendar = Calendar.getInstance()
        calendar.time = Date()
        val simpleDateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.KOREA)
        // 종료일 (현재)
        salesListViewModel.salesPeriodEnd.value = simpleDateFormat.format(calendar.time)

        when(periodType){
            // 조회기간 1개월
            InquiryPeriod.ONE_MONTH -> {
                calendar.add(Calendar.MONTH, InquiryPeriod.ONE_MONTH.num)
                // 1개월 전의 날짜로 설정
                salesListViewModel.salesPeriodStart.value = simpleDateFormat.format(calendar.time)
                Log.d("testtest", "${ salesListViewModel.salesPeriodStart.value }")
            }
            // 조회기간 3개월
            InquiryPeriod.THREE_MONTHS -> {
                calendar.add(Calendar.MONTH, InquiryPeriod.THREE_MONTHS.num)
                // 3개월 전의 날짜로 설정
                salesListViewModel.salesPeriodStart.value = simpleDateFormat.format(calendar.time)
            }
            // 조회기간 6개월
            InquiryPeriod.SIX_MONTHS -> {
                calendar.add(Calendar.MONTH, InquiryPeriod.SIX_MONTHS.num)
                // 6개월 전의 날짜로 설정
                salesListViewModel.salesPeriodStart.value = simpleDateFormat.format(calendar.time)
            }
        }
        // to do - 조회 기간에 맞는 판매 내역 불러오기
    }

    // 판매내역 상단에 날짜를 표기하고, 판매일이 일치하는 판매내역을 보여주는 RecyclerView
    inner class SalesListRecyclerViewAdapter : RecyclerView.Adapter<SalesListRecyclerViewAdapter.SalesListHeaderViewHolder>(){
        inner class SalesListHeaderViewHolder(itemSaleslistHeaderBinding: ItemSaleslistHeaderBinding) : RecyclerView.ViewHolder(itemSaleslistHeaderBinding.root){
            val itemSaleslistHeaderBinding : ItemSaleslistHeaderBinding
            init {
                this.itemSaleslistHeaderBinding = itemSaleslistHeaderBinding
                this.itemSaleslistHeaderBinding.root.layoutParams = ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT
                )
            }
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SalesListHeaderViewHolder {
            val itemSaleslistHeaderBinding = DataBindingUtil.inflate<ItemSaleslistHeaderBinding>(layoutInflater,
                R.layout.item_saleslist_header, parent, false)
            val saleslistHeaderViewModel = SaleslistHeaderViewModel()
            itemSaleslistHeaderBinding.saleslistViewModel = saleslistHeaderViewModel
            itemSaleslistHeaderBinding.lifecycleOwner = this@SalesListFragment

            val salesListHeaderViewHolder = SalesListHeaderViewHolder(itemSaleslistHeaderBinding)

            return salesListHeaderViewHolder
        }

        override fun onBindViewHolder(holder: SalesListHeaderViewHolder, position: Int) {
            holder.itemSaleslistHeaderBinding.saleslistViewModel?.textviewSaleslistDate?.value = "4월 ${position}일"

            // 내부 리사이클러뷰 (판매 목록)
            val itemRecyclerView = holder.itemSaleslistHeaderBinding.recyclerviewSalesList
            itemRecyclerView.apply {
                adapter = SaleItemRecycerViewAdapter()
                layoutManager = LinearLayoutManager(mainActivity)
            }
        }

        override fun getItemCount(): Int {
            return 10
        }
    }

    // 판매내역(구매자들의 주문내역) 목록을 표시하기 위한 RecyclerView
    inner class SaleItemRecycerViewAdapter : RecyclerView.Adapter<SaleItemRecycerViewAdapter.SaleItemViewHolder>(){
        inner class SaleItemViewHolder(itemSalesListBinding: ItemSaleslistBinding): RecyclerView.ViewHolder(itemSalesListBinding.root){
            val itemSalesListBinding : ItemSaleslistBinding
            init {
                this.itemSalesListBinding = itemSalesListBinding
                this.itemSalesListBinding.root.layoutParams = ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT
                )
            }
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SaleItemViewHolder {
            val itemSalesListBinding = DataBindingUtil.inflate<ItemSaleslistBinding>(layoutInflater,
                R.layout.item_saleslist, parent, false)
            val saleListItemViewModel = SaleslistItemViewModel()
            itemSalesListBinding.salelistItemViewModel = saleListItemViewModel
            itemSalesListBinding.lifecycleOwner = this@SalesListFragment

            val saleItemViewHolder = SaleItemViewHolder(itemSalesListBinding)

            return saleItemViewHolder
        }

        override fun onBindViewHolder(holder: SaleItemViewHolder, position: Int) {
            holder.itemSalesListBinding.salelistItemViewModel?.textviewSaleslistProduct?.value = "상품명 $position"
            holder.itemSalesListBinding.salelistItemViewModel?.textviewSaleslistOrderNumber?.value = "상품번호12345"
            holder.itemSalesListBinding.salelistItemViewModel?.textviewSaleslistPrice?.value = "100,000원"
        }

        override fun getItemCount(): Int {
            return 2
        }
    }
}