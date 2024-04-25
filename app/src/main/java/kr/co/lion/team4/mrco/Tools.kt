package kr.co.lion.team4.mrco

import android.app.Activity
import android.content.Context
import android.content.DialogInterface
import android.content.res.ColorStateList
import android.graphics.Bitmap
import android.graphics.Color
import android.graphics.Matrix
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.RippleDrawable
import android.media.ExifInterface
import android.net.Uri
import android.os.Build
import android.os.SystemClock
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.FileProvider
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.MutableLiveData
import com.google.android.material.datepicker.CalendarConstraints
import com.google.android.material.datepicker.DateValidatorPointBackward
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import java.io.File
import java.io.FileOutputStream
import java.text.DecimalFormat
import java.text.SimpleDateFormat
import java.util.*
import kotlin.concurrent.thread

class Tools {
    companion object{

        // position 값에 따라 다른 MBTI 색상 설정
        fun mbtiColor(mbti: String): String {
            when (mbti) {
                "ENFJ" -> return "#DA9D00" // 끝
                "ENFP" -> return "#00387A" // 끝
                "ENTJ" -> return "#CA4646" // 끝
                "ENTP" -> return "#36C87C" // 끝
                "ESFJ" -> return "#13D4EF" // 끝
                "ESFP" -> return "#503778" // 끝
                "ESTJ" -> return "#41630A" // 끝
                "ESTP" -> return "#814011" // 끝
                "INFJ" -> return "#BDB14C" // 끝
                "INFP" -> return "#1434DC" // 끝
                "INTJ" -> return "#FF7373" // 끝
                "INTP" -> return "#B75AB6" // 끝
                "ISFJ" -> return "#50A399" // 끝
                "ISFP" -> return "#EAC816" // 끝
                "ISTJ" -> return "#4D2DCE" // 끝
                // else 는 ISTP 이다
                else -> return "#444444"
            }
        }

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

            // 배경색을 흰색으로 설정
            materialAlertDialogBuilder.setBackground(ContextCompat.getDrawable(context, R.drawable.dialog_background_white))

            materialAlertDialogBuilder.setTitle(title)
            materialAlertDialogBuilder.setMessage(message)
            materialAlertDialogBuilder.setPositiveButton("확인"){ dialogInterface: DialogInterface, i: Int ->
                showSoftInput(context, view)
            }

            // PositiveButton 가져오기
            val positiveButton = materialAlertDialogBuilder.show().getButton(DialogInterface.BUTTON_POSITIVE)

            // 버튼에 스타일 적용
            positiveButton.setTextColor(ContextCompat.getColor(context, R.color.gray))

            // Ripple 효과의 색상을 변경
            val rippleColor = ContextCompat.getColor(context, R.color.gray)
            val positiveButtonBackground = positiveButton.background as RippleDrawable
            positiveButtonBackground.setColor(ColorStateList.valueOf(rippleColor))
        }

        // DateRangerPicker
        fun setPeriodFromDateRagnePicker(fragmentManager: FragmentManager, periodStart:MutableLiveData<String>, periodEnd:MutableLiveData<String> ){
            // 날짜는 최대 오늘까지 선택할 수 있도록 선택 가능한 기간 설정
            val calendarConstraints = CalendarConstraints.Builder().setValidator(DateValidatorPointBackward.now()).build()
            val dateRangePicker = MaterialDatePicker.Builder.dateRangePicker()
                .setTitleText("조회기간을 설정해주세요")
                .setCalendarConstraints(calendarConstraints)
                .build()
            dateRangePicker.show(fragmentManager, "manageShipmentsPeriod")
            dateRangePicker.addOnPositiveButtonClickListener {
                // 시작일
                periodStart.value = getDateFromLongValue(it.first)
                // 종료일
                periodEnd.value = getDateFromLongValue(it.second)
            }
        }

        // dateRangePicker에서 선택된 날짜의 Long 값을 날짜로 변환
        fun getDateFromLongValue(date: Long) : String{
            val calendar = Calendar.getInstance()
            calendar.timeInMillis = date
            val date = SimpleDateFormat("yyyy-MM-dd").format(calendar.time)

            return date
        }

        // 가격(Int)을 1000 단위 표기법으로 변환
        // 100000 => return 100,000원
        fun gettingPriceDecimalFormat(price: Int) : String{
            val decimalFormat = DecimalFormat("#,###")
            val priceText = "${decimalFormat.format(price)}원"

            return priceText
        }

        ///////// 카메라 관련 /////////

        // 촬영된 사진이 저장될 경로를 구해서 반환하는 메서드
        // authorities : AndroidManifest.xml에 등록한 File Provider의 이름
        fun getPictureUri(context:Context, authorities:String):Uri{
            // 촬영한 사진이 저장될 경로
            // 외부 저장소 중에 애플리케이션 영역 경로를 가져온다.
            val rootPath = context.getExternalFilesDir(null).toString()
            // 이미지 파일명을 포함한 경로
            val picPath = "${rootPath}/tempImage.jpg"
            // File 객체 생성
            val file = File(picPath)
            // 사진이 저장된 위치를 관리할 Uri 생성
            val contentUri = FileProvider.getUriForFile(context, authorities, file)

            return contentUri
        }

        ///// 카메라, 앨범 공통 ////////
        // 사진의 회전 각도값을 반환하는 메서드
        // ExifInterface : 사진, 영상, 소리 등의 파일에 기록한 정보
        // 위치, 날짜, 조리개값, 노출 정도 등등 다양한 정보가 기록된다.
        // ExifInterface 정보에서 사진 회전 각도값을 가져와서 그만큼 다시 돌려준다.
        fun getDegree(context:Context, uri: Uri) : Int {
            // 사진 정보를 가지고 있는 객체 가져온다.
            var exifInterface: ExifInterface? = null


            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q){
                // 이미지 데이터를 가져올 수 있는 Content Provide의 Uri를 추출한다.
                // val photoUri = MediaStore.setRequireOriginal(uri)
                // ExifInterface 정보를 읽어올 스트림을 추출한다.

                val inputStream = context.contentResolver.openInputStream(uri)!!
                // ExifInterface 객체를 생성한다.
                exifInterface = ExifInterface(inputStream)
            } else {
                // ExifInterface 객체를 생성한다.
                exifInterface = ExifInterface(uri.path!!)
            }

            if(exifInterface != null){
                // 반환할 각도값을 담을 변수
                var degree = 0
                // ExifInterface 객체에서 회전 각도값을 가져온다.
                val ori = exifInterface.getAttributeInt(ExifInterface.TAG_ORIENTATION, -1)

                degree = when(ori){
                    ExifInterface.ORIENTATION_ROTATE_90 -> 90
                    ExifInterface.ORIENTATION_ROTATE_180 -> 180
                    ExifInterface.ORIENTATION_ROTATE_270 -> 270
                    else -> 0
                }

                return degree
            }

            return 0
        }

        // 회전시키는 메서드
        fun rotateBitmap(bitmap: Bitmap, degree:Float): Bitmap {
            // 회전 이미지를 생성하기 위한 변환 행렬
            val matrix = Matrix()
            matrix.postRotate(degree)

            // 회전 행렬을 적용하여 회전된 이미지를 생성한다.
            // 첫 번째 : 원본 이미지
            // 두 번째와 세번째 : 원본 이미지에서 사용할 부분의 좌측 상단 x, y 좌표
            // 네번째와 다섯번째 : 원본 이미지에서 사용할 부분의 가로 세로 길이
            // 여기에서는 이미지데이터 전체를 사용할 것이기 때문에 전체 영역으로 잡아준다.
            // 여섯번째 : 변환행렬. 적용해준 변환행렬이 무엇이냐에 따라 이미지 변형 방식이 달라진다.
            val rotateBitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.width, bitmap.height, matrix, false)

            return rotateBitmap
        }

        // 이미지 사이즈를 조정하는 메서드
        fun resizeBitmap(bitmap: Bitmap, targetWidth:Int): Bitmap {
            // 이미지의 확대/축소 비율을 구한다.
            val ratio = targetWidth.toDouble() / bitmap.width.toDouble()
            // 세로 길이를 구한다.
            val targetHeight = (bitmap.height * ratio).toInt()
            // 크기를 조장한 Bitmap을 생성한다.
            val resizedBitmap = Bitmap.createScaledBitmap(bitmap, targetWidth, targetHeight, false)

            return resizedBitmap
        }

        // 이미지뷰의 이미지를 추출해 로컬에 저장한다.
        fun saveImageViewIndividualItemData(context: Context, bitmap: Bitmap, fileName: String) {
            // 외부 저장소까지의 경로를 가져온다.
            val filePath = context.getExternalFilesDir(null).toString()

            // 로컬에 저장할 경로
            val file = File("${filePath}/${fileName}")
            // 스트림 추출
            val fileOutputStream = FileOutputStream(file)
            // 이미지를 저장한다.
            // 첫 번째 : 이미지 데이터 포멧(JPEG, PNG, WEBP_LOSSLESS, WEBP_LOSSY)
            // 두 번째 : 이미지의 퀄리티
            // 세 번째 : 이미지 데이터를 저장할 파일과 연결된 스트림
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fileOutputStream)
            fileOutputStream.flush()
            fileOutputStream.close()
        }






    }
}

// MainActivity에서 보여줄 프레그먼트들의 이름
enum class MainFragmentName(var str: String) {
    HOME_MAIN_FULL("HomeMainFullFragment"),
    HOME_RECOMMEND("HomeRecommendFragment"),
    HOME_MBTI("MainMbtiFragment"),
    HOME_COORDINATOR("HomeCoordinatorFragment"),
    HOME_COORDINATOR_INFO("Home_CoordinatorInfo_Fragment"),
    HOME_COORDINATOR_RANK("Home_CoordinatorRank_Fragment"),
    COORDINATOR_MAIN("CoordinatorMain_Fragment"),
    MBTI_PRODUCT_MAIN("MbtiProductMainFragment"),

    LIKE("LikeFragment"),
    LIKE_PRODUCT("Like_Product_Fragment"),
    LIKE_COORDINATOR("Like_Coordinator_Fragment"),

    MY_REVIEW("My_Review_Fragment"),
    WRITE_REVIEW("Write_Review_Fragment"),

    ORDER_DETAIL("Order_Detail_Fragment"),

    SALES_MANAGEMENT("Sales_Management_Fragment"),
    MANAGE_SHIPMENT_FRAGMENT("ManageShipmentsFragment"), // 판매자 - 배송관리 화면
    SALES_LIST_FRAGMENT("SalesListFragment"), // 판매 내역 화면

    APP_NOTICE_FRAGMENT("AppNoticeFragment"),

    CATEGORY_SEARCH_FRAGMENT("CategorySearchFragment"),
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
    FRAGMENT_PRODUCT_REVIEW("ProductReviewFragment"),
    FRAGMENT_REVIEW_CREATED("ReviewCreatedFragment"), // ProductReviewFragment - 작성한 리뷰 클릭시 나오는 화면
    FRAGMENT_MY_REVIEW("MyReviewFragment"),// 상품 리뷰 화면
    // 상품구매관련
    PRODUCT_FRAGMENT("ProductFragment"), // 코디상품 클릭시 나오는 화면
    PRODUCT_DETAIL_FRAGMENT("ProductDetailFragment"), // 코디 상세 버튼 클릭시 나오는 화면
    REVIEW_IMAGE_MORE_FRAGMENT("ReviewImageMoreFragment"), // 후기 탭의 후기 사진 더보기 버튼 클릭시 보이는 화면

    // 상민
    LOGIN_FRAGMENT("LoginFragment"),
    JOIN_FRAGMENT("JoinFragment"),
    MODIFY_USER_FRAGMENT("ModifyUserFragment"),
    JOIN_COORDINATOR_FRAGMENT("JoinCoordinatorFragment"),
    JOIN_COORDINATOR_NEXT_FRAGMENT("JoinCoordinatorNextFragment"),
    MODIFY_COORDINATOR_FRAGMENT("ModifyCoordinatorFragment"),
}
enum class SubFragmentName(var str: String) {
    PRODUCT_SHIPPING_FRAGMENT("ProductShippingFragment"), // 배송 탭
    PRODUCT_REVIEW_FRAGMENT("ProductReviewFragment"), // 후기 탭
    PRODUCT_QNA_FRAGMENT("ProductQnaFragment"), // 문의 탭
    REVIEW_IMAGE_MORE_FRAGMENT("ReviewImageMoreFragment"), // 후기 탭의 후기 사진 더보기 버튼 클릭시 보이는 화면
    REGISTER_PRODUCT_QNA_FRAGMENT("RegisterProductQnaFragment") // 문의 탭에서 상품 문의 남기기 클릭시 보이는 화면
}

enum class SalesManagementSubFragmentName(var str: String) {
    SALES_INVOICE_REPORT("SalesManagementInvoiceReportFragment"), // 리포트 탭
    SALES_CALENDAR("SalesManagementCalendarFragment"), // 캘린더 탭
    SALES_HISTORY("SalesManagementHistoryFragment"), // 정산 내역 탭
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

enum class CategoryId(var str: String){
    TPO("TPO"),
    SEASON("SEASON"),
    MOOD("MOOD"),
}

enum class CategoryIdSubTPO(var str: String, var strKor:String){
    DEFAULT("Default","기본"),
    TRAVEL("Travel","여행"),
    DATE("Date","데이트"),
    CAFE("Cafe","카페"),
    WORK("Work","출근"),
    DAILY("Daily","데일리"),
    CAMPUS("Campus","캠퍼스"),
    OCEAN("Ocean","바다"),
    WEDDING("Wedding","결혼식"),
}

enum class CategoryIdSubSEASON(var str: String, var strKor:String){
    DEFAULT("Default","기본"),
    SPRING("Spring","봄"),
    SUMMER("Summer","여름"),
    FALL("Fall","가을"),
    WINTER("Winter","겨울"),
}

enum class CategoryIdSubMOOD(var str: String, var strKor:String){
    DEFAULT("Default","기본"),
    MINIMAL("Minimal","미니멀"),
    BUSINESS("Business","비즈니스 캐주얼"),
    ONE_MILE("OneMile","원마일웨어"),
    AMECAJI("Amecaji","아메카지"),
    CITY_BOY("City Boy","시티보이"),
    STREET("Street","스트릿"),
    SPORTY("Sporty","스포티"),
    RETRO("Retro","레트로"),
    LOVELY("Lovely","러블리"),
    MODERN("Modern","모던 캐주얼")
}

enum class CodiMbti(var str: String){
    DEFAULT_MBTI("Default Mbti"),
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
    E("E"),
    I("I")
}

enum class MbtiSN(var str:String){
    S("S"),
    N("N")
}

enum class MbtiTF(var str:String){
    T("T"),
    F("F")
}

enum class MbtiPJ(var str:String){
    P("P"),
    J("J")
}

// 회원 상태를 나타내는 값을 정의한다
enum class UserState(var str:String, var num:Int){
    USER_STATE_NORMAL("정상", 1),
    USER_STATE_SIGNOUT("탈퇴", 2),
}

// 남자 또는 여자를 나타내는 값을 정의한다.
enum class Gender(var str:String, var num:Int){
    MALE("male", 1),
    FEMALE("female", 2)
}

// 상품 상태를 나타내는 값을 정의한다.
enum class ProductState(var str: String, var num: Int){
    PRODUCT_STATE_NORMAL("정상", 1),
    PRODUCT_STATE_DELETE("탈퇴",2),
}

// 검색 조회 기간
enum class InquiryPeriod(var num: Int){
    ONE_MONTH(-1),
    THREE_MONTHS(-3),
    SIX_MONTHS(-6)
}

// 주문 상태
enum class OrderState(var str: String, var num:Int){
    ORDERED("주문 접수", 0),
    IN_TRANSIT("배송중", 1),
    DELIVERED("배송완료", 2),
    ORDER_CONFIRM("구매 확정", 3),
    RETURN("반품 접수", 4),
    EXCHANGE("교환 접수", 5),
    CANCEL("주문 취소", 6),
}

// 배송 상태
enum class ShippingState(var str: String, var num:Int){
    READY_TO_SHIP("발송준비", 0),
    SHIPPED("배송시작", 1),
    IN_TRANSIT("배송중", 2),
    ARRIVE_SOON("도착예정", 3),
    DELIVERED("배송완료", 4),
}

// 리뷰 작성 상태
enum class ReviewState(var buttonText: String, var num: Int) {
    WAITING("리뷰 작성", 0), // 리뷰 작성 대기중
    COMPLETE("리뷰 보기", 1) // 리뷰 작성 완료
}