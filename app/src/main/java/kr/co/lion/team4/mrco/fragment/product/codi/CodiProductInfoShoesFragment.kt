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
import kr.co.lion.team4.mrco.MainActivity
import kr.co.lion.team4.mrco.R
import kr.co.lion.team4.mrco.databinding.FragmentCodiProductInfoShoesBinding
import kr.co.lion.team4.mrco.databinding.RowCodiProductInfoShoesBinding
import kr.co.lion.team4.mrco.viewmodel.CodiProductInfoShoesViewModel

class CodiProductInfoShoesFragment : Fragment() {

    private lateinit var binding: FragmentCodiProductInfoShoesBinding
    private lateinit var viewModel: CodiProductInfoShoesViewModel
    private lateinit var mainActivity: MainActivity
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        Log.d("taejin", "코디 상품 상세 - 신발")
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_codi_product_info_shoes, container, false)
        viewModel = CodiProductInfoShoesViewModel()
        mainActivity = activity as MainActivity
        binding.lifecycleOwner = this
        binding.viewmodel = viewModel

        Log.d("CodiProductInfoShoesFragment","onCreateViewStart")
        settingView()
        Log.d("CodiProductInfoShoesFragment","onCreateViewholderEnd")
        return binding.root
    }

    private fun settingView(){
        binding.apply {
            recyclerViewProductInfoShoes.apply {
                // 어댑터 설정
                adapter = CodiProductInfoShoesAdapter()
                // 레이아웃 매니저
                layoutManager = LinearLayoutManager(mainActivity)

                // 구분선
                val deco = MaterialDividerItemDecoration(mainActivity, MaterialDividerItemDecoration.VERTICAL)
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
            Log.d("CodiProductInfoShoesFragment","onCreateViewholderStart")
            val rowCodiProductInfoShoesBinding = DataBindingUtil.inflate<RowCodiProductInfoShoesBinding>(layoutInflater, R.layout.row_codi_product_info_shoes, parent, false)
            val codiProductInfoShoesViewModel = CodiProductInfoShoesViewModel()
            rowCodiProductInfoShoesBinding.codiProductInfoShoesViewModel = codiProductInfoShoesViewModel
            rowCodiProductInfoShoesBinding.lifecycleOwner = this@CodiProductInfoShoesFragment

            val codiProductInfoShoesViewHolder = CodiProductInfoShoesViewHolder(rowCodiProductInfoShoesBinding)
            Log.d("CodiProductInfoShoesFragment","onCreateViewholderEnd")
            return codiProductInfoShoesViewHolder
        }

        override fun getItemCount(): Int {
            return 6
        }

        override fun onBindViewHolder(holder: CodiProductInfoShoesViewHolder, position: Int) {

        }
    }
}