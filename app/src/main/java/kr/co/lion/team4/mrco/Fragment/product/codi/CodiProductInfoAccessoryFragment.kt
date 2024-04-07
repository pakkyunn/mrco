package kr.co.lion.team4.mrco.Fragment.product.codi

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kr.co.lion.team4.mrco.Activity.MainActivity
import kr.co.lion.team4.mrco.R
import kr.co.lion.team4.mrco.ViewModel.CodiProductInfoAccesoryViewModel
import kr.co.lion.team4.mrco.ViewModel.CodiProductInfoShoesViewModel
import kr.co.lion.team4.mrco.databinding.FragmentCodiProductInfoAccessoryBinding
import kr.co.lion.team4.mrco.databinding.FragmentCodiProductInfoShoesBinding

class CodiProductInfoAccessoryFragment : Fragment() {

    private lateinit var binding: FragmentCodiProductInfoAccessoryBinding
    private lateinit var viewModel: CodiProductInfoAccesoryViewModel
    private lateinit var mainActivity: MainActivity

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_codi_product_info_accessory, container, false)
    }
}