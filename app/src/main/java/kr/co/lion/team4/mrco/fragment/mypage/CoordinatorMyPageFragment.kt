package kr.co.lion.team4.mrco.fragment.mypage

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import kr.co.lion.team4.mrco.MainActivity
import kr.co.lion.team4.mrco.R
import kr.co.lion.team4.mrco.databinding.FragmentCoordinatorMyPageBinding
import kr.co.lion.team4.mrco.viewmodel.mypage.CoordinatorMyPageViewModel

class CoordinatorMyPageFragment : Fragment() {

    lateinit var fragmentCoordinatorMyPageBinding: FragmentCoordinatorMyPageBinding
    lateinit var mainActivity: MainActivity
    lateinit var coordinatorMyPageViewModel: CoordinatorMyPageViewModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        fragmentCoordinatorMyPageBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_coordinator_my_page, container, false)
        coordinatorMyPageViewModel = CoordinatorMyPageViewModel()
        fragmentCoordinatorMyPageBinding.coordinatorMyPageViewModel = coordinatorMyPageViewModel
        fragmentCoordinatorMyPageBinding.lifecycleOwner = this

        mainActivity = activity as MainActivity

        return fragmentCoordinatorMyPageBinding.root
    }
}