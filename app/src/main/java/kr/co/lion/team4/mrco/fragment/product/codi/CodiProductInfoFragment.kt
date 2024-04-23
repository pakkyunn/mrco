package kr.co.lion.team4.mrco.fragment.product.codi

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentPagerAdapter
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kr.co.lion.team4.mrco.MainActivity
import kr.co.lion.team4.mrco.MainFragmentName
import kr.co.lion.team4.mrco.R
import kr.co.lion.team4.mrco.dao.ProductDao
import kr.co.lion.team4.mrco.viewmodel.CodiProductInfoViewModel
import kr.co.lion.team4.mrco.databinding.FragmentCodiProductInfoBinding
import kr.co.lion.team4.mrco.fragment.product.management.ProductManagementFragment
import kr.co.lion.team4.mrco.model.ProductModel


class CodiProductInfoFragment : Fragment() {

    private lateinit var binding: FragmentCodiProductInfoBinding
    private lateinit var viewModel: CodiProductInfoViewModel
    private lateinit var mainActivity: MainActivity
    var productIdx = 0
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_codi_product_info, container, false)
        mainActivity = activity as MainActivity
        binding.lifecycleOwner = this

        gettingProductIdx()

        // ViewPager 작업
        viewPagerActivation()

        settingView()



        return binding.root
    }

    private fun settingView(){
        binding.apply {
            toolbarProductInfo.apply {
                setNavigationOnClickListener {
                    // 뒤로가기
                    mainActivity.removeFragment(MainFragmentName.FRAGMENT_CODI_PRODUCT_INFO)
                    mainActivity.removeFragment(MainFragmentName.FRAGMENT_CODI_PRODUCT_INFO_ALL)
                    mainActivity.removeFragment(MainFragmentName.FRAGMENT_CODI_PRODUCT_INFO_TOP)
                    mainActivity.removeFragment(MainFragmentName.FRAGMENT_CODI_PRODUCT_INFO_BOTTOM)
                    mainActivity.removeFragment(MainFragmentName.FRAGMENT_CODI_PRODUCT_INFO_SHOES)
                    mainActivity.removeFragment(MainFragmentName.FRAGMENT_CODI_PRODUCT_INFO_ACCESSORY)
                }
            }
        }
    }

    // Bundle 객체에서 productIdx 받아오기
    private fun gettingProductIdx(){
        productIdx = arguments?.getInt("productIdx")!!
    }



    // ViewPager 활성화
    private fun viewPagerActivation(){
        binding.apply {

            // 1. 페이지 데이터를 로드
            val list = listOf(
                CodiProductInfoAllFragment(),
                CodiProductInfoTopFragment(),
                CodiProductInfoBottomFragment(),
                CodiProductInfoShoesFragment(),
                CodiProductInfoAccessoryFragment())
            // 2. Adpater 생성
            val pagerAdapter = FragmentPagerAdapter(list, mainActivity)
            // 3. Adpater와 ViewPager 연결
            viewPagerCodiProductInfo.adapter = pagerAdapter
            // 4. 탭 메뉴와 갯수만큰 제목을 목록으로 생성
//            val titles = listOf("전체", "상의", "하의", "신발", "악세서리")
            // 5. Tab Layout과 ViewPager 연결
            TabLayoutMediator(tabLayoutCodiProductInfo, viewPagerCodiProductInfo){ tab, position ->
                tab.text = when(position){
                    0 -> "전체"
                    1 -> "상의"
                    2 -> "하의"
                    3 -> "신발"
                    4 -> "악세서리"
                    else -> throw IllegalArgumentException("Invalid position: $position")
                }
            }.attach()
        }
    }

    private inner class FragmentPagerAdapter(val fragmentList: List<Fragment>, fragmentActivity: FragmentActivity):
        FragmentStateAdapter(fragmentActivity){
        override fun getItemCount(): Int {
            return fragmentList.size
        }

        override fun createFragment(position: Int): Fragment {
            val fragment = fragmentList[position]

            // 각 Fragment에 전달할 데이터를 설정해준다.
            val bundle = Bundle().apply {
                putInt("productIdx", productIdx)
            }
            fragment.arguments = bundle

            return fragment
        }

    }
}
