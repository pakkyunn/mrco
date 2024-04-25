package kr.co.lion.team4.mrco.fragment.product.individual

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import kr.co.lion.team4.mrco.MainActivity
import kr.co.lion.team4.mrco.MainFragmentName
import kr.co.lion.team4.mrco.R
import kr.co.lion.team4.mrco.viewmodel.IndividualProductInfoViewModel
import kr.co.lion.team4.mrco.databinding.FragmentIndividualProductInfofragmentBinding


class IndividualProductInfoFragment : Fragment() {
    private lateinit var binding: FragmentIndividualProductInfofragmentBinding
    private lateinit var viewModel: IndividualProductInfoViewModel
    private lateinit var mainActivity: MainActivity

    var individualProdcutIdx = 0
    var individualProductName = ""
    var individualStockNum = ""


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_individual_product_infofragment, container, false)
        viewModel = ViewModelProvider(this).get(IndividualProductInfoViewModel::class.java)
        mainActivity = activity as MainActivity
        binding.lifecycleOwner = this
        binding.individualProductInfoViewModel = viewModel

        gettingBundleData()

        settingToolbar()
        settingView()

        return binding.root
    }

    // Data 받아오기
    fun gettingBundleData(){
        val bundle = arguments
        individualProdcutIdx = bundle!!.getInt("individualProductNum")
        individualProductName = bundle.getString("individualProductName").toString()
        individualStockNum = bundle.getString("individualProductStockNum").toString()
    }

    // View 세팅
    fun settingView(){
        binding.apply {
            individualProductInfoViewModel?.individualProductName?.value = individualProductName
            individualProductInfoViewModel?.individualProductSerialNum?.value = individualProdcutIdx
            individualProductInfoViewModel?.individualProductStockNum?.value = individualStockNum
        }
    }

    // 툴바 세팅
    fun settingToolbar(){
        binding.toolbarIndividualProductInfo.apply {
            setNavigationOnClickListener {
                // 뒤로가기
                mainActivity.removeFragment(MainFragmentName.FRAGMENT_INDIVIDUAL_PRODUCT_INFO)
            }
        }
    }
}