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
import kr.co.lion.team4.mrco.databinding.FragmentCodiProductInfoBottomBinding
import kr.co.lion.team4.mrco.databinding.RowCodiProductInfoBottomBinding
import kr.co.lion.team4.mrco.viewmodel.CodiProductInfoBottomViewModel

class CodiProductInfoBottomFragment : Fragment() {

    private lateinit var binding: FragmentCodiProductInfoBottomBinding
    private lateinit var mainActivity: MainActivity

    // 상품 번호를 받는다
    var productIdx = 0
    // 코디 상품명을 받는다
    var codiProductName = ""
    // 상의 상품 정보를 가지고 있는 리스트
    var codiProductBottomList: ArrayList<List<Map<String,String>>> = arrayListOf()
    var codiProductBottomFineList: List<Map<String, String>> = listOf()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Log.d("taejin", "코디 상품 상세 - 하의")
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_codi_product_info_bottom,
            container,
            false
        )
        mainActivity = activity as MainActivity
        binding.lifecycleOwner = this

        gettingBundleData()

        settingView()

        gettingCodiProductBottomData()


        return binding.root
    }

    fun gettingBundleData(){
        val bundle = arguments
        productIdx = bundle!!.getInt("productIdx")
        codiProductName = bundle.getString("productName")!!
        Log.d("taejinCheck", codiProductName)
    }

    // DB에서 coordiItem 데이터 받아옴
    fun gettingCodiProductBottomData(){
        CoroutineScope(Dispatchers.Main).launch {
            // 상품 정보를 가져온다.
            codiProductBottomList = ProductDao.selectProductInfoData(productIdx)

            filterData()


            // 리사이클러뷰를 갱신한다
            binding.recyclerViewProductInfoBottom.adapter?.notifyDataSetChanged()
        }
    }
    // DB 데이터 걸러줌
    fun filterData(){
        // 타입이 하의인 것만 골라낸다
        val temp = codiProductBottomList[0].filter { item ->
            item["3"] == "하의"
        }
        Log.d("taejinCheck", "$temp")

        if (temp.isNotEmpty()){
            codiProductBottomFineList = temp
        }
    }

    private fun settingView() {
        binding.apply {
            recyclerViewProductInfoBottom.apply {
                // 어댑터 설정
                adapter = CodiProductInfoBottomAdapter()

                // 레이아웃 매니저 설정
                layoutManager = LinearLayoutManager(mainActivity)

                // 구분선 추가
                val deco = MaterialDividerItemDecoration(
                    mainActivity,
                    MaterialDividerItemDecoration.VERTICAL
                )
                addItemDecoration(deco)
            }
        }
    }

    inner class CodiProductInfoBottomAdapter :
        RecyclerView.Adapter<CodiProductInfoBottomAdapter.CodiProductInfoBottomViewHolder>() {
        // ViewHolder
        inner class CodiProductInfoBottomViewHolder(rowCodiProductInfoBottomBinding: RowCodiProductInfoBottomBinding) :
            RecyclerView.ViewHolder(rowCodiProductInfoBottomBinding.root) {
            val rowCodiProductInfoBottomBinding: RowCodiProductInfoBottomBinding

            init {
                this.rowCodiProductInfoBottomBinding = rowCodiProductInfoBottomBinding

                this.rowCodiProductInfoBottomBinding.root.layoutParams = ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT
                )
            }
        }

        override fun onCreateViewHolder(
            parent: ViewGroup,
            viewType: Int
        ): CodiProductInfoBottomViewHolder {
            val binding = DataBindingUtil.inflate<RowCodiProductInfoBottomBinding>(layoutInflater, R.layout.row_codi_product_info_bottom, parent,
                false
            )
            val codiProductInfoBottomViewModel = CodiProductInfoBottomViewModel()
            binding.codiProductInfoBottomViewModel = codiProductInfoBottomViewModel
            binding.lifecycleOwner = this@CodiProductInfoBottomFragment

            val codiProductInfoBottomViewHolder = CodiProductInfoBottomViewHolder(binding)
            return codiProductInfoBottomViewHolder
        }

        override fun getItemCount(): Int {
//            return 0
            return codiProductBottomFineList.size
        }

        override fun onBindViewHolder(holder: CodiProductInfoBottomViewHolder, position: Int) {
            holder.rowCodiProductInfoBottomBinding.codiProductInfoBottomViewModel!!.codiProductNameBottom.value = codiProductName
//            holder.rowCodiProductInfoTopBinding.rowCodiProductInfoTopViewModel!!.productSerialNumTop.value = codiProductTopFineList[position]["0"]
            holder.rowCodiProductInfoBottomBinding.codiProductInfoBottomViewModel!!.codiProductNameBottom.value = codiProductBottomFineList[position]["0"]
            holder.rowCodiProductInfoBottomBinding.codiProductInfoBottomViewModel!!.codiProductSizeBottom.value = codiProductBottomFineList[position]["1"]
            holder.rowCodiProductInfoBottomBinding.codiProductInfoBottomViewModel!!.codiProductTypeBottom.value = codiProductBottomFineList[position]["3"]+ "${position + 1}"
            holder.rowCodiProductInfoBottomBinding.codiProductInfoBottomViewModel!!.codiProductColorBottom.value = codiProductBottomFineList[position]["4"]
//            holder.rowCodiProductInfoTopBinding.rowCodiProductInfoTopViewModel!!.productPriceTop.value = codiProductTopList[position].itemPrice.toString()

            // 사진

        }
    }
}
