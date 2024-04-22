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
import kr.co.lion.team4.mrco.MainActivity
import kr.co.lion.team4.mrco.R
import kr.co.lion.team4.mrco.databinding.FragmentCodiProductInfoAccessoryBinding
import kr.co.lion.team4.mrco.databinding.RowCodiProductInfoAccessoryBinding
import kr.co.lion.team4.mrco.viewmodel.CodiProductInfoAccesoryViewModel

class CodiProductInfoAccessoryFragment : Fragment() {

    private lateinit var binding: FragmentCodiProductInfoAccessoryBinding
    private lateinit var viewModel: CodiProductInfoAccesoryViewModel
    private lateinit var mainActivity: MainActivity

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        Log.d("taejin", "코디 상품 상세 - 악세서리")
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_codi_product_info_accessory, container, false)
        viewModel = ViewModelProvider(this).get(CodiProductInfoAccesoryViewModel::class.java)
        mainActivity = activity as MainActivity
        binding.lifecycleOwner = this
        binding.codiProductInfoAccessoryViewModel = viewModel

        setiingView()

        Log.d("CodiProductAccessoryFragment", "onCreateViewEnd")
        return binding.root
    }

    private fun setiingView(){
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
            Log.d("CodiProductAccessoryFragment", "onCreateViewHolder Start")
            val rowbinding = DataBindingUtil.inflate<RowCodiProductInfoAccessoryBinding>(layoutInflater, R.layout.row_codi_product_info_accessory, parent, false)
            val rowViewModel = CodiProductInfoAccesoryViewModel()
            rowbinding.codiProductInfoAccessoryViewModel = rowViewModel
            rowbinding.lifecycleOwner = this@CodiProductInfoAccessoryFragment

            val viewholder = CodiProductInfoAccessoryViewHolder(rowbinding)
            Log.d("CodiProductAccessoryFragment", "onCreateViewHolder End")
            return viewholder
        }

        override fun getItemCount(): Int {
            return 6
        }

        override fun onBindViewHolder(holder: CodiProductInfoAccessoryViewHolder, position: Int) {

        }
    }


}