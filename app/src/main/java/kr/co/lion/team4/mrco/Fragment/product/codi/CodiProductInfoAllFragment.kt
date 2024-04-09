package kr.co.lion.team4.mrco.Fragment.product.codi

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import kr.co.lion.team4.mrco.MainActivity
import kr.co.lion.team4.mrco.R
import kr.co.lion.team4.mrco.viewmodel.CodiProductInfoAllViewModel
import kr.co.lion.team4.mrco.databinding.FragmentCodiProductInfoAllBinding

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