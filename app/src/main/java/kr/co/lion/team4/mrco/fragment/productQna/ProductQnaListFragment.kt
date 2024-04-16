package kr.co.lion.team4.mrco.fragment.productQna

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kr.co.lion.team4.mrco.viewmodel.productQna.ItemQnaListViewModel
import kr.co.lion.team4.mrco.MainActivity
import kr.co.lion.team4.mrco.MainFragmentName
import kr.co.lion.team4.mrco.viewmodel.productQna.ProductQnaListViewModel
import kr.co.lion.team4.mrco.R
import kr.co.lion.team4.mrco.databinding.FragmentProductQnaListBinding
import kr.co.lion.team4.mrco.databinding.ItemQnalistBinding


/* (판매자) 상품 문의 내역 화면 */
class ProductQnaListFragment : Fragment() {
    lateinit var fragmentProductQnaListBinding: FragmentProductQnaListBinding
    lateinit var productQnaListViewModel: ProductQnaListViewModel

    // Activity
    lateinit var mainActivity: MainActivity

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        fragmentProductQnaListBinding = DataBindingUtil.inflate(inflater,
            R.layout.fragment_product_qna_list, container, false)
        productQnaListViewModel = ProductQnaListViewModel()
        fragmentProductQnaListBinding.productQnaListViewModel = productQnaListViewModel
        fragmentProductQnaListBinding.lifecycleOwner = this

        mainActivity = activity as MainActivity

        // 툴바, 하단 메뉴 세팅
        settingtoolbarProduntQnaList()

        settingQnaListAdapter()

        return fragmentProductQnaListBinding.root
    }

    fun settingtoolbarProduntQnaList(){
        fragmentProductQnaListBinding.toolbarProduntQnaList.apply {
            setNavigationIcon(R.drawable.arrow_back_24px)
            setNavigationOnClickListener {
                backProcess()
            }
        }
    }
    fun settingQnaListAdapter(){
        fragmentProductQnaListBinding.recyclerviewQnaList.apply {
            adapter = ProductQnaListRecyclerViewAdapter()
            layoutManager = LinearLayoutManager(mainActivity)
        }
    }

    inner class ProductQnaListRecyclerViewAdapter : RecyclerView.Adapter<ProductQnaListRecyclerViewAdapter.QnaListViewHolder>(){
        inner class QnaListViewHolder(itemQnalistBinding:ItemQnalistBinding):RecyclerView.ViewHolder(itemQnalistBinding.root){
            val itemQnalistBinding : ItemQnalistBinding
            init {
                this.itemQnalistBinding = itemQnalistBinding
                this.itemQnalistBinding.root.layoutParams = ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT
                )
            }
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): QnaListViewHolder {
            val itemQnalistBinding = DataBindingUtil.inflate<ItemQnalistBinding>(layoutInflater,
                R.layout.item_qnalist, parent, false)
            val itemQnaListViewModel = ItemQnaListViewModel()
            itemQnalistBinding.itemQnaListViewModel = itemQnaListViewModel
            itemQnalistBinding.lifecycleOwner =this@ProductQnaListFragment

            val qnaListViewHolder = QnaListViewHolder(itemQnalistBinding)

            return qnaListViewHolder
        }

        override fun onBindViewHolder(holder: QnaListViewHolder, position: Int) {
            // 코디 상품명
            holder.itemQnalistBinding.itemQnaListViewModel?.textviewQnaListCoordiName?.value = "코디 상품명 $position"
            // 코디 상품번호
            holder.itemQnalistBinding.itemQnaListViewModel?.textviewQnaListCoordiIndex?.value = "12345678"

            // 답변 등록 버튼 - 답변 등록 상태에 따른 버튼 텍스트
            if(position%2 == 0){ // to do - 답변 state에 따라 분기할 것
                // 답변 등록이전 -> 답변 등록
                holder.itemQnalistBinding.itemQnaListViewModel?.buttonQnaListCoordiAnswer?.value = "답변 등록"
            }else{
                // 답변 완료된 상태 -> "답변 완료(hint로 표기)"
                holder.itemQnalistBinding.itemQnaListViewModel?.buttonQnaListCoordiAnswer?.value = null
            }

            // 작성자, 작성일
            holder.itemQnalistBinding.itemQnaListViewModel?.textviewQnaListWriter?.value = "홍길동  |  2024-04-01"
            // 문의내용
            holder.itemQnalistBinding.itemQnaListViewModel?.textviewQnaListContent?.value = "문의 내용 lorem ipsum dolor sit amet, \n consectetur adipiscing elit"

            // 답변 등록 버튼
            holder.itemQnalistBinding.buttonQnalistItemAnswer.setOnClickListener {
                // to do - 문의 인덱스 번호 전달할 것
                mainActivity.replaceFragment(MainFragmentName.REGISTER_QNA_ANSWER_FRAGMENT, true, true, null)
            }
        }

        override fun getItemCount(): Int {
            return 10
        }
    }

    fun backProcess(){
        mainActivity.removeFragment(MainFragmentName.PRODUCT_QNA_LIST_FRAGMENT)
    }
}