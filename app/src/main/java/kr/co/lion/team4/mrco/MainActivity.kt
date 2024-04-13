package kr.co.lion.team4.mrco

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.google.android.material.transition.MaterialSharedAxis
import kr.co.lion.team4.mrco.databinding.ActivityMainBinding
import kr.co.lion.team4.mrco.fragment.JoinCoordinatorFragment
import kr.co.lion.team4.mrco.fragment.JoinCoordinatorNextFragment
import kr.co.lion.team4.mrco.fragment.JoinFragment
import kr.co.lion.team4.mrco.fragment.LoginFragment
import kr.co.lion.team4.mrco.fragment.ModifyCoordinatorFragment
import kr.co.lion.team4.mrco.fragment.ModifyUserFragment
import kr.co.lion.team4.mrco.fragment.appNotice.AppNoticeFragment
import kr.co.lion.team4.mrco.fragment.cart.CartFragment
import kr.co.lion.team4.mrco.fragment.category.CategoryFragment
import kr.co.lion.team4.mrco.fragment.category.CategoryMainFragment
import kr.co.lion.team4.mrco.fragment.home.coordinator.CoordinatorInfoFragment
import kr.co.lion.team4.mrco.fragment.coordinatormain.CoordinatorMainFragment
import kr.co.lion.team4.mrco.fragment.customerService.CustomerInquiryFragment
import kr.co.lion.team4.mrco.fragment.home.coordinator.CoordinatorRankFragment
import kr.co.lion.team4.mrco.fragment.customerService.CustomerServiceFragment
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
import kr.co.lion.team4.mrco.fragment.product.purchase.ProductReviewFragment
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
import kr.co.lion.team4.mrco.fragment.product.purchase.ProductDetailFragment
import kr.co.lion.team4.mrco.fragment.product.purchase.ProductFragment
import kr.co.lion.team4.mrco.fragment.product.purchase.ProductQnaFragment
import kr.co.lion.team4.mrco.fragment.product.purchase.ProductShippingFragment
import kr.co.lion.team4.mrco.fragment.productManagement.AddProductFragment
import kr.co.lion.team4.mrco.fragment.productQna.ProductQnaListFragment
import kr.co.lion.team4.mrco.fragment.productQna.RegisterProductQnaFragment
import kr.co.lion.team4.mrco.fragment.productQna.RegisterQnaAnswerFragment
import kr.co.lion.team4.mrco.fragment.review.CreateReviewFragment
import kr.co.lion.team4.mrco.fragment.review.ReviewCreatedFragment
import kr.co.lion.team4.mrco.fragment.review.ReviewFragment
import kr.co.lion.team4.mrco.fragment.salesManagement.SalesManagementCalendarFragment
import kr.co.lion.team4.mrco.fragment.salesManagement.SalesManagementFragment
import kr.co.lion.team4.mrco.fragment.review.WriteReviewFragment
import kr.co.lion.team4.mrco.fragment.salesManagement.ManageShipmentsFragment
import kr.co.lion.team4.mrco.fragment.salesManagement.SalesListFragment

class MainActivity : AppCompatActivity() {

    lateinit var activityMainBinding: ActivityMainBinding

    // 프레그먼트의 주소 값을 담을 프로퍼티
    var oldFragment: Fragment? = null
    var newFragment: Fragment? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        activityMainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(activityMainBinding.root)

        // Status 바 (최상단 / 툴바 위)
        // window.statusBarColor = ContextCompat.getColor(this, R.color.black)

        // 하단 바 설정(이동 관련)
        bottomSheetSetting()

        // 로그인부터 시작 - 테스트
        replaceFragment(MainFragmentName.LOGIN_FRAGMENT, false, false, null)

        // 홈 화면(추천, MBTI 별 코디, 인기 코디네이터, 코디네이터 소개, 코디네이터 메인) - (원빈)완료
        // 탭으로 화면 이동 가능
        // replaceFragment(MainFragmentName.HOME_RECOMMEND, false, false, null)
        // replaceFragment(MainFragmentName.HOME_MBTI, false, false, null)
        // replaceFragment(MainFragmentName.HOME_COORDINATOR_RANK, false, false, null)
        // replaceFragment(MainFragmentName.HOME_COORDINATOR_INFO, false, false, null)
        // replaceFragment(MainFragmentName.MBTI_PRODUCT_MAIN, true, true, null)
        // replaceFragment(MainFragmentName.COORDINATOR_MAIN, true, true, null)

        // 좋아요 화면(코디네이터) - (원빈)완료
        // 탭으로 화면 이동 가능 / 코디화면, 코디네이터 화면
        // replaceFragment(MainFragmentName.LIKE_COORDINATOR, false, false, null)
        // replaceFragment(MainFragmentName.LIKE_PRODUCT, false, false, null)

        // 리뷰 작성 페이지 - (원빈)완료
        // replaceFragment(MainFragmentName.WRITE_REVIEW, true, true, null)

        // 주문 상세 정보 페이지 - (원빈)완료
        // replaceFragment(MainFragmentName.ORDER_DETAIL, true, true, null)

        // 매출관리(내역), 매출관리(캘린더) - (원빈)완료
        // 탭으로 화면 이동 가능 / 리포트는 현재 연동 X
        // replaceFragment(MainFragmentName.SALES_MANAGEMENT, false, false, null)
        // replaceFragment(MainFragmentName.SALES_MANAGEMENT_CALENDAR, false, false, null)

        // 알림 - 현준(완료)
        // replaceFragment(MainFragmentName.APP_NOTICE_FRAGMENT, false, false, null)

        // 카테고리 - 현준(완료)
        // replaceFragment(MainFragmentName.CATEGORY_FRAGMENT, false, false, null)

        // 코디네이터 마이 페이지 - 현준(완료)
        // replaceFragment(MainFragmentName.COORDINATOR_MYPAGE_FRAGMENT, false, false, null)

        // 사용자 마이 페이지 - 현준(완료)
        // replaceFragment(MainFragmentName.USER_MYPAGE_FRAGMENT, false, false, null)

        // 고객센터 - 현준(완료)
        // replaceFragment(MainFragmentName.CUSTOMER_SERVICE_FRAGMENT, false, false, null)
    }

    // 지정한 Fragment를 보여주는 메서드
    fun replaceFragment(name: MainFragmentName, addToBackStack: Boolean, isAnimate: Boolean, data: Bundle?) {

        // SystemClock.sleep(50)

        // Fragment를 교체할 수 있는 객체를 추출한다.
        val fragmentTransaction = supportFragmentManager.beginTransaction()

        // oldFragment에 newFragment가 가지고 있는 Fragment 객체를 담아준다.
        if(newFragment != null){
            oldFragment = newFragment
        }

        when(name){
            // 홈 메인 (첫 화면 - 추천, MBTI 별 코디, 인기 코디네이터, 코디네이터 소개) 순서
            MainFragmentName.HOME_RECOMMEND, -> newFragment = HomeRecommendFragment()
            MainFragmentName.HOME_MBTI, -> newFragment = HomeMbtiFragment()
            MainFragmentName.HOME_COORDINATOR_RANK -> newFragment = CoordinatorRankFragment()
            MainFragmentName.HOME_COORDINATOR_INFO -> newFragment = CoordinatorInfoFragment()

            // 메인 MBTI 상품 페이지 와 메인 코디네이터 상세 페이지
            MainFragmentName.MBTI_PRODUCT_MAIN -> newFragment = MbtiProductMainFragment()
            MainFragmentName.COORDINATOR_MAIN -> newFragment = CoordinatorMainFragment()

            // 좋아요 화면 (코디, 코디네이터)
            MainFragmentName.LIKE_PRODUCT -> newFragment = LikeProductFragment()
            MainFragmentName.LIKE_COORDINATOR -> newFragment = LikeCoordinatorFragment()

            // 상품 구매 화면
            MainFragmentName.PRODUCT_FRAGMENT -> newFragment = ProductFragment()
            MainFragmentName.PRODUCT_DETAIL_FRAGMENT -> newFragment = ProductDetailFragment()

            // 리뷰 작성 화면
            MainFragmentName.WRITE_REVIEW -> newFragment = WriteReviewFragment()

            // 주문 상세 정보
            MainFragmentName.ORDER_DETAIL -> newFragment = OrderDetailFragment()

            // 매출관리(내역) 화면
            MainFragmentName.SALES_MANAGEMENT -> newFragment = SalesManagementFragment()

            // 매출관리(캘린더) 화면
            MainFragmentName.SALES_MANAGEMENT_CALENDAR -> newFragment = SalesManagementCalendarFragment()

            // 판매자 - 배송관리 화면
            MainFragmentName.MANAGE_SHIPMENT_FRAGMENT -> newFragment = ManageShipmentsFragment()

            // 판매자 - 판매 내역 관리 화면
            MainFragmentName.SALES_LIST_FRAGMENT -> newFragment = SalesListFragment()

            // 카테고리 메인 화면
            MainFragmentName.CATEGORY_MAIN_FRAGMENT -> newFragment = CategoryMainFragment()


            // 알림 화면
            MainFragmentName.APP_NOTICE_FRAGMENT -> newFragment = AppNoticeFragment()

            // 카테고리 선택 화면
            MainFragmentName.CATEGORY_FRAGMENT -> newFragment = CategoryFragment()

            // 마이페이지 (코디네이터) 화면
            MainFragmentName.COORDINATOR_MYPAGE_FRAGMENT -> newFragment = CoordinatorMyPageFragment()

            // 마이페이지 (사용자) 화면
            MainFragmentName.USER_MYPAGE_FRAGMENT -> newFragment = UserMyPageFragment()

            // 고객센터 화면
            MainFragmentName.CUSTOMER_SERVICE_FRAGMENT -> newFragment = CustomerServiceFragment()


            // 고객센터 1:1 문의 작성 화면
            MainFragmentName.CUSTOMER_INQUIRY_FRAGMENT -> newFragment = CustomerInquiryFragment()

            // 장바구니 화면
            MainFragmentName.CART_FRAGMENT -> newFragment = CartFragment()

            // 주문 및 결제 화면
            MainFragmentName.ORDER_FRAGMENT -> newFragment = OrderFragment()

            // 구매자 주문내역 화면
            MainFragmentName.ORDER_HISTORY_FRAGMENT -> newFragment = OrderHistoryFragment()

            // 상품 1:1문의 작성화면
            MainFragmentName.REGISTER_PRODUCT_QNA_FRAGMENT -> newFragment = RegisterProductQnaFragment()

            // 판매자 - 상품 문의 내역 화면
            MainFragmentName.PRODUCT_QNA_LIST_FRAGMENT -> newFragment = ProductQnaListFragment()

            // 상품 문의 답변 등록 화면
            MainFragmentName.REGISTER_QNA_ANSWER_FRAGMENT -> newFragment = RegisterQnaAnswerFragment()

            // 판매자 - 코디 상품등록 화면
            MainFragmentName.ADD_PRODUCT_FRAGMENT -> newFragment = AddProductFragment()

            MainFragmentName.FRAGMENT_CODI_PRODUCT_INFO -> newFragment = CodiProductInfoFragment()

            MainFragmentName.FRAGMENT_CODI_PRODUCT_INFO_ALL -> newFragment = CodiProductInfoAllFragment()

            MainFragmentName.FRAGMENT_CODI_PRODUCT_INFO_TOP -> newFragment = CodiProductInfoTopFragment()

            MainFragmentName.FRAGMENT_CODI_PRODUCT_INFO_BOTTOM -> newFragment = CodiProductInfoBottomFragment()

            MainFragmentName.FRAGMENT_CODI_PRODUCT_INFO_SHOES -> newFragment = CodiProductInfoShoesFragment()

            MainFragmentName.FRAGMENT_CODI_PRODUCT_INFO_ACCESSORY -> newFragment = CodiProductInfoAccessoryFragment()

            MainFragmentName.FRAGMENT_PRODUCT_MANAGEMENT -> newFragment = ProductManagementFragment()

            MainFragmentName.FRAGMENT_INDIVIDUAL_PRODUCT_MANAGEMENT -> newFragment = IndividualProductManagementFragment()

            MainFragmentName.FRAGMENT_CODI_PRODUCT_MANAGEMENT -> newFragment = CodiProductMangementFragment()

            MainFragmentName.FRAGMENT_REVIEW_CREATED -> newFragment = ReviewCreatedFragment()

            MainFragmentName.FRAGMENT_PRODUCT_REVIEW -> newFragment = ProductReviewFragment()

            MainFragmentName.FRAGMENT_CREATE_REVIEW_FRAGMENT -> newFragment = CreateReviewFragment()

            MainFragmentName.FRAGMENT_INDIVIDUAL_PRODUCT_INFO -> newFragment = IndividualProductInfoFragment()

            MainFragmentName.FRAGMENT_REVIEW -> newFragment = ReviewFragment()


            // 상민
            MainFragmentName.LOGIN_FRAGMENT -> newFragment = LoginFragment()

            MainFragmentName.JOIN_FRAGMENT -> newFragment = JoinFragment()

            MainFragmentName.MODIFY_USER_FRAGMENT -> newFragment = ModifyUserFragment()

            MainFragmentName.JOIN_COORDINATOR_FRAGMENT -> newFragment = JoinCoordinatorFragment()

            MainFragmentName.JOIN_COORDINATOR_NEXT_FRAGMENT -> newFragment = JoinCoordinatorNextFragment()

            MainFragmentName.MODIFY_COORDINATOR_FRAGMENT -> newFragment = ModifyCoordinatorFragment()
        }

        // 새로운 Fragment에 전달할 객체가 있다면 arguments 프로퍼티에 넣어준다.
        if(data != null){
            newFragment?.arguments = data
        }

        if(newFragment != null){

            // 애니메이션 설정
            if(isAnimate == true){

                if(oldFragment != null){
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
            fragmentTransaction.replace(R.id.containerMain, newFragment!!)

            // addToBackStack 변수의 값이 true면 새롭게 보여질 Fragment를 BackStack에 포함시켜 준다.
            if(addToBackStack == true){
                // BackStack 포함 시킬때 이름을 지정해주면 원하는 Fragment를 BackStack에서 제거할 수 있다.
                fragmentTransaction.addToBackStack(name.str)
            }
            // Fragment 교체를 확정한다.
            fragmentTransaction.commit()
        }
    }


    // BackStack에서 Fragment를 제거한다.
    fun removeFragment(name: MainFragmentName){
        // SystemClock.sleep(50)

        // 지정한 이름으로 있는 Fragment를 BackStack에서 제거한다.
        supportFragmentManager.popBackStack(name.str, FragmentManager.POP_BACK_STACK_INCLUSIVE)
    }

    // 하단 바 안보이게 하기
    fun removeBottomSheet(){
        activityMainBinding.apply {
            mainBottomNavi.isVisible = false
        }
    }

    // 하단 바 보이게 하기
    fun viewBottomSheet(){
        activityMainBinding.apply {
            mainBottomNavi.isVisible = true
        }
    }

    // 하단 바 설정
    fun bottomSheetSetting(){
        activityMainBinding.apply {
            mainBottomNavi.setOnItemSelectedListener { item ->
                when(item.itemId) {
                    R.id.main_bottom_navi_home -> {
                        replaceFragment(MainFragmentName.HOME_RECOMMEND, false, false, null)
                    }
                    R.id.main_bottom_navi_category -> {
                        replaceFragment(MainFragmentName.CATEGORY_FRAGMENT, false, false, null)
                    }
                    R.id.main_bottom_navi_like -> {
                        replaceFragment(MainFragmentName.LIKE_PRODUCT, false, false, null)
                    }
                    else -> {
                        replaceFragment(MainFragmentName.USER_MYPAGE_FRAGMENT, false, false, null)
                    }
                }
                true
            }
        }
    }
}