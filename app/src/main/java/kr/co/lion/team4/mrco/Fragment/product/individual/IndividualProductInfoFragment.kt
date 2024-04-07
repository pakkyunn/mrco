package kr.co.lion.team4.mrco.Fragment.product.individual

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kr.co.lion.team4.mrco.Activity.MainActivity
import kr.co.lion.team4.mrco.Fragment.product.codi.CodiProductInfoAccessoryFragment
import kr.co.lion.team4.mrco.R
import kr.co.lion.team4.mrco.ViewModel.IndividualProductInfoViewModel
import kr.co.lion.team4.mrco.databinding.FragmentIndividualProductInfofragmentBinding


class IndividualProductInfoFragment : Fragment() {
    private lateinit var binding: FragmentIndividualProductInfofragmentBinding
    private lateinit var viewModel: IndividualProductInfoViewModel
    private lateinit var mainActivity: MainActivity

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_individual_product_infofragment, container, false)
    }
}