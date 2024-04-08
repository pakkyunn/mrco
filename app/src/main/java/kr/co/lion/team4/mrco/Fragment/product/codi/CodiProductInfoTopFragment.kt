package kr.co.lion.team4.mrco.Fragment.product.codi

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.get
import kr.co.lion.team4.mrco.Activity.MainActivity
import kr.co.lion.team4.mrco.R
import kr.co.lion.team4.mrco.databinding.FragmentCodiProductInfoTopBinding
import kr.co.lion.team4.mrco.viewmodel.CodiProductInfoTopViewModel

class CodiProductInfoTopFragment : Fragment() {

    private lateinit var binding: FragmentCodiProductInfoTopBinding
    private lateinit var viewModel: CodiProductInfoTopViewModel
    private lateinit var mainActivity: MainActivity

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_codi_product_info_top, container, false)
        viewModel = ViewModelProvider(this).get(CodiProductInfoTopViewModel::class.java)
        mainActivity = activity as MainActivity
        binding.lifecycleOwner = this


        return binding.root
    }
}