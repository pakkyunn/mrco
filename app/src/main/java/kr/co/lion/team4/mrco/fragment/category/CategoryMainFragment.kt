package kr.co.lion.team4.mrco.fragment.category

import android.os.Bundle
import android.os.SystemClock
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kr.co.lion.team4.mrco.MainActivity
import kr.co.lion.team4.mrco.MainFragmentName
import kr.co.lion.team4.mrco.R
import kr.co.lion.team4.mrco.databinding.FragmentCategoryMainBinding
import kr.co.lion.team4.mrco.databinding.HeaderCategoryDrawerBinding
import kr.co.lion.team4.mrco.databinding.RowCategoryMainBinding

class CategoryMainFragment : Fragment() {

    lateinit var fragmentCategoryMainBinding: FragmentCategoryMainBinding
    lateinit var mainActivity: MainActivity

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment

        fragmentCategoryMainBinding = FragmentCategoryMainBinding.inflate(inflater)
        mainActivity = activity as MainActivity

        // 툴바, 하단바, 탭 관련
        toolbarSetting()
        settingBottomTabs()
        settingNavigationView()
        mainActivity.viewBottomSheet()

        // 리사이클러 뷰
        settingRecyclerViewCategory()

        return fragmentCategoryMainBinding.root
    }

    // 툴바 설정
    fun toolbarSetting(){
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
                        mainActivity.replaceFragment(MainFragmentName.CART_FRAGMENT, true, true, null)
                    }
                }
                true
            }
        }
    }

    // 네비게이션 뷰 설정
    fun settingNavigationView(){
        fragmentCategoryMainBinding.apply {
            navigationViewContent.apply {

                // 헤더로 보여줄 View를 생성한다.
                val headerCategoryDrawerBinding = HeaderCategoryDrawerBinding.inflate(layoutInflater)
                // 헤더로 보여줄 View를 설정한다.
                addHeaderView(headerCategoryDrawerBinding.root)

                // 메뉴를 눌렀을 때 동작하는 리스너
                setNavigationItemSelectedListener {

                    // 딜레이
                    SystemClock.sleep(50)

                    when(it.itemId) {
                        R.id.menuItemCategoryNavigationMbti -> {

                            // NavigationView를 닫아준다.
                            drawerLayoutContent.close()
                        }
                        //
                        R.id.menuItemCategoryNavigationTpo -> {

                            // NavigationView를 닫아준다.
                            drawerLayoutContent.close()
                        }
                        //
                        R.id.menuItemCategoryNavigationSeason -> {

                            // NavigationView를 닫아준다.
                            drawerLayoutContent.close()
                        }
                        //
                        R.id.menuItemCategoryNavigationMood -> {

                            // NavigationView를 닫아준다.
                            drawerLayoutContent.close()
                        }
                        //
                        R.id.menuItemCategoryNavigationAll -> {

                            // NavigationView를 닫아준다.
                            drawerLayoutContent.close()
                        }
                    }
                    true
                }

            }
        }
    }

    // 하단 바 홈으로 체크 표시 설정
    fun settingBottomTabs() {
        mainActivity.activityMainBinding.apply {
            val menuItemId = R.id.main_bottom_navi_category
            mainActivity.activityMainBinding.mainBottomNavi.menu.findItem(menuItemId)?.isChecked = true
        }
    }

    // 리사이클러 뷰 설정
    fun settingRecyclerViewCategory() {
        fragmentCategoryMainBinding.apply {
            recyclerViewCategoryMain.apply {
                adapter = CategoryMainRecyclerViewAdapter()
                layoutManager = GridLayoutManager(mainActivity, 2)
            }
        }
    }

    // 리사이클러 뷰 어뎁터
    inner class CategoryMainRecyclerViewAdapter: RecyclerView.Adapter<CategoryMainRecyclerViewAdapter.CategoryMainViewHolder>(){
        inner class CategoryMainViewHolder(rowCategoryMainBinding: RowCategoryMainBinding): RecyclerView.ViewHolder(rowCategoryMainBinding.root){
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
            val rowCategoryMainBinding = RowCategoryMainBinding.inflate(layoutInflater)
            val categoryMainViewHolder = CategoryMainViewHolder(rowCategoryMainBinding)

            return categoryMainViewHolder
        }

        override fun getItemCount(): Int {
            return 20
        }

        override fun onBindViewHolder(holder: CategoryMainViewHolder, position: Int) {
            // 이미지 클릭시
            holder.rowCategoryMainBinding.itemMainProductThumbnail.setOnClickListener {
                mainActivity.replaceFragment(MainFragmentName.PRODUCT_FRAGMENT,true,true,null)
            }
        }
    }

    // 뒤로가기 처리
    fun backProcess(){
        mainActivity.removeFragment(MainFragmentName.CATEGORY_MAIN_FRAGMENT)
    }
}