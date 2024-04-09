package kr.co.lion.team4.mrco.fragment.product.management

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.divider.MaterialDividerItemDecoration
import kr.co.lion.team4.mrco.MainActivity
import kr.co.lion.team4.mrco.MainFragmentName
import kr.co.lion.team4.mrco.R
import kr.co.lion.team4.mrco.databinding.FragmentCodiProductMangementBinding
import kr.co.lion.team4.mrco.databinding.RowCodiProductBinding
import kr.co.lion.team4.mrco.model.ProductModel
import kr.co.lion.team4.mrco.viewmodel.CodiProductManagementViewModel

class CodiProductMangementFragment : Fragment() {

    private lateinit var binding: FragmentCodiProductMangementBinding
    private lateinit var viewModel: CodiProductManagementViewModel
    lateinit var mainActivity: MainActivity

    // 코디 상품 리스트
    val codiProductList = mutableListOf<ProductModel>()
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_codi_product_mangement, container, false)
        viewModel = CodiProductManagementViewModel()
        mainActivity = activity as MainActivity
        binding.lifecycleOwner = this

        settingRecyclerView()

        return binding.root
    }

    fun settingRecyclerView(){
        binding.recyclerViewCodiProductManagement.apply {
            // 어댑터 설정
            adapter = CodiProductManagementAdapter()
            val deco = MaterialDividerItemDecoration(mainActivity, MaterialDividerItemDecoration.VERTICAL)
            addItemDecoration(deco)
        }
    }

    // 리스트 화면
    inner class CodiProductManagementAdapter: RecyclerView.Adapter<CodiProductManagementAdapter.CodiProductManagementViewHolder>(){
        // ViewHolder
        inner class CodiProductManagementViewHolder(binding: RowCodiProductBinding): RecyclerView.ViewHolder(binding.root){
            val binding: RowCodiProductBinding

            init {
                this.binding = binding
                this.binding.root.layoutParams = ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT
                )
            }

        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CodiProductManagementViewHolder {
            val binding = RowCodiProductBinding.inflate(layoutInflater)
            val viewHolder = CodiProductManagementViewHolder(binding)
            return viewHolder
        }

        override fun getItemCount(): Int {
            return codiProductList.size
        }

        override fun onBindViewHolder(holder: CodiProductManagementViewHolder, position: Int) {
            holder.binding.textViewRowCodiProductProductName.text = codiProductList[position].coordiName
            holder.binding.textViewRowCodiProductSerialNumber.text = codiProductList[position].productIdx.toString()
            // 사진 등록 필요
//            holder.binding.imageViewRowCodiProduct.


            // 항목을 눌렀을 때 동작할 리스너
            holder.binding.root.setOnClickListener {
                // 필요한 데이터 담아주기
                val readBundle = Bundle()
                readBundle.putInt("codiIdx", codiProductList[position].productIdx)

                // 화면 전환 -> CodiProductInfo
                mainActivity.replaceFragment(MainFragmentName.FRAGMENT_CODI_PRODUCT_INFO, true, true, readBundle)

            }
        }
    }
}