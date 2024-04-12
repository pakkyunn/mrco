package kr.co.lion.team4.mrco.fragment.review

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
import kr.co.lion.team4.mrco.databinding.FragmentMyReviewBinding
import kr.co.lion.team4.mrco.fragment.product.management.CodiProductMangementFragment
import kr.co.lion.team4.mrco.fragment.product.management.IndividualProductManagementFragment
import kr.co.lion.team4.mrco.viewmodel.MyReviewViewModel

class MyReviewFragment : Fragment() {

    private lateinit var binding: FragmentMyReviewBinding
    private lateinit var viewModel: MyReviewViewModel
    private lateinit var mainActivity: MainActivity

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_my_review, container, false)
        viewModel = ViewModelProvider(this).get(MyReviewViewModel::class.java)
        mainActivity = activity as MainActivity
        binding.lifecycleOwner = this

        // 툴바, 탭 세팅
        settingToolbar()
        settingTab()

        return binding.root
    }

    // 툴바 세팅
    fun settingToolbar(){
        binding.toolbarMyReview.apply {
            setNavigationOnClickListener {
                // 뒤로가기
                mainActivity.removeFragment(MainFragmentName.FRAGMENT_INDIVIDUAL_PRODUCT_INFO)
            }
        }
    }

    // 탭 세팅
    fun settingTab(){
        CoroutineScope(Dispatchers.Main).launch {
            binding.apply {
                val tab = tabsMyReview
                // 탭 선택 리스너 설정
                tab.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
                    override fun onTabSelected(tab: TabLayout.Tab?) {
                        // 리뷰 작성 탭
                        if (tab?.position == 0) {
                            changeFragment(CreateReviewFragment())
                        }
                        // 작성한 리뷰 탭
                        else {
                            changeFragment(ReviewCreatedFragment())
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

        transaction.replace(R.id.fragmentContainerView_productReview, newFragment)
        transaction.commit()
    }
}