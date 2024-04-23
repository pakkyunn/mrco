package kr.co.lion.team4.mrco.fragment.home.mbti

import android.graphics.Color
import android.os.Bundle
import android.util.Log
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
import kr.co.lion.team4.mrco.Tools
import kr.co.lion.team4.mrco.dao.ProductDao
import kr.co.lion.team4.mrco.databinding.FragmentHomeMbtiBinding
import kr.co.lion.team4.mrco.databinding.RowHomeMbti2Binding
import kr.co.lion.team4.mrco.databinding.RowHomeMbtiBinding
import kr.co.lion.team4.mrco.fragment.home.coordinator.HomeMainFullFragment
import kr.co.lion.team4.mrco.model.ProductModel
import kr.co.lion.team4.mrco.viewmodel.home.mbti.HomeMbtiViewModel
import kr.co.lion.team4.mrco.viewmodel.home.mbti.RowHomeMbti2ViewModel
import kr.co.lion.team4.mrco.viewmodel.home.mbti.RowHomeMbtiViewModel
import java.text.NumberFormat
import java.util.Locale

class HomeMbtiFragment : Fragment() {

    lateinit var fragmentHomeMbtiBinding: FragmentHomeMbtiBinding
    lateinit var mainActivity: MainActivity

    lateinit var homeMbtiViewModel: HomeMbtiViewModel

    // 상품 정보를 담고 있을 리스트
    var productList = mutableListOf<ProductModel>()

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

        // TextView 기본 설정
        settingInit()

        // 데이터 가져오기
        gettingMainData(mainActivity.loginUserMbti, mainActivity.loginUserGender)

        // 코디 상품(첫번째) TextView 관찰
        homeMbtiViewModel.textViewHomeMbtiTextFirst.observe(viewLifecycleOwner) { text ->
            // MBTI TextView 업데이트
            fragmentHomeMbtiBinding.homeMbtiContent1.text = text
        }

        // 코디 상품(첫번째) TextView 관찰
        homeMbtiViewModel.textViewHomeMbtiTextSecond.observe(viewLifecycleOwner) { text ->
            // MBTI TextView 업데이트
            fragmentHomeMbtiBinding.homeMbtiContent2.text = text
        }

        return fragmentHomeMbtiBinding.root
    }

    // TextView 세팅
    fun settingInit() {
        // 남자일때
        if (mainActivity.loginUserGender == 1) {
            homeMbtiViewModel.textViewHomeMbtiTextFirst.value = "${mainActivity.loginUserMbti} 남성에게 잘 어울리는 코디"
            homeMbtiViewModel.textViewHomeMbtiTextSecond.value = "${mainActivity.loginUserMbti} 여성이 좋아하는 남자 코디"
        }
        // 여자일때
        else {
            homeMbtiViewModel.textViewHomeMbtiTextFirst.value = "${mainActivity.loginUserMbti} 여성에게 잘 어울리는 코디"
            homeMbtiViewModel.textViewHomeMbtiTextSecond.value = "${mainActivity.loginUserMbti} 남성이 좋아하는 여자 코디"
        }
    }

    // 더보기 버튼
    fun settingContentMoreButton(){
        val bundle = Bundle()

        fragmentHomeMbtiBinding.apply {
            homeMbtiContent1MoreButton.setOnClickListener {
                bundle.putInt("buttonInt", 1)
                mainActivity.replaceFragment(MainFragmentName.MBTI_PRODUCT_MAIN, true, true, bundle)
            }

            homeMbtiContent2MoreButton.setOnClickListener {
                bundle.putInt("buttonInt", 2)
                mainActivity.replaceFragment(MainFragmentName.MBTI_PRODUCT_MAIN, true, true, bundle)
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

    // RecyclerView Adapter : 홈(MBTI) - MBTI @@에게 잘 어울리는 코디 리사이클러 뷰 어뎁터
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
            if (productList.size > 12) return 12
            else return productList.size
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

            holder.rowHomeMbtiBinding.itemMainMbtiProductMbti.setBackgroundColor(Color.parseColor(Tools.mbtiColor(productList[position].coordiMBTI)))
            holder.rowHomeMbtiBinding.itemMainMbtiProductMbti.text = mainActivity.loginUserMbti
            // 해당 코디네이터의 이름
            holder.rowHomeMbtiBinding.itemMainMbtiCoordinatorName.text = "코디네이터 아이유"
            // 해당 코디 상품의 이름
            holder.rowHomeMbtiBinding.itemMainMbtiProductName.text = "${productList[position].coordiName}"
            // 해당 코디 상품의 가격
            holder.rowHomeMbtiBinding.itemMainMbtiProductPrice.text =
                "${NumberFormat.getNumberInstance(Locale.getDefault()).format(productList[position].price)}"

            holder.rowHomeMbtiBinding.root.setOnClickListener {
                mainActivity.replaceFragment(MainFragmentName.PRODUCT_FRAGMENT, true, true, null)
            }
        }
    }

    // RecyclerView Adapter : 홈(MBTI) - MBTI 성별이 좋아하는 이성 코디
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

    // 해당 상품의 데이터를 가져와 메인 화면의 RecyclerView를 갱신한다.
    fun gettingMainData(mbti: String, gender: Int) {
        CoroutineScope(Dispatchers.Main).launch {
            // MBTI와 성별에 맞는 상품의 정보를 가져온다. (연동 On)
            productList = ProductDao.gettingProductMBTIList(mbti, gender)
            Log.d("test1234", "MBTI별 코디 탭 - 상품 개수: ${productList.size}개")
            fragmentHomeMbtiBinding.homeMbtiContent1Recycler.adapter?.notifyDataSetChanged()
        }
    }
}