package kr.co.lion.team4.mrco.fragment.product.management

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
import kr.co.lion.team4.mrco.MainFragmentName
import kr.co.lion.team4.mrco.R
import kr.co.lion.team4.mrco.dao.CoordinatorDao
import kr.co.lion.team4.mrco.dao.ProductDao
import kr.co.lion.team4.mrco.databinding.FragmentCodiProductMangementBinding
import kr.co.lion.team4.mrco.databinding.RowCodiProductBinding
import kr.co.lion.team4.mrco.model.CoordinatorModel
import kr.co.lion.team4.mrco.model.ProductModel
import kr.co.lion.team4.mrco.viewmodel.CodiProductManagementViewModel

class CodiProductMangementFragment : Fragment() {

    private lateinit var binding: FragmentCodiProductMangementBinding
    lateinit var mainActivity: MainActivity

    // 상품 정보를 가지고 있는 리스트
    var codiProductList = mutableListOf<ProductModel>()

    // 현재 접속한 코디네이터 Idx
    var coordinatorIdx = 0

    // 코디 상품 리스트
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_codi_product_mangement, container, false)
        mainActivity = activity as MainActivity

        // RecyclerView 세팅
        settingRecyclerView()
        // Codi 정보 가져오기
        gettingCodiData()

        return binding.root
    }

    // 현재 접속한 코디네이터 index 가져오기
    fun gettingBundleData(){
        val bundle = arguments
        if (bundle != null){
            coordinatorIdx = bundle.getInt("coordinatorIdx")
        }
    }

    fun settingRecyclerView(){
        binding.recyclerViewCodiProductManagement.apply {
            // 어댑터 설정
            adapter = CodiProductManagementAdapter()
            layoutManager = LinearLayoutManager(mainActivity)

            val deco = MaterialDividerItemDecoration(mainActivity, MaterialDividerItemDecoration.VERTICAL)
            deco.isLastItemDecorated = false // 맨 마지막줄 구분선 제거
            addItemDecoration(deco)
        }
    }

    // 서버로 부터 데이터를 가져와 리사이클러뷰를 갱신한다.
    fun gettingCodiData(){
        CoroutineScope(Dispatchers.Main).launch {
            val userIdx = mainActivity.loginUserIdx

            // 상품 정보를 가져온다
            codiProductList = ProductDao.gettingProductList(userIdx)
            Log.d("taejinCheck", "현재 가져온 codiProductList: ${codiProductList}")

            // 리사이클러뷰를 갱신한다.
            binding.recyclerViewCodiProductManagement.adapter?.notifyDataSetChanged()
        }
    }

    // 리스트 화면
    inner class CodiProductManagementAdapter: RecyclerView.Adapter<CodiProductManagementAdapter.CodiProductManagementViewHolder>(){
        // ViewHolder
        inner class CodiProductManagementViewHolder(rowCodiProductBinding: RowCodiProductBinding): RecyclerView.ViewHolder(rowCodiProductBinding.root){
            val rowCodiProductBinding: RowCodiProductBinding

            init {
                this.rowCodiProductBinding = rowCodiProductBinding
                this.rowCodiProductBinding.root.layoutParams = ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT
                )
            }

        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CodiProductManagementViewHolder {

            val rowCodiProductbinding = DataBindingUtil.inflate<RowCodiProductBinding>(
                layoutInflater, R.layout.row_codi_product, parent, false
            )
            val codiProductManagementViewModel = CodiProductManagementViewModel()
            rowCodiProductbinding.codiProductManagementViewModel = codiProductManagementViewModel
            rowCodiProductbinding.lifecycleOwner = this@CodiProductMangementFragment

            val viewHolder = CodiProductManagementViewHolder(rowCodiProductbinding)
            return viewHolder
        }

        override fun getItemCount(): Int {
            return codiProductList.size
        }


        override fun onBindViewHolder(holder: CodiProductManagementViewHolder, position: Int) {

            // 상품명
            holder.rowCodiProductBinding.codiProductManagementViewModel!!.codiProductName.value = codiProductList[position].coordiName //?.coordiName
            // 일련번호
            holder.rowCodiProductBinding.codiProductManagementViewModel!!.codiProductSerialNum.value = codiProductList[position].productIdx.toString() // ?.productIdx.toString()
            // 사진

            // 항목을 눌렀을 때 동작할 리스너
            holder.rowCodiProductBinding.root.setOnClickListener {
                val bundle = Bundle()
                bundle.putInt("productIdx", codiProductList[position].productIdx)
                bundle.putString("productName", codiProductList[position].coordiName)

                // 화면 전환 -> CodiProductInfo
                mainActivity.replaceFragment(MainFragmentName.FRAGMENT_CODI_PRODUCT_INFO, true, true, bundle)
            }
        }
    }
}