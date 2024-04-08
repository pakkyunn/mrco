package kr.co.lion.team4.mrco

import java.text.SimpleDateFormat
import java.util.Date

class Tools {

    companion object {

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

    LIKE_PRODUCT("Like_Product_Fragment"),
    LIKE_COORDINATOR("Like_Coordinator_Fragment"),

    WRITE_REVIEW("Write_Review_Fragment"),

    ORDER_DETAIL("Order_Detail_Fragment"),

    SALES_MANAGEMENT("Sales_Management_Fragment"),
    SALES_MANAGEMENT_CALENDAR("Sales_Management_Calendar_Fragment"),
    MANAGE_SHIPMENT_FRAGMENT("ManageShipmentsFragment"), // 판매자 - 배송관리 화면
    SALES_LIST_FRAGMENT("SalesListFragment"), // 판매 내역 화면

    APP_NOTICE_FRAGMENT("AppNoticeFragment"),

    CATEGORY_FRAGMENT("CategoryFragment"),

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
}