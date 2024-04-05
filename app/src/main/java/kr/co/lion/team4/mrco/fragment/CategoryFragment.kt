package kr.co.lion.team4.mrco.fragment

import android.graphics.Color
import android.os.Bundle
import android.os.SystemClock
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import kr.co.lion.team4.mrco.FragmentName
import kr.co.lion.team4.mrco.MainActivity
import kr.co.lion.team4.mrco.R
import kr.co.lion.team4.mrco.databinding.FragmentCategoryBinding
import kr.co.lion.team4.mrco.viewmodel.CategoryViewModel

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

        return fragmentCategoryBinding.root
    }

//    fun click() {
//        fragmentCategoryBinding.apply {
//            textViewMoodCategory.apply {
//                setOnClickListener {
//                    setBackgroundColor(Color.parseColor("#FFE5E9EE"))
//                }
//            }
//        }
//    }
}