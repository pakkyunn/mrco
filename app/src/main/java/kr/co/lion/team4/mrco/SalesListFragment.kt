package kr.co.lion.team4.mrco

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import kr.co.lion.team4.mrco.databinding.FragmentSalesListBinding

/* (판매자) 판매내역 화면 */
class SalesListFragment : Fragment() {
    lateinit var fragmentSalesListBinding: FragmentSalesListBinding
    lateinit var salesListViewModel: SalesListViewModel

    lateinit var mainActivity: MainActivity
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        fragmentSalesListBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_sales_list, container, false)
        salesListViewModel = SalesListViewModel()
        fragmentSalesListBinding.salesListViewModel = salesListViewModel
        fragmentSalesListBinding.lifecycleOwner = this

        mainActivity = activity as MainActivity

        return fragmentSalesListBinding.root
    }
}