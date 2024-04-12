package kr.co.lion.team4.mrco.fragment.product.management

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.google.android.material.tabs.TabLayout
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kr.co.lion.team4.mrco.MainActivity
import kr.co.lion.team4.mrco.MainFragmentName
import kr.co.lion.team4.mrco.R
import kr.co.lion.team4.mrco.viewmodel.ProductManagementViewModel
import kr.co.lion.team4.mrco.databinding.FragmentProductManagementBinding



class ProductManagementFragment : Fragment() {

    private lateinit var binding: FragmentProductManagementBinding
    private lateinit var viewModel: ProductManagementViewModel
    private lateinit var mainActivity: MainActivity
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_product_management, container, false)
        viewModel = ProductManagementViewModel()
        mainActivity = activity as MainActivity
        binding.lifecycleOwner = this


        settingToolbar()
        settingClickEvent()

        return binding.root
    }

    fun settingToolbar(){
        binding.toolbarProductManagement.apply {
//            setNavigationOnClickListener {
//                // 뒤로가기
//                mainActivity.removeFragment(MainFragmentName.FRAGMENT_PRODUCT_MANAGEMENT)
//            }
        }
    }

    fun settingClickEvent(){
        CoroutineScope(Dispatchers.Main).launch {
            binding.apply {
                val tab = tabsProductManagement

                // 탭 선택 리스너 설정
                tab.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
                    override fun onTabSelected(tab: TabLayout.Tab?) {
                        // 코디상품관리 탭 클릭
                        if (tab?.position == 0) {
                            // fragmentContainerProductManagement -> CodiProductManagemnetFragment
                            changeFragment(CodiProductMangementFragment())

                        }
                        // 코디상품관리 탭 클릭
                        else {
                            // fragmentContainerProductManagement -> IndividualProductManagemnetFragment
                            changeFragment(IndividualProductManagementFragment())
                        }
                    }

                    override fun onTabUnselected(tab: TabLayout.Tab?) {
                    }

                    override fun onTabReselected(tab: TabLayout.Tab?) {
                    }
                })
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