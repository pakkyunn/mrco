package kr.co.lion.team4.mrco.fragment.product.codi

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.get
import androidx.recyclerview.widget.RecyclerView
import kr.co.lion.team4.mrco.MainActivity
import kr.co.lion.team4.mrco.R
import kr.co.lion.team4.mrco.databinding.FragmentCodiProductInfoBottomBinding
import kr.co.lion.team4.mrco.databinding.RowCodiProductInfoBottomBinding
import kr.co.lion.team4.mrco.viewmodel.CodiProductInfoBottomViewModel

class CodiProductInfoBottomFragment : Fragment() {

    private lateinit var binding: FragmentCodiProductInfoBottomBinding
    private lateinit var viewModel: CodiProductInfoBottomViewModel
    private lateinit var mainActivity: MainActivity
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_codi_product_info_bottom, container, false)
        viewModel = ViewModelProvider(this).get(CodiProductInfoBottomViewModel::class.java)
        mainActivity = activity as MainActivity
        binding.lifecycleOwner = this



        return binding.root
    }

    inner class CodiProductInfoBottomAdapter: RecyclerView.Adapter<CodiProductInfoBottomAdapter.CodiProductInfoBottomViewHolder>(){
        // ViewHolder
        inner class CodiProductInfoBottomViewHolder(binding: RowCodiProductInfoBottomBinding): RecyclerView.ViewHolder(binding.root){
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
            TODO("Not yet implemented")
        }

        override fun getItemCount(): Int {
            return 0
        }

        override fun onBindViewHolder(holder: CodiProductInfoBottomViewHolder, position: Int) {
            TODO("Not yet implemented")
        }
    }
}
