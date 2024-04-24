package kr.co.lion.team4.mrco.fragment.category

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.Menu
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kr.co.lion.team4.mrco.Gender
import kr.co.lion.team4.mrco.MainActivity
import kr.co.lion.team4.mrco.MainFragmentName
import kr.co.lion.team4.mrco.R
import kr.co.lion.team4.mrco.Tools
import kr.co.lion.team4.mrco.dao.ProductDao
import kr.co.lion.team4.mrco.dao.UserDao
import kr.co.lion.team4.mrco.databinding.FragmentCategoryMainBinding
import kr.co.lion.team4.mrco.databinding.HeaderCategoryDrawerBinding
import kr.co.lion.team4.mrco.databinding.RowCategoryMainBinding
import kr.co.lion.team4.mrco.databinding.RowCategoryMainFilteredBinding
import kr.co.lion.team4.mrco.model.ProductModel
import kr.co.lion.team4.mrco.model.UserModel
import kr.co.lion.team4.mrco.viewmodel.category.RowCategoryMainViewModel
import java.text.NumberFormat
import java.util.Locale

class CategoryMainFragment : Fragment() {

    lateinit var fragmentCategoryMainBinding: FragmentCategoryMainBinding
    lateinit var mainActivity: MainActivity
    lateinit var headerCategoryDrawerBinding: HeaderCategoryDrawerBinding


    val menuItemsMbti = arrayOf(
        R.id.drawerMenuMbtiEnfj,
        R.id.drawerMenuMbtiEnfp,
        R.id.drawerMenuMbtiEntj,
        R.id.drawerMenuMbtiEntp,
        R.id.drawerMenuMbtiEsfj,
        R.id.drawerMenuMbtiEsfp,
        R.id.drawerMenuMbtiEstj,
        R.id.drawerMenuMbtiEstp,
        R.id.drawerMenuMbtiInfj,
        R.id.drawerMenuMbtiInfp,
        R.id.drawerMenuMbtiIntj,
        R.id.drawerMenuMbtiIntp,
        R.id.drawerMenuMbtiIsfj,
        R.id.drawerMenuMbtiIsfp,
        R.id.drawerMenuMbtiIstj,
        R.id.drawerMenuMbtiIstp
    )
    val menuItemsTpo = arrayOf(
        R.id.drawerMenuTpo1,
        R.id.drawerMenuTpo2,
        R.id.drawerMenuTpo3,
        R.id.drawerMenuTpo4,
        R.id.drawerMenuTpo5,
        R.id.drawerMenuTpo6,
        R.id.drawerMenuTpo7,
        R.id.drawerMenuTpo8
    )
    val menuItemsSeason = arrayOf(
        R.id.drawerMenuSeasonSpring,
        R.id.drawerMenuSeasonSummer,
        R.id.drawerMenuSeasonFall,
        R.id.drawerMenuSeasonWinter
    )
    val menuItemsMood = arrayOf(
        R.id.drawerMenuMood1,
        R.id.drawerMenuMood2,
        R.id.drawerMenuMood3,
        R.id.drawerMenuMood4,
        R.id.drawerMenuMood5,
        R.id.drawerMenuMood6,
        R.id.drawerMenuMood7,
        R.id.drawerMenuMood8,
        R.id.drawerMenuMood9,
        R.id.drawerMenuMood10
    )
    val menuItemsAll = arrayOf(
        R.id.drawerMenuMbtiEnfj,
        R.id.drawerMenuMbtiEnfp,
        R.id.drawerMenuMbtiEntj,
        R.id.drawerMenuMbtiEntp,
        R.id.drawerMenuMbtiEsfj,
        R.id.drawerMenuMbtiEsfp,
        R.id.drawerMenuMbtiEstj,
        R.id.drawerMenuMbtiEstp,
        R.id.drawerMenuMbtiInfj,
        R.id.drawerMenuMbtiInfp,
        R.id.drawerMenuMbtiIntj,
        R.id.drawerMenuMbtiIntp,
        R.id.drawerMenuMbtiIsfj,
        R.id.drawerMenuMbtiIsfp,
        R.id.drawerMenuMbtiIstj,
        R.id.drawerMenuMbtiIstp,
        R.id.drawerMenuTpo1,
        R.id.drawerMenuTpo2,
        R.id.drawerMenuTpo3,
        R.id.drawerMenuTpo4,
        R.id.drawerMenuTpo5,
        R.id.drawerMenuTpo6,
        R.id.drawerMenuTpo7,
        R.id.drawerMenuTpo8,
        R.id.drawerMenuSeasonSpring,
        R.id.drawerMenuSeasonSummer,
        R.id.drawerMenuSeasonFall,
        R.id.drawerMenuSeasonWinter,
        R.id.drawerMenuMood1,
        R.id.drawerMenuMood2,
        R.id.drawerMenuMood3,
        R.id.drawerMenuMood4,
        R.id.drawerMenuMood5,
        R.id.drawerMenuMood6,
        R.id.drawerMenuMood7,
        R.id.drawerMenuMood8,
        R.id.drawerMenuMood9,
        R.id.drawerMenuMood10
    )

    val menuMapAll = mapOf(
        R.id.drawerMenuMbtiEnfj to "ENFJ",
        R.id.drawerMenuMbtiEnfp to "ENFP",
        R.id.drawerMenuMbtiEntj to "ENTJ",
        R.id.drawerMenuMbtiEntp to "ENTP",
        R.id.drawerMenuMbtiEsfj to "ESFJ",
        R.id.drawerMenuMbtiEsfp to "ESFP",
        R.id.drawerMenuMbtiEstj to "ESTJ",
        R.id.drawerMenuMbtiEstp to "ESTP",
        R.id.drawerMenuMbtiInfj to "INFJ",
        R.id.drawerMenuMbtiInfp to "INFP",
        R.id.drawerMenuMbtiIntj to "INTJ",
        R.id.drawerMenuMbtiIntp to "INTP",
        R.id.drawerMenuMbtiIsfj to "ISFJ",
        R.id.drawerMenuMbtiIsfp to "ISFP",
        R.id.drawerMenuMbtiIstj to "ISTJ",
        R.id.drawerMenuMbtiIstp to "ISTP",
        R.id.drawerMenuTpo1 to "여행",
        R.id.drawerMenuTpo2 to "데이트",
        R.id.drawerMenuTpo3 to "카페",
        R.id.drawerMenuTpo4 to "출근",
        R.id.drawerMenuTpo5 to "데일리",
        R.id.drawerMenuTpo6 to "캠퍼스",
        R.id.drawerMenuTpo7 to "바다",
        R.id.drawerMenuTpo8 to "결혼식",
        R.id.drawerMenuSeasonSpring to "봄",
        R.id.drawerMenuSeasonSummer to "여름",
        R.id.drawerMenuSeasonFall to "가을",
        R.id.drawerMenuSeasonWinter to "겨울",
        R.id.drawerMenuMood1 to "미니멀",
        R.id.drawerMenuMood2 to "비즈니스 캐주얼",
        R.id.drawerMenuMood3 to "원마일웨어",
        R.id.drawerMenuMood4 to "아메카지",
        R.id.drawerMenuMood5 to "시티보이",
        R.id.drawerMenuMood6 to "스트릿",
        R.id.drawerMenuMood7 to "스포티",
        R.id.drawerMenuMood8 to "레트로",
        R.id.drawerMenuMood9 to "러블리",
        R.id.drawerMenuMood10 to "모던 캐주얼"
    )

    val checkedMenuList = arrayListOf<String>()

    val filteredItemBundle = Bundle()


    var productList = mutableListOf<ProductModel>()
    var filteredProductList = mutableListOf<ProductModel>()

    // 코디네이터 인덱스와 이름 정보를 담고 있을 맵
    var coordinatorMap = mutableMapOf<Int, String>()

    var genderIdx = 0

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        fragmentCategoryMainBinding = FragmentCategoryMainBinding.inflate(inflater)
        headerCategoryDrawerBinding = HeaderCategoryDrawerBinding.inflate(inflater)

        mainActivity = activity as MainActivity

        initFilterData()

        gettingProductData()

        // 툴바, 하단바, 탭 관련
        toolbarSetting()
        bottomSheetSetting()
        settingBottomTabs()
        settingNavigationView()

        // 상품 목록 리사이클러 뷰
        settingRecyclerViewCategoryMain()
        // 필터링된 카테고리 항목 표시 리사이클러 뷰
        settingRecyclerViewCategoryFiltered()

        return fragmentCategoryMainBinding.root
    }

    fun initFilterData() {
        fragmentCategoryMainBinding.apply {
            if (arguments != null) {
                val searchedCategory = arguments?.getString("searchedCategory")
                genderIdx = requireArguments().getInt("genderIdx")
                checkedMenuList.add(searchedCategory!!)
                filteredItemBundle.putString(
                    "searchedCategory",
                    searchedCategory
                )
            }
        }
    }

    // 툴바 설정
    fun toolbarSetting() {
        fragmentCategoryMainBinding.apply {

            toolbarCoordinatorMain.apply {
                setNavigationOnClickListener {
                    drawerLayoutContent.open()
                }
                setOnMenuItemClickListener {
                    when (it.itemId) {
                        // 장바구니 클릭 시
                        R.id.category_toolbar_shopping -> {
                            mainActivity.replaceFragment(
                                MainFragmentName.CART_FRAGMENT,
                                true,
                                true,
                                null
                            )
                        }
                    }
                    true
                }
            }
        }
    }


    fun resetMenuItems(menu: Menu, menuItemIds: Array<Int>) {
        menuItemIds.forEach { menuItemId ->
            val menuItem = menu.findItem(menuItemId)
            val checkBox = menuItem?.actionView?.findViewById<CheckBox>(R.id.drawerCheckbox)
            checkBox?.isChecked = false
            menuItem?.isChecked = false
            menuItem?.isCheckable = false
        }
    }

    fun applyMenuItems(menu: Menu, menuItemIds: Array<Int>) {
        checkedMenuList.clear()
        menuItemIds.forEach { menuItemId ->
            val menuItem = menu.findItem(menuItemId)
            val menuItemTitle = menuMapAll[menuItemId]
            if (menuItem.isChecked) {
                checkedMenuList.add(menuItemTitle!!)
            }
        }
        filteredItemBundle.putStringArrayList(
            "searchedCategory",
            checkedMenuList
        )
    }

    fun settingCategoryMenuClick(menu: Menu, menuItemIds: Array<Int>) {
        menuItemIds.forEach { menuItemId ->
            val menuItem = menu.findItem(menuItemId)
            val checkBox = menuItem?.actionView?.findViewById<CheckBox>(R.id.drawerCheckbox)
            menuItem.setOnMenuItemClickListener {
                checkBox?.isChecked = !checkBox?.isChecked!!
                menuItem.isCheckable = false

                true
            }
        }
    }

    fun checkSync(menu: Menu, menuItemIds: Array<Int>) {
        menuItemIds.forEach { menuItemId ->
            val menuItem = menu.findItem(menuItemId)
            val checkBox = menuItem?.actionView?.findViewById<CheckBox>(R.id.drawerCheckbox)

            checkBox?.setOnCheckedChangeListener { buttonView, isChecked ->
                menuItem.isChecked = !menuItem.isChecked
                menuItem.isCheckable = false
            }
        }
    }

    fun settingGenderIdx() {
        fragmentCategoryMainBinding.apply {
            navigationViewContent.apply {
                val chipGroupCategoryGender = findViewById<ChipGroup>(R.id.chipGroupCategoryGender)
                val chipCategoryGenderMEN = findViewById<Chip>(R.id.chipCategoryGenderMEN)
                val chipCategoryGenderWOMEN = findViewById<Chip>(R.id.chipCategoryGenderWOMEN)

                val genderId = chipGroupCategoryGender.checkedChipId
                if (genderId == chipCategoryGenderMEN.id) {
                    genderIdx = Gender.MALE.num
                } else if (genderId == chipCategoryGenderWOMEN.id) {
                    genderIdx = Gender.FEMALE.num
                }
            }
        }
    }


    fun settingCategoryChipChange(chip: Chip, array: Array<Int>) {
        fragmentCategoryMainBinding.apply {
            navigationViewContent.apply {
                val headerCategoryDrawerBinding =
                    HeaderCategoryDrawerBinding.inflate(layoutInflater)

                headerCategoryDrawerBinding.apply {
                    // chip(4개)을 클릭할 때 동작하는 리스너
                    chip.setOnCheckedChangeListener { _, isChecked ->
                        if (isChecked) {
                            array.forEach {
                                menu.findItem(it).isVisible = true
                            }
                        } else {
                            array.forEach {
                                menu.findItem(it).isVisible = false
                            }
                        }
                    }
                }
            }
        }
    }

    // 네비게이션 뷰 설정
    fun settingNavigationView() {
        fragmentCategoryMainBinding.apply {
            navigationViewContent.apply {
                // 헤더로 보여줄 View를 설정한다.
                addHeaderView(headerCategoryDrawerBinding.root)

                headerCategoryDrawerBinding.apply {
                    // 리셋 버튼 클릭
                    buttonDrawerCategoryReset.setOnClickListener {
                        resetMenuItems(menu, menuItemsAll)
                    }

                    // 적용 버튼 클릭
                    buttonDrawerCategoryApply.setOnClickListener {
                        settingGenderIdx()
                        // 체크된 카테고리 항목을 카테고리 메인화면에 번들로 전달
                        applyMenuItems(menu, menuItemsAll)

                        // 번들 데이터를 바탕으로 productList의 상품 목록을 필터링하여 filteredProductList에 저장하는 메서드(productListFilter) 호출
                        productListFilter()


                        fragmentCategoryMainBinding.recyclerViewCategoryMainFiltered?.adapter?.notifyDataSetChanged()
                        fragmentCategoryMainBinding.recyclerViewCategoryMain.adapter?.notifyDataSetChanged()

                        // NavigationView를 닫아준다.
                        drawerLayoutContent.close()

                        val ft: FragmentTransaction = requireFragmentManager().beginTransaction()
                        ft.detach(this@CategoryMainFragment).attach(this@CategoryMainFragment)
                            .commit()
                    }

                    // chip을 클릭할 때 동작하는 리스너 메서드
                    settingCategoryChipChange(chipCategoriesMbti, menuItemsMbti)
                    settingCategoryChipChange(chipCategoriesTpo, menuItemsTpo)
                    settingCategoryChipChange(chipCategoriesSeason, menuItemsSeason)
                    settingCategoryChipChange(chipCategoriesMood, menuItemsMood)

                    // 메뉴항목을 클릭했을 때 동작하는 리스너 메서드
                    settingCategoryMenuClick(menu, menuItemsAll)
                    // 체크박스 클릭과 메뉴항목 클릭 상태 동기화 메서드
                    checkSync(menu, menuItemsAll)
                }
            }
        }
    }

    fun productListFilter(): MutableList<ProductModel> {
        filteredProductList.clear()
        if (checkedMenuList.size != 0) {
            for (i in 0 until productList.size) {
                for (j in 0 until checkedMenuList.size) {
                    if (productList[i].coordiMBTI.uppercase() == checkedMenuList[j] && productList[i].coordiGender == genderIdx) {
                        filteredProductList.add(productList[i])
                    }
                }
            }
            for (i in 0 until productList.size) {
                for (j in 0 until checkedMenuList.size) {
                    if (productList[i].coordiTPO?.strKor == checkedMenuList[j] && productList[i].coordiGender == genderIdx) {
                        filteredProductList.add(productList[i])
                    }
                }
            }

            for (i in 0 until productList.size) {
                for (j in 0 until checkedMenuList.size) {
                    if (productList[i].coordiSeason?.strKor == checkedMenuList[j] && productList[i].coordiGender == genderIdx) {
                        filteredProductList.add(productList[i])
                    }
                }
            }

            for (i in 0 until productList.size) {
                for (j in 0 until checkedMenuList.size) {
                    if (productList[i].coordiMood?.strKor == checkedMenuList[j] && productList[i].coordiGender == genderIdx) {
                        filteredProductList.add(productList[i])
                    }
                }
            }
        } else {

        }

        filteredProductList = filteredProductList.distinct().toMutableList()

        return filteredProductList
    }


    // 리사이클러 뷰 설정
    fun settingRecyclerViewCategoryMain() {
        fragmentCategoryMainBinding.apply {
            val screenWidthDp = resources.configuration.screenWidthDp
            if (screenWidthDp >= 600) {
                // 너비가 600dp 이상인 디바이스에서 실행될 동작
                recyclerViewCategoryMain.apply {
                    adapter = CategoryMainRecyclerViewAdapter()
                    layoutManager = GridLayoutManager(mainActivity, 4)
                }
            } else {
                recyclerViewCategoryMain.apply {
                    adapter = CategoryMainRecyclerViewAdapter()
                    layoutManager = GridLayoutManager(mainActivity, 2)
                }
            }
        }
    }


    fun settingRecyclerViewCategoryFiltered() {
        fragmentCategoryMainBinding.apply {
            recyclerViewCategoryMainFiltered?.apply {
                // 어뎁터 및 레이아웃 매니저 설정
                adapter = CategoeyFilteredRecyclerViewAdapter()
                layoutManager =
                    LinearLayoutManager(mainActivity, LinearLayoutManager.HORIZONTAL, false)
            }
        }
    }

    inner class CategoeyFilteredRecyclerViewAdapter :
        RecyclerView.Adapter<CategoeyFilteredRecyclerViewAdapter.CategoryFilteredViewHolder>() {
        inner class CategoryFilteredViewHolder(rowCategoryMainFilteredBinding: RowCategoryMainFilteredBinding) :
            RecyclerView.ViewHolder(rowCategoryMainFilteredBinding.root) {
            val rowCategoryMainFilteredBinding: RowCategoryMainFilteredBinding

            init {
                this.rowCategoryMainFilteredBinding = rowCategoryMainFilteredBinding

                this.rowCategoryMainFilteredBinding.root.layoutParams = ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT
                )
            }
        }

        override fun onCreateViewHolder(
            parent: ViewGroup,
            viewType: Int
        ): CategoryFilteredViewHolder {
            val rowCategoryMainFilteredBinding =
                DataBindingUtil.inflate<RowCategoryMainFilteredBinding>(
                    layoutInflater, R.layout.row_category_main_filtered, parent, false
                )

            rowCategoryMainFilteredBinding.lifecycleOwner = this@CategoryMainFragment

            val categoryFilteredViewHolder =
                CategoryFilteredViewHolder(rowCategoryMainFilteredBinding)

            return categoryFilteredViewHolder
        }

        override fun getItemCount(): Int {
            return checkedMenuList.size
        }

        override fun onBindViewHolder(holder: CategoryFilteredViewHolder, position: Int) {
            holder.rowCategoryMainFilteredBinding.textViewRowCategoryFiltered.text =
                "#${checkedMenuList[position]}"
        }
    }


    // 리사이클러 뷰 어뎁터
    inner class CategoryMainRecyclerViewAdapter :
        RecyclerView.Adapter<CategoryMainRecyclerViewAdapter.CategoryMainViewHolder>() {
        inner class CategoryMainViewHolder(rowCategoryMainBinding: RowCategoryMainBinding) :
            RecyclerView.ViewHolder(rowCategoryMainBinding.root) {
            val rowCategoryMainBinding: RowCategoryMainBinding

            init {
                this.rowCategoryMainBinding = rowCategoryMainBinding

                this.rowCategoryMainBinding.root.layoutParams = ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT
                )
            }
        }

        override fun onCreateViewHolder(
            parent: ViewGroup,
            viewType: Int
        ): CategoryMainViewHolder {
            val rowCategoryMainBinding = DataBindingUtil.inflate<RowCategoryMainBinding>(
                layoutInflater, R.layout.row_category_main, parent, false
            )
            val rowCategoryMainViewModel = RowCategoryMainViewModel()
            rowCategoryMainBinding.rowCategoryMainViewModel = rowCategoryMainViewModel
            rowCategoryMainBinding.lifecycleOwner = this@CategoryMainFragment

            val categoryMainViewHolder = CategoryMainViewHolder(rowCategoryMainBinding)

            return categoryMainViewHolder
        }

        override fun getItemCount(): Int {
            return if (filteredItemBundle.keySet().isNotEmpty()) {
                filteredProductList.size
            } else {
                productList.size
            }
        }

        override fun onBindViewHolder(holder: CategoryMainViewHolder, position: Int) {

            // position 값에 따라 다른 이미지 설정
            val imageResource = when (position % 6) {
                0 -> R.drawable.iu_image2
                1 -> R.drawable.iu_image3
                2 -> R.drawable.iu_image4
                3 -> R.drawable.iu_image5
                4 -> R.drawable.iu_image6
                else -> R.drawable.iu_image7
            }
            holder.rowCategoryMainBinding.imageViewCategoryMainProductThumbnail.setImageResource(
                imageResource
            )

            if (filteredItemBundle.keySet().isNotEmpty()) {
//                filteredProductList.add(filteredItemBundle.getString("filteredItemBundle"))

                holder.rowCategoryMainBinding.textViewCategoryMainProductMbti.setBackgroundColor(
                    Color.parseColor(Tools.mbtiColor(filteredProductList[position].coordiMBTI))
                )
                holder.rowCategoryMainBinding.textViewCategoryMainProductMbti.text =
                    "${filteredProductList[position].coordiMBTI}"

                // 코디 상품의 코디네이터
                holder.rowCategoryMainBinding.textViewCategoryMainCoordinatorName.text =
                    "${coordinatorMap[filteredProductList[position].coordinatorIdx]}"
                // 코디 상품의 이름
                holder.rowCategoryMainBinding.textViewCategoryMainProductName.text =
                    "${filteredProductList[position].coordiName}"
                // 코디 상품의 가격
                holder.rowCategoryMainBinding.textViewCategoryMainProductPrice.text =
                    "${
                        NumberFormat.getNumberInstance(Locale.getDefault())
                            .format(filteredProductList[position].price)
                    }"
            } else {
                holder.rowCategoryMainBinding.textViewCategoryMainProductMbti.setBackgroundColor(
                    Color.parseColor(Tools.mbtiColor(productList[position].coordiMBTI))
                )
                holder.rowCategoryMainBinding.textViewCategoryMainProductMbti.text =
                    "${productList[position].coordiMBTI}"

                // 코디 상품의 코디네이터
                holder.rowCategoryMainBinding.textViewCategoryMainCoordinatorName.text =
                    "${coordinatorMap[productList[position].coordinatorIdx]}"
                // 코디 상품의 이름
                holder.rowCategoryMainBinding.textViewCategoryMainProductName.text =
                    "${productList[position].coordiName}"
                // 코디 상품의 가격
                holder.rowCategoryMainBinding.textViewCategoryMainProductPrice.text =
                    "${
                        NumberFormat.getNumberInstance(Locale.getDefault())
                            .format(productList[position].price)
                    }"
            }

            // 이미지 -> 상품 이미지 클릭 시
            holder.rowCategoryMainBinding.root.setOnClickListener {
                mainActivity.replaceFragment(MainFragmentName.PRODUCT_FRAGMENT, true, true, null)
            }

            // 버튼 -> 카트 담기 클릭 시
            holder.rowCategoryMainBinding.buttonCategoryMainCart.setOnClickListener {
                mainActivity.replaceFragment(MainFragmentName.CART_FRAGMENT, true, true, null)
            }
        }
    }


    fun gettingProductData() {
        CoroutineScope(Dispatchers.Main).launch {
            // 상품의 정보를 가져온다. (연동 On)
            productList = ProductDao.gettingProductAll()

            productListFilter()
            settingRecyclerViewCategoryMain()
        }
    }

    // 하단 바 설정
    fun bottomSheetSetting() {
        fragmentCategoryMainBinding.apply {
            mainBottomNavi.setOnItemSelectedListener { item ->
                when (item.itemId) {
                    R.id.main_bottom_navi_home -> {
                        mainActivity.replaceFragment(
                            MainFragmentName.HOME_MAIN_FULL,
                            false,
                            false,
                            mainActivity.bundle
                        )
                    }

                    R.id.main_bottom_navi_category -> {
                        mainActivity.replaceFragment(
                            MainFragmentName.CATEGORY_MAIN_FRAGMENT,
                            false,
                            false,
                            null
                        )
                    }

                    R.id.main_bottom_navi_like -> {
                        mainActivity.replaceFragment(MainFragmentName.LIKE, false, false, null)
                    }

                    else -> {
                        mainActivity.replaceFragment(
                            MainFragmentName.USER_MYPAGE_FRAGMENT,
                            false,
                            false,
                            null
                        )
                    }
                }
                true
            }
        }
    }

    // 하단 바 홈으로 체크 표시 설정
    fun settingBottomTabs() {
        fragmentCategoryMainBinding.apply {
            val menuItemId = R.id.main_bottom_navi_category
            fragmentCategoryMainBinding.mainBottomNavi.menu.findItem(menuItemId)?.isChecked =
                true
        }
    }

}