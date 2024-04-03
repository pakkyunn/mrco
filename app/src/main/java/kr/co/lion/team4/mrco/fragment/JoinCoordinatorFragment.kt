package kr.co.lion.team4.mrco.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kr.co.lion.team4.mrco.MainFragmentName
import kr.co.lion.team4.mrco.databinding.FragmentJoinCoordinatorBinding


class JoinCoordinatorFragment : Fragment() {

    lateinit var fragmentJoinCoordinatorBinding: FragmentJoinCoordinatorBinding
    lateinit var mainActivity: MainActivity

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        fragmentJoinCoordinatorBinding = FragmentJoinCoordinatorBinding.inflate(inflater)
        mainActivity = activity as MainActivity

        settingButtonJoinCoordinatorNext()

        return fragmentJoinCoordinatorBinding.root
    }

    fun settingButtonJoinCoordinatorNext() {
        fragmentJoinCoordinatorBinding.buttonJoinCoordinatorNext.setOnClickListener {
            // JoinCoordinatorNextFragment를 보여준다.
            mainActivity.replaceFragment(MainFragmentName.JOIN_COORDINATOR_NEXT_FRAGMENT, true, true, null)

        }
    }


}