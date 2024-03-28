package kr.co.lion.team4.mrco

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kr.co.lion.team4.mrco.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    lateinit var activityMainBinding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        activityMainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(activityMainBinding.root)
    }
    //push test
}