package kr.co.lion.team4.mrco.Fragment.product.codi

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import kr.co.lion.team4.mrco.Activity.MainActivity
import kr.co.lion.team4.mrco.FragmentName
import kr.co.lion.team4.mrco.R
import kr.co.lion.team4.mrco.ViewModel.CodiProductInfoViewModel
import kr.co.lion.team4.mrco.databinding.FragmentCodiProductInfoBinding


class CodiProductInfoFragment : Fragment() {

    private lateinit var binding: FragmentCodiProductInfoBinding
    private lateinit var viewModel: CodiProductInfoViewModel
    private lateinit var mainActivity: MainActivity
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        mainActivity = activity as MainActivity
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_codi_product_info, container, false )
        viewModel = ViewModelProvider(this).get(CodiProductInfoViewModel::class.java)



        return binding.root
    }

    fun setEvent(){
        binding.apply {
            // CodiProductInfoAll Fragment

            tabItemProuctInfoAll.setOnClickListener {

                mainActivity.replaceFragment(FragmentName.FRAGMENT_CODI_PRODUCT_INFO_ALL, true, true, null)
            }

            // CodiProductInfoTop Fragment
            tabItemProuctInfoTop.setOnClickListener {
                // frameLayout 바꾸기
                mainActivity.replaceFragment(FragmentName.FRAGMENT_CODI_PRODUCT_INFO_TOP, true, true, null)
            }
            // CodiProductInfoBottom Fragment
            tabItemProuctInfoBottoms.setOnClickListener {
                // frameLayout 바꾸기
                mainActivity.replaceFragment(FragmentName.FRAGMENT_CODI_PRODUCT_INFO_BOTTOM, true, true, null)
            }
            // CodiProductInfoShoes Fragment
            tabItemProuctInfoShoes.setOnClickListener {
                // frameLayout 바꾸기
                mainActivity.replaceFragment(FragmentName.FRAGMENT_CODI_PRODUCT_INFO_SHOES, true, true, null)
            }
            // CodiProductInfoAccessory Fragment
            tabItemProuctInfoAccessory.setOnClickListener {
                // frameLayout 바꾸기
                mainActivity.replaceFragment(FragmentName.FRAGMENT_CODI_PRODUCT_INFO_ACCESSORY, true, true, null)
            }
        }
    }
}