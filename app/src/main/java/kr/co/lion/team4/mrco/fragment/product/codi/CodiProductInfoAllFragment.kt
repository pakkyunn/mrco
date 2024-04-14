package kr.co.lion.team4.mrco.fragment.product.codi

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import kr.co.lion.team4.mrco.MainActivity
import kr.co.lion.team4.mrco.R
import kr.co.lion.team4.mrco.databinding.FragmentCodiProductInfoAllBinding
import kr.co.lion.team4.mrco.viewmodel.CodiProductInfoAllViewModel

class CodiProductInfoAllFragment : Fragment() {

    private lateinit var binding: FragmentCodiProductInfoAllBinding
    private lateinit var viewModel: CodiProductInfoAllViewModel
    private lateinit var mainActivity: MainActivity
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_codi_product_info_all, container, false)
        viewModel = ViewModelProvider(this).get(CodiProductInfoAllViewModel::class.java)
        mainActivity = activity as MainActivity
        binding.lifecycleOwner = this


        return binding.root
    }
}