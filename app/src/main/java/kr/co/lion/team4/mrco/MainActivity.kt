package kr.co.lion.team4.mrco

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.google.android.material.transition.MaterialSharedAxis
import kr.co.lion.team4.mrco.databinding.ActivityMainBinding
import kr.co.lion.team4.mrco.fragment.coordinator.CoordinatorInfoFragment
import kr.co.lion.team4.mrco.fragment.coordinator.CoordinatorMainFragment
import kr.co.lion.team4.mrco.fragment.coordinator.CoordinatorRankFragment
import kr.co.lion.team4.mrco.fragment.like.LikeCoordinatorFragment
import kr.co.lion.team4.mrco.fragment.like.LikeProductFragment
import kr.co.lion.team4.mrco.fragment.order.OrderDetailFragment
import kr.co.lion.team4.mrco.fragment.salesManagement.SalesManagementCalendarFragment
import kr.co.lion.team4.mrco.fragment.salesManagement.SalesManagementFragment
import kr.co.lion.team4.mrco.fragment.review.WriteReviewFragment

class MainActivity : AppCompatActivity() {

    lateinit var activityMainBinding: ActivityMainBinding

    // 프레그먼트의 주소 값을 담을 프로퍼티
    var oldFragment: Fragment? = null
    var newFragment: Fragment? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        activityMainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(activityMainBinding.root)

        // 인기 코디네이터, 코디네이터 소개, 코디네이터 메인 - (원빈)완료
        // 탭으로 화면 이동 가능 / 코디네이터 메인은 코디네이터 소개에서 하나 클릭하면 이동가능
        // replaceFragment(MainFragmentName.HOME_COORDINATOR_RANK, false, false, null)
        // replaceFragment(MainFragmentName.HOME_COORDINATOR_INFO, false, false, null)
        // replaceFragment(MainFragmentName.COORDINATOR_MAIN, true, true, null)

        // 좋아요 화면(코디네이터) - (원빈)완료
        // 탭으로 화면 이동 가능 / 코디화면, 코디네이터 화면
        // replaceFragment(MainFragmentName.LIKE_COORDINATOR, false, false, null)
        replaceFragment(MainFragmentName.LIKE_PRODUCT, false, false, null)

        // 리뷰 작성 페이지 - (원빈)완료
        // replaceFragment(MainFragmentName.WRITE_REVIEW, true, true, null)

        // 주문 상세 정보 페이지 - (원빈)완료
        // replaceFragment(MainFragmentName.ORDER_DETAIL, true, true, null)

        // 매출관리(내역), 매출관리(캘린더) - (원빈)완료
        // 탭으로 화면 이동 가능 / 리포트는 현재 연동 X
        // replaceFragment(MainFragmentName.SALES_MANAGEMENT, false, false, null)
        // replaceFragment(MainFragmentName.SALES_MANAGEMENT_CALENDAR, false, false, null)
    }

    // 지정한 Fragment를 보여주는 메서드
    fun replaceFragment(name: MainFragmentName, addToBackStack: Boolean, isAnimate: Boolean, data: Bundle?){

        // SystemClock.sleep(50)

        // Fragment를 교체할 수 있는 객체를 추출한다.
        val fragmentTransaction = supportFragmentManager.beginTransaction()

        // oldFragment에 newFragment가 가지고 있는 Fragment 객체를 담아준다.
        if(newFragment != null){
            oldFragment = newFragment
        }

        when(name){
            // 코디네이터 소개
            MainFragmentName.HOME_COORDINATOR_INFO -> newFragment = CoordinatorInfoFragment()

            // 인기 코디네이터
            MainFragmentName.HOME_COORDINATOR_RANK -> newFragment = CoordinatorRankFragment()

            // 메인 코디네이터 상세 페이지
            MainFragmentName.COORDINATOR_MAIN -> newFragment = CoordinatorMainFragment()

            // 좋아요 화면 (코디)
            MainFragmentName.LIKE_PRODUCT -> newFragment = LikeProductFragment()

            // 좋아요 화면 (코디네이터)
            MainFragmentName.LIKE_COORDINATOR -> newFragment = LikeCoordinatorFragment()

            // 리뷰 작성 화면
            MainFragmentName.WRITE_REVIEW -> newFragment = WriteReviewFragment()

            // 주문 상세 정보
            MainFragmentName.ORDER_DETAIL -> newFragment = OrderDetailFragment()

            // 매출관리(내역)
            MainFragmentName.SALES_MANAGEMENT -> newFragment = SalesManagementFragment()

            // 매출관리(캘린더)
            MainFragmentName.SALES_MANAGEMENT_CALENDAR -> newFragment = SalesManagementCalendarFragment()

            // 추가 할 화면
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
}