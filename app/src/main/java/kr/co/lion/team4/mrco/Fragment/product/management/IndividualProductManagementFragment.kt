package kr.co.lion.team4.mrco.Fragment.product.management

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kr.co.lion.team4.mrco.Activity.MainActivity
import kr.co.lion.team4.mrco.R
import kr.co.lion.team4.mrco.ViewModel.CodiProductInfoShoesViewModel
import kr.co.lion.team4.mrco.ViewModel.IndividualProductManagementViewModel
import kr.co.lion.team4.mrco.databinding.FragmentCodiProductInfoShoesBinding
import kr.co.lion.team4.mrco.databinding.FragmentIndividualProdcutManagementBinding

class IndividualProductManagementFragment : Fragment() {

    private lateinit var binding: FragmentIndividualProdcutManagementBinding
    private lateinit var viewModel: IndividualProductManagementViewModel
    private lateinit var mainActivity: MainActivity
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_individual_prodcut_management, container, false)
    }
}
