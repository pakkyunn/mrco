package kr.co.lion.team4.mrco.fragment.product.codi

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import kr.co.lion.team4.mrco.MainActivity
import kr.co.lion.team4.mrco.R
import kr.co.lion.team4.mrco.databinding.FragmentCodiProductInfoTopBinding
import kr.co.lion.team4.mrco.databinding.RowCodiProductInfoTopBinding
import kr.co.lion.team4.mrco.viewmodel.CodiProductInfoTopViewModel

class CodiProductInfoTopFragment : Fragment() {

    private lateinit var binding: FragmentCodiProductInfoTopBinding
    private lateinit var viewModel: CodiProductInfoTopViewModel
    private lateinit var mainActivity: MainActivity
    // 상품의 정보를 담고있는 리스트
    var productTopList = mutableListOf<CodiProductInfoTopViewModel>()


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_codi_product_info_top, container, false)
        viewModel = ViewModelProvider(this).get(CodiProductInfoTopViewModel::class.java)
        mainActivity = activity as MainActivity
        binding.lifecycleOwner = this

        settingView()

        return binding.root
    }

    fun settingView(){
        binding.apply {
            recyclerViewProductInfoTop.apply {
                // 어댑터 설정
                adapter = CodiProductInfoTopAdapater()
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
            val rowCodiProductInfoTopBinding = RowCodiProductInfoTopBinding.inflate(layoutInflater)
            val topViewHolder = TopViewHolder(rowCodiProductInfoTopBinding)
            return topViewHolder
        }

        override fun onBindViewHolder(holder: CodiProductInfoTopAdapater.TopViewHolder, position: Int) {
            holder.rowCodiProductInfoTopBinding.textViewCodiProductName.text = productTopList[position].codiProductNameTop.toString()
            holder.rowCodiProductInfoTopBinding.textViewCodiProductProductName.text = productTopList[position].productNameTop.toString()
            holder.rowCodiProductInfoTopBinding.textViewCodiProductProductType.text = productTopList[position].productTypeTop.toString()
            holder.rowCodiProductInfoTopBinding.textviewCodiProductColor.text = productTopList[position].productColorTop.toString()
            holder.rowCodiProductInfoTopBinding.textviewCodiProductSize.text = productTopList[position].productSizeTop.toString()
            holder.rowCodiProductInfoTopBinding.textviewCodiProductPrice.text = productTopList[position].productPriceTop.toString()
        }

        override fun getItemCount(): Int {
            return productTopList.size
        }

    }
}