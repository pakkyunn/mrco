package kr.co.lion.team4.mrco.fragment.product.management

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.google.android.material.tabs.TabLayoutMediator
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

        settingToolbarProductManagement()
        viewPagerActiviation()

        return binding.root
    }

    // setting toolbar
    fun settingToolbarProductManagement(){
        binding.toolbarProductManagement.apply {
            setNavigationOnClickListener {
                backProcess()
            }
        }
    }

    private fun viewPagerActiviation(){
        binding.apply {
            // 1. 페이지 데이터를 로드
            val list = listOf(CodiProductMangementFragment(), IndividualProductManagementFragment())
            // 2. Adapter 생성
            val pagerAdapter = FragmentPagerAdapter(list, mainActivity)
            // 3. Adapater와 Pager연결
            viewPagerProductManagement.adapter = pagerAdapter
            // 4. 탭 메뉴의 갯수만큼 제목을 목록으로 생성
            val titles = listOf("코디상품관리", "개별상품관리")
            // 5. 탭 레이아웃과 뷰페이저 연결
            TabLayoutMediator(tablayoutProductManagement, viewPagerProductManagement){tab, position ->
                tab.text = titles.get(position)
            }.attach()

        }
    }

    private inner class FragmentPagerAdapter(val fragmentList: List<Fragment>, fragmentActivity: FragmentActivity):
        FragmentStateAdapter(fragmentActivity){
        override fun getItemCount(): Int {
            return fragmentList.size
        }

        override fun createFragment(position: Int): Fragment {
            return fragmentList.get(position)
        }
    }

    // 이전 화면으로
    fun backProcess(){
        mainActivity.removeFragment(MainFragmentName.FRAGMENT_PRODUCT_MANAGEMENT)
    }
}