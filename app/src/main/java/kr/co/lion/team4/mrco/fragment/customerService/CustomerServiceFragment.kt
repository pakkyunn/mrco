package kr.co.lion.team4.mrco.fragment.customerService

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.tabs.TabLayout
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kr.co.lion.team4.mrco.MainActivity
import kr.co.lion.team4.mrco.MainFragmentName
import kr.co.lion.team4.mrco.R
import kr.co.lion.team4.mrco.dao.FaqDao
import kr.co.lion.team4.mrco.databinding.FragmentCustomerServiceBinding
import kr.co.lion.team4.mrco.databinding.RowCustomerServiceBinding
import kr.co.lion.team4.mrco.model.FaqCategory
import kr.co.lion.team4.mrco.model.FaqModel
import kr.co.lion.team4.mrco.viewmodel.customerService.CustomerServiceViewModel
import kr.co.lion.team4.mrco.viewmodel.customerService.RowCustomerServiceViewModel

class CustomerServiceFragment : Fragment() {

    lateinit var fragmentCustomerServiceBinding: FragmentCustomerServiceBinding
    lateinit var mainActivity: MainActivity
    lateinit var customerServiceViewModel: CustomerServiceViewModel

    // 전체 FAQ 목록을 담을 리스트
    var faqList = mutableListOf<FaqModel>()
    // 카테고리별로 구분하여 담아줄 리스트
    var filteredList = mutableListOf<FaqModel>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        fragmentCustomerServiceBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_customer_service, container, false)
        customerServiceViewModel = CustomerServiceViewModel()
        fragmentCustomerServiceBinding.customerServiceViewModel = customerServiceViewModel
        fragmentCustomerServiceBinding.lifecycleOwner = this

        mainActivity = activity as MainActivity

        // 툴바 세팅
        toolbarSetting()

        // 1:1 문의 버튼
        leaveCustomerInquiry()

        // 자주 묻는 질문 리사이클러뷰 어댑터 세팅
        settingFaqRecyclerViewAdapter()
        // 자주 묻는 질문 목록 불러오기
        gettingFaqList()

        // Tab Layout 세팅
        settingFaqListTab()

        return fragmentCustomerServiceBinding.root
    }

    // 서버에서 FAQ 목록을 불러온다.
    fun gettingFaqList(){
        CoroutineScope(Dispatchers.Main).launch {
            filteredList.clear()
            faqList = FaqDao.gettingFaqList()
            filteredList.addAll( faqList )
            fragmentCustomerServiceBinding.recyclerViewCustomerServiceFaq.adapter?.notifyDataSetChanged()
        }
    }

    // FAQ 목록 리사이클러뷰 어댑터 세팅
    fun settingFaqRecyclerViewAdapter(){
        fragmentCustomerServiceBinding.recyclerViewCustomerServiceFaq.apply {
            adapter = FaqRecyclerViewAdapter()
            layoutManager = LinearLayoutManager(mainActivity)
        }
    }

    // 상단 탭 선택 설정
    fun settingFaqListTab(){
        fragmentCustomerServiceBinding.apply {
            val tabLayout = tabCustomerServiceFaq

            tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener{
                override fun onTabSelected(tab: TabLayout.Tab?) {
                    // 탭의 카테고리에 맞는 데이터만 담아서 보여준다.
                    when(tab?.position){
                        0 -> {  // 전체
                            filteredList.clear()
                            filteredList.addAll(faqList) // 전체 FAQ 목록을 담아준다.
                            fragmentCustomerServiceBinding.recyclerViewCustomerServiceFaq.adapter?.notifyDataSetChanged()
                        }
                        1 -> { filterFaqListByTab(FaqCategory.USER.categoryNum) } // 회원정보
                        2 -> { filterFaqListByTab(FaqCategory.PRODUCT_AND_ORDER.categoryNum) } // 상품/주문
                        3 -> { filterFaqListByTab(FaqCategory.SHIPPING.categoryNum) } // 배송
                        4 -> { filterFaqListByTab(FaqCategory.RETURN_AND_EXCHANGE.categoryNum) } // 교환/반품
                    }
                }

                override fun onTabUnselected(p0: TabLayout.Tab?) {
                    //"Not yet implemented"
                }

                override fun onTabReselected(p0: TabLayout.Tab?) {
                    //"Not yet implemented"
                }
            })
        }
    }

    // 카데고리별 Faq 데이터 분류
    fun filterFaqListByTab( categoryNum : Int ){
        filteredList.clear() // 리스트 초기화
        faqList.forEach {
            if(it.faqCategory == categoryNum ){ // Tab 카테고리와 faq 카테고리가 일치하는 faq 질문만 추가
                filteredList.add(it)
            }
        }
        // 리사이클러뷰 갱신
        fragmentCustomerServiceBinding.recyclerViewCustomerServiceFaq.adapter?.notifyDataSetChanged()
    }

    // 툴바 설정
    fun toolbarSetting(){
        fragmentCustomerServiceBinding.toolbarCustomerService.apply {
            // 네비게이션
            setNavigationIcon(R.drawable.arrow_back_24px)
            setNavigationOnClickListener {
                backProcesss()
            }
        }
    }

    // '1:1 문의하기 버튼' 클릭이벤트
    fun leaveCustomerInquiry(){
        fragmentCustomerServiceBinding.apply {
            buttonCustomerServiceInquiry.setOnClickListener {
                mainActivity.replaceFragment(MainFragmentName.CUSTOMER_INQUIRY_FRAGMENT, true, true, null)
            }
        }
    }

    // 뒤로가기 처리
    fun backProcesss(){
        mainActivity.removeFragment(MainFragmentName.CUSTOMER_SERVICE_FRAGMENT)
    }

    // 자주 묻는 질문의 RecyclerView Adapter
    inner class FaqRecyclerViewAdapter : RecyclerView.Adapter<FaqRecyclerViewAdapter.FaqViewHolder>(){
        inner class FaqViewHolder (rowCustomerServiceBinding: RowCustomerServiceBinding) : RecyclerView.ViewHolder(rowCustomerServiceBinding.root){
            val rowCustomerServiceBinding : RowCustomerServiceBinding
            init {
                this.rowCustomerServiceBinding = rowCustomerServiceBinding
                this.rowCustomerServiceBinding.root.layoutParams = ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT
                )
            }
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FaqViewHolder {
            val rowCustomerServiceBinding = DataBindingUtil.inflate<RowCustomerServiceBinding>(
                layoutInflater, R.layout.row_customer_service, parent, false)
            val rowCustomerServiceViewModel = RowCustomerServiceViewModel()
            rowCustomerServiceBinding.rowCustomerServiceViewModel = rowCustomerServiceViewModel
            rowCustomerServiceBinding.lifecycleOwner = this@CustomerServiceFragment

            val faqViewHolder = FaqViewHolder(rowCustomerServiceBinding)

            return faqViewHolder
        }

        override fun onBindViewHolder(holder: FaqViewHolder, position: Int) {
            holder.rowCustomerServiceBinding.rowCustomerServiceViewModel?.apply {
                // 문의 유형 (카테고리)
                textViewCategoryRowCustomerService.value = filteredList[position].faqSubCategory
                // 자주 묻는 질문 제목
                textViewSubtitleRowCustomerService.value = filteredList[position].faqQuestion
                // 자주 묻는 질문 답변
                textViewAnswerRowCustomerService.value = filteredList[position].faqAnswer
            }

            holder.rowCustomerServiceBinding.apply {
                // 답변은 숨겨준다.
                textViewRowCustomerServiceAnswer.visibility = View.GONE
                // 질문을 클릭하면 답변 visibility 상태를 toggle
                layoutRowCustomerServiceQuestion.setOnClickListener {
                    if (textViewRowCustomerServiceAnswer.visibility == View.VISIBLE) textViewRowCustomerServiceAnswer.visibility = View.GONE
                    else if (textViewRowCustomerServiceAnswer.visibility == View.GONE) textViewRowCustomerServiceAnswer.visibility = View.VISIBLE
                }
            }
        }

        override fun getItemCount(): Int {
            return filteredList.size
        }
    }
}