package kr.co.lion.team4.mrco.fragment.product.purchase

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.transition.MaterialSharedAxis
import kr.co.lion.team4.mrco.MainActivity
import kr.co.lion.team4.mrco.MainFragmentName
import kr.co.lion.team4.mrco.R
import kr.co.lion.team4.mrco.SubFragmentName
import kr.co.lion.team4.mrco.databinding.FragmentReviewImageMoreBinding
import kr.co.lion.team4.mrco.databinding.RowProductReviewUserImageBinding
import kr.co.lion.team4.mrco.fragment.JoinCoordinatorFragment
import kr.co.lion.team4.mrco.fragment.JoinCoordinatorNextFragment
import kr.co.lion.team4.mrco.fragment.JoinFragment
import kr.co.lion.team4.mrco.fragment.LoginFragment
import kr.co.lion.team4.mrco.fragment.ModifyCoordinatorFragment
import kr.co.lion.team4.mrco.fragment.ModifyUserFragment
import kr.co.lion.team4.mrco.fragment.appNotice.AppNoticeFragment
import kr.co.lion.team4.mrco.fragment.cart.CartFragment
import kr.co.lion.team4.mrco.fragment.category.CategoryMainFragment
import kr.co.lion.team4.mrco.fragment.category.CategorySearchFragment
import kr.co.lion.team4.mrco.fragment.coordinatormain.CoordinatorMainFragment
import kr.co.lion.team4.mrco.fragment.customerService.CustomerInquiryFragment
import kr.co.lion.team4.mrco.fragment.customerService.CustomerServiceFragment
import kr.co.lion.team4.mrco.fragment.home.coordinator.CoordinatorInfoFragment
import kr.co.lion.team4.mrco.fragment.home.coordinator.CoordinatorRankFragment
import kr.co.lion.team4.mrco.fragment.home.coordinator.HomeCoordinatorFragment
import kr.co.lion.team4.mrco.fragment.home.coordinator.HomeMainFullFragment
import kr.co.lion.team4.mrco.fragment.home.coordinator.LikeFragment
import kr.co.lion.team4.mrco.fragment.home.mbti.HomeMbtiFragment
import kr.co.lion.team4.mrco.fragment.home.recommend.HomeRecommendFragment
import kr.co.lion.team4.mrco.fragment.like.LikeCoordinatorFragment
import kr.co.lion.team4.mrco.fragment.like.LikeProductFragment
import kr.co.lion.team4.mrco.fragment.mbtiproductmain.MbtiProductMainFragment
import kr.co.lion.team4.mrco.fragment.mypage.CoordinatorMyPageFragment
import kr.co.lion.team4.mrco.fragment.mypage.UserMyPageFragment
import kr.co.lion.team4.mrco.fragment.order.OrderDetailFragment
import kr.co.lion.team4.mrco.fragment.order.OrderFragment
import kr.co.lion.team4.mrco.fragment.order.OrderHistoryFragment
import kr.co.lion.team4.mrco.fragment.product.codi.CodiProductInfoAccessoryFragment
import kr.co.lion.team4.mrco.fragment.product.codi.CodiProductInfoAllFragment
import kr.co.lion.team4.mrco.fragment.product.codi.CodiProductInfoBottomFragment
import kr.co.lion.team4.mrco.fragment.product.codi.CodiProductInfoFragment
import kr.co.lion.team4.mrco.fragment.product.codi.CodiProductInfoShoesFragment
import kr.co.lion.team4.mrco.fragment.product.codi.CodiProductInfoTopFragment
import kr.co.lion.team4.mrco.fragment.product.individual.IndividualProductInfoFragment
import kr.co.lion.team4.mrco.fragment.product.management.CodiProductMangementFragment
import kr.co.lion.team4.mrco.fragment.product.management.IndividualProductManagementFragment
import kr.co.lion.team4.mrco.fragment.product.management.ProductManagementFragment
import kr.co.lion.team4.mrco.fragment.productManagement.AddProductFragment
import kr.co.lion.team4.mrco.fragment.productQna.ProductQnaListFragment
import kr.co.lion.team4.mrco.fragment.productQna.RegisterProductQnaFragment
import kr.co.lion.team4.mrco.fragment.productQna.RegisterQnaAnswerFragment
import kr.co.lion.team4.mrco.fragment.review.CreateReviewFragment
import kr.co.lion.team4.mrco.fragment.review.MyReviewFragment
import kr.co.lion.team4.mrco.fragment.review.ReviewCreatedFragment
import kr.co.lion.team4.mrco.fragment.review.WriteReviewFragment
import kr.co.lion.team4.mrco.fragment.salesManagement.ManageShipmentsFragment
import kr.co.lion.team4.mrco.fragment.salesManagement.SalesListFragment
import kr.co.lion.team4.mrco.fragment.salesManagement.SalesManagementFragment

class ReviewImageMoreFragment : Fragment() {

    lateinit var fragmentReviewImageMoreBinding: FragmentReviewImageMoreBinding
    lateinit var mainActivity: MainActivity

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        fragmentReviewImageMoreBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_review_image_more, container, false)
        fragmentReviewImageMoreBinding.lifecycleOwner = this

        mainActivity = activity as MainActivity

        settingRecyclerViewImageMore()
        settingToolbar()

        return fragmentReviewImageMoreBinding.root
    }

    fun settingToolbar(){
        fragmentReviewImageMoreBinding.apply {
            toolbarReviewImageMore.apply {
                inflateMenu(R.menu.menu_close_toolbar)
                setOnMenuItemClickListener {

                    when (it.itemId) {
                        R.id.main_toolbar_close -> {
                           removeFragment(SubFragmentName.REVIEW_IMAGE_MORE_FRAGMENT)
                        }
                    }
                    true
                }
            }
        }
    }

    // 상품 후기 사진 전체 RecyclerView 구성
    fun settingRecyclerViewImageMore() {
        fragmentReviewImageMoreBinding.apply {
            recyclerViewReviewImageMore.apply {
                // 어뎁터
                adapter = ReviewImageMoreRecyclerViewAdapter()
                // 레이아웃 매니저
                layoutManager = GridLayoutManager(mainActivity, 4)
            }
        }
    }

    inner class ReviewImageMoreRecyclerViewAdapter :
        RecyclerView.Adapter<ReviewImageMoreRecyclerViewAdapter.ReviewImageMoreViewHolder>() {
        inner class ReviewImageMoreViewHolder(rowProductReviewUserImageBinding: RowProductReviewUserImageBinding) :
            RecyclerView.ViewHolder(rowProductReviewUserImageBinding.root) {
            val rowProductReviewUserImageBinding: RowProductReviewUserImageBinding

            init {
                this.rowProductReviewUserImageBinding = rowProductReviewUserImageBinding

                this.rowProductReviewUserImageBinding.root.layoutParams = ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT
                )
            }
        }

        override fun onCreateViewHolder(
            parent: ViewGroup,
            viewType: Int
        ): ReviewImageMoreViewHolder {
            val rowProductReviewUserImageBinding =
                DataBindingUtil.inflate<RowProductReviewUserImageBinding>(
                    layoutInflater,
                    R.layout.row_product_review_user_image,
                    parent,
                    false
                )
            rowProductReviewUserImageBinding.lifecycleOwner = this@ReviewImageMoreFragment

            val ReviewImageMoreViewHolder =
                ReviewImageMoreViewHolder(rowProductReviewUserImageBinding)
            return ReviewImageMoreViewHolder
        }

        override fun getItemCount(): Int {
            return 50
        }

        override fun onBindViewHolder(holder: ReviewImageMoreViewHolder, position: Int) {

        }
    }

    fun removeFragment(name: SubFragmentName) {
        // 지정한 이름으로 있는 Fragment를 BackStack에서 제거한다.
        mainActivity.supportFragmentManager.popBackStack(
            name.str,
            FragmentManager.POP_BACK_STACK_INCLUSIVE
        )
    }

}