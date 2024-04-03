package kr.co.lion.team4.mrco

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import kr.co.lion.team4.mrco.databinding.FragmentProductQnaListBinding
import kr.co.lion.team4.mrco.databinding.ItemQnalistBinding


/* (판매자) 상품 문의 내역 화면 */
class ProductQnaListFragment : Fragment() {
    lateinit var fragmentProductQnaListBinding: FragmentProductQnaListBinding
    lateinit var productQnaListViewModel: ProductQnaListViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        fragmentProductQnaListBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_product_qna_list, container, false)
        productQnaListViewModel = ProductQnaListViewModel()
        fragmentProductQnaListBinding.productQnaListViewModel = productQnaListViewModel
        fragmentProductQnaListBinding.lifecycleOwner = this

        return fragmentProductQnaListBinding.root
    }

    fun settingQnaListAdapter(){
        fragmentProductQnaListBinding.recyclerviewQnaList.apply {
            adapter = ProductQnaListRecyclerViewAdapter()
            // to do - layoutManager = LinearLayoutManager("context")

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
            val itemQnalistBinding = ItemQnalistBinding.inflate(layoutInflater)
            val qnaListViewHolder = QnaListViewHolder(itemQnalistBinding)

            return qnaListViewHolder
        }

        override fun onBindViewHolder(holder: QnaListViewHolder, position: Int) {

        }

        override fun getItemCount(): Int {
            return 10
        }
    }
}