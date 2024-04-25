package kr.co.lion.team4.mrco.fragment.product.purchase

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.PagerSnapHelper
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.SnapHelper
import kr.co.lion.team4.mrco.MainActivity
import kr.co.lion.team4.mrco.MainFragmentName
import kr.co.lion.team4.mrco.R
import kr.co.lion.team4.mrco.databinding.FragmentProductDetailBinding
import kr.co.lion.team4.mrco.databinding.RowProductDetailBinding
import kr.co.lion.team4.mrco.databinding.RowProductReviewUserImageBinding

class ProductDetailFragment : Fragment() {
    lateinit var fragmentProductDetailBinding: FragmentProductDetailBinding
    lateinit var mainActivity: MainActivity

    // 장바구니버튼인지 구매하기버튼인지 구분
    var buttonIdx = true
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        fragmentProductDetailBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_product_detail, container, false)
        fragmentProductDetailBinding.lifecycleOwner = this
        mainActivity = activity as MainActivity

        settingToolbar()

        settingBottomButton()

//        settingRecyclerViewProductDetail()

        return fragmentProductDetailBinding.root
    }

    fun settingToolbar() {
        fragmentProductDetailBinding.apply {
            toolbarProductDetail.apply {
                setNavigationOnClickListener {
                    backProcess()
                }
            }
        }
    }

    fun settingBottomButton() {
        fragmentProductDetailBinding.apply {
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


    // 상품 후기 사진 전체 RecyclerView 구성
    fun settingRecyclerViewProductDetail() {
        fragmentProductDetailBinding.apply {
            recyclerViewProductDetail.apply {
                // 어뎁터
                adapter = ProductDetailRecyclerViewAdapter()
                // 레이아웃 매니저
                layoutManager = LinearLayoutManager(mainActivity)
            }
        }
    }

    inner class ProductDetailRecyclerViewAdapter :
        RecyclerView.Adapter<ProductDetailRecyclerViewAdapter.ProductDetailViewHolder>() {
        inner class ProductDetailViewHolder(rowProductDetailBinding: RowProductDetailBinding) :
            RecyclerView.ViewHolder(rowProductDetailBinding.root) {
            val rowProductDetailBinding: RowProductDetailBinding

            init {
                this.rowProductDetailBinding = rowProductDetailBinding

                this.rowProductDetailBinding.root.layoutParams = ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT
                )
            }
        }

        override fun onCreateViewHolder(
            parent: ViewGroup,
            viewType: Int
        ): ProductDetailViewHolder {
            val rowProductDetailBinding =
                DataBindingUtil.inflate<RowProductDetailBinding>(
                    layoutInflater,
                    R.layout.row_product_detail,
                    parent,
                    false
                )
            rowProductDetailBinding.lifecycleOwner = this@ProductDetailFragment

            val ProductDetailViewHolder =
                ProductDetailViewHolder(rowProductDetailBinding)
            return ProductDetailViewHolder
        }

        override fun getItemCount(): Int {
            return 2
        }

        override fun onBindViewHolder(holder: ProductDetailViewHolder, position: Int) {

        }
    }

    fun backProcess() {
        mainActivity.removeFragment(MainFragmentName.PRODUCT_DETAIL_FRAGMENT)
    }
}