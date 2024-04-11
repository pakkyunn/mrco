package kr.co.lion.team4.mrco.fragment.category

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kr.co.lion.team4.mrco.MainActivity
import kr.co.lion.team4.mrco.MainFragmentName
import kr.co.lion.team4.mrco.R
import kr.co.lion.team4.mrco.databinding.FragmentCategoryBinding
import kr.co.lion.team4.mrco.databinding.FragmentCategoryMainBinding
import kr.co.lion.team4.mrco.databinding.RowCategoryMainBinding
import kr.co.lion.team4.mrco.databinding.RowCategorySemiCategoryBinding

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

        // 리사이클러 뷰
        settingRecyclerViewCategory()

        return fragmentCategoryMainBinding.root
    }

    // 툴바 설정
    fun toolbarSetting(){
        fragmentCategoryMainBinding.toolbarCoordinatorMain.apply {
            // 네비게이션
            setNavigationIcon(R.drawable.arrow_back_24px)
            setNavigationOnClickListener {
                backProcesss()
            }
            // setNavigationIcon(R.drawable.event_list_24px)
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
                mainActivity.startProductActivity()
            }
        }
    }

    // 뒤로가기 처리
    fun backProcesss(){
        mainActivity.removeFragment(MainFragmentName.CATEGORY_MAIN_FRAGMENT)
    }
}