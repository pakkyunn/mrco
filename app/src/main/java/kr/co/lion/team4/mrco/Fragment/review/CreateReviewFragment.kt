package kr.co.lion.team4.mrco.Fragment.review

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import kr.co.lion.team4.mrco.MainActivity
import kr.co.lion.team4.mrco.R
import kr.co.lion.team4.mrco.databinding.FragmentCreatedReviewBinding
import kr.co.lion.team4.mrco.viewmodel.CreateReviewViewModel

class CreateReviewFragment : Fragment() {
    private lateinit var binding: FragmentCreatedReviewBinding
    private lateinit var viewModel: CreateReviewViewModel
    private lateinit var mainActivity: MainActivity
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_created_review, container, false)
        viewModel = ViewModelProvider(this).get(CreateReviewViewModel::class.java)
        mainActivity = activity as MainActivity


        settingClickEvent()

        return binding.root
    }

    fun settingClickEvent(){
        binding.apply {
            // 전체 클릭시
            tabItemCreatedReviewAll.setOnClickListener {
                // 프래그먼트 컨테이너에 전체 리스트를 다 보여줌


            }

            // 최근 1년 클릭시
            tabItemCreatedReviewOneYear.setOnClickListener {
                // 프래그먼트 컨테이너에 최근 1년 리스트만 보여줌
            }
            // 기간 버튼 클릭시 -> 바텀시트를 띄운다
            iconButtonCreatedReviewCalendarDialog.setOnClickListener {
                // 바텀시트를 띄우고 바텀시트에서 기간설정 후 완료 버튼 클릭시 그 기간에 맞는 리스트를 띄워줌

            }
        }
    }
}