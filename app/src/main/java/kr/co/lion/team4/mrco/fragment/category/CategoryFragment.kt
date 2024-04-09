package kr.co.lion.team4.mrco.fragment.category

import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import kr.co.lion.team4.mrco.MainActivity
import kr.co.lion.team4.mrco.R
import kr.co.lion.team4.mrco.databinding.FragmentCategoryBinding
import kr.co.lion.team4.mrco.viewmodel.category.CategoryViewModel

class CategoryFragment : Fragment() {

    lateinit var fragmentCategoryBinding: FragmentCategoryBinding
    lateinit var mainActivity: MainActivity
    lateinit var categoryViewModel: CategoryViewModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        fragmentCategoryBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_category, container, false)
        categoryViewModel = CategoryViewModel()
        fragmentCategoryBinding.categoryViewModel = categoryViewModel
        fragmentCategoryBinding.lifecycleOwner = this

        mainActivity = activity as MainActivity

        categoriTextViewClick()

        return fragmentCategoryBinding.root
    }

    fun categoriTextViewClick() {
        fragmentCategoryBinding.apply {

            textViewMbtiCategory.apply {
                setOnClickListener {
                    resetBackground()
                    setBackgroundColor(Color.BLACK)
                    setTextColor(Color.WHITE)
                }
            }

            textViewTpoCategory.apply {
                setOnClickListener {
                    resetBackground()
                    setBackgroundColor(Color.BLACK)
                    setTextColor(Color.WHITE)
                }
            }


            textViewSeasonCategory.apply {
                setOnClickListener {
                    resetBackground()
                    setBackgroundColor(Color.BLACK)
                    setTextColor(Color.WHITE)
                }
            }

            textViewMoodCategory.apply {
                setOnClickListener {
                    resetBackground()
                    setBackgroundColor(Color.BLACK)
                    setTextColor(Color.WHITE)
                }
            }

            textViewAllCategory.apply {
                setOnClickListener {
                    resetBackground()
                    setBackgroundColor(Color.BLACK)
                    setTextColor(Color.WHITE)
                }
            }
        }
    }

    // 카테고리 선택 백그라운드 초기화
    fun resetBackground() {
        fragmentCategoryBinding.apply {
            textViewMbtiCategory.setBackgroundColor(Color.WHITE)
            textViewMbtiCategory.setTextColor(Color.BLACK)
            textViewTpoCategory.setBackgroundColor(Color.WHITE)
            textViewTpoCategory.setTextColor(Color.BLACK)
            textViewSeasonCategory.setBackgroundColor(Color.WHITE)
            textViewSeasonCategory.setTextColor(Color.BLACK)
            textViewMoodCategory.setBackgroundColor(Color.WHITE)
            textViewMoodCategory.setTextColor(Color.BLACK)
            textViewAllCategory.setBackgroundColor(Color.WHITE)
            textViewAllCategory.setTextColor(Color.BLACK)
        }
    }
}
