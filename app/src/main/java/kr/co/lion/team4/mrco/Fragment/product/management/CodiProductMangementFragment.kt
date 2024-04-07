package kr.co.lion.team4.mrco.Fragment.product.management

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import kr.co.lion.team4.mrco.Activity.MainActivity
import kr.co.lion.team4.mrco.R
import kr.co.lion.team4.mrco.ViewModel.CodiProductInfoViewModel
import kr.co.lion.team4.mrco.ViewModel.CodiProductManagementViewModel
import kr.co.lion.team4.mrco.databinding.FragmentCodiProductMangementBinding

class CodiProductMangementFragment : Fragment() {

    private lateinit var binding: FragmentCodiProductMangementBinding
    private lateinit var viewModel: CodiProductInfoViewModel
    lateinit var mainActivity: MainActivity
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        mainActivity = activity as MainActivity
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_codi_product_mangement, container, false)
        viewModel = CodiProductInfoViewModel()


        return binding.root
    }
}