package kr.co.lion.team4.mrco

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import kr.co.lion.team4.mrco.databinding.FragmentManageShipmentsBinding

/* (판매자) 배송관리 화면 - 운송장 번호를 등록할 수 있는 화면 */
class ManageShipmentsFragment : Fragment() {
    lateinit var fragmentManageShipmentsBinding : FragmentManageShipmentsBinding
    lateinit var manageShipmentsViewModel: ManageShipmentsViewModel

    lateinit var mainActivity: MainActivity
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        fragmentManageShipmentsBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_manage_shipments, container, false)
        manageShipmentsViewModel = ManageShipmentsViewModel()
        fragmentManageShipmentsBinding.manageShipmentsViewModel = manageShipmentsViewModel
        fragmentManageShipmentsBinding.lifecycleOwner = this

        // Activity
        mainActivity = activity as MainActivity

        return fragmentManageShipmentsBinding.root
    }
}