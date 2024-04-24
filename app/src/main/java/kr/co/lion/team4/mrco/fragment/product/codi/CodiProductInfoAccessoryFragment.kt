package kr.co.lion.team4.mrco.fragment.product.codi

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.google.android.material.divider.MaterialDividerItemDecoration
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kr.co.lion.team4.mrco.MainActivity
import kr.co.lion.team4.mrco.R
import kr.co.lion.team4.mrco.dao.ProductDao
import kr.co.lion.team4.mrco.databinding.FragmentCodiProductInfoAccessoryBinding
import kr.co.lion.team4.mrco.databinding.RowCodiProductInfoAccessoryBinding
import kr.co.lion.team4.mrco.viewmodel.CodiProductInfoAccesoryViewModel

class CodiProductInfoAccessoryFragment : Fragment() {

    private lateinit var binding: FragmentCodiProductInfoAccessoryBinding
    private lateinit var mainActivity: MainActivity

    var productIdx = 0
    var codiProductName = ""

    var codiProductAccessoryList: ArrayList<List<Map<String, String>>> = arrayListOf()
    var codiProductAccessoryFineList: List<Map<String, String>> = listOf()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        Log.d("taejin", "코디 상품 상세 - 악세서리")
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_codi_product_info_accessory, container, false)
        mainActivity = activity as MainActivity
        binding.lifecycleOwner = this

        gettingBundleData()

        setiingRecyclerView()

        gettingCodiProductAccessoryData()

        return binding.root
    }

    fun gettingBundleData(){
        val bundle = arguments
        productIdx = bundle!!.getInt("productIdx")
        codiProductName = bundle.getString("productName")!!
    }

    fun gettingCodiProductAccessoryData(){
        CoroutineScope(Dispatchers.Main).launch {
            codiProductAccessoryList = ProductDao.selectProductInfoData(productIdx)

            filterData()
            Log.d("taejinCheck", "데이터 받아왔는지 확인 :\n${codiProductAccessoryFineList}")
            binding.recyclerViewCodiProductInfoAccessory.adapter?.notifyDataSetChanged()
        }
    }

    // DB 데이터 걸러줌
    fun filterData(){
        // 타입이 하의인 것만 골라낸다
        val temp = codiProductAccessoryList[0].filter { item ->
            item["3"] == "악세사리"
        }
        if (temp.isNotEmpty()){
            codiProductAccessoryFineList = temp
        }
    }



    private fun setiingRecyclerView(){
        binding.apply {
            recyclerViewCodiProductInfoAccessory.apply {
                // 어댑터
                adapter = CodiProductInfoAccessoryAdapter()
                // 레이아웃 매니저
                layoutManager = LinearLayoutManager(mainActivity)
                // 구분선
                val deco = MaterialDividerItemDecoration(mainActivity, MaterialDividerItemDecoration.VERTICAL)
                addItemDecoration(deco)
            }
        }
    }

    inner class CodiProductInfoAccessoryAdapter: RecyclerView.Adapter<CodiProductInfoAccessoryAdapter.CodiProductInfoAccessoryViewHolder>(){
        // ViewHolder
        inner class CodiProductInfoAccessoryViewHolder(rowbinding: RowCodiProductInfoAccessoryBinding): RecyclerView.ViewHolder(rowbinding.root){
            val rowbinding: RowCodiProductInfoAccessoryBinding
            init {
                this.rowbinding = rowbinding
                this.rowbinding.root.layoutParams = ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT
                )
            }
        }

        override fun onCreateViewHolder(
            parent: ViewGroup,
            viewType: Int
        ): CodiProductInfoAccessoryViewHolder {
            val rowbinding = DataBindingUtil.inflate<RowCodiProductInfoAccessoryBinding>(layoutInflater, R.layout.row_codi_product_info_accessory, parent, false)
            val rowViewModel = CodiProductInfoAccesoryViewModel()
            rowbinding.codiProductInfoAccessoryViewModel = rowViewModel
            rowbinding.lifecycleOwner = this@CodiProductInfoAccessoryFragment

            val viewholder = CodiProductInfoAccessoryViewHolder(rowbinding)
            return viewholder
        }

        override fun getItemCount(): Int {
            return codiProductAccessoryFineList.size
        }

        override fun onBindViewHolder(holder: CodiProductInfoAccessoryViewHolder, position: Int) {
            holder.rowbinding.codiProductInfoAccessoryViewModel!!.codiProductNameAccessory.value = codiProductName
//            holder.rowCodiProductInfoTopBinding.rowCodiProductInfoTopViewModel!!.productSerialNumTop.value = codiProductTopFineList[position]["0"]
            holder.rowbinding.codiProductInfoAccessoryViewModel!!.codiProductNameAccessory.value = codiProductAccessoryFineList[position]["0"]
            holder.rowbinding.codiProductInfoAccessoryViewModel!!.codiProductSizeAccessory.value = codiProductAccessoryFineList[position]["1"]
            holder.rowbinding.codiProductInfoAccessoryViewModel!!.codiProductTypeAccessory.value = codiProductAccessoryFineList[position]["3"]+ "${position + 1}"
            holder.rowbinding.codiProductInfoAccessoryViewModel!!.codiProductColorAccessory.value = codiProductAccessoryFineList[position]["4"]
//            holder.rowCodiProductInfoTopBinding.rowCodiProductInfoTopViewModel!!.productPriceTop.value = codiProductTopList[position].itemPrice.toString()
        }
    }


}