package kr.co.lion.team4.mrco.fragment.product.management

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kr.co.lion.team4.mrco.MainActivity
import kr.co.lion.team4.mrco.MainFragmentName
import kr.co.lion.team4.mrco.R
import kr.co.lion.team4.mrco.dao.CoordinatorDao
import kr.co.lion.team4.mrco.dao.ProductDao
import kr.co.lion.team4.mrco.viewmodel.IndividualProductManagementViewModel
import kr.co.lion.team4.mrco.databinding.FragmentIndividualProdcutManagementBinding
import kr.co.lion.team4.mrco.databinding.FragmentProductManagementBinding
import kr.co.lion.team4.mrco.databinding.RowIndividualProductBinding
import kr.co.lion.team4.mrco.databinding.RowLikeCoordinatorBinding
import kr.co.lion.team4.mrco.model.ProductCategoryLinkedListModel
import kr.co.lion.team4.mrco.viewmodel.IndividualProductInfoViewModel
import kr.co.lion.team4.mrco.viewmodel.like.RowLikeCoordinatorViewModel

class IndividualProductManagementFragment : Fragment() {

    lateinit var binding: FragmentIndividualProdcutManagementBinding
    lateinit var mainActivity: MainActivity

    // 상품 정보를 담고있는 리스트
    var individualProductList: ArrayList<List<Map<String, String>>> = arrayListOf()
    // 현재 접속한 코디네이터 Idx
    var coordinatorIdx = 0
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_individual_prodcut_management, container, false)
        mainActivity = activity as MainActivity
        binding.lifecycleOwner = this

        gettingCoordinatorData()

        settingRecyclerViewIndividualProduct()

        gettingIndividualProductData()

        return binding.root
    }

    // 현재 접속한 코디네이터 index 가져오기
    fun gettingCoordinatorData(){
        val userIdx = mainActivity.loginUserIdx
        CoroutineScope(Dispatchers.Main).launch{
            // 현재 코디네이터 번호를 가져온다
            coordinatorIdx = CoordinatorDao.returnCoordiIdx(userIdx)
        }
    }

    // DB에서 개별상품 데이터 가져오기
    fun gettingIndividualProductData(){
        CoroutineScope(Dispatchers.Main).launch {
            individualProductList = ProductDao.gettingIndividualProductList(coordinatorIdx)
            Log.d("taejinCheck", "가져온 individualProductList: ${individualProductList}")

            binding.recyclerViewIndividualProductManagement.adapter?.notifyDataSetChanged()
        }
    }

    // 인기 코디네이터 리사이클러 뷰 설정
    fun settingRecyclerViewIndividualProduct() {
        binding.apply {
            recyclerViewIndividualProductManagement.apply {
                // 어뎁터 및 레이아웃 매니저 설정
                adapter = IndividualProductRecyclerViewAdapter()
                layoutManager = LinearLayoutManager(mainActivity)
            }
        }
    }

    // 리사이클러 뷰 어뎁터
    inner class IndividualProductRecyclerViewAdapter: RecyclerView.Adapter<IndividualProductRecyclerViewAdapter.IndividualProductViewHolder>(){
        inner class IndividualProductViewHolder(rowIndividualProductBinding: RowIndividualProductBinding): RecyclerView.ViewHolder(rowIndividualProductBinding.root){
            val rowIndividualProductBinding: RowIndividualProductBinding

            init {
                this.rowIndividualProductBinding = rowIndividualProductBinding

                this.rowIndividualProductBinding.root.layoutParams = ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT
                )
            }
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): IndividualProductViewHolder {
            val rowIndividualProductBinding = DataBindingUtil.inflate<RowIndividualProductBinding>(
                layoutInflater, R.layout.row_individual_product, parent, false
            )
            val rowIndividualProductViewModel = IndividualProductManagementViewModel()
            rowIndividualProductBinding.rowIndividualProductManagementViewModel = rowIndividualProductViewModel
            rowIndividualProductBinding.lifecycleOwner = this@IndividualProductManagementFragment

            val individualProductViewHolder = IndividualProductViewHolder(rowIndividualProductBinding)

            return individualProductViewHolder
        }

        override fun getItemCount(): Int {
            return 6
        }

        override fun onBindViewHolder(holder: IndividualProductViewHolder, position: Int) {
//            holder.rowIndividualProductBinding.rowIndividualProductManagementViewModel.productName = individualProductList[position]

            holder.rowIndividualProductBinding.root.setOnClickListener {
                mainActivity.replaceFragment(MainFragmentName.FRAGMENT_INDIVIDUAL_PRODUCT_INFO, true, true, null)
            }
        }
    }
}
