package kr.co.lion.team4.mrco.fragment.appNotice

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kr.co.lion.team4.mrco.MainActivity
import kr.co.lion.team4.mrco.MainFragmentName
import kr.co.lion.team4.mrco.R
import kr.co.lion.team4.mrco.databinding.FragmentAppNoticeBinding
import kr.co.lion.team4.mrco.databinding.RowAppNoticeBinding

class AppNoticeFragment : Fragment() {

    lateinit var fragmentAppNoticeBinding: FragmentAppNoticeBinding
    lateinit var mainActivity: MainActivity
    override fun onCreateView( inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        fragmentAppNoticeBinding = FragmentAppNoticeBinding.inflate(inflater)
        mainActivity = activity as MainActivity

        // 하단 바 안보이게
        mainActivity.removeBottomSheet()
        // 툴바
        toolbarSetting()
        // 알림목록
        settingRecyclerViewAppNotice()
        return fragmentAppNoticeBinding.root
    }

//    // 알림 유무에 따라 보이고/사라지는 안내문구
//    fun settingEmpty() {
//        fragmentAppNoticeBinding.apply {
//            recyclerViewAppNotice.adapter?.notifyDataSetChanged()
//            if (data_size == 0) {
//                textViewEmptyAppNotice.visibility = View.VISIBLE
//                imageViewAppNotice.visibility = View.VISIBLE
//            } else {
//                textViewEmptyAppNotice.visibility = View.GONE
//                imageViewAppNotice.visibility = View.GONE
//            }
//        }
//    }

    // 툴바 설정
    fun toolbarSetting() {
        fragmentAppNoticeBinding.toolbarAppNotice.apply {
            setOnMenuItemClickListener {

                when (it.itemId) {
                    // x버튼 클릭 시
                    R.id.main_toolbar_close -> {
                        backProcesss()
                    }
                }
                true
            }
        }
    }

    // 뒤로가기 처리
    fun backProcesss() {
        mainActivity.removeFragment(MainFragmentName.APP_NOTICE_FRAGMENT)
        mainActivity.viewBottomSheet()
    }

    fun settingRecyclerViewAppNotice() {
        fragmentAppNoticeBinding.apply {
            recyclerViewAppNotice.apply {
                // 어뎁터 및 레이아웃 매니저 설정
                adapter = AppNoticeRecyclerViewAdapter()
                layoutManager = LinearLayoutManager(mainActivity)
            }
        }
    }


    inner class AppNoticeRecyclerViewAdapter :
        RecyclerView.Adapter<AppNoticeRecyclerViewAdapter.AppNoticeViewHolder>() {
        inner class AppNoticeViewHolder(rowAppNoticeBinding: RowAppNoticeBinding) :
            RecyclerView.ViewHolder(rowAppNoticeBinding.root) {
            val rowAppNoticeBinding: RowAppNoticeBinding

            init {
                this.rowAppNoticeBinding = rowAppNoticeBinding

                this.rowAppNoticeBinding.root.layoutParams = ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT
                )
            }
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AppNoticeViewHolder {
            val rowAppNoticeBinding = RowAppNoticeBinding.inflate(layoutInflater)
            val appNoticeViewHolder = AppNoticeViewHolder(rowAppNoticeBinding)
            return appNoticeViewHolder
        }

        override fun getItemCount(): Int {
            return 20
        }

        override fun onBindViewHolder(holder: AppNoticeViewHolder, position: Int) {
        }
    }
}