package kr.co.lion.team4.mrco

import android.os.Bundle
import android.view.Gravity
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MenuInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toolbar.LayoutParams
import androidx.core.content.ContextCompat
import kr.co.lion.team4.mrco.databinding.FragmentCoordinatorMainBinding
import kr.co.lion.team4.mrco.databinding.FragmentOrderDetailBinding

class OrderDetailFragment : Fragment() {

    lateinit var fragmentOrderDetailBinding: FragmentOrderDetailBinding
    lateinit var mainActivity: MainActivity
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment

        fragmentOrderDetailBinding = FragmentOrderDetailBinding.inflate(inflater)
        mainActivity = activity as MainActivity

        mainActivity.removeTabsBar()
        mainActivity.removeBottomSheet()

        toolbarSetting()

        return fragmentOrderDetailBinding.root
    }

    // 툴바 설정
    fun toolbarSetting(){
        mainActivity.activityMainBinding.apply {
            toolbarMain.apply {
                title = "주문 상세 정보"
                // 네비게이션
                setNavigationIcon(R.drawable.arrow_back_24px)
                setNavigationOnClickListener {
                    backProcesss()
                }
                // 메뉴제거
                menu.clear()
            }
        }
    }

    // 뒤로가기 처리
    fun backProcesss(){
        mainActivity.removeFragment(MainFragmentName.ORDER_DETAIL)
    }
}