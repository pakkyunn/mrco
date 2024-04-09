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
import kr.co.lion.team4.mrco.model.ProductCategoryLinkedListModel
import kr.co.lion.team4.mrco.R
import kr.co.lion.team4.mrco.databinding.FragmentCodiProductInfoTopBinding
import kr.co.lion.team4.mrco.databinding.RowCodiProductInfoBinding
import kr.co.lion.team4.mrco.viewmodel.CodiProductInfoTopViewModel

class CodiProductInfoTopFragment : Fragment() {

    private lateinit var binding: FragmentCodiProductInfoTopBinding
    private lateinit var viewModel: CodiProductInfoTopViewModel
    private lateinit var mainActivity: MainActivity
    // 상품의 정보를 담고있는 리스트
    var productTopList = mutableListOf<ProductCategoryLinkedListModel>()


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
                adapter = CodiProductInfoTopAdapater()
            }
        }
    }


    inner class CodiProductInfoTopAdapater: RecyclerView.Adapter<CodiProductInfoTopAdapater.TopViewHolder>(){
        inner class TopViewHolder(rowCodiProductInfoBinding: RowCodiProductInfoBinding): RecyclerView.ViewHolder(rowCodiProductInfoBinding.root){
            val rowCodiProductInfoBinding: RowCodiProductInfoBinding
            init {
                this.rowCodiProductInfoBinding = rowCodiProductInfoBinding

                this.rowCodiProductInfoBinding.root.layoutParams = ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT
                )
            }
        }
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CodiProductInfoTopAdapater.TopViewHolder {
            val rowCodiProductInfoBinding = RowCodiProductInfoBinding.inflate(layoutInflater)
            val topViewHolder = TopViewHolder(rowCodiProductInfoBinding)
            return topViewHolder
        }

        override fun onBindViewHolder(holder: CodiProductInfoTopAdapater.TopViewHolder, position: Int) {
            TODO("Not yet implemented")
        }

        override fun getItemCount(): Int {
            TODO("Not yet implemented")
        }

    }
}