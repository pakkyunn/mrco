package kr.co.lion.team4.mrco.fragment.category

import android.graphics.Color
import android.os.Bundle
import android.os.SystemClock
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.CompoundButton
import androidx.activity.OnBackPressedCallback
import androidx.core.view.GravityCompat
import androidx.core.view.isInvisible
import androidx.databinding.DataBindingUtil
import androidx.drawerlayout.widget.DrawerLayout
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.chip.Chip
import com.google.android.material.navigation.NavigationView
import kr.co.lion.team4.mrco.MainActivity
import kr.co.lion.team4.mrco.MainFragmentName
import kr.co.lion.team4.mrco.R
import kr.co.lion.team4.mrco.databinding.FragmentCategoryMainBinding
import kr.co.lion.team4.mrco.databinding.HeaderCategoryDrawerBinding
import kr.co.lion.team4.mrco.databinding.RowCategoryMainBinding
import kr.co.lion.team4.mrco.viewmodel.category.RowCategoryMainViewModel

class CategoryMainFragment : Fragment() {

    lateinit var fragmentCategoryMainBinding: FragmentCategoryMainBinding
    lateinit var mainActivity: MainActivity

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
    val menuItemsALL = arrayOf(
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

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        fragmentCategoryMainBinding = FragmentCategoryMainBinding.inflate(inflater)
        mainActivity = activity as MainActivity

        // 툴바, 하단바, 탭 관련
        toolbarSetting()
        bottomSheetSetting()
        settingBottomTabs()
        settingNavigationView()

        // 리사이클러 뷰
        settingRecyclerViewCategory()

        return fragmentCategoryMainBinding.root
    }

    // 툴바 설정
    fun toolbarSetting() {
        fragmentCategoryMainBinding.toolbarCoordinatorMain.apply {
            // 네비게이션
            setNavigationOnClickListener {
                // 여기 햄버거 메뉴 나오게 하기
                // Drawer 메뉴가 나타나게 한다.
                fragmentCategoryMainBinding.drawerLayoutContent.open()
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


    fun resetMenuItems(menu: Menu, menuItemIds: Array<Int>) {
        menuItemIds.forEach { menuItemId ->
            val menuItem = menu.findItem(menuItemId)
            val checkBox = menuItem?.actionView?.findViewById<CheckBox>(R.id.drawerCheckbox)
            checkBox?.isChecked = false
            menuItem?.isChecked = false
            menuItem?.isCheckable = false
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
                val headerCategoryDrawerBinding =
                    HeaderCategoryDrawerBinding.inflate(layoutInflater)

                // 헤더로 보여줄 View를 설정한다.
                addHeaderView(headerCategoryDrawerBinding.root)


                headerCategoryDrawerBinding.apply {

                    // 리셋 버튼 클릭시
                    buttonDrawerCategoryReset.setOnClickListener {
                        resetMenuItems(menu, menuItemsALL)
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
                            chipCategoriesAll.isChecked = false
                        }

                        // 검색 완료하면 ALL칩 선택 후 검색결과에 맞는 메뉴항목 보여주기
                    }

                    // chip을 클릭할 때 동작하는 리스너 메서드
                    settingCategoryChipChange(chipCategoriesMbti, menuItemsMbti)
                    settingCategoryChipChange(chipCategoriesTpo, menuItemsTpo)
                    settingCategoryChipChange(chipCategoriesSeason, menuItemsSeason)
                    settingCategoryChipChange(chipCategoriesMood, menuItemsMood)
                    settingCategoryChipChange(chipCategoriesAll, menuItemsALL)


                    // 메뉴항목을 클릭했을 때 동작하는 리스너 메서드
                    settingCategoryMenuClick(menu, menuItemsALL)

                    // 체크박스 클릭과 메뉴항목 클릭 상태 동기화 메서드
                    checkSync(menu, menuItemsALL)
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
                            null
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
            fragmentCategoryMainBinding.mainBottomNavi.menu.findItem(menuItemId)?.isChecked = true
        }
    }

    // 리사이클러 뷰 설정
    fun settingRecyclerViewCategory() {
        fragmentCategoryMainBinding.apply {
            val screenWidthDp = resources.configuration.screenWidthDp
            if (screenWidthDp >= 600 || screenWidthDp < 720) {
                // 너비가 600dp 이상인 디바이스에서 실행될 동작
                recyclerViewCategoryMain.apply {
                    adapter = CategoryMainRecyclerViewAdapter()
                    layoutManager = GridLayoutManager(mainActivity, 4)
                }
            } else if (screenWidthDp >= 720) {
                // 너비가 720dp 이상인 디바이스에서 실행될 동작
                recyclerViewCategoryMain.apply {
                    adapter = CategoryMainRecyclerViewAdapter()
                    layoutManager = GridLayoutManager(mainActivity, 6)
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

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryMainViewHolder {
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
            holder.rowCategoryMainBinding.itemMainProductThumbnail.setImageResource(imageResource)

            // 이미지 -> 상품 이미지 클릭 시
            holder.rowCategoryMainBinding.root.setOnClickListener {
                mainActivity.replaceFragment(MainFragmentName.PRODUCT_FRAGMENT, true, true, null)
            }

            // 버튼 -> 카트 담기 클릭 시
            holder.rowCategoryMainBinding.buttonRowCategoryMainCart.setOnClickListener {
                mainActivity.replaceFragment(MainFragmentName.CART_FRAGMENT, true, true, null)
            }
        }
    }


    // 뒤로가기 처리
    fun backProcess() {
        mainActivity.removeFragment(MainFragmentName.CATEGORY_MAIN_FRAGMENT)
    }
}