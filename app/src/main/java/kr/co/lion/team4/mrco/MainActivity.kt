package kr.co.lion.team4.mrco

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.google.android.material.transition.MaterialSharedAxis
import kr.co.lion.team4.mrco.databinding.ActivityMainBinding
import kr.co.lion.team4.mrco.fragment.AppNoticeFragment
import kr.co.lion.team4.mrco.fragment.CategoryFragment
import kr.co.lion.team4.mrco.fragment.CoordinatorMyPageFragment
import kr.co.lion.team4.mrco.fragment.CustomerServiceFragment
import kr.co.lion.team4.mrco.fragment.UserMyPageFragment

class MainActivity : AppCompatActivity() {

    lateinit var activityMainBinding: ActivityMainBinding

    // 프래그먼트의 주소값을 담을 프로퍼티
    var oldFragment:Fragment? = null
    var newFragment: Fragment? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        activityMainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(activityMainBinding.root)
    }


    fun replaceFragment(name:FragmentName, addToBackStack:Boolean, isAnimate:Boolean, data:Bundle?){
        val fragmentTransaction = supportFragmentManager.beginTransaction()

        if(newFragment != null){
            oldFragment = newFragment
        }

        when(name){
            FragmentName.APP_NOTICE_FRAGMENT -> { newFragment = AppNoticeFragment()}
            FragmentName.CATEGORY_FRAGMENT -> { newFragment = CategoryFragment()}
            FragmentName.COORDINATOR_MYPAGE_FRAGMENT -> { newFragment = CoordinatorMyPageFragment()}
            FragmentName.CUSTOMER_SERVICE_FRAGMENT -> { newFragment = CustomerServiceFragment()}
            FragmentName.USER_MYPAGE_FRAGMENT -> { newFragment = UserMyPageFragment()}
        }

        if(data != null){
            newFragment?.arguments = data
        }

        if(newFragment != null){
            if(isAnimate == true){
                if(oldFragment != null){
                    oldFragment?.exitTransition = MaterialSharedAxis(MaterialSharedAxis.X, true)
                    oldFragment?.reenterTransition = MaterialSharedAxis(MaterialSharedAxis.X, false)

                    oldFragment?.enterTransition = null
                    oldFragment?.returnTransition = null
                }
                newFragment?.enterTransition = MaterialSharedAxis(MaterialSharedAxis.X, true)
                newFragment?.returnTransition = MaterialSharedAxis(MaterialSharedAxis.X, false)

                newFragment?.exitTransition = null
                newFragment?.reenterTransition = null
            }

            fragmentTransaction.replace(R.id.fragmentContainerMain, newFragment!!)

            if(addToBackStack == true){
                fragmentTransaction.addToBackStack(name.str)
            }

            fragmentTransaction.commit()
        }
    }
    fun removeFragment(name:FragmentName){
        supportFragmentManager.popBackStack(name.str, FragmentManager.POP_BACK_STACK_INCLUSIVE)
    }
}