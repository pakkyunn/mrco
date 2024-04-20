package kr.co.lion.team4.mrco.fragment.like

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.tabs.TabLayout
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kr.co.lion.team4.mrco.MainActivity
import kr.co.lion.team4.mrco.MainFragmentName
import kr.co.lion.team4.mrco.R
import kr.co.lion.team4.mrco.databinding.FragmentLikeProductBinding
import kr.co.lion.team4.mrco.databinding.RowLikeProductBinding
import kr.co.lion.team4.mrco.viewmodel.like.LikeProductViewModel
import kr.co.lion.team4.mrco.viewmodel.like.RowLikeProductViewModel

class LikeProductFragment : Fragment() {

    // 원빈 - 좋아요 화면(코디네이터)

    lateinit var fragmentLikeProductBinding: FragmentLikeProductBinding
    lateinit var mainActivity: MainActivity

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        fragmentLikeProductBinding = FragmentLikeProductBinding.inflate(inflater)
        fragmentLikeProductBinding.lifecycleOwner = this

        mainActivity = activity as MainActivity

        // 리사이클러 뷰
        settingRecyclerViewLikeProduct()

        return fragmentLikeProductBinding.root
    }

    // 인기 코디네이터 리사이클러 뷰 설정
    fun settingRecyclerViewLikeProduct() {
        fragmentLikeProductBinding.apply {
            val screenWidthDp = resources.configuration.screenWidthDp
            if (screenWidthDp >= 600) {
                // 너비가 600dp 이상인 디바이스에서 실행될 동작
                recyclerViewLikeCoordi.apply {
                    // 어뎁터 및 레이아웃 매니저 설정
                    adapter = LikeProductRecyclerViewAdapter()
                    layoutManager = GridLayoutManager(mainActivity, 6)
                }
            } else {
                // 너비가 600dp 미만인 디바이스에서 실행될 동작
                recyclerViewLikeCoordi.apply {
                    // 어뎁터 및 레이아웃 매니저 설정
                    adapter = LikeProductRecyclerViewAdapter()
                    layoutManager = GridLayoutManager(mainActivity, 2)
                }
            }
        }
    }

    // 리사이클러 뷰 어뎁터
    inner class LikeProductRecyclerViewAdapter: RecyclerView.Adapter<LikeProductRecyclerViewAdapter.LikeProductViewHolder>(){
        inner class LikeProductViewHolder(rowLikeProductBinding: RowLikeProductBinding): RecyclerView.ViewHolder(rowLikeProductBinding.root){
            val rowLikeProductBinding: RowLikeProductBinding

            init {
                this.rowLikeProductBinding = rowLikeProductBinding

                this.rowLikeProductBinding.root.layoutParams = ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT
                )
            }
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LikeProductViewHolder {
            val rowLikeProductBinding = DataBindingUtil.inflate<RowLikeProductBinding>(
                layoutInflater, R.layout.row_like_product, parent, false
            )
            val rowLikeProductViewModel = RowLikeProductViewModel()
            rowLikeProductBinding.rowLikeProductViewModel = rowLikeProductViewModel
            rowLikeProductBinding.lifecycleOwner = this@LikeProductFragment

            val likeProductViewHolder = LikeProductViewHolder(rowLikeProductBinding)

            return likeProductViewHolder
        }

        override fun getItemCount(): Int {
            return 7
        }

        override fun onBindViewHolder(holder: LikeProductViewHolder, position: Int) {

            val rowLikeProductViewModel = RowLikeProductViewModel()

            // position 값에 따라 다른 이미지 설정
            val imageResource = when (position % 5) {
                0 -> R.drawable.iu_image
                1 -> R.drawable.iu_image2
                2 -> R.drawable.iu_image3
                3 -> R.drawable.iu_image4
                else -> R.drawable.iu_image5
            }
            holder.rowLikeProductBinding.itemMainLikeProductThumbnail.setImageResource(imageResource)

            // 리사이클러 뷰 항목 클릭
            holder.rowLikeProductBinding.root.setOnClickListener {
                mainActivity.replaceFragment(MainFragmentName.PRODUCT_FRAGMENT, true, true, null)
            }

            // 장바구니 담기 버튼 클릭
            holder.rowLikeProductBinding.buttonRowLikeProductCart.setOnClickListener {
                mainActivity.replaceFragment(MainFragmentName.CART_FRAGMENT, true, true, null)
            }
        }
    }
}