package kr.co.lion.team4.mrco.fragment.product.codi

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kr.co.lion.team4.mrco.MainActivity
import kr.co.lion.team4.mrco.R
import kr.co.lion.team4.mrco.dao.ProductDao
import kr.co.lion.team4.mrco.dao.UserDao
import kr.co.lion.team4.mrco.databinding.FragmentCodiProductInfoAllBinding
import kr.co.lion.team4.mrco.viewmodel.CodiProductInfoAllViewModel
import kr.co.lion.team4.mrco.viewmodel.LoginViewModel

class CodiProductInfoAllFragment : Fragment() {

    private lateinit var binding: FragmentCodiProductInfoAllBinding
    private lateinit var viewModel: CodiProductInfoAllViewModel
    private lateinit var mainActivity: MainActivity

    // 상품 번호를 받는다
    var productIdx = 0
    var codiProductName = ""
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        Log.d("taejin", "코디 상품 상세 - 전체")
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_codi_product_info_all, container, false)
        viewModel = ViewModelProvider(this).get(CodiProductInfoAllViewModel::class.java)
        mainActivity = activity as MainActivity
        binding.lifecycleOwner = this
        binding.codiProductInfoViewModel = viewModel

        gettingBundleData()
        settingInputForm()
        gettingCodiProductAllData()


        return binding.root
    }

    // Bundle 객체에 저장된 데이터 받아오기
    fun gettingBundleData(){
        val bundle = arguments
        productIdx = bundle!!.getInt("productIdx")
        codiProductName = bundle.getString("productName")!!
    }

    fun gettingCodiProductAllData(){
        CoroutineScope(Dispatchers.Main).launch {
//            val productCategoryLinkedListModel = ProductDao.selectProductInfoData(productIdx)

            viewModel.productName.value = codiProductName
//            // 이미지 데이터를 불러온다.
//            if(productCategoryLinkedListModel?.itemImageFileName != null) {
//                ProductDao.gettingProductImage(mainActivity, productCategoryLinkedListModel.itemImageFileName, binding.imageViewCondiProductInfoAllCodiImage)
//            }
        }
    }

    fun settingInputForm() {
        // 이미지 뷰를 안 보이게 한다.
        binding.imageViewCondiProductInfoAllCodiImage.visibility = View.INVISIBLE
        // 입력 요소에 띄어쓰기를 넣어준다.
        viewModel.productName.value = " "
    }
}
