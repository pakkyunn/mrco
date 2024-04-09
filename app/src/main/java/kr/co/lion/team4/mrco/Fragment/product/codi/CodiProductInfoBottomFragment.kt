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
import kr.co.lion.team4.mrco.databinding.FragmentCodiProductInfoBottomBinding
import kr.co.lion.team4.mrco.viewmodel.CodiProductInfoBottomViewModel

class CodiProductInfoBottomFragment : Fragment() {

    private lateinit var binding: FragmentCodiProductInfoBottomBinding
    private lateinit var viewModel: CodiProductInfoBottomViewModel
    private lateinit var mainActivity: MainActivity
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_codi_product_info_bottom, container, false)
        viewModel = ViewModelProvider(this).get(CodiProductInfoBottomViewModel::class.java)
        mainActivity = activity as MainActivity
        binding.lifecycleOwner = this



        return binding.root
    }
}