package kr.co.lion.team4.mrco.fragment.category

import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.graphics.drawable.GradientDrawable.Orientation
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kr.co.lion.team4.mrco.MainActivity
import kr.co.lion.team4.mrco.MainFragmentName
import kr.co.lion.team4.mrco.R
import kr.co.lion.team4.mrco.databinding.FragmentCategoryBinding
import kr.co.lion.team4.mrco.databinding.RowCategorySemiCategory2Grid4Binding
import kr.co.lion.team4.mrco.databinding.RowCategorySemiCategoryBinding
import kr.co.lion.team4.mrco.viewmodel.category.CategoryViewModel

class CategoryFragment : Fragment() {

    lateinit var fragmentCategoryBinding: FragmentCategoryBinding
    lateinit var mainActivity: MainActivity
    lateinit var categoryViewModel: CategoryViewModel

    var mbtiData = mutableListOf<String>("ENFJ", "ENFP", "ENTJ", "ENTP", "ESFJ", "ESFP", "ESTJ", "ESTP",
        "INFJ", "INFP", "INTJ", "INTP", "ISFJ", "ISFP", "ISTJ", "ISTP")
    var tpoData = mutableListOf<String>("여행", "데이트", "카페", "출근", "데일리", "캠퍼스", "바다", "결혼식")
    var seasonData = mutableListOf<String>("봄", "여름", "가을", "겨울")
    var moodData = mutableListOf<String>("미니멀", "비즈니스 캐주얼", "원마일웨어", "아메카지", "시티보이", "스트릿", "스포티", "레트로", "러블리", "모던캐주얼")
    var allData = mutableListOf<String>("전체", "ENFJ", "ENFP", "ENTJ", "ENTP", "ESFJ", "ESFP", "ESTJ", "ESTP",
        "INFJ", "INFP", "INTJ", "INTP", "ISFJ", "ISFP", "ISTJ", "ISTP", "여행", "데이트", "카페", "출근", "데일리", "캠퍼스", "바다", "결혼식",
        "봄", "여름", "가을", "겨울", "미니멀", "비즈니스\n 캐주얼", "원마일웨어", "아메카지", "시티보이", "스트릿", "스포티", "레트로", "러블리", "모던캐주얼")

    var checkMbtiCategory = true
    var checkTpoCategory = false
    var checkSeasonCategory = false
    var checkMoodCategory = false
    var checkAllCategory = false
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        fragmentCategoryBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_category, container, false)
        categoryViewModel = CategoryViewModel()
        fragmentCategoryBinding.categoryViewModel = categoryViewModel
        fragmentCategoryBinding.lifecycleOwner = this

        mainActivity = activity as MainActivity

        // 리사이클러 뷰
        settingRecyclerViewCategory()

        // 카테고리 클릭 시
        categoriTextViewClick()

        return fragmentCategoryBinding.root
    }

    // 리사이클러 뷰 설정
    fun settingRecyclerViewCategory() {
        fragmentCategoryBinding.apply {
            recyclerViewCategory.apply {
                if (checkMbtiCategory == true || checkAllCategory == true){
                    // 어뎁터 및 레이아웃 매니저 설정
                    adapter = Category2RecyclerViewAdapter()
                    layoutManager = GridLayoutManager(mainActivity, 4)
                }
                else {
                    // 어뎁터 및 레이아웃 매니저 설정
                    adapter = CategoryRecyclerViewAdapter()
                    layoutManager = GridLayoutManager(mainActivity, 2)
                }
            }
        }
    }

    // 리사이클러 뷰 어뎁터
    inner class CategoryRecyclerViewAdapter: RecyclerView.Adapter<CategoryRecyclerViewAdapter.CategoryViewHolder>(){
        inner class CategoryViewHolder(rowCategorySemiCategoryBinding: RowCategorySemiCategoryBinding): RecyclerView.ViewHolder(rowCategorySemiCategoryBinding.root){
            val rowCategorySemiCategoryBinding: RowCategorySemiCategoryBinding

            init {
                this.rowCategorySemiCategoryBinding = rowCategorySemiCategoryBinding

                this.rowCategorySemiCategoryBinding.root.layoutParams = ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT
                )
            }
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
            val rowCategorySemiCategoryBinding = RowCategorySemiCategoryBinding.inflate(layoutInflater)
            val categoryViewHolder = CategoryViewHolder(rowCategorySemiCategoryBinding)

            return categoryViewHolder
        }

        override fun getItemCount(): Int {
            if (checkTpoCategory) {
                return tpoData.size
            }
            else if (checkSeasonCategory) {
                return seasonData.size
            }
            // MOOD
            else {
                return moodData.size
            }
        }

        override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
            if (checkTpoCategory) {
                holder.rowCategorySemiCategoryBinding.textViewSemiCategory.text = tpoData[position]
            } else if (checkSeasonCategory) {
                holder.rowCategorySemiCategoryBinding.textViewSemiCategory.text = seasonData[position]
            } else {
                holder.rowCategorySemiCategoryBinding.textViewSemiCategory.text = moodData[position]
            }

            // 이미지 클릭시
            holder.rowCategorySemiCategoryBinding.imageViewRowCategorySemiCategory.setOnClickListener {
                mainActivity.replaceFragment(MainFragmentName.CATEGORY_MAIN_FRAGMENT, true, true, null)
            }
        }
    }

    // MBTI 및 All 리사이클러 뷰 어뎁터
    inner class Category2RecyclerViewAdapter: RecyclerView.Adapter<Category2RecyclerViewAdapter.CategoryViewHolder>(){
        inner class CategoryViewHolder(rowCategorySemiCategory2Grid4Binding: RowCategorySemiCategory2Grid4Binding): RecyclerView.ViewHolder(rowCategorySemiCategory2Grid4Binding.root){
            val rowCategorySemiCategory2Grid4Binding: RowCategorySemiCategory2Grid4Binding

            init {
                this.rowCategorySemiCategory2Grid4Binding = rowCategorySemiCategory2Grid4Binding

                this.rowCategorySemiCategory2Grid4Binding.root.layoutParams = ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT
                )
            }
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
            val rowCategorySemiCategory2Grid4Binding = RowCategorySemiCategory2Grid4Binding.inflate(layoutInflater)
            val categoryViewHolder = CategoryViewHolder(rowCategorySemiCategory2Grid4Binding)

            return categoryViewHolder
        }

        override fun getItemCount(): Int {
            if (checkMbtiCategory) {
                return mbtiData.size
            }
            // All
            else {
                return allData.size
            }
        }

        override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
            if (checkMbtiCategory) {
                holder.rowCategorySemiCategory2Grid4Binding.textViewSemiCategory.text = mbtiData[position]
            } else {
                holder.rowCategorySemiCategory2Grid4Binding.textViewSemiCategory.text = allData[position]
            }
            // 이미지 클릭시
            holder.rowCategorySemiCategory2Grid4Binding.imageViewRowCategorySemiCategory.setOnClickListener {
                mainActivity.replaceFragment(MainFragmentName.CATEGORY_MAIN_FRAGMENT, true, true, null)
            }
        }
    }

    // 카테고리 사이드 TextView 클릭 시
    fun categoriTextViewClick() {
        fragmentCategoryBinding.apply {

            textViewMbtiCategory.apply {
                setOnClickListener {
                    resetBackground()
                    setBackgroundColor(Color.parseColor("#80CDCDCD"))
                    setTextColor(Color.BLACK)
                    checkMbtiCategory = true
                    settingRecyclerViewCategory()
                    fragmentCategoryBinding.recyclerViewCategory.adapter?.notifyDataSetChanged()
                }
            }

            textViewTpoCategory.apply {
                setOnClickListener {
                    resetBackground()
                    setBackgroundColor(Color.parseColor("#80CDCDCD"))
                    setTextColor(Color.BLACK)
                    checkTpoCategory = true
                    settingRecyclerViewCategory()
                    fragmentCategoryBinding.recyclerViewCategory.adapter?.notifyDataSetChanged()
                }
            }


            textViewSeasonCategory.apply {
                setOnClickListener {
                    resetBackground()
                    setBackgroundColor(Color.parseColor("#80CDCDCD"))
                    setTextColor(Color.BLACK)
                    checkSeasonCategory = true
                    settingRecyclerViewCategory()
                    fragmentCategoryBinding.recyclerViewCategory.adapter?.notifyDataSetChanged()
                }
            }

            textViewMoodCategory.apply {
                setOnClickListener {
                    resetBackground()
                    setBackgroundColor(Color.parseColor("#80CDCDCD"))
                    setTextColor(Color.BLACK)
                    checkMoodCategory = true
                    settingRecyclerViewCategory()
                    fragmentCategoryBinding.recyclerViewCategory.adapter?.notifyDataSetChanged()
                }
            }

            textViewAllCategory.apply {
                setOnClickListener {
                    resetBackground()
                    setBackgroundColor(Color.parseColor("#80CDCDCD"))
                    setTextColor(Color.BLACK)
                    checkAllCategory = true
                    settingRecyclerViewCategory()
                    fragmentCategoryBinding.recyclerViewCategory.adapter?.notifyDataSetChanged()
                }
            }
        }
    }

    // 카테고리 선택 백그라운드 초기화
    fun resetBackground() {
        fragmentCategoryBinding.apply {
            textViewMbtiCategory.setBackgroundColor(Color.WHITE)
            textViewMbtiCategory.setTextColor(Color.parseColor("#656565"))
            textViewTpoCategory.setBackgroundColor(Color.WHITE)
            textViewTpoCategory.setTextColor(Color.parseColor("#656565"))
            textViewSeasonCategory.setBackgroundColor(Color.WHITE)
            textViewSeasonCategory.setTextColor(Color.parseColor("#656565"))
            textViewMoodCategory.setBackgroundColor(Color.WHITE)
            textViewMoodCategory.setTextColor(Color.parseColor("#656565"))
            textViewAllCategory.setBackgroundColor(Color.WHITE)
            textViewAllCategory.setTextColor(Color.parseColor("#656565"))

            checkMbtiCategory = false
            checkTpoCategory = false
            checkSeasonCategory = false
            checkMoodCategory = false
            checkAllCategory = false
        }
    }
}
