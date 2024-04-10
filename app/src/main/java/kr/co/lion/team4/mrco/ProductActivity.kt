package kr.co.lion.team4.mrco

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import kr.co.lion.team4.mrco.databinding.ActivityMainBinding
import kr.co.lion.team4.mrco.databinding.ActivityProductBinding

class ProductActivity : AppCompatActivity() {

    lateinit var activityProductBinding: ActivityProductBinding
    lateinit var mainActivity: MainActivity

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        activityProductBinding = ActivityProductBinding.inflate(layoutInflater)
        setContentView(activityProductBinding.root)

        settingToolbar()
    }

    // 툴바 세팅(메인 / 검색, 알림, 장바구니)
    fun settingToolbar() {
        activityProductBinding.apply {
            productToolbar.apply {
                // 네비게이션
                setNavigationIcon(R.drawable.arrow_back_24px)
                setNavigationOnClickListener {
                    startMainActivity()
                }
            }
        }
    }

    // MainActivity 실행
    fun startMainActivity(){
        // MainActivity 실행하고 현재 Acrivity는 종료한다.
        val mainIntent = Intent(this, MainActivity::class.java)
        startActivity(mainIntent)

        finish()
    }
}