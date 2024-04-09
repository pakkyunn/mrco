package kr.co.lion.team4.mrco.Fragment.product.management

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import kr.co.lion.team4.mrco.MainActivity
import kr.co.lion.team4.mrco.R
import kr.co.lion.team4.mrco.viewmodel.IndividualProductManagementViewModel
import kr.co.lion.team4.mrco.databinding.FragmentIndividualProdcutManagementBinding
import kr.co.lion.team4.mrco.viewmodel.IndividualProductInfoViewModel

class IndividualProductManagementFragment : Fragment() {

    private lateinit var binding: FragmentIndividualProdcutManagementBinding
    private lateinit var viewModel: IndividualProductManagementViewModel
    private lateinit var mainActivity: MainActivity
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_individual_prodcut_management, container, false)
        viewModel = ViewModelProvider(this).get(IndividualProductManagementViewModel::class.java)
        mainActivity = activity as MainActivity
        binding.lifecycleOwner = this


        return binding.root
    }
}
