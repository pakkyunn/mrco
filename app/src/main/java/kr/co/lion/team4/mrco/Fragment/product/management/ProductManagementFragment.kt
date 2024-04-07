package kr.co.lion.team4.mrco.Fragment.product.management

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import kr.co.lion.team4.mrco.Activity.MainActivity
import kr.co.lion.team4.mrco.R
import kr.co.lion.team4.mrco.viewmodel.ProductManagementViewModel
import kr.co.lion.team4.mrco.databinding.FragmentProductManagementBinding


class ProductManagementFragment : Fragment() {

    private lateinit var binding: FragmentProductManagementBinding
    private lateinit var viewModel: ProductManagementViewModel
    private lateinit var mainActivity: MainActivity

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_product_management, container, false)


        return binding.root
    }
}