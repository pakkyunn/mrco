package kr.co.lion.team4.mrco.fragment.product.purchase

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.PagerSnapHelper
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.SnapHelper
import kr.co.lion.team4.mrco.MainActivity
import kr.co.lion.team4.mrco.R
import kr.co.lion.team4.mrco.databinding.FragmentProductBinding
import kr.co.lion.team4.mrco.databinding.RowProductBannerBinding
import kr.co.lion.team4.mrco.viewmodel.product.ProductViewModel

class ProductFragment : Fragment() {
    lateinit var fragmentProductBinding: FragmentProductBinding
    lateinit var mainActivity: MainActivity
    lateinit var productViewModel: ProductViewModel

    lateinit var snapHelper: SnapHelper

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        fragmentProductBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_product, container, false)
        productViewModel = ProductViewModel()
        fragmentProductBinding.productViewModel = productViewModel
        fragmentProductBinding.lifecycleOwner = this

        mainActivity = activity as MainActivity


        // 리사이클러 뷰
        settingRecyclerViewHomeRecommendBanner()


        return fragmentProductBinding.root
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
    inner class ProductBannerRecyclerViewAdapter: RecyclerView.Adapter<ProductBannerRecyclerViewAdapter.ProductBannerViewHolder>(){
        inner class ProductBannerViewHolder(rowProductBannerBinding: RowProductBannerBinding): RecyclerView.ViewHolder(rowProductBannerBinding.root){
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
            holder.rowProductBannerBinding.textViewBannerPage.text = "${position+1}/8"

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

}