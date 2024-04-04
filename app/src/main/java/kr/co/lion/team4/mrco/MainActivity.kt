package kr.co.lion.team4.mrco

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.google.android.material.transition.MaterialSharedAxis
import kr.co.lion.team4.mrco.databinding.ActivityMainBinding
import kr.co.lion.team4.mrco.fragment.MainHomeFragment


class MainActivity : AppCompatActivity() {

    lateinit var activityMainBinding: ActivityMainBinding

    // 프레그먼트의 주소 값을 담을 프로퍼티
    var oldFragment: Fragment? = null
    var newFragment: Fragment? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        activityMainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(activityMainBinding.root)

        // 메인 홈 화면
        replaceFragment(MainFragmentName.MAIN_HOME, false, false, null)

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
            MainFragmentName.MAIN_HOME -> newFragment = MainHomeFragment()
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


    // 이 안보이게는 임시적으로 테스트하려고 씀
    // 하단 바 안보이게 하기
    fun removeBottomSheet(){
        activityMainBinding.apply {
            mainBottomNavi.isVisible = false
        }
    }
}