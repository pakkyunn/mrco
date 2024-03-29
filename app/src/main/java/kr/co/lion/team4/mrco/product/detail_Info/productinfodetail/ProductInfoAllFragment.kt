package kr.co.lion.team4.mrco.product.detail_Info.productinfodetail

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kr.co.lion.team4.mrco.R

class ProductInfoAllFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_product_info_all, container, false)

        // setClipToOutline : 이미지를 배경에 맞게 자른다.
        // ImageView.setClipToOutline(true)를 사용한다.
        // binding.imageView.clipToOutline = true
    }
}