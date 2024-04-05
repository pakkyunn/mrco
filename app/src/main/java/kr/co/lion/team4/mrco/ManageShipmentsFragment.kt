package kr.co.lion.team4.mrco

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.divider.MaterialDividerItemDecoration
import kr.co.lion.team4.mrco.databinding.FragmentManageShipmentsBinding
import kr.co.lion.team4.mrco.databinding.ItemMangeshipmentsBinding
import kr.co.lion.team4.mrco.databinding.ItemMangeshipmentsProductBinding

/* (판매자) 배송관리 화면 - 운송장 번호를 등록할 수 있는 화면 */
class ManageShipmentsFragment : Fragment() {
    lateinit var fragmentManageShipmentsBinding : FragmentManageShipmentsBinding
    lateinit var manageShipmentsViewModel: ManageShipmentsViewModel

    lateinit var mainActivity: MainActivity
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        fragmentManageShipmentsBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_manage_shipments, container, false)
        manageShipmentsViewModel = ManageShipmentsViewModel()
        fragmentManageShipmentsBinding.manageShipmentsViewModel = manageShipmentsViewModel
        fragmentManageShipmentsBinding.lifecycleOwner = this

        // Activity
        mainActivity = activity as MainActivity

        settingManageShipmentsRecyclerView()
        return fragmentManageShipmentsBinding.root
    }

    fun settingManageShipmentsRecyclerView(){
        fragmentManageShipmentsBinding.apply {
            recyclerviewManageShipments.apply {
                adapter = ManageShipmentsRecyclerViewAdapter()
                layoutManager = LinearLayoutManager(mainActivity)
            }
        }
    }

    // 운송장을 등록화면에 보여주는 주문내역 목록 RecyclerView의 Adapter
    inner class ManageShipmentsRecyclerViewAdapter : RecyclerView.Adapter<ManageShipmentsRecyclerViewAdapter.MangaeShipmentsItemViewHolder>(){
        inner class MangaeShipmentsItemViewHolder(itemMangeshipmentsBinding: ItemMangeshipmentsBinding) : RecyclerView.ViewHolder(itemMangeshipmentsBinding.root){
            val itemMangeshipmentsBinding : ItemMangeshipmentsBinding
            init {
                this.itemMangeshipmentsBinding = itemMangeshipmentsBinding
                this.itemMangeshipmentsBinding.root.layoutParams = ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT
                )
            }
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MangaeShipmentsItemViewHolder {
            val itemMangeshipmentsBinding = DataBindingUtil.inflate<ItemMangeshipmentsBinding>(
                layoutInflater, R.layout.item_mangeshipments, parent, false)
            val manageShipmentsItemViewModel = ManageShipmentsItemViewModel()
            itemMangeshipmentsBinding.manageShipmentsItemViewModel = manageShipmentsItemViewModel
            itemMangeshipmentsBinding.lifecycleOwner = this@ManageShipmentsFragment

            val mangaeShipmentsItemViewHolder = MangaeShipmentsItemViewHolder(itemMangeshipmentsBinding)

            return mangaeShipmentsItemViewHolder
        }

        override fun onBindViewHolder(holder: MangaeShipmentsItemViewHolder, position: Int) {
            holder.itemMangeshipmentsBinding.manageShipmentsItemViewModel?.textViewManageShipmentsDate?.value = "2024.01.01"

            // 내부 리사이클러뷰 (판매된 상품 목록)
            val productRecyclerView = holder.itemMangeshipmentsBinding.recyclerviewManageShipments
            productRecyclerView.apply {
                adapter = ManageShipmentsProductsRecyclerViewAdapter()
                layoutManager = LinearLayoutManager(mainActivity)
                val deco = MaterialDividerItemDecoration(mainActivity, MaterialDividerItemDecoration.VERTICAL)
                deco.isLastItemDecorated = false // 마지막 아이템에는 MaterialDividerItemDecoration 제거
                addItemDecoration(deco)
            }
        }

        override fun getItemCount(): Int {
            return 10
        }
    }

    // 주문 내역에 표시할 주문 상품 목록 RecyclerView의 Adapter
    inner class ManageShipmentsProductsRecyclerViewAdapter : RecyclerView.Adapter<ManageShipmentsProductsRecyclerViewAdapter.ManageShipmentsProductViewHolder>(){
        inner class ManageShipmentsProductViewHolder(itemMangeshipmentsProductBinding: ItemMangeshipmentsProductBinding)
            : RecyclerView.ViewHolder(itemMangeshipmentsProductBinding.root){
                val itemMangeshipmentsProductBinding: ItemMangeshipmentsProductBinding
                init {
                    this.itemMangeshipmentsProductBinding = itemMangeshipmentsProductBinding
                    this.itemMangeshipmentsProductBinding.root.layoutParams = ViewGroup.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT
                    )
                }
            }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ManageShipmentsProductViewHolder {
            val itemMangeshipmentsProductBinding = DataBindingUtil.inflate<ItemMangeshipmentsProductBinding>(
                layoutInflater, R.layout.item_mangeshipments_product, parent, false)
            val manageShipmentsProductViewModel = ManageShipmentsProductViewModel()
            itemMangeshipmentsProductBinding.manageShipmentsProductViewModel = manageShipmentsProductViewModel
            itemMangeshipmentsProductBinding.lifecycleOwner = this@ManageShipmentsFragment

            val manageShipmentsProductViewHolder = ManageShipmentsProductViewHolder(itemMangeshipmentsProductBinding)

            return manageShipmentsProductViewHolder
        }

        override fun onBindViewHolder(holder: ManageShipmentsProductViewHolder, position: Int) {
            holder.itemMangeshipmentsProductBinding.manageShipmentsProductViewModel?.textViewManageShipmentsState?.value = "배송 중"
            holder.itemMangeshipmentsProductBinding.manageShipmentsProductViewModel?.textViewManageShipmentsCoordiName?.value = "코디 상품명"
            holder.itemMangeshipmentsProductBinding.manageShipmentsProductViewModel?.textViewManageShipmentsOption?.value = "코디 상품 주문 옵션"
            holder.itemMangeshipmentsProductBinding.manageShipmentsProductViewModel?.textViewManageShipmentsPrice?.value = "100,000원"
        }

        override fun getItemCount(): Int {
            return 2
        }
    }
}