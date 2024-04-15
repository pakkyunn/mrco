package kr.co.lion.team4.mrco.fragment.salesManagement

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kr.co.lion.team4.mrco.MainActivity
import kr.co.lion.team4.mrco.MainFragmentName
import kr.co.lion.team4.mrco.R
import kr.co.lion.team4.mrco.databinding.FragmentSalesManagementCalendarBinding
import kr.co.lion.team4.mrco.databinding.RowSalesManagementCalBinding
import kr.co.lion.team4.mrco.viewmodel.salesManagement.SalesManagementCalendarViewModel
import java.text.SimpleDateFormat
import java.util.Date

class SalesManagementCalendarFragment : Fragment() {

    // 원빈 - 매출 관리(정산) - 내역 화면 (태진님 관련)

    lateinit var fragmentSalesManagementCalendarBinding: FragmentSalesManagementCalendarBinding
    lateinit var mainActivity: MainActivity

    lateinit var salesManagementCalendarViewModel: SalesManagementCalendarViewModel

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mainActivity = activity as MainActivity
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        fragmentSalesManagementCalendarBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_sales_management_calendar, container, false)
        salesManagementCalendarViewModel = SalesManagementCalendarViewModel()
        fragmentSalesManagementCalendarBinding.salesManagementCalendarViewModel = SalesManagementCalendarViewModel()
        fragmentSalesManagementCalendarBinding.lifecycleOwner = this

        // 툴바, 하단바, 탭 관련
        mainActivity.removeBottomSheet()

        // 리사이클러 뷰
        settingRecyclerViewSalesManagement()

        return fragmentSalesManagementCalendarBinding.root
    }


    // 리사이클러 뷰 설정
    fun settingRecyclerViewSalesManagement() {
        fragmentSalesManagementCalendarBinding.apply {
            recyclerViewSalesManagementCalendar.apply {
                // 어뎁터 및 레이아웃 매니저 설정
                adapter = SalesManagementCalendarRecyclerViewAdapter()
                layoutManager = LinearLayoutManager(mainActivity, LinearLayoutManager.HORIZONTAL, false)
            }
        }
    }

    // 리사이클러 뷰 어뎁터
    inner class SalesManagementCalendarRecyclerViewAdapter: RecyclerView.Adapter<SalesManagementCalendarRecyclerViewAdapter.SalesManagementCalViewHolder>(){
        inner class SalesManagementCalViewHolder(rowSalesManagementCalBinding: RowSalesManagementCalBinding): RecyclerView.ViewHolder(rowSalesManagementCalBinding.root){
            val rowSalesManagementCalBinding: RowSalesManagementCalBinding

            init {
                this.rowSalesManagementCalBinding = rowSalesManagementCalBinding

                this.rowSalesManagementCalBinding.root.layoutParams = ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT
                )
            }
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SalesManagementCalViewHolder {
            val rowSalesManagementCalBinding = RowSalesManagementCalBinding.inflate(layoutInflater)
            val salesManagementCalViewHolder = SalesManagementCalViewHolder(rowSalesManagementCalBinding)

            return salesManagementCalViewHolder
        }

        override fun getItemCount(): Int {
            return 6
        }

        override fun onBindViewHolder(holder: SalesManagementCalViewHolder, position: Int) {

            val simpleDateFormatMonth = SimpleDateFormat("MM")
            val simpleDateFormatDays = SimpleDateFormat("dd")
            val month = (simpleDateFormatMonth.format(Date())).toInt() // toInt 하는건 01월 이거를 1월로 바꿈
            val days = simpleDateFormatDays.format(Date()).toInt() // toInt 하는건 01일 이거를 1일로 바꿈 & 앞뒤 날짜 계산
            
            // 달마다 31일 30일 구분 -> % 이용해 구현 해야함 (아직 안함)
            // -1 붙인 이유 UI에 오늘 날짜가 중앙에 배치 (첫 순서에 어제 날짜가 나오게 끔)
            holder.rowSalesManagementCalBinding.textViewDate.text = "${month}월 ${days + position - 1}일"
        }
    }

}