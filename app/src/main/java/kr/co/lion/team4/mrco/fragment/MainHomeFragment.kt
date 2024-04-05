package kr.co.lion.team4.mrco.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.tabs.TabLayout
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kr.co.lion.team4.mrco.MainActivity
import kr.co.lion.team4.mrco.MainFragmentName
import kr.co.lion.team4.mrco.R
import kr.co.lion.team4.mrco.databinding.FragmentMainHomeBinding

class MainHomeFragment : Fragment() {

    lateinit var fragmentMainHomeBinding: FragmentMainHomeBinding
    lateinit var mainActivity: MainActivity

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment

        fragmentMainHomeBinding = FragmentMainHomeBinding.inflate(inflater)
        mainActivity = activity as MainActivity

        // loadHomeRecommendFragment()
        loadHomeMbtiFragment()

        return fragmentMainHomeBinding.root
    }

    private fun loadHomeRecommendFragment() {

        val fragmentTransaction = childFragmentManager.beginTransaction()
        val newFragment = HomeRecommendFragment()

        fragmentTransaction.replace(R.id.containerMainHome, newFragment)
        fragmentTransaction.addToBackStack(null)
        fragmentTransaction.commit()
    }

    private fun loadHomeMbtiFragment() {

        val fragmentTransaction = childFragmentManager.beginTransaction()
        val newFragment = HomeMbtiFragment()

        fragmentTransaction.replace(R.id.containerMainHome, newFragment)
        fragmentTransaction.addToBackStack(null)
        fragmentTransaction.commit()
    }
}