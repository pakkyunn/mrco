package kr.co.lion.team4.mrco.fragment.salesManagement

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kr.co.lion.team4.mrco.MainActivity
import kr.co.lion.team4.mrco.R
import kr.co.lion.team4.mrco.viewmodel.salesManagement.SalesListViewModel
import kr.co.lion.team4.mrco.viewmodel.salesManagement.SaleslistHeaderViewModel
import kr.co.lion.team4.mrco.viewmodel.salesManagement.SaleslistItemViewModel
import kr.co.lion.team4.mrco.databinding.FragmentSalesListBinding
import kr.co.lion.team4.mrco.databinding.ItemSaleslistBinding
import kr.co.lion.team4.mrco.databinding.ItemSaleslistHeaderBinding

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

        settingSalesListRecyclerView()

        return fragmentSalesListBinding.root
    }

    fun settingSalesListRecyclerView(){
        fragmentSalesListBinding.apply {
            recyvlerviewSalesList.apply {
                adapter = SalesListRecyclerViewAdapter()
                layoutManager = LinearLayoutManager(mainActivity)
            }
        }
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