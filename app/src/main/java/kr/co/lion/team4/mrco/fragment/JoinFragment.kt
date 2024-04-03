package kr.co.lion.team4.mrco.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kr.co.lion.mrco_01.databinding.FragmentJoinBinding
import kr.co.lion.team4.mrco.MainFragmentName


class JoinFragment : Fragment() {

    lateinit var fragmentJoinBinding: FragmentJoinBinding
    lateinit var mainActivity: MainActivity


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        fragmentJoinBinding = FragmentJoinBinding.inflate(inflater)
        mainActivity = activity as MainActivity

        settingButtonJoinSubmit()

        return fragmentJoinBinding.root
    }

    fun settingButtonJoinSubmit(){
        fragmentJoinBinding.buttonJoinSubmit.setOnClickListener {
            mainActivity.removeFragment(MainFragmentName.JOIN_FRAGMENT)
        }
    }


}