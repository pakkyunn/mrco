package kr.co.lion.team4.mrco

import android.app.Activity
import android.content.Context
import android.content.DialogInterface
import android.os.SystemClock
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import kotlin.concurrent.thread

class Tools {
    companion object{

        // 뷰에 포커스를 주고 키보드를 올린다.
        fun showSoftInput(context: Context, view: View){
            // 뷰에 포커스를 준다.
            view.requestFocus()
            thread {
                // 딜레이
                SystemClock.sleep(200)
                // 키보드 관리 객체를 가져온다.
                val inputMethodManger = context.getSystemService(AppCompatActivity.INPUT_METHOD_SERVICE) as
                        InputMethodManager
                // 키보드를 올린다.
                inputMethodManger.showSoftInput(view, 0)
            }
        }

        // 키보드를 내려주고 포커스를 제거한다.
        fun hideSoftInput(activity: Activity){
            // 포커스를 가지고 있는 뷰가 있다면..
            if(activity.window.currentFocus != null){
                // 키보드 관리 객체를 가져온다.
                val inputMethodManger = activity.getSystemService(AppCompatActivity.INPUT_METHOD_SERVICE) as
                        InputMethodManager
                // 키보드를 내려준다.
                inputMethodManger.hideSoftInputFromWindow(activity.window.currentFocus?.windowToken, 0)
                // 포커스를 제거해준다.
                activity.window.currentFocus?.clearFocus()
            }
        }

        // 입력 요소가 비어있을때 보여줄 다이얼로그를 구성하는 메서드
        fun showErrorDialog(context: Context, view: View, title:String, message:String){
            val materialAlertDialogBuilder = MaterialAlertDialogBuilder(context)
            materialAlertDialogBuilder.setTitle(title)
            materialAlertDialogBuilder.setMessage(message)
            materialAlertDialogBuilder.setPositiveButton("확인"){ dialogInterface: DialogInterface, i: Int ->
                showSoftInput(context, view)
            }
            materialAlertDialogBuilder.show()
        }

    }
}

// MainActivity에서 보여줄 프레그먼트들의 이름
enum class MainFragmentName(var str: String) {
//    MAIN_HOME("MainHomeFragment"),
    HOME_RECOMMEND("HomeRecommendFragment"),
    HOME_MBTI("MainMbtiFragment"),
    HOME_COORDINATOR_INFO("Home_CoordinatorInfo_Fragment"),
    HOME_COORDINATOR_RANK("Home_CoordinatorRank_Fragment"),
    COORDINATOR_MAIN("CoordinatorMain_Fragment"),
    MBTI_PRODUCT_MAIN("MbtiProductMainFragment"),

    LIKE_PRODUCT("Like_Product_Fragment"),
    LIKE_COORDINATOR("Like_Coordinator_Fragment"),

    MY_REVIEW("My_Review_Fragment"),
    WRITE_REVIEW("Write_Review_Fragment"),

    ORDER_DETAIL("Order_Detail_Fragment"),

    SALES_MANAGEMENT("Sales_Management_Fragment"),
    SALES_MANAGEMENT_CALENDAR("Sales_Management_Calendar_Fragment"),
    MANAGE_SHIPMENT_FRAGMENT("ManageShipmentsFragment"), // 판매자 - 배송관리 화면
    SALES_LIST_FRAGMENT("SalesListFragment"), // 판매 내역 화면

    APP_NOTICE_FRAGMENT("AppNoticeFragment"),

    CATEGORY_FRAGMENT("CategoryFragment"),
    CATEGORY_MAIN_FRAGMENT("CategoryMainFragment"),

    COORDINATOR_MYPAGE_FRAGMENT("CoordinatorMyPageFragment"),
    USER_MYPAGE_FRAGMENT("UserMyPageFragment"),

    CUSTOMER_SERVICE_FRAGMENT("CustomerServiceFragment"),
    CUSTOMER_INQUIRY_FRAGMENT("CustomerInquiryFragment"), // 고객센터 1:1 문의 작성 화면

    CART_FRAGMENT("CartFragment"), //장바구니

    ORDER_FRAGMENT("OrderFragment"), // 주문,결제 화면
    ORDER_HISTORY_FRAGMENT("OrderHistoryFragment"), // 구매자 주문내역 화면

    // 상품 1:1 문의
    REGISTER_PRODUCT_QNA_FRAGMENT("RegisterProductQnaFragment"), // 상품 1:1문의 작성화면
    PRODUCT_QNA_LIST_FRAGMENT("ProductQnaListFragment"), // 판매자 - 상품 문의 내역 화면
    REGISTER_QNA_ANSWER_FRAGMENT("RegisterQnaAnswerFragment"), // 상품 문의 답변 등록 화면

    ADD_PRODUCT_FRAGMENT("AddProductFragment"), // 상품등록 화면

    // product.codi 패키지
    FRAGMENT_CODI_PRODUCT_INFO("CodiPRoductInfo"), // 판매자 - 코디상품관리 탭 레이아웃 화면
    FRAGMENT_CODI_PRODUCT_INFO_ALL("CodiProductInfoAllFragment"), // 판매자 - 코디상품관리 상품 전체 화면
    FRAGMENT_CODI_PRODUCT_INFO_TOP("CodiProductInfoTopFragment"), // 판매자 - 코디사품관리 상의 화면
    FRAGMENT_CODI_PRODUCT_INFO_BOTTOM("CodiProductInfoBottomFragment"), // 판매자 - 코디상품관리 하의 화면
    FRAGMENT_CODI_PRODUCT_INFO_SHOES("CodiProductInfoShoesFragment"), // 판매자 - 코디상품관리 신발 화면
    FRAGMENT_CODI_PRODUCT_INFO_ACCESSORY("CodiProductInfoAccessoryFragment"), // 판매자 - 코디상품관리 악세서리 화면

    // product.individual 패키지
    FRAGMENT_INDIVIDUAL_PRODUCT_INFO("IndividualProductInfoFragment"), // 판매자 개별상품 정보 화면

    // product.management 패키지
    FRAGMENT_PRODUCT_MANAGEMENT("ProductManagementFragment"), // 등록상품관리 탭 레이아웃
    FRAGMENT_CODI_PRODUCT_MANAGEMENT("CodiProductManagementFragment"), // 코디상품관리 탭 클릭시 나오는 리스트 화면
    FRAGMENT_INDIVIDUAL_PRODUCT_MANAGEMENT("IndividualProductManagement"), // 개별상품관리 탭 클릭시 나오는 리스트 화면

    FRAGMENT_CREATE_REVIEW_FRAGMENT("CreateReviewFragment"), // ProductReviewFragment - 리뷰작성 클릭시 나오는 화면
    FRAGMENT_PRODUCT_REVIEW("ProductReviewFragment"), // 상품 리뷰 화면
    FRAGMENT_REVIEW_CREATED("ReviewCreatedFragment"), // ProductReviewFragment - 작성한 리뷰 클릭시 나오는 화면
    FRAGMENT_REVIEW("ReviewFragment"), // 작성한 리뷰

    
    // 상민
    LOGIN_FRAGMENT("LoginFragment"),
    JOIN_FRAGMENT("JoinFragment"),
    MODIFY_USER_FRAGMENT("ModifyUserFragment"),
    JOIN_COORDINATOR_FRAGMENT("JoinCoordinatorFragment"),
    JOIN_COORDINATOR_NEXT_FRAGMENT("JoinCoordinatorNextFragment"),
    MODIFY_COORDINATOR_FRAGMENT("ModifyCoordinatorFragment"),
}

enum class ProductSize(var ps: String){
    SIZE_XXXSMALL("XXXS"),
    SIZE_XXSMALL("XXS"),
    SIZE_XSMALL("XS"),
    SIZE_SMALL("S"),
    SIZE_MEDIUM("M"),
    SIZE_LARGE("L"),
    SIZE_XLARGE("XL"),
    SIZE_XXLARGE("XXL"),
    SIZE_XXXLARGE("XXXL")
}

enum class ProductColor(var pc: String){
    COLOR_BLACK("Black"),
    COLOR_WHITE("White")
}

enum class CategoryId(var cate: String){
    LOREM("Lorem")
}

enum class CodiMbti(var mbti: String){
    ENFP("ENFP"),
    ENFJ("ENFJ"),
    ENTJ("ENTJ"),
    ENTP("ENTP"),
    ESFP("ESFP"),
    ESFJ("ESFJ"),
    ESTP("ESTP"),
    ESTJ("ESTJ"),
    INFP("INFP"),
    INFJ("INFJ"),
    INTP("INTP"),
    INTJ("INTJ"),
    ISFP("ISFP"),
    ISFJ("ISFJ"),
    ISTP("ISTP"),
    ISTJ("ISTJ")
}


// 상민
enum class MbtiEI(var str:String){
    E("e"),
    I("i")
}

enum class MbtiSN(var str:String){
    S("s"),
    N("n")
}

enum class MbtiTF(var str:String){
    T("t"),
    F("f")
}

enum class MbtiPJ(var str:String){
    P("p"),
    J("j")
}
