package kr.co.lion.team4.mrco.fragment.review

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.divider.MaterialDividerItemDecoration
import kr.co.lion.team4.mrco.MainActivity
import kr.co.lion.team4.mrco.R
import kr.co.lion.team4.mrco.databinding.FragmentReviewCreatedBinding
import kr.co.lion.team4.mrco.databinding.RowReviewCreatedBinding
import kr.co.lion.team4.mrco.viewmodel.ReviewCreatedViewModel
import kr.co.lion.team4.mrco.viewmodel.RowReviewCreatedViewModel

class ReviewCreatedFragment : Fragment() {


//    ### 작성한 리뷰 화면 ###
//
//    xml 파일 : fragment_review_created.xml
//    viewModel 파일 : ReviewCreatedViewModel.kt
//
//    recyclerview id : @id/recyclerview_reviewCreated
//    recyclerview 관련 viewModel파일 : RowReviewCreatedViewModel.kt
//    recyclerview 행 xml 파일 : row_review_created.xml
//    recyclerview 행 ViewModel : RowReviewCreatedViewModel.kt

    private lateinit var binding: FragmentReviewCreatedBinding
    private lateinit var viewModel: ReviewCreatedViewModel
    private lateinit var mainActivity: MainActivity

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        mainActivity = activity as MainActivity
        viewModel = ViewModelProvider(this).get(ReviewCreatedViewModel::class.java)
        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_review_created, container, false)
        binding.reviewCreatedViewModel = viewModel
        binding.lifecycleOwner = this

        settingRecyclerView()

        return binding.root
    }

    fun settingRecyclerView() {
        binding.apply {
            recyclerviewReviewCreated.apply {
                // 어댑터
                adapter = ReviewCreatedAdapter()
                // 레이아웃 매니저
                layoutManager = LinearLayoutManager(mainActivity) // 기본설정 -> VERTICAL
                // 구분선
                val deco = MaterialDividerItemDecoration(
                    mainActivity,
                    MaterialDividerItemDecoration.VERTICAL
                )
                addItemDecoration(deco)
            }
        }
    }

    inner class ReviewCreatedAdapter :
        RecyclerView.Adapter<ReviewCreatedAdapter.ReviewCreatedViewHolder>() {
        // ViewHolder
        inner class ReviewCreatedViewHolder(binding: RowReviewCreatedBinding) :
            RecyclerView.ViewHolder(binding.root) {
            val binding: RowReviewCreatedBinding

            init {
                this.binding = binding
                this.binding.root.layoutParams = ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT
                )
            }
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReviewCreatedViewHolder {
            val binding = DataBindingUtil.inflate<RowReviewCreatedBinding>(
                layoutInflater,
                R.layout.row_review_created,
                parent,
                false
            )
            val viewModel = RowReviewCreatedViewModel()
            binding.rowReviewCreatedViewModel = viewModel
            binding.lifecycleOwner = this@ReviewCreatedFragment

            val viewHolder = ReviewCreatedViewHolder(binding)
            return viewHolder
        }

        override fun getItemCount(): Int {
            return 10
        }

        override fun onBindViewHolder(holder: ReviewCreatedViewHolder, position: Int) {
            // 넘어가는 화면 있나?
        }
    }
}