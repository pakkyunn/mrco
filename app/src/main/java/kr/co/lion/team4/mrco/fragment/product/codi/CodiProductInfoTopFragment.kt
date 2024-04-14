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
import kr.co.lion.team4.mrco.MainActivity
import kr.co.lion.team4.mrco.R
import kr.co.lion.team4.mrco.databinding.FragmentCodiProductInfoTopBinding
import kr.co.lion.team4.mrco.databinding.RowCodiProductInfoTopBinding
import kr.co.lion.team4.mrco.model.ProductCategoryLinkedListModel
import kr.co.lion.team4.mrco.viewmodel.CodiProductInfoTopViewModel

class CodiProductInfoTopFragment : Fragment() {

    private lateinit var binding: FragmentCodiProductInfoTopBinding
    private lateinit var viewModel: CodiProductInfoTopViewModel
    private lateinit var mainActivity: MainActivity
    // 상품의 정보를 담고있는 리스트
    var productTopList = mutableListOf<ProductCategoryLinkedListModel>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        Log.d("CodiProductInfoFragment", "onCreateView")
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_codi_product_info_top, container, false)
        // CodiProductInfoFragment에서 사용할 viewModel 정의
        viewModel = CodiProductInfoTopViewModel()
        mainActivity = activity as MainActivity

        // Lifecycle 및 xml에 사용할 변수에 모델을 넣어준다.
        binding.lifecycleOwner = this
        binding.codiProductInfoTopViewModel = viewModel

        settingView()

        return binding.root
    }

    private fun settingView(){
        binding.apply {
            recyclerViewProductInfoTop.apply {
                Log.d("CodiProductInfoFragment", "settingView")

                // 어댑터 설정
                adapter = CodiProductInfoTopAdapater()
                // 레이아웃 매니저
                layoutManager = LinearLayoutManager(mainActivity)
                // 구분선 추가
                val deco = MaterialDividerItemDecoration(mainActivity, MaterialDividerItemDecoration.VERTICAL)
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
            Log.d("CodiProductInfoFragment", "onBindViewHolder start")
//            holder.rowCodiProductInfoTopBinding.rowCodiProductInfoTopViewModel?.productNameTop?.value = productTopList[position].itemName
//            holder.rowCodiProductInfoTopBinding.rowCodiProductInfoTopViewModel?.productTypeTop?.value = productTopList[position].itemCategory
//            holder.rowCodiProductInfoTopBinding.rowCodiProductInfoTopViewModel?.productSizeTop?.value = productTopList[position].itemSize.toString()
            Log.d("CodiProductInfoFragment", "onBindViewHolder end")
        }

        override fun getItemCount(): Int {
//            return productTopList.size
            return 6
        }

    }
}