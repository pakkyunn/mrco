package kr.co.lion.team4.mrco

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kr.co.lion.team4.mrco.databinding.FragmentCoordinatorInfoBinding

class CoordinatorInfoFragment : Fragment() {

    lateinit var fragmentCoordinatorInfoBinding: FragmentCoordinatorInfoBinding
    lateinit var mainActivity: MainActivity

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment

        fragmentCoordinatorInfoBinding = FragmentCoordinatorInfoBinding.inflate(inflater)
        mainActivity = activity as MainActivity

        return fragmentCoordinatorInfoBinding.root
    }
}