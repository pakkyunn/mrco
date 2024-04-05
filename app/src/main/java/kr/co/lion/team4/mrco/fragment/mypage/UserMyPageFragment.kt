package kr.co.lion.team4.mrco.fragment.mypage

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import kr.co.lion.team4.mrco.MainActivity
import kr.co.lion.team4.mrco.R
import kr.co.lion.team4.mrco.databinding.FragmentUserMyPageBinding
import kr.co.lion.team4.mrco.viewmodel.mypage.UserMyPageViewModel

class UserMyPageFragment : Fragment() {

    lateinit var fragmentUserMyPageBinding: FragmentUserMyPageBinding
    lateinit var mainActivity: MainActivity
    lateinit var userMyPageViewModel: UserMyPageViewModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        fragmentUserMyPageBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_user_my_page, container, false)
        userMyPageViewModel = UserMyPageViewModel()
        fragmentUserMyPageBinding.userMyPageViewModel = userMyPageViewModel
        fragmentUserMyPageBinding.lifecycleOwner = this

        mainActivity = activity as MainActivity

        return fragmentUserMyPageBinding.root
    }
}