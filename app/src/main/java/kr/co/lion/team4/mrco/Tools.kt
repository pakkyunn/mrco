package kr.co.lion.team4.mrco

import java.text.SimpleDateFormat
import java.util.Date

class Tools {

    companion object {

    }

}

// MainActivity에서 보여줄 프레그먼트들의 이름
enum class MainFragmentName(var str: String) {
    MAIN_HOME("MainHomeFragment"),
    HOME_COORDINATOR_INFO("Home_CoordinatorInfo_Fragment"),
    HOME_COORDINATOR_RANK("Home_CoordinatorRank_Fragment"),
    COORDINATOR_MAIN("CoordinatorMain_Fragment"),

    LIKE_PRODUCT("Like_Product_Fragment"),
    LIKE_COORDINATOR("Like_Coordinator_Fragment"),

    WRITE_REVIEW("Write_Review_Fragment"),

    ORDER_DETAIL("Order_Detail_Fragment"),

    SALES_MANAGEMENT("Sales_Management_Fragment"),
    SALES_MANAGEMENT_CALENDAR("Sales_Management_Calendar_Fragment"),
  
//    APP_NOTICE_FRAGMENT("AppNoticeFragment"),
//    CATEGORY_FRAGMENT("CategoryFragment"),
//    COORDINATOR_MYPAGE_FRAGMENT("CoordinatorMyPageFragment"),
//    CUSTOMER_SERVICE_FRAGMENT("CustomerServiceFragment"),
//    USER_MYPAGE_FRAGMENT("UserMyPageFragment"),
}