package kr.co.lion.team4.mrco.fragment.product.codi

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.tabs.TabLayout
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kr.co.lion.team4.mrco.MainActivity
import kr.co.lion.team4.mrco.MainFragmentName
import kr.co.lion.team4.mrco.R
import kr.co.lion.team4.mrco.viewmodel.CodiProductInfoViewModel
import kr.co.lion.team4.mrco.databinding.FragmentCodiProductInfoBinding
import kr.co.lion.team4.mrco.fragment.product.management.CodiProductMangementFragment
import kr.co.lion.team4.mrco.fragment.product.management.IndividualProductManagementFragment


class CodiProductInfoFragment : Fragment() {

    private lateinit var binding: FragmentCodiProductInfoBinding
    private lateinit var viewModel: CodiProductInfoViewModel
    private lateinit var mainActivity: MainActivity
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_codi_product_info, container, false)
        viewModel = ViewModelProvider(this).get(CodiProductInfoViewModel::class.java)
        mainActivity = activity as MainActivity
        binding.lifecycleOwner = this

        // 툴바, 탭 세팅
        settingTab()
        settingToolbar()

        return binding.root
    }

    fun settingToolbar(){
        binding.toolbarProductInfo.apply {
            setNavigationOnClickListener {
                // 뒤로가기
                mainActivity.removeFragment(MainFragmentName.FRAGMENT_CODI_PRODUCT_INFO)
            }
        }
    }

    // 탭바 세팅
    fun settingTab(){
        CoroutineScope(Dispatchers.Main).launch {
            binding.apply {
                val tab = tabsCodiProductInfo

                // 탭 선택 리스너 설정
                tab.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
                    override fun onTabSelected(tab: TabLayout.Tab?) {
                        // 전체 탭
                        if (tab?.position == 0) {
                            changeFragment(CodiProductInfoAllFragment())

                        }
                        // 상의 탭
                        else if (tab?.position == 1) {
                            changeFragment(CodiProductInfoTopFragment())
                        }
                        // 하의 탭
                        else if (tab?.position == 2) {
                            changeFragment(CodiProductInfoBottomFragment())
                        }
                        // 신발 탭
                        else if (tab?.position == 3) {
                            changeFragment(CodiProductInfoShoesFragment())
                        }
                        // 악세사리 탭
                        else {
                            changeFragment(CodiProductInfoAccessoryFragment())
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

        transaction.replace(R.id.container_CodiProductInfo, newFragment)
        transaction.commit()
    }
}