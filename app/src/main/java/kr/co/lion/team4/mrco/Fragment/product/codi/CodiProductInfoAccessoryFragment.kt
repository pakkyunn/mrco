package kr.co.lion.team4.mrco.fragment.product.codi

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import kr.co.lion.team4.mrco.Activity.MainActivity
import kr.co.lion.team4.mrco.R
import kr.co.lion.team4.mrco.databinding.FragmentCodiProductInfoAccessoryBinding
import kr.co.lion.team4.mrco.viewmodel.CodiProductInfoAccesoryViewModel

class CodiProductInfoAccessoryFragment : Fragment() {

    private lateinit var binding: FragmentCodiProductInfoAccessoryBinding
    private lateinit var viewModel: CodiProductInfoAccesoryViewModel
    private lateinit var mainActivity: MainActivity

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment

        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_codi_product_info_accessory, container, false)
        viewModel = ViewModelProvider(this).get(CodiProductInfoAccesoryViewModel::class.java)
        mainActivity = activity as MainActivity
        binding.lifecycleOwner = this

        
        return binding.root

    }


}