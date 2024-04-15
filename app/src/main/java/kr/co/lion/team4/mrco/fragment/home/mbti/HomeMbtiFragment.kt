package kr.co.lion.team4.mrco.fragment.home.mbti

import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.tabs.TabLayout
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kr.co.lion.team4.mrco.MainActivity
import kr.co.lion.team4.mrco.MainFragmentName
import kr.co.lion.team4.mrco.R
import kr.co.lion.team4.mrco.databinding.FragmentHomeMbtiBinding
import kr.co.lion.team4.mrco.databinding.RowHomeMbti2Binding
import kr.co.lion.team4.mrco.databinding.RowHomeMbtiBinding
import kr.co.lion.team4.mrco.viewmodel.home.mbti.HomeMbtiViewModel
import kr.co.lion.team4.mrco.viewmodel.home.mbti.RowHomeMbti2ViewModel
import kr.co.lion.team4.mrco.viewmodel.home.mbti.RowHomeMbtiViewModel

class HomeMbtiFragment : Fragment() {

    lateinit var fragmentHomeMbtiBinding: FragmentHomeMbtiBinding
    lateinit var mainActivity: MainActivity

    lateinit var homeMbtiViewModel: HomeMbtiViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        fragmentHomeMbtiBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_home_mbti, container, false)
        homeMbtiViewModel = HomeMbtiViewModel()
        fragmentHomeMbtiBinding.homeMbtiViewModel = HomeMbtiViewModel()
        fragmentHomeMbtiBinding.lifecycleOwner = this

        mainActivity = activity as MainActivity

        // 리사이클러 뷰
        settingRecyclerViewHomeMBTI()
        settingRecyclerViewHomeMBTI2()

        // 버튼(더보기)
        settingContentMoreButton()

        return fragmentHomeMbtiBinding.root
    }

    // 더보기 버튼
    fun settingContentMoreButton(){
        fragmentHomeMbtiBinding.apply {
            homeMbtiContent1MoreButton.setOnClickListener {
                mainActivity.replaceFragment(MainFragmentName.MBTI_PRODUCT_MAIN, true, true, null)
            }

            homeMbtiContent2MoreButton.setOnClickListener {
                mainActivity.replaceFragment(MainFragmentName.MBTI_PRODUCT_MAIN, true, true, null)
            }
        }
    }

    // 홈(추천) - 신규 코디 리사이클러 뷰 설정
    fun settingRecyclerViewHomeMBTI() {
        fragmentHomeMbtiBinding.apply {
            homeMbtiContent1Recycler.apply {
                // 어뎁터 및 레이아웃 매니저 설정
                adapter = HomeMBTIRecyclerViewAdapter()
                // layoutManager = LinearLayoutManager(mainActivity, LinearLayoutManager.HORIZONTAL, false)
            }
        }
    }

    // 홈(추천) - 신규 코디 리사이클러 뷰 설정
    fun settingRecyclerViewHomeMBTI2() {
        fragmentHomeMbtiBinding.apply {
            homeMbtiContent2Recycler.apply {
                // 어뎁터 및 레이아웃 매니저 설정
                adapter = HomeMBTI2RecyclerViewAdapter()
                // layoutManager = LinearLayoutManager(mainActivity, LinearLayoutManager.HORIZONTAL, false)
            }
        }
    }

    // 홈(MBTI) - MBTI @@에게 잘 어울리는 코디 리사이클러 뷰 어뎁터
    inner class HomeMBTIRecyclerViewAdapter: RecyclerView.Adapter<HomeMBTIRecyclerViewAdapter.HomeMBTIViewHolder>(){
        inner class HomeMBTIViewHolder(rowHomeMbtiBinding: RowHomeMbtiBinding): RecyclerView.ViewHolder(rowHomeMbtiBinding.root){
            val rowHomeMbtiBinding: RowHomeMbtiBinding

            init {
                this.rowHomeMbtiBinding = rowHomeMbtiBinding

                this.rowHomeMbtiBinding.root.layoutParams = ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT
                )
            }
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeMBTIViewHolder {
            val rowHomeMbtiBinding = DataBindingUtil.inflate<RowHomeMbtiBinding>(
                layoutInflater, R.layout.row_home_mbti, parent, false
            )
            val rowHomeMbtiViewModel = RowHomeMbtiViewModel()
            rowHomeMbtiBinding.rowHomeMbtiViewModel = rowHomeMbtiViewModel
            rowHomeMbtiBinding.lifecycleOwner = this@HomeMbtiFragment

            val homeMBTIViewHolder = HomeMBTIViewHolder(rowHomeMbtiBinding)

            return homeMBTIViewHolder
        }

        override fun getItemCount(): Int {
            return 12
        }

        override fun onBindViewHolder(holder: HomeMBTIViewHolder, position: Int) {

            // position 값에 따라 다른 이미지 설정
            val imageResource = when (position % 4) {
                0 -> R.drawable.iu_image2
                1 -> R.drawable.iu_image3
                2 -> R.drawable.iu_image6
                else -> R.drawable.iu_image7
            }
            holder.rowHomeMbtiBinding.itemMainMbtiProductThumbnail.setImageResource(imageResource)

            // position 값에 따라 다른 MBTI 색상 설정
            val colorResource = when (position % 4) {
                0 -> Color.parseColor("#13D4EF")
                1 -> Color.parseColor("#BDB14C")
                2 -> Color.parseColor("#B75AB6")
                else -> Color.parseColor("#36C87C")
            }
            holder.rowHomeMbtiBinding.itemMainMbtiProductMbti.setBackgroundColor(colorResource)

            holder.rowHomeMbtiBinding.root.setOnClickListener {
                mainActivity.replaceFragment(MainFragmentName.PRODUCT_FRAGMENT, true, true, null)
            }
        }
    }

    // 홈(MBTI) - MBTI 성별이 좋아하는 이성의 코디
    inner class HomeMBTI2RecyclerViewAdapter: RecyclerView.Adapter<HomeMBTI2RecyclerViewAdapter.HomeMBTI2ViewHolder>(){
        inner class HomeMBTI2ViewHolder(rowHomeMbti2Binding: RowHomeMbti2Binding): RecyclerView.ViewHolder(rowHomeMbti2Binding.root){
            val rowHomeMbti2Binding: RowHomeMbti2Binding

            init {
                this.rowHomeMbti2Binding = rowHomeMbti2Binding

                this.rowHomeMbti2Binding.root.layoutParams = ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT
                )
            }
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeMBTI2ViewHolder {
            val rowHomeMbti2Binding = DataBindingUtil.inflate<RowHomeMbti2Binding>(
                layoutInflater, R.layout.row_home_mbti2, parent, false
            )
            val rowHomeMbti2ViewModel = RowHomeMbti2ViewModel()
            rowHomeMbti2Binding.rowHomeMbti2ViewModel = rowHomeMbti2ViewModel
            rowHomeMbti2Binding.lifecycleOwner = this@HomeMbtiFragment

            val homeMBTI2ViewHolder = HomeMBTI2ViewHolder(rowHomeMbti2Binding)

            return homeMBTI2ViewHolder
        }

        override fun getItemCount(): Int {
            return 12
        }

        override fun onBindViewHolder(holder: HomeMBTI2ViewHolder, position: Int) {

            val rowHomeMbti2ViewModel = RowHomeMbti2ViewModel()

            // position 값에 따라 다른 이미지 설정
            val imageResource = when (position % 4) {
                0 -> R.drawable.iu_image4
                1 -> R.drawable.iu_image5
                2 -> R.drawable.iu_image6
                else -> R.drawable.iu_image7
            }
            holder.rowHomeMbti2Binding.itemMainMbtiProductThumbnail2.setImageResource(imageResource)

            // position 값에 따라 다른 MBTI 색상 설정
            val colorResource = when (position % 4) {
                0 -> Color.parseColor("#13D4EF")
                1 -> Color.parseColor("#BDB14C")
                2 -> Color.parseColor("#B75AB6")
                else -> Color.parseColor("#36C87C")
            }
            holder.rowHomeMbti2Binding.itemMainMbtiProductMbti2.setBackgroundColor(colorResource)

            holder.rowHomeMbti2Binding.root.setOnClickListener {
                mainActivity.replaceFragment(MainFragmentName.PRODUCT_FRAGMENT, true, true, null)
            }
        }
    }
}