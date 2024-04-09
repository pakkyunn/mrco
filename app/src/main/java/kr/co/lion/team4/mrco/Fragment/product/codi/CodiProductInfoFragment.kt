package kr.co.lion.team4.mrco.fragment.product.codi

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import kr.co.lion.team4.mrco.MainActivity
import kr.co.lion.team4.mrco.MainFragmentName
import kr.co.lion.team4.mrco.R
import kr.co.lion.team4.mrco.viewmodel.CodiProductInfoViewModel
import kr.co.lion.team4.mrco.databinding.FragmentCodiProductInfoBinding
import kr.co.lion.team4.mrco.viewmodel.CodiProductInfoBottomViewModel


class CodiProductInfoFragment : Fragment() {

    private lateinit var binding: FragmentCodiProductInfoBinding
    private lateinit var viewModel: CodiProductInfoViewModel
    private lateinit var mainActivity: MainActivity
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_codi_product_info, container, false)
        viewModel = ViewModelProvider(this).get(CodiProductInfoViewModel::class.java)
        mainActivity = activity as MainActivity
        binding.lifecycleOwner = this


        settingClickEvent()

        return binding.root
    }
    fun settingClickEvent(){
        binding.apply {
            // 전체 탭
            tabItemProuctInfoAll.setOnClickListener {

                mainActivity.replaceFragment(MainFragmentName.FRAGMENT_CODI_PRODUCT_INFO_ALL, true, true, null)
            }

            // 상의 탭
            tabItemProuctInfoTop.setOnClickListener {
                mainActivity.replaceFragment(MainFragmentName.FRAGMENT_CODI_PRODUCT_INFO_TOP, true, true, null)

            }

            // 하의 탭
            tabItemProuctInfoBottoms.setOnClickListener {
                mainActivity.replaceFragment(MainFragmentName.FRAGMENT_CODI_PRODUCT_INFO_BOTTOM, true, true, null)
            }

            // 신발 탭
            tabItemProuctInfoShoes.setOnClickListener {
                mainActivity.replaceFragment(MainFragmentName.FRAGMENT_CODI_PRODUCT_INFO_SHOES, true, true, null)
            }

            // 악세서리 탭
            tabItemProuctInfoAccessory.setOnClickListener {
                mainActivity.replaceFragment(MainFragmentName.FRAGMENT_CODI_PRODUCT_INFO_ACCESSORY, true, true, null)
            }
        }
    }
}