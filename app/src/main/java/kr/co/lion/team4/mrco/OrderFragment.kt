package kr.co.lion.team4.mrco

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kr.co.lion.team4.mrco.databinding.FragmentOrderBinding
import kr.co.lion.team4.mrco.databinding.ItemOrderBinding

/* (구매자) 상품 주문 화면 */
class OrderFragment : Fragment() {
    lateinit var fragmentOrderBinding: FragmentOrderBinding
    lateinit var orderViewModel: OrderViewModel

    lateinit var mainActivity: MainActivity
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        fragmentOrderBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_order, container, false)
        orderViewModel = OrderViewModel()
        fragmentOrderBinding.orderViewModel = orderViewModel
        fragmentOrderBinding.lifecycleOwner = this

        // Activity
        mainActivity = activity as MainActivity

        // 주문할 상품 목록 RecyclerView 세팅
        settingRecyclerViewOrderProducts()

        return fragmentOrderBinding.root
    }

    fun settingRecyclerViewOrderProducts(){
        fragmentOrderBinding.apply {
            recyclerviewOrderProducts.apply {
                adapter = OrderProductsRecyclerViewAdapter()
                layoutManager = LinearLayoutManager(mainActivity)
            }
        }
    }


    inner class OrderProductsRecyclerViewAdapter : RecyclerView.Adapter<OrderProductsRecyclerViewAdapter.OrderProductViewHolder>(){
        inner class OrderProductViewHolder(itemOrderBinding: ItemOrderBinding) : RecyclerView.ViewHolder(itemOrderBinding.root){
            val itemOrderBinding : ItemOrderBinding
            init {
                this.itemOrderBinding = itemOrderBinding
                this.itemOrderBinding.root.layoutParams = ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT
                )
            }
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrderProductViewHolder {
            val itemOrderBinding = DataBindingUtil.inflate<ItemOrderBinding>(layoutInflater, R.layout.item_order, parent, false)
            val itemOrderViewModel = ItemOrderViewModel()
            itemOrderBinding.itemOrderViewModel = itemOrderViewModel
            itemOrderBinding.lifecycleOwner = this@OrderFragment

            val orderProductViewHolder = OrderProductViewHolder(itemOrderBinding)

            return orderProductViewHolder
        }

        override fun onBindViewHolder(holder: OrderProductViewHolder, position: Int) {
            // 코디네이터명
            holder.itemOrderBinding.itemOrderViewModel?.textviewOrderItemCoordinator?.value = "홍길동"
            // 코디 상품명
            holder.itemOrderBinding.itemOrderViewModel?.textviewOrderItemCoordi_name?.value = "데일리 출근룩 코디"
            // 상품 선택 옵션
            holder.itemOrderBinding.itemOrderViewModel?.textviewOrderItemOption?.value = "상의: 블랙, L / 하의: 공통, M ..."
            // 상품 가격
            holder.itemOrderBinding.itemOrderViewModel?.textviewOrderItemPrice?.value = "100,000원"
        }

        override fun getItemCount(): Int {
            return 2
        }
    }
}