package kr.co.lion.team4.mrco.fragment.product.codi

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.divider.MaterialDividerItemDecoration
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kr.co.lion.team4.mrco.MainActivity
import kr.co.lion.team4.mrco.R
import kr.co.lion.team4.mrco.dao.ProductDao
import kr.co.lion.team4.mrco.databinding.FragmentCodiProductInfoTopBinding
import kr.co.lion.team4.mrco.databinding.RowCodiProductInfoTopBinding
import kr.co.lion.team4.mrco.model.ProductCategoryLinkedListModel
import kr.co.lion.team4.mrco.model.ProductModel
import kr.co.lion.team4.mrco.viewmodel.CodiProductInfoTopViewModel

class CodiProductInfoTopFragment : Fragment() {

    private lateinit var binding: FragmentCodiProductInfoTopBinding
    private lateinit var mainActivity: MainActivity

    // 상품 번호를 받는다
    var productIdx = 0
    // 코디 상품명을 받는다
    var codiProductName = ""
    // 상의 상품 정보를 가지고 있는 리스트
    var codiProductTopList: ArrayList<List<Map<String,String>>> = arrayListOf()
    var codiProductTopFineList: List<Map<String, String>> = listOf()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        Log.d("taejin", "코디 상품 상세 - 상의")
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_codi_product_info_top, container, false)
        mainActivity = activity as MainActivity
        binding.lifecycleOwner = this

        gettingBundleData()
        settingRecyclerView()

        gettingCodiProductTopData()

        return binding.root
    }

    fun gettingBundleData(){
        val bundle = arguments
        productIdx = bundle!!.getInt("productIdx")
        codiProductName = bundle.getString("productName")!!

    }

    // DB에서 coordiItem 데이터 받아옴
    fun gettingCodiProductTopData(){
        CoroutineScope(Dispatchers.Main).launch {
            // 상품 정보를 가져온다.
            codiProductTopList = ProductDao.selectProductInfoData(productIdx)
            codiProductTopFineList = codiProductTopList[0]
            Log.d("taejin","${codiProductTopList}")
            Log.d("taejin","0 : ${codiProductTopList[0]}")
            Log.d("taejin","1 : ${codiProductTopList[0][0]}")
            Log.d("taejin","2 : ${codiProductTopList[0][0]["0"]}")
            Log.d("taejin","3 : ${codiProductTopList[0][0]["1"]}")
            Log.d("taejin","4 : ${codiProductTopList[0][0]["2"]}")
//            Log.d("taejin","${codiProductTopList[0][3]}")


            // 리사이클러뷰를 갱신한다
            binding.recyclerViewProductInfoTop.adapter?.notifyDataSetChanged()
        }
    }
    //

    private fun settingRecyclerView(){
        binding.apply {
            recyclerViewProductInfoTop.apply {

                // 어댑터 설정
                adapter = CodiProductInfoTopAdapater()
                // 레이아웃 매니저
                layoutManager = LinearLayoutManager(mainActivity)
                // 구분선 추가
                val deco = MaterialDividerItemDecoration(mainActivity, MaterialDividerItemDecoration.VERTICAL)
                deco.isLastItemDecorated = false // 마지막 구분선 제거
                addItemDecoration(deco)
            }
        }
    }



    inner class CodiProductInfoTopAdapater: RecyclerView.Adapter<CodiProductInfoTopAdapater.TopViewHolder>(){
        inner class TopViewHolder(rowCodiProductInfoTopBinding: RowCodiProductInfoTopBinding): RecyclerView.ViewHolder(rowCodiProductInfoTopBinding.root){
            val rowCodiProductInfoTopBinding: RowCodiProductInfoTopBinding
            init {
                this.rowCodiProductInfoTopBinding = rowCodiProductInfoTopBinding

                this.rowCodiProductInfoTopBinding.root.layoutParams = ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT
                )
            }
        }
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CodiProductInfoTopAdapater.TopViewHolder {
            val rowCodiProductInfoTopBinding = DataBindingUtil.inflate<RowCodiProductInfoTopBinding>(layoutInflater, R.layout.row_codi_product_info_top, parent, false)
            val rowCodiProductInfoTopViewModel = CodiProductInfoTopViewModel()
            rowCodiProductInfoTopBinding.rowCodiProductInfoTopViewModel = rowCodiProductInfoTopViewModel
            rowCodiProductInfoTopBinding.lifecycleOwner = this@CodiProductInfoTopFragment

            val topViewHolder = TopViewHolder(rowCodiProductInfoTopBinding)
            return topViewHolder
        }

        override fun onBindViewHolder(holder: CodiProductInfoTopAdapater.TopViewHolder, position: Int) {
            holder.rowCodiProductInfoTopBinding.rowCodiProductInfoTopViewModel!!.codiProductNameTop.value = codiProductName
//            holder.rowCodiProductInfoTopBinding.rowCodiProductInfoTopViewModel!!.productSerialNumTop.value = codiProductTopFineList[position]["0"]
            holder.rowCodiProductInfoTopBinding.rowCodiProductInfoTopViewModel!!.productNameTop.value = codiProductTopFineList[position]["0"]
            holder.rowCodiProductInfoTopBinding.rowCodiProductInfoTopViewModel!!.productSizeTop.value = codiProductTopFineList[position]["1"]
            holder.rowCodiProductInfoTopBinding.rowCodiProductInfoTopViewModel!!.productTypeTop.value = codiProductTopFineList[position]["3"]+ "${position + 1}"
            holder.rowCodiProductInfoTopBinding.rowCodiProductInfoTopViewModel!!.productColorTop.value = codiProductTopFineList[position]["4"]
//            holder.rowCodiProductInfoTopBinding.rowCodiProductInfoTopViewModel!!.productPriceTop.value = codiProductTopList[position].itemPrice.toString()
        }

        override fun getItemCount(): Int {
            return codiProductTopFineList.size
        }

    }
}