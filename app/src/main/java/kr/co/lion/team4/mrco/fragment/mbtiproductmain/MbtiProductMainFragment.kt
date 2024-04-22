package kr.co.lion.team4.mrco.fragment.mbtiproductmain

import android.content.DialogInterface
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kr.co.lion.team4.mrco.MainActivity
import kr.co.lion.team4.mrco.MainFragmentName
import kr.co.lion.team4.mrco.R
import kr.co.lion.team4.mrco.Tools
import kr.co.lion.team4.mrco.dao.ProductDao
import kr.co.lion.team4.mrco.databinding.FragmentHomeRecommendBinding
import kr.co.lion.team4.mrco.databinding.FragmentMbtiProductMainBinding
import kr.co.lion.team4.mrco.databinding.RowMbtiProductMainBinding
import kr.co.lion.team4.mrco.model.ProductModel
import kr.co.lion.team4.mrco.viewmodel.home.recommend.HomeRecommendViewModel
import kr.co.lion.team4.mrco.viewmodel.mbtiproductmain.MbtiProductMainViewModel
import kr.co.lion.team4.mrco.viewmodel.mbtiproductmain.RowMbtiProductMainViewModel
import java.text.NumberFormat
import java.util.Locale

class MbtiProductMainFragment : Fragment() {

    lateinit var fragmentMbtiProductMainBinding: FragmentMbtiProductMainBinding
    lateinit var mainActivity: MainActivity

    lateinit var mbtiProductMainViewModel: MbtiProductMainViewModel

    // 상품 정보를 담고 있을 리스트
    var productList = mutableListOf<ProductModel>()

    // 더보기 버튼 위에꺼/아래꺼
    var buttonInt = 0

    var gender = 1

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        fragmentMbtiProductMainBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_mbti_product_main, container, false)
        mbtiProductMainViewModel = MbtiProductMainViewModel()
        fragmentMbtiProductMainBinding.mbtiProductMainViewModel = MbtiProductMainViewModel()
        fragmentMbtiProductMainBinding.lifecycleOwner = this

        mainActivity = activity as MainActivity

        buttonInt = arguments?.getInt("buttonInt")!!

        // 툴바, 하단바, 탭 관련
        settingToolbar()

        // 리사이클러 뷰
        settingRecyclerViewMbtiProductMain()

        // 버튼
        settingButton()

        // 기본 세팅
        settingInit()

        // Firebase에서 (성별,MBTI) 맞춤 상품 데이터 가져오기
        gettingMainData(mainActivity.loginUserMbti, mainActivity.loginUserGender)

        // MBTI TextView 관찰
        mbtiProductMainViewModel.textViewMbtiProductMainMBTI.observe(viewLifecycleOwner) { mbti ->
            // MBTI TextView 업데이트
            fragmentMbtiProductMainBinding.textViewMbti.text = mbti
            fragmentMbtiProductMainBinding.recyclerViewMbtiProductMain.adapter?.notifyDataSetChanged()
        }

        return fragmentMbtiProductMainBinding.root
    }

    // 기본 세팅
    fun settingInit() {
        Log.d("test1234", "MBTI 상품 페이지: settingInit 실행")

        // 첫번째 버튼으로 들어왔을때
        if (buttonInt == 1) {
            mbtiProductMainViewModel.textViewMbtiProductMainMBTI.value = mainActivity.loginUserMbti
            fragmentMbtiProductMainBinding.textViewSideMbti.setText("에게 잘 어울리는 코디")
            // 남자일때
            if (mainActivity.loginUserGender == 1) {
                fragmentMbtiProductMainBinding.chipMbtiProductMainGenderMEN.isChecked = true
                fragmentMbtiProductMainBinding.chipMbtiProductMainGenderWOMEN.isChecked = false
            }
            // 여자일때
            else {
                fragmentMbtiProductMainBinding.chipMbtiProductMainGenderMEN.isChecked = false
                fragmentMbtiProductMainBinding.chipMbtiProductMainGenderWOMEN.isChecked = true
            }
        }
        // 두번째 버튼으로 들어왔을때
        else {
            mbtiProductMainViewModel.textViewMbtiProductMainMBTI.value = mainActivity.loginUserMbti
            // 남자일때
            if (mainActivity.loginUserGender == 1) {
                fragmentMbtiProductMainBinding.chipMbtiProductMainGenderMEN.isChecked = true
                fragmentMbtiProductMainBinding.chipMbtiProductMainGenderWOMEN.isChecked = false
                fragmentMbtiProductMainBinding.textViewSideMbti.setText("여성이 좋아하는 남자 코디")
            }
            // 여자일때
            else {
                fragmentMbtiProductMainBinding.chipMbtiProductMainGenderMEN.isChecked = false
                fragmentMbtiProductMainBinding.chipMbtiProductMainGenderWOMEN.isChecked = true
                fragmentMbtiProductMainBinding.textViewSideMbti.setText("남성이 좋아하는 여자 코디")
            }
        }
    }

    fun settingToolbar(){
        fragmentMbtiProductMainBinding.apply {
            toolbarMbtiProductMain.apply {
                // subtitle = "$MBTI 에게 잘 어울리는 코디"
                setNavigationOnClickListener {
                    backProcesss()
                }
                setOnMenuItemClickListener {
                    when (it.itemId) {
                        // 장바구니 클릭 시
                        R.id.menuItemShoppingBagProduct -> {
                            mainActivity.replaceFragment(MainFragmentName.CART_FRAGMENT, true, true, null)
                        }
                    }
                    true
                }
            }
        }
    }

    // 리사이클러 뷰 설정
    fun settingRecyclerViewMbtiProductMain() {
        fragmentMbtiProductMainBinding.apply {
            val screenWidthDp = resources.configuration.screenWidthDp
            if (screenWidthDp >= 600) {
                // 너비가 600dp 이상인 디바이스에서 실행될 동작
                recyclerViewMbtiProductMain.apply {
                    // 어뎁터 및 레이아웃 매니저 설정
                    adapter = MbtiProductMainRecyclerViewAdapter()
                    layoutManager = GridLayoutManager(mainActivity, 3)
                }
            }
            else {
                // 너비가 600dp 미만인 디바이스에서 실행될 동작
                recyclerViewMbtiProductMain.apply {
                    // 어뎁터 및 레이아웃 매니저 설정
                    adapter = MbtiProductMainRecyclerViewAdapter()
                }
            }
        }
    }

    fun settingButton(){
        fragmentMbtiProductMainBinding.apply {
            buttonMbtiProductMainMBTI.setOnClickListener {
                showMBTIBottomSheet()
            }

            chipGroupMbtiProductMainGender.setOnCheckedStateChangeListener { chipGroup, checkedChipIds ->
                when (chipGroup.checkedChipId) {
                    // Men 버튼
                    2131363241 -> {
                        gender = 1
                        gettingMainData(mbtiProductMainViewModel?.textViewMbtiProductMainMBTI?.value!!, 1)
                        // fragmentMbtiProductMainBinding.recyclerViewMbtiProductMain.adapter?.notifyDataSetChanged()
                        Log.d("test1234", "MBTI 상품 페이지: Men 버튼 클릭 : $gender")
                    }
                    // Women 버튼
                    2131363242 -> {
                        gender = 2
                        gettingMainData(mbtiProductMainViewModel?.textViewMbtiProductMainMBTI?.value!!, 2)
                        // fragmentMbtiProductMainBinding.recyclerViewMbtiProductMain.adapter?.notifyDataSetChanged()
                        Log.d("test1234", "MBTI 상품 페이지: Women 버튼 클릭 : $gender")
                    }
                }
            }

        }
    }

    // 리사이클러 뷰 어뎁터
    inner class MbtiProductMainRecyclerViewAdapter: RecyclerView.Adapter<MbtiProductMainRecyclerViewAdapter.MbtiProductMainViewHolder>(){
        inner class MbtiProductMainViewHolder(rowMbtiProductMainBinding: RowMbtiProductMainBinding): RecyclerView.ViewHolder(rowMbtiProductMainBinding.root){
            val rowMbtiProductMainBinding: RowMbtiProductMainBinding

            init {
                this.rowMbtiProductMainBinding = rowMbtiProductMainBinding

                this.rowMbtiProductMainBinding.root.layoutParams = ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT
                )
            }
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MbtiProductMainViewHolder {
            val rowMbtiProductMainBinding = DataBindingUtil.inflate<RowMbtiProductMainBinding>(
                layoutInflater, R.layout.row_mbti_product_main, parent, false
            )
            val rowMbtiProductMainViewModel = RowMbtiProductMainViewModel()
            rowMbtiProductMainBinding.rowMbtiProductMainViewModel = rowMbtiProductMainViewModel
            rowMbtiProductMainBinding.lifecycleOwner = this@MbtiProductMainFragment

            val mbtiProductMainViewHolder = MbtiProductMainViewHolder(rowMbtiProductMainBinding)

            return mbtiProductMainViewHolder
        }

        override fun getItemCount(): Int {
            return productList.size
        }

        override fun onBindViewHolder(holder: MbtiProductMainViewHolder, position: Int) {
            // position 값에 따라 다른 이미지 설정
            val imageResource = when (position % 5) {
                0 -> R.drawable.iu_image
                1 -> R.drawable.iu_image2
                2 -> R.drawable.iu_image3
                3 -> R.drawable.iu_image4
                else -> R.drawable.iu_image5
            }
            holder.rowMbtiProductMainBinding.itemMainMbtiFullProductThumbnail.setImageResource(imageResource)
            // 해당 코디네이터의 이름
            holder.rowMbtiProductMainBinding.itemMainMbtiFullCoordinatorName.text = "코디네이터 아이유"
            // 해당 코디 상품의 이름
            holder.rowMbtiProductMainBinding.itemMainMbtiFullProductName.text = "${productList[position].coordiName}"
            // 해당 코디 상품의 가격
            holder.rowMbtiProductMainBinding.itemMainMbtiFullProductPrice.text =
                "${NumberFormat.getNumberInstance(Locale.getDefault()).format(productList[position].price)}"
            // 해당 상품 클릭 시
            holder.rowMbtiProductMainBinding.root.setOnClickListener {
                mainActivity.replaceFragment(MainFragmentName.PRODUCT_FRAGMENT, true, true, null)
            }
        }
    }

    // MBTI를 설정할 BottomSheet를 띄워준다.
    fun showMBTIBottomSheet(){
        val mbtiProductBottomFragment = MbtiProductBottomFragment(mbtiProductMainViewModel?.textViewMbtiProductMainMBTI!!)
        mbtiProductBottomFragment.show(mainActivity.supportFragmentManager, "MBTIBottomSheet")
    }

    // 뒤로가기 처리
    fun backProcesss(){
        mainActivity.removeFragment(MainFragmentName.MBTI_PRODUCT_MAIN)
    }

    // 현재 게시판의 데이터를 가져와 메인 화면의 RecyclerView를 갱신한다.
    fun gettingMainData(mbti: String, gender: Int) {
        CoroutineScope(Dispatchers.Main).launch {
            // MBTI와 성별에 맞는 상품의 정보를 가져온다. (연동 OFF)
            productList = ProductDao.gettingProductMBTIList(mbti, gender)
            Log.d("test1234", "MBTI 상품 페이지: productList: $productList")
            fragmentMbtiProductMainBinding.recyclerViewMbtiProductMain.adapter?.notifyDataSetChanged()
        }
    }
}