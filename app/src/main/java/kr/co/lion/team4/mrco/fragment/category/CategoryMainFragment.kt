package kr.co.lion.team4.mrco.fragment.category

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.Menu
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.chip.Chip
import kr.co.lion.team4.mrco.MainActivity
import kr.co.lion.team4.mrco.MainFragmentName
import kr.co.lion.team4.mrco.R
import kr.co.lion.team4.mrco.databinding.FragmentCategoryMainBinding
import kr.co.lion.team4.mrco.databinding.HeaderCategoryDrawerBinding
import kr.co.lion.team4.mrco.databinding.RowCategoryDrawerBinding
import kr.co.lion.team4.mrco.databinding.RowCategoryMainBinding
import kr.co.lion.team4.mrco.viewmodel.category.RowCategoryMainViewModel

class CategoryMainFragment : Fragment() {

    lateinit var fragmentCategoryMainBinding: FragmentCategoryMainBinding
    lateinit var mainActivity: MainActivity
    lateinit var headerCategoryDrawerBinding: HeaderCategoryDrawerBinding


    var mbtiData = mutableListOf<String>(
        "ENFJ", "ENFP", "ENTJ", "ENTP", "ESFJ", "ESFP", "ESTJ", "ESTP",
        "INFJ", "INFP", "INTJ", "INTP", "ISFJ", "ISFP", "ISTJ", "ISTP"
    )
    var tpoData = mutableListOf<String>("여행", "데이트", "카페", "출근", "데일리", "캠퍼스", "바다", "결혼식")
    var seasonData = mutableListOf<String>("봄", "여름", "가을", "겨울")
    var moodData = mutableListOf<String>(
        "미니멀",
        "비즈니스 캐주얼",
        "원마일웨어",
        "아메카지",
        "시티보이",
        "스트릿",
        "스포티",
        "레트로",
        "러블리",
        "모던캐주얼"
    )
    var allData = mutableListOf<String>(
        "ENFJ",
        "ENFP",
        "ENTJ",
        "ENTP",
        "ESFJ",
        "ESFP",
        "ESTJ",
        "ESTP",
        "INFJ",
        "INFP",
        "INTJ",
        "INTP",
        "ISFJ",
        "ISFP",
        "ISTJ",
        "ISTP",
        "여행",
        "데이트",
        "카페",
        "출근",
        "데일리",
        "캠퍼스",
        "바다",
        "결혼식",
        "봄",
        "여름",
        "가을",
        "겨울",
        "미니멀",
        "비즈니스캐주얼",
        "원마일웨어",
        "아메카지",
        "시티보이",
        "스트릿",
        "스포티",
        "레트로",
        "러블리",
        "모던캐주얼"
    )

    var chipIdx = 0
    var clickIdx = false

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        fragmentCategoryMainBinding = FragmentCategoryMainBinding.inflate(inflater)
        headerCategoryDrawerBinding = HeaderCategoryDrawerBinding.inflate(inflater)

        mainActivity = activity as MainActivity

        // 툴바, 하단바, 탭 관련
        toolbarSetting()
        bottomSheetSetting()
        settingBottomTabs()
        settingNavigationView()

        settingCategoryFilterButton()

        // 리사이클러 뷰 메인
        settingRecyclerViewCategoryMain()

        return fragmentCategoryMainBinding.root
    }

    // 툴바 설정
    fun toolbarSetting() {
        fragmentCategoryMainBinding.toolbarCoordinatorMain.apply {
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

//    fun settingChipClickIdx(chip: Chip) {
//        fragmentCategoryMainBinding.apply {
//            navigationViewContent.apply {
//                val headerCategoryDrawerBinding =
//                    HeaderCategoryDrawerBinding.inflate(layoutInflater)
//                headerCategoryDrawerBinding.apply {
//                    chipIdx = when (chip) {
//                        chipCategoriesMbti -> R.id.chipCategoriesMbti
//                        chipCategoriesTpo -> R.id.chipCategoriesTpo
//                        chipCategoriesSeason -> R.id.chipCategoriesSeason
//                        chipCategoriesMood -> R.id.chipCategoriesMood
//                        else -> 0
//                    }
//                }
//            }
//        }
//    }


    fun settingCategoryFilterButton() {
        fragmentCategoryMainBinding.apply {
            buttonCategoryFilter!!.setOnClickListener {
                fragmentCategoryMainBinding.drawerLayoutContent.open()

            }
        }
    }


//    fun checkSync(menu: Menu, menuItemIds: Array<Int>) {
//        menuItemIds.forEach { menuItemId ->
//            val menuItem = menu.findItem(menuItemId)
//            val checkBox = menuItem?.actionView?.findViewById<CheckBox>(R.id.drawerCheckbox)
//
//            checkBox?.setOnCheckedChangeListener { buttonView, isChecked ->
//                menuItem.isChecked = !menuItem.isChecked
//                menuItem.isCheckable = false
//            }
//        }
//    }


    fun resetMenuItems(menu: Menu, menuItemIds: Array<Int>) {
        menuItemIds.forEach { menuItemId ->
            val menuItem = menu.findItem(menuItemId)
            menuItem?.isChecked = false
            menuItem?.isCheckable = false
        }
    }

    fun settingCategoryChipChange(chip: Chip) {
        fragmentCategoryMainBinding.apply {
            navigationViewContent.apply {
                headerCategoryDrawerBinding.apply {

                    Log.d("testclick", "칩 클릭 전")

                    chip.setOnCheckedChangeListener { _, isChecked ->
                        Log.d("testclick", "칩 클릭: $isChecked")

                        chipIdx = R.id.chipCategoriesMbti
                        Log.d("testclick", "칩 인덱스 변경: $chipIdx")

                        recyclerViewCategoryDrawer.adapter?.notifyDataSetChanged()
                        Log.d("testclick", "리싸이클러뷰 새로고침")

                        if (chip.isChecked) {
                            recyclerViewCategoryDrawer.isVisible = true
                        } else {
                            recyclerViewCategoryDrawer.isVisible = false
                        }
                        Log.d(
                            "testclick",
                            "리싸이클러뷰 상태 변경: ${recyclerViewCategoryDrawer.isVisible}"
                        )

                    }


//                    // chip(4개)을 클릭할 때 동작하는 리스너
//                    chip.setOnCheckedChangeListener { _, isChecked ->
//                        Log.d("testclick","칩 클릭: $chip")
//
//                        chipIdx = when (chip) {
//                            chipCategoriesMbti -> R.id.chipCategoriesMbti
//                            chipCategoriesTpo -> R.id.chipCategoriesTpo
//                            chipCategoriesSeason -> R.id.chipCategoriesSeason
//                            chipCategoriesMood -> R.id.chipCategoriesMood
//                            else -> R.id.chipCategoriesMbti
//                        }
//                        Log.d("testclick","칩 인덱스 변경: $chipIdx")
//
//                        if (isChecked) {
//                            recyclerViewCategoryDrawer.isVisible = true
//                        } else {
//                            recyclerViewCategoryDrawer.isVisible = false
//                        }
//                        Log.d("testclick","리싸이클러뷰 상태 변경: ${recyclerViewCategoryDrawer.isVisible}")
//
//                        recyclerViewCategoryDrawer.adapter?.notifyDataSetChanged()
//                        Log.d("testclick","어댑터 새로고침")
//                    }
                }
            }
        }
    }


    // 네비게이션 뷰 설정
    fun settingNavigationView() {
        fragmentCategoryMainBinding.apply {
            navigationViewContent.apply {
//                // 헤더로 보여줄 View를 설정한다.
//                addHeaderView(headerCategoryDrawerBinding.root)


                headerCategoryDrawerBinding.apply {
                    settingRecyclerViewCategoryDrawer()

                    // 리셋 버튼 클릭시
                    buttonDrawerCategoryReset.setOnClickListener {

                    }

                    // 적용 버튼 클릭시
                    buttonDrawerCategoryApply.setOnClickListener {
                        // check된 카테고리를 적용하여 검색결과를 CategoryMainFragment에 노출한다.


                        // NavigationView를 닫아준다.
                        drawerLayoutContent.close()
                    }

                    // 검색바 기능 세팅
                    searchBarDrawer.apply {
                        setOnClickListener {
                            // 모든 칩 선택해제
                            chipCategoriesMbti.isChecked = false
                            chipCategoriesTpo.isChecked = false
                            chipCategoriesSeason.isChecked = false
                            chipCategoriesMood.isChecked = false
                        }

                        // 검색 완료하면 ALL칩 선택 후 검색결과에 맞는 메뉴항목 보여주기
                    }

                    // chip을 클릭할 때 동작하는 리스너 메서드
                    settingCategoryChipChange(chipCategoriesMbti)
                    settingCategoryChipChange(chipCategoriesTpo)
                    settingCategoryChipChange(chipCategoriesSeason)
                    settingCategoryChipChange(chipCategoriesMood)

                }
            }
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
            return 24
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
            holder.rowCategoryMainBinding.itemMainProductThumbnail.setImageResource(
                imageResource
            )

            // 이미지 -> 상품 이미지 클릭 시
            holder.rowCategoryMainBinding.root.setOnClickListener {
                mainActivity.replaceFragment(
                    MainFragmentName.PRODUCT_FRAGMENT,
                    true,
                    true,
                    null
                )
            }

            // 버튼 -> 카트 담기 클릭 시
            holder.rowCategoryMainBinding.buttonRowCategoryMainCart.setOnClickListener {
                mainActivity.replaceFragment(MainFragmentName.CART_FRAGMENT, true, true, null)
            }
        }
    }


    // drawer 리사이클러 뷰 설정
    fun settingRecyclerViewCategoryDrawer() {
        fragmentCategoryMainBinding.apply {
            navigationViewContent.apply {
                addHeaderView(headerCategoryDrawerBinding.root)
                headerCategoryDrawerBinding.apply {
                    recyclerViewCategoryDrawer.apply {
                        adapter = CategoryDrawerRecyclerViewAdapter()
                        layoutManager = LinearLayoutManager(mainActivity)
                    }
                }
            }
        }
    }

    // drawer 리사이클러 뷰 어뎁터
    inner class CategoryDrawerRecyclerViewAdapter :
        RecyclerView.Adapter<CategoryDrawerRecyclerViewAdapter.CategoryDrawerViewHolder>() {
        inner class CategoryDrawerViewHolder(rowCategoryDrawerBinding: RowCategoryDrawerBinding) :
            RecyclerView.ViewHolder(rowCategoryDrawerBinding.root) {
            val rowCategoryDrawerBinding: RowCategoryDrawerBinding

            init {
                this.rowCategoryDrawerBinding = rowCategoryDrawerBinding

                this.rowCategoryDrawerBinding.root.layoutParams = ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT
                )
            }
        }

        override fun onCreateViewHolder(
            parent: ViewGroup,
            viewType: Int
        ): CategoryDrawerViewHolder {
            val rowCategoryDrawerBinding = DataBindingUtil.inflate<RowCategoryDrawerBinding>(
                layoutInflater, R.layout.row_category_drawer, parent, false
            )

            rowCategoryDrawerBinding.lifecycleOwner = this@CategoryMainFragment

            val categoryDrawerViewHolder = CategoryDrawerViewHolder(rowCategoryDrawerBinding)

            return categoryDrawerViewHolder
        }

        override fun getItemCount(): Int {
//            return when (chipIdx) {
//                R.id.chipCategoriesMbti -> mbtiData.size
//                R.id.chipCategoriesTpo -> tpoData.size
//                R.id.chipCategoriesSeason -> seasonData.size
//                R.id.chipCategoriesMood -> moodData.size
//                else -> 10
//            }
            return 10
        }

        override fun onBindViewHolder(holder: CategoryDrawerViewHolder, position: Int) {
            holder.rowCategoryDrawerBinding.rowDrawerButton.text = "test"
//            when (chipIdx) {
//                R.id.chipCategoriesMbti -> {
////                    mbtiData.forEach {
////                        holder.rowCategoryDrawerBinding.rowDrawerButton.text = it
////                    }
//                    holder.rowCategoryDrawerBinding.root.setOnClickListener {
//
//                    }
//                }
//
//                R.id.chipCategoriesTpo -> {
//                    tpoData.forEach {
//                        holder.rowCategoryDrawerBinding.rowDrawerButton.text = it
//                    }
//                }
//
//                R.id.chipCategoriesSeason -> {
//                    seasonData.forEach {
//                        holder.rowCategoryDrawerBinding.rowDrawerButton.text = it
//                    }
//                }
//
//                R.id.chipCategoriesMood -> {
//                    moodData.forEach {
//                        holder.rowCategoryDrawerBinding.rowDrawerButton.text = it
//                    }
//                }
//
//                else -> {}
//            }
//
            // 클릭시
            holder.rowCategoryDrawerBinding.rowDrawerButton.setOnClickListener {
                holder.rowCategoryDrawerBinding.rowDrawerButton.apply {
                    isActivated = !isActivated
                    Log.d("clickTest", "리싸이클러뷰 항목 활성화 상태 변경")
                    Log.d(
                        "clickTest",
                        "${holder.rowCategoryDrawerBinding.rowDrawerButton.isActivated}"
                    )
                    if (isActivated) {
                        setBackgroundColor(Color.parseColor("#C5C5C5"))
                    } else {
                        setBackgroundColor(Color.parseColor("#E4E4E4"))
                    }
                }
            }
        }
    }
}