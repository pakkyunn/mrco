package kr.co.lion.team4.mrco

import android.content.Intent
import android.os.Bundle
import android.view.ViewGroup
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.PagerSnapHelper
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.SnapHelper
import kr.co.lion.team4.mrco.databinding.ActivityMainBinding
import kr.co.lion.team4.mrco.databinding.ActivityProductBinding
import kr.co.lion.team4.mrco.databinding.RowHomeRecommendBannerBinding
import kr.co.lion.team4.mrco.databinding.RowProductBannerBinding
import kr.co.lion.team4.mrco.viewmodel.home.recommend.RowHomeRecommendBannerViewModel

class ProductActivity : AppCompatActivity() {

    lateinit var activityProductBinding: ActivityProductBinding
    lateinit var mainActivity: MainActivity

    lateinit var snapHelper: SnapHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        activityProductBinding = ActivityProductBinding.inflate(layoutInflater)
        setContentView(activityProductBinding.root)

        // 툴바
        settingToolbar()

        // 리사이클러 뷰
        settingRecyclerViewHomeRecommendBanner()
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

    //  Product - 배너 리사이클러 뷰 설정
    fun settingRecyclerViewHomeRecommendBanner() {
        activityProductBinding.apply {
            recyclerViewProductBanner.apply {
                // 어뎁터 및 레이아웃 매니저 설정
                adapter = ProductBannerRecyclerViewAdapter()

                snapHelper = PagerSnapHelper()
                snapHelper.attachToRecyclerView(this)
            }
        }
    }

    // Product - 배너 리사이클러 뷰 어뎁터
    inner class ProductBannerRecyclerViewAdapter: RecyclerView.Adapter<ProductBannerRecyclerViewAdapter.ProductBannerViewHolder>(){
        inner class ProductBannerViewHolder(rowProductBannerBinding: RowProductBannerBinding): RecyclerView.ViewHolder(rowProductBannerBinding.root){
            val rowProductBannerBinding: RowProductBannerBinding

            init {
                this.rowProductBannerBinding = rowProductBannerBinding

                this.rowProductBannerBinding.root.layoutParams = ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT
                )
            }
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductBannerViewHolder {
            val rowProductBannerBinding = RowProductBannerBinding.inflate(layoutInflater)
            val productBannerViewHolder = ProductBannerViewHolder(rowProductBannerBinding)

            return productBannerViewHolder
        }

        override fun getItemCount(): Int {
            return 6
        }

        override fun onBindViewHolder(holder: ProductBannerViewHolder, position: Int) {
            holder.rowProductBannerBinding.textViewBannerPage.text = "${position+1}/6"

            // position 값에 따라 다른 이미지 설정
            val imageResource = when (position % 5) {
                0 -> R.drawable.iu_image2
                1 -> R.drawable.iu_image3
                2 -> R.drawable.iu_image4
                3 -> R.drawable.iu_image5
                else -> R.drawable.iu_image
            }
            holder.rowProductBannerBinding.constraintLayout.setBackgroundResource(imageResource)
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