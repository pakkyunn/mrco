package kr.co.lion.team4.mrco.fragment.product.codi

import android.os.Bundle
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
import kr.co.lion.team4.mrco.databinding.FragmentCodiProductInfoBottomBinding
import kr.co.lion.team4.mrco.databinding.RowCodiProductInfoBottomBinding
import kr.co.lion.team4.mrco.viewmodel.CodiProductInfoBottomViewModel

class CodiProductInfoBottomFragment : Fragment() {

    private lateinit var binding: FragmentCodiProductInfoBottomBinding
    private lateinit var viewModel: CodiProductInfoBottomViewModel
    private lateinit var mainActivity: MainActivity
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_codi_product_info_bottom,
            container,
            false
        )
        viewModel = ViewModelProvider(this).get(CodiProductInfoBottomViewModel::class.java)
        mainActivity = activity as MainActivity
        binding.lifecycleOwner = this


        settingView()

        return binding.root
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
        inner class CodiProductInfoBottomViewHolder(binding: RowCodiProductInfoBottomBinding) :
            RecyclerView.ViewHolder(binding.root) {
            val binding: RowCodiProductInfoBottomBinding

            init {
                this.binding = binding

                this.binding.root.layoutParams = ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT
                )
            }
        }

        override fun onCreateViewHolder(
            parent: ViewGroup,
            viewType: Int
        ): CodiProductInfoBottomViewHolder {
            val binding = DataBindingUtil.inflate<RowCodiProductInfoBottomBinding>(
                layoutInflater,
                R.layout.row_codi_product_info_bottom,
                parent,
                false
            )
            val codiProductInfoBottomViewModel = CodiProductInfoBottomViewModel()
            binding.codiProductInfoBottomViewModel = codiProductInfoBottomViewModel
            binding.lifecycleOwner = this@CodiProductInfoBottomFragment

            val codiProductInfoBottomViewHolder = CodiProductInfoBottomViewHolder(binding)
            return codiProductInfoBottomViewHolder
        }

        override fun getItemCount(): Int {
            return 6
        }

        override fun onBindViewHolder(holder: CodiProductInfoBottomViewHolder, position: Int) {

        }
    }
}
