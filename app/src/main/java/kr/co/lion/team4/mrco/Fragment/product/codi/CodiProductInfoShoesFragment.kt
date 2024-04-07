package kr.co.lion.team4.mrco.Fragment.product.codi

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kr.co.lion.team4.mrco.Activity.MainActivity
import kr.co.lion.team4.mrco.R
import kr.co.lion.team4.mrco.viewmodel.CodiProductInfoShoesViewModel
import kr.co.lion.team4.mrco.databinding.FragmentCodiProductInfoShoesBinding

class CodiProductInfoShoesFragment : Fragment() {

    private lateinit var binding: FragmentCodiProductInfoShoesBinding
    private lateinit var viewModel: CodiProductInfoShoesViewModel
    private lateinit var mainActivity: MainActivity
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_codi_product_info_shoes, container, false)
    }
}