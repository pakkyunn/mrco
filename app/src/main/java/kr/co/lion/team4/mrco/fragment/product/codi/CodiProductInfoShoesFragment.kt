package kr.co.lion.team4.mrco.fragment.product.codi

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.divider.MaterialDividerItemDecoration
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kr.co.lion.team4.mrco.MainActivity
import kr.co.lion.team4.mrco.R
import kr.co.lion.team4.mrco.dao.ProductDao
import kr.co.lion.team4.mrco.databinding.FragmentCodiProductInfoShoesBinding
import kr.co.lion.team4.mrco.databinding.RowCodiProductInfoShoesBinding
import kr.co.lion.team4.mrco.viewmodel.CodiProductInfoShoesViewModel

class CodiProductInfoShoesFragment : Fragment() {

    private lateinit var binding: FragmentCodiProductInfoShoesBinding
    private lateinit var mainActivity: MainActivity

    var productIdx = 0
    var codiProductName = ""

    var codiProductShoesList: ArrayList<List<Map<String,String>>> = arrayListOf()
    var codiProductShoesFineList: List<Map<String,String>> = listOf()
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        Log.d("taejin", "코디 상품 상세 - 신발")
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_codi_product_info_shoes, container, false)
        mainActivity = activity as MainActivity
        binding.lifecycleOwner = this

        gettingBundleData()

        settingRecyclerView()

        gettingCodiProductShoesData()

        return binding.root
    }

    fun gettingBundleData(){
        val bundle = arguments
        productIdx = bundle!!.getInt("productIdx")
        codiProductName = bundle.getString("productName")!!
    }

    fun gettingCodiProductShoesData(){
        val userIdx = mainActivity.loginUserIdx
        CoroutineScope(Dispatchers.Main).launch {
            // 상품 정보를 가져온다
            codiProductShoesList = ProductDao.selectProductInfoData(productIdx, userIdx)
            filterData()

            binding.recyclerViewProductInfoShoes.adapter?.notifyDataSetChanged()
        }
    }

    // DB 데이터 걸러줌
    fun filterData(){
        // 타입이 하의인 것만 골라낸다
        val temp = codiProductShoesList[0].filter { item ->
            item["3"] == "신발"
        }
        if (temp.isNotEmpty()){
            codiProductShoesFineList = temp
        }
    }


    private fun settingRecyclerView(){
        binding.apply {
            recyclerViewProductInfoShoes.apply {
                // 어댑터 설정
                adapter = CodiProductInfoShoesAdapter()
                // 레이아웃 매니저
                layoutManager = LinearLayoutManager(mainActivity)

                // 구분선
                val deco = MaterialDividerItemDecoration(mainActivity, MaterialDividerItemDecoration.VERTICAL)
                deco.isLastItemDecorated = false
                addItemDecoration(deco)
            }
        }
    }

    inner class CodiProductInfoShoesAdapter: RecyclerView.Adapter<CodiProductInfoShoesAdapter.CodiProductInfoShoesViewHolder>(){
        // Viewholder
        inner class CodiProductInfoShoesViewHolder(rowCodiProductInfoShoesBinding: RowCodiProductInfoShoesBinding): RecyclerView.ViewHolder(rowCodiProductInfoShoesBinding.root){
            val rowCodiProductInfoShoesBinding: RowCodiProductInfoShoesBinding

            init {
                this.rowCodiProductInfoShoesBinding = rowCodiProductInfoShoesBinding
                this.rowCodiProductInfoShoesBinding.root.layoutParams = ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT
                )
            }
        }

        override fun onCreateViewHolder(
            parent: ViewGroup,
            viewType: Int
        ): CodiProductInfoShoesViewHolder {
            val rowCodiProductInfoShoesBinding = DataBindingUtil.inflate<RowCodiProductInfoShoesBinding>(layoutInflater, R.layout.row_codi_product_info_shoes, parent, false)
            val codiProductInfoShoesViewModel = CodiProductInfoShoesViewModel()
            rowCodiProductInfoShoesBinding.codiProductInfoShoesViewModel = codiProductInfoShoesViewModel
            rowCodiProductInfoShoesBinding.lifecycleOwner = this@CodiProductInfoShoesFragment

            val codiProductInfoShoesViewHolder = CodiProductInfoShoesViewHolder(rowCodiProductInfoShoesBinding)

            return codiProductInfoShoesViewHolder
        }

        override fun getItemCount(): Int {
            return codiProductShoesFineList.size
        }

        override fun onBindViewHolder(holder: CodiProductInfoShoesViewHolder, position: Int) {
            holder.rowCodiProductInfoShoesBinding.codiProductInfoShoesViewModel!!.codiProductNameShoes.value = codiProductName
//            holder.rowCodiProductInfoShoesBinding.codiProductInfoShoesViewModel!!.codiProductSerialNumShoes.value = codiProductShoesFineList[position]["0"]      :: 상품번호 없는 정보? ... 아마도 2차 개발로 넘겨진듯
            holder.rowCodiProductInfoShoesBinding.codiProductInfoShoesViewModel!!.codiProductNameShoes.value = codiProductShoesFineList[position]["0"]
            holder.rowCodiProductInfoShoesBinding.codiProductInfoShoesViewModel!!.codiProductSizeShoes.value = codiProductShoesFineList[position]["1"]
            holder.rowCodiProductInfoShoesBinding.codiProductInfoShoesViewModel!!.codiProductTypeShoes.value = codiProductShoesFineList[position]["3"]+ "${position + 1}"
            holder.rowCodiProductInfoShoesBinding.codiProductInfoShoesViewModel!!.codiProductColorShoes.value = codiProductShoesFineList[position]["4"]
//            holder.rowCodiProductInfoShoesBinding.codiProductInfoShoesViewModel!!.productPriceTop.value = codiProductTopList[position].itemPrice.toString()     ::  가격 => 없는 정보?

        }
    }
}