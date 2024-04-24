package kr.co.lion.team4.mrco.fragment.like

import android.graphics.Color
import android.os.Bundle
import android.util.Log
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
import kr.co.lion.team4.mrco.Tools
import kr.co.lion.team4.mrco.dao.CoordinatorDao
import kr.co.lion.team4.mrco.dao.LikeDao
import kr.co.lion.team4.mrco.databinding.FragmentLikeProductBinding
import kr.co.lion.team4.mrco.databinding.RowLikeProductBinding
import kr.co.lion.team4.mrco.model.CoordinatorModel
import kr.co.lion.team4.mrco.model.LikeModel
import kr.co.lion.team4.mrco.model.ProductModel
import kr.co.lion.team4.mrco.viewmodel.like.LikeProductViewModel
import kr.co.lion.team4.mrco.viewmodel.like.RowLikeProductViewModel
import java.text.NumberFormat
import java.util.Locale

class LikeProductFragment : Fragment() {

    // 원빈 - 좋아요 화면(코디네이터)

    lateinit var fragmentLikeProductBinding: FragmentLikeProductBinding
    lateinit var mainActivity: MainActivity

    // 코디네이터 인덱스와 이름 정보를 담고 있을 맵
    var coordinatorMap = mutableMapOf<Int, String>()

    // 모든 회원의 코디네이터 팔로우 정보를 담고 있을 리스트
    var likeProductsList = mutableListOf<LikeModel>()

    // 로그인한 회원이 좋아요 한 상품의 인덱스 번호
    var productsLikeArray = mutableListOf<Int>()

    // 좋아요 한 상품의 정보를 담고 있을 리스트
    var productsLikeList = mutableListOf<ProductModel>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        fragmentLikeProductBinding = FragmentLikeProductBinding.inflate(inflater)
        fragmentLikeProductBinding.lifecycleOwner = this

        mainActivity = activity as MainActivity

        // 리사이클러 뷰
        settingRecyclerViewLikeProduct()

        // 데이터를 가져온다.
        gettingCoordinatorsFollowData()

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
                    layoutManager = GridLayoutManager(mainActivity, 4)
                }
            }
            else {
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
            return productsLikeArray.size
        }

        override fun onBindViewHolder(holder: LikeProductViewHolder, position: Int) {

            // position 값에 따라 다른 이미지 설정
            val imageResource = when (position % 5) {
                0 -> R.drawable.iu_image
                1 -> R.drawable.iu_image2
                2 -> R.drawable.iu_image3
                3 -> R.drawable.iu_image4
                else -> R.drawable.iu_image5
            }
            holder.rowLikeProductBinding.itemMainLikeProductThumbnail.setImageResource(imageResource)

            // 좋아요 상태 초기 세팅
            for (i in 0 until likeProductsList.size) {
                for (j in 0 until (likeProductsList[i].like_product_idx).size) {
                    if (likeProductsList[i].like_product_idx[j] == productsLikeList[position].productIdx) {
                        holder.rowLikeProductBinding.checkBoxRowLikeProductLike.apply {
                            isChecked = true
                        }
                    }
                }
            }

            // 하트 모양(좋아요) 버튼 클릭 시
            holder.rowLikeProductBinding.checkBoxRowLikeProductLike.setOnCheckedChangeListener { buttonView, isChecked ->
                if (isChecked) {
                    CoroutineScope(Dispatchers.Main).launch {
                        LikeDao.insertLikeProductData(mainActivity.loginUserIdx, productsLikeList[position].productIdx)
                    }
                } else {
                    CoroutineScope(Dispatchers.Main).launch {
                        LikeDao.deleteLikeProductData(mainActivity.loginUserIdx, productsLikeList[position].productIdx)
                    }
                }
            }

            holder.rowLikeProductBinding.textViewRowCoordinatorMainMBTI.setBackgroundColor(
                Color.parseColor(
                Tools.mbtiColor(productsLikeList[position].coordiMBTI)))
            holder.rowLikeProductBinding.textViewRowCoordinatorMainMBTI.text = "${productsLikeList[position].coordiMBTI}"
            // 해당 코디네이터의 이름
            holder.rowLikeProductBinding.itemMainLikeCoordinatorName.text =
                "${coordinatorMap[productsLikeList[position].coordinatorIdx]}"
            // 해당 코디 상품의 이름
            holder.rowLikeProductBinding.itemMainLikeProductName.text = "${productsLikeList[position].coordiName}"
            // 해당 코디 상품의 가격
            holder.rowLikeProductBinding.itemMainLikeProductPrice.text =
                "￦${NumberFormat.getNumberInstance(Locale.getDefault()).format(productsLikeList[position].price)}"

            // 해당 코디 상품의 할인률 0이면 표시안함
            if (productsLikeList[position].productDiscoutPrice == 0) {
                holder.rowLikeProductBinding.itemMainLikeProductDiscountPercent.text = ""
            } else {
                holder.rowLikeProductBinding.itemMainLikeProductDiscountPercent.text = "${productsLikeList[position].productDiscoutPrice}%  "
            }

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

    // 모든 상품의 좋아요 상태 데이터를 가져와 메인 화면의 RecyclerView를 갱신한다.
    fun gettingCoordinatorsFollowData() {
        CoroutineScope(Dispatchers.Main).launch {
            // MBTI와 성별에 맞는 상품의 정보를 가져온다. (연동 On)
            coordinatorMap = CoordinatorDao.getCoordinatorName()
            // 모든 코디네이터의 팔로우 상태 정보를 가져온다. (연동 On)
            likeProductsList = LikeDao.getLikeData(mainActivity.loginUserIdx)
            for (i in 0 until likeProductsList.size) {
                for (j in 0 until (likeProductsList[i].like_product_idx).size) {
                    productsLikeArray.add(likeProductsList[i].like_product_idx[j])
                }
            }
            productsLikeList = LikeDao.getProductInfo(productsLikeArray)
            Log.d("test1234", "Like 페이지(상품) - 좋아요 한 상품 정보 : ${productsLikeList}")
            fragmentLikeProductBinding.recyclerViewLikeCoordi.adapter?.notifyDataSetChanged()
        }
    }
}