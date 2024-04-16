package kr.co.lion.team4.mrco.fragment.product.purchase

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.PagerSnapHelper
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.SnapHelper
import com.google.android.material.tabs.TabLayout
import com.google.android.material.transition.MaterialSharedAxis
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kr.co.lion.team4.mrco.MainActivity
import kr.co.lion.team4.mrco.MainFragmentName
import kr.co.lion.team4.mrco.R
import kr.co.lion.team4.mrco.SubFragmentName
import kr.co.lion.team4.mrco.databinding.FragmentProductBinding
import kr.co.lion.team4.mrco.databinding.FragmentProductReviewBinding
import kr.co.lion.team4.mrco.databinding.RowProductBannerBinding
import kr.co.lion.team4.mrco.fragment.review.CreateReviewFragment
import kr.co.lion.team4.mrco.viewmodel.product.ProductViewModel

class ProductFragment : Fragment() {
    lateinit var fragmentProductBinding: FragmentProductBinding
    lateinit var mainActivity: MainActivity
    lateinit var productViewModel: ProductViewModel
    lateinit var fragmentProductReviewBinding: FragmentProductReviewBinding

    lateinit var snapHelper: SnapHelper

    var oldFragment: Fragment? = null
    var newFragment: Fragment? = null

    // 장바구니버튼인지 구매하기버튼인지 구분
    var buttonIdx = true

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        fragmentProductBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_product, container, false)
        fragmentProductReviewBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_product_review, container, false)
        productViewModel = ProductViewModel()
        fragmentProductBinding.productViewModel = productViewModel
        fragmentProductBinding.lifecycleOwner = this

        mainActivity = activity as MainActivity

        mainActivity.removeBottomSheet()

        // 상단 코디상품 이미지 리사이클러 뷰
        settingRecyclerViewHomeRecommendBanner()
        settingToolbar()

        clickCoordinatorName()
        clickProductDetailButton()

        settingTab()
        settingBottomButton()

        settingButtonImageMore()

        return fragmentProductBinding.root
    }

    fun settingToolbar() {
        fragmentProductBinding.apply {
            toolbarProduct.apply {
                setNavigationOnClickListener {
                    backProcess()
                }
                setOnMenuItemClickListener {
                    when (it.itemId) {
                        // 검색 클릭 시
                        R.id.menuItemShoppingBagProduct -> {
                            mainActivity.replaceFragment(
                                MainFragmentName.CART_FRAGMENT,
                                true,
                                true,
                                null
                            )
                        }
                    }
                    true
                }
            }
        }
    }

    fun clickCoordinatorName() {
        fragmentProductBinding.apply {
            textViewCoordinatorName.setOnClickListener {
//                val bundle = "coordinator_data"
                mainActivity.replaceFragment(MainFragmentName.COORDINATOR_MAIN, true, true, null)
            }
        }
    }

    fun clickProductDetailButton() {
        fragmentProductBinding.apply {
            buttonProductDetail.setOnClickListener {
                mainActivity.replaceFragment(
                    MainFragmentName.PRODUCT_DETAIL_FRAGMENT,
                    true,
                    true,
                    null
                )
            }
        }
    }

    fun settingTab() {
        childFragmentManager.beginTransaction()
            .replace(R.id.fragmentContainerViewProduct, ProductShippingFragment()).commit()

        fragmentProductBinding.apply {
            val tabLayout = tabLayputProduct

            // 탭 선택 리스너 설정
            tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
                override fun onTabSelected(tab: TabLayout.Tab?) {
                    // 선택된 탭이 첫 번째 탭인 경우
                    if (tab?.position == 0) {
                        changeFragment(ProductShippingFragment())
                    } else if (tab?.position == 1) {
                        changeFragment(ProductReviewFragment())
                    } else {
                        changeFragment(ProductQnaFragment())
                    }
                }

                override fun onTabUnselected(tab: TabLayout.Tab?) {
                    // Not implemented
                }

                override fun onTabReselected(tab: TabLayout.Tab?) {
                    // Not implemented
                }
            })
        }
    }

    fun settingButtonImageMore() {
        fragmentProductReviewBinding.apply {
            buttonImageMore.setOnClickListener {
                fragmentProductBinding.bottomButtonView.isVisible = false
            }
        }
    }

    fun settingBottomButton() {
        fragmentProductBinding.apply {
            buttonBottomAddCart.setOnClickListener {
                buttonIdx = false
                val productPurchaseBottomFragment = ProductPurchaseBottomFragment(buttonIdx)
                productPurchaseBottomFragment.show(
                    mainActivity.supportFragmentManager,
                    "ReplyBottomSheet"
                )
            }

            buttonBottomPurchase.setOnClickListener {
                buttonIdx = true
                val productPurchaseBottomFragment = ProductPurchaseBottomFragment(buttonIdx)
                productPurchaseBottomFragment.show(
                    mainActivity.supportFragmentManager,
                    "ReplyBottomSheet"
                )
            }
        }
    }


    //  Product - 배너 리사이클러 뷰 설정
    fun settingRecyclerViewHomeRecommendBanner() {
        fragmentProductBinding.apply {
            recyclerViewBannerProduct.apply {
                // 어뎁터 및 레이아웃 매니저 설정
                adapter = ProductBannerRecyclerViewAdapter()

                snapHelper = PagerSnapHelper()
                snapHelper.attachToRecyclerView(this)
            }
        }
    }

    // Product - 배너 리사이클러 뷰 어뎁터
    inner class ProductBannerRecyclerViewAdapter :
        RecyclerView.Adapter<ProductBannerRecyclerViewAdapter.ProductBannerViewHolder>() {
        inner class ProductBannerViewHolder(rowProductBannerBinding: RowProductBannerBinding) :
            RecyclerView.ViewHolder(rowProductBannerBinding.root) {
            val rowProductBannerBinding: RowProductBannerBinding

            init {
                this.rowProductBannerBinding = rowProductBannerBinding

                this.rowProductBannerBinding.root.layoutParams = ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT
                )
            }
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductBannerViewHolder {
            val rowProductBannerBinding = RowProductBannerBinding.inflate(layoutInflater)
            val productBannerViewHolder = ProductBannerViewHolder(rowProductBannerBinding)

            return productBannerViewHolder
        }

        override fun getItemCount(): Int {
            return 8
        }

        override fun onBindViewHolder(holder: ProductBannerViewHolder, position: Int) {
            holder.rowProductBannerBinding.textViewBannerPage.text = "${position + 1}/8"

            // position 값에 따라 다른 이미지 설정
            val imageResource = when (position % 4) {
                0 -> R.drawable.iu_image2
                1 -> R.drawable.iu_image6
                2 -> R.drawable.iu_image3
                else -> R.drawable.iu_image7
            }
            holder.rowProductBannerBinding.constraintLayout.setBackgroundResource(imageResource)
        }
    }

    fun backProcess() {
        mainActivity.removeFragment(MainFragmentName.PRODUCT_FRAGMENT)
    }


    fun replaceFragment(
        name: SubFragmentName,
        addToBackStack: Boolean,
        isAnimate: Boolean,
        data: Bundle?
    ) {

        // Fragment를 교체할 수 있는 객체를 추출한다.
        val fragmentTransaction = mainActivity.supportFragmentManager.beginTransaction()

        // oldFragment에 newFragment가 가지고 있는 Fragment 객체를 담아준다.
        if (newFragment != null) {
            oldFragment = newFragment
        }

        when (name) {
            SubFragmentName.PRODUCT_SHIPPING_FRAGMENT -> newFragment = ProductShippingFragment()

            SubFragmentName.PRODUCT_REVIEW_FRAGMENT -> newFragment = ProductReviewFragment()

            SubFragmentName.PRODUCT_QNA_FRAGMENT -> newFragment = ProductQnaFragment()
        }

        // 새로운 Fragment에 전달할 객체가 있다면 arguments 프로퍼티에 넣어준다.
        if (data != null) {
            newFragment?.arguments = data
        }

        if (newFragment != null) {

            // 애니메이션 설정
            if (isAnimate == true) {

                if (oldFragment != null) {
                    // old에서 new가 새롭게 보여질 때 old의 애니메이션
                    oldFragment?.exitTransition = MaterialSharedAxis(MaterialSharedAxis.X, true)
                    // new에서 old로 되돌아갈때 old의 애니메이션
                    oldFragment?.reenterTransition = MaterialSharedAxis(MaterialSharedAxis.X, false)

                    oldFragment?.enterTransition = null
                    oldFragment?.returnTransition = null
                }

                // old에서 new가 새롭게 보여질 때 new의 애니메이션
                newFragment?.enterTransition = MaterialSharedAxis(MaterialSharedAxis.X, true)
                // new에서 old로 되돌아갈때 new의 애니메이션
                newFragment?.returnTransition = MaterialSharedAxis(MaterialSharedAxis.X, false)

                newFragment?.exitTransition = null
                newFragment?.reenterTransition = null
            }

            // Fragment를 교체한다.(이전 Fragment가 없으면 새롭게 추가하는 역할을 수행한다)
            fragmentTransaction.replace(R.id.fragmentContainerViewProduct, newFragment!!)

            // addToBackStack 변수의 값이 true면 새롭게 보여질 Fragment를 BackStack에 포함시켜 준다.
            if (addToBackStack == true) {
                // BackStack 포함 시킬때 이름을 지정해주면 원하는 Fragment를 BackStack에서 제거할 수 있다.
                fragmentTransaction.addToBackStack(name.str)
            }
            // Fragment 교체를 확정한다.
            fragmentTransaction.commit()
        }
    }

    fun changeFragment(changeFragment: Fragment) {
        childFragmentManager.beginTransaction()
            .replace(R.id.fragmentContainerViewProduct, changeFragment).commit()
    }

    // BackStack에서 Fragment를 제거한다.
    fun removeFragment(name: SubFragmentName) {
        // 지정한 이름으로 있는 Fragment를 BackStack에서 제거한다.
        mainActivity.supportFragmentManager.popBackStack(
            name.str,
            FragmentManager.POP_BACK_STACK_INCLUSIVE
        )
    }

    fun hideBottomButton() {
        fragmentProductBinding.apply {
            bottomButtonView.isVisible = false
        }
    }

    fun showBottomButton() {
        fragmentProductBinding.apply {
            bottomButtonView.isVisible = true
        }
    }
}