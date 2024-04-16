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
import kr.co.lion.team4.mrco.MainActivity
import kr.co.lion.team4.mrco.R
import kr.co.lion.team4.mrco.databinding.FragmentSalesManagementHistoryBinding
import kr.co.lion.team4.mrco.databinding.RowSalesManagementBinding
import kr.co.lion.team4.mrco.viewmodel.salesManagement.RowSalesManagementViewModel


class SalesManagementHistoryFragment : Fragment() {

    lateinit var fragmentSalesManagementHistoryBinding: FragmentSalesManagementHistoryBinding
    lateinit var mainActivity : MainActivity

    override fun onAttach(context: Context) {
        super.onAttach(context)

        mainActivity = activity as MainActivity
    }
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        fragmentSalesManagementHistoryBinding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_sales_management_history, container, false
        )
        fragmentSalesManagementHistoryBinding.lifecycleOwner = this

        settingRecyclerViewSalesManagement()

        return fragmentSalesManagementHistoryBinding.root
    }

    // 리사이클러 뷰 설정
    fun settingRecyclerViewSalesManagement() {
        fragmentSalesManagementHistoryBinding.apply {
            recyclerviewSalesManagementHistory.apply {
                // 어뎁터 및 레이아웃 매니저 설정
                adapter = SalesManagementRecyclerViewAdapter()
                layoutManager = LinearLayoutManager(mainActivity)
            }
        }
    }

    // 리사이클러 뷰 어뎁터
    inner class SalesManagementRecyclerViewAdapter: RecyclerView.Adapter<SalesManagementRecyclerViewAdapter.SalesManagementViewHolder>(){
        inner class SalesManagementViewHolder(rowSalesManagementBinding: RowSalesManagementBinding): RecyclerView.ViewHolder(rowSalesManagementBinding.root){
            val rowSalesManagementBinding: RowSalesManagementBinding

            init {
                this.rowSalesManagementBinding = rowSalesManagementBinding

                this.rowSalesManagementBinding.root.layoutParams = ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT
                )
            }
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SalesManagementViewHolder {
            val rowSalesManagementBinding = DataBindingUtil.inflate<RowSalesManagementBinding>(
                layoutInflater, R.layout.row_sales_management, parent, false
            )
            val rowSalesManagementViewModel = RowSalesManagementViewModel()
            rowSalesManagementBinding.rowSalesManagementViewModel = rowSalesManagementViewModel
            rowSalesManagementBinding.lifecycleOwner = this@SalesManagementHistoryFragment

            val salesManagementViewHolder = SalesManagementViewHolder(rowSalesManagementBinding)

            return salesManagementViewHolder
        }

        override fun getItemCount(): Int {
            return 6
        }

        override fun onBindViewHolder(holder: SalesManagementViewHolder, position: Int) {

        }
    }

}