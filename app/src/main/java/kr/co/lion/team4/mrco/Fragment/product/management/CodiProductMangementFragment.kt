package kr.co.lion.team4.mrco.fragment.product.management

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import kr.co.lion.team4.mrco.Activity.MainActivity
import kr.co.lion.team4.mrco.R
import kr.co.lion.team4.mrco.viewmodel.CodiProductInfoViewModel
import kr.co.lion.team4.mrco.databinding.FragmentCodiProductMangementBinding
import kr.co.lion.team4.mrco.viewmodel.CodiProductManagementViewModel

class CodiProductMangementFragment : Fragment() {

    private lateinit var binding: FragmentCodiProductMangementBinding
    private lateinit var viewModel: CodiProductManagementViewModel
    lateinit var mainActivity: MainActivity
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_codi_product_mangement, container, false)
        viewModel = CodiProductManagementViewModel()
        mainActivity = activity as MainActivity
        binding.lifecycleOwner = this


        return binding.root
    }
}