package kr.co.lion.team4.mrco.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kr.co.lion.team4.mrco.MainActivity
import kr.co.lion.team4.mrco.R
import kr.co.lion.team4.mrco.databinding.FragmentCameraAlbumBottomSheetBinding

class CameraAlbumBottomSheetFragment : BottomSheetDialogFragment() {

    lateinit var fragmentCameraAlbumBottomSheetBinding: FragmentCameraAlbumBottomSheetBinding
    lateinit var mainActivity: MainActivity
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        fragmentCameraAlbumBottomSheetBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_camera_album_bottom_sheet, container, false)
        fragmentCameraAlbumBottomSheetBinding.lifecycleOwner = this

        mainActivity = activity as MainActivity


        return fragmentCameraAlbumBottomSheetBinding.root
    }

}