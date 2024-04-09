package kr.co.lion.team4.mrco.Fragment.product.management

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import kr.co.lion.team4.mrco.MainFragmentName
import kr.co.lion.team4.mrco.R
import kr.co.lion.team4.mrco.viewmodel.ProductManagementViewModel
import kr.co.lion.team4.mrco.databinding.FragmentProductManagementBinding



class ProductManagementFragment : Fragment() {

    private lateinit var binding: FragmentProductManagementBinding
    private lateinit var viewModel: ProductManagementViewModel
    private lateinit var mainActivity: kr.co.lion.team4.mrco.MainActivity
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_product_management, container, false)
        viewModel = ProductManagementViewModel()
        mainActivity = activity as kr.co.lion.team4.mrco.MainActivity
        binding.lifecycleOwner = this


        settingToolbar()
        settingClickEvent()

        return binding.root
    }

    fun settingToolbar(){
        binding.toolbarProductManagement.apply {
            setNavigationOnClickListener {
                // 뒤로가기
                mainActivity.removeFragment(MainFragmentName.FRAGMENT_PRODUCT_MANAGEMENT)
            }
        }
    }

    fun settingClickEvent(){
        binding.apply {
            // 코디상품관리 탭 클릭
            tabItemProductManagementCodiProductManagement.setOnClickListener {
//                fragmentContainerProductManagement -> CodiProductManagemnetFragment
            }

            // 개별상품관리 탭 클릭
            tabItemProdcutManagementIndividualProductManagement.setOnClickListener {
//                fragmentContainerProductManagement -> IndividualProductManagemnetFragment

            }
        }
    }

    // FragmentContainerView 전환
    fun changeFragment(changeFragment: Fragment){
        val newFragment = changeFragment
        val transaction = childFragmentManager.beginTransaction()

        transaction.replace(R.id.fragmentContainer_productManagement, newFragment)
        transaction.commit()
    }
}