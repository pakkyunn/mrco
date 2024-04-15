package kr.co.lion.team4.mrco.fragment.mbtiproductmain

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kr.co.lion.team4.mrco.MainActivity
import kr.co.lion.team4.mrco.MainFragmentName
import kr.co.lion.team4.mrco.R
import kr.co.lion.team4.mrco.databinding.FragmentLikeProductBinding
import kr.co.lion.team4.mrco.databinding.FragmentMbtiProductMainBinding
import kr.co.lion.team4.mrco.databinding.RowLikeProductBinding
import kr.co.lion.team4.mrco.databinding.RowMbtiProductMainBinding
import kr.co.lion.team4.mrco.viewmodel.like.RowLikeProductViewModel

class MbtiProductMainFragment : Fragment() {

    lateinit var fragmentMbtiProductMainBinding: FragmentMbtiProductMainBinding
    lateinit var mainActivity: MainActivity

    var MBTI: String = "ISFP"

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment

        fragmentMbtiProductMainBinding = FragmentMbtiProductMainBinding.inflate(inflater)
        mainActivity = activity as MainActivity

        // 툴바, 하단바, 탭 관련
        settingToolbar()
        mainActivity.removeBottomSheet()

        // 리사이클러 뷰
        settingRecyclerViewMbtiProductMain()

        // 버튼
        settingButton()

        return fragmentMbtiProductMainBinding.root
    }

    fun settingToolbar(){
        fragmentMbtiProductMainBinding.apply {
            toolbarMbtiProductMain.apply {
                subtitle = "$MBTI 에게 잘 어울리는 코디"
                setNavigationOnClickListener {
                    backProcesss()
                }
                setOnMenuItemClickListener {
                    when (it.itemId) {
                        // 장바구니 클릭 시
                        R.id.menuItemShoppingBagProduct -> {
                            mainActivity.replaceFragment(MainFragmentName.CART_FRAGMENT, true, true, null)
                        }
                    }
                    true
                }
            }
        }
    }

    // 리사이클러 뷰 설정
    fun settingRecyclerViewMbtiProductMain() {
        fragmentMbtiProductMainBinding.apply {
            recyclerViewMbtiProductMain.apply {
                // 어뎁터 및 레이아웃 매니저 설정
                adapter = MbtiProductMainRecyclerViewAdapter()
            }
        }
    }

    fun settingButton(){
        fragmentMbtiProductMainBinding.apply {
            buttonMbtiProductMainMBTI.setOnClickListener {
                showMBTIBottomSheet()
            }

            buttonMbtiProductMainGender.setOnClickListener {
                showGenderBottomSheet()
            }
        }
    }

    // 리사이클러 뷰 어뎁터
    inner class MbtiProductMainRecyclerViewAdapter: RecyclerView.Adapter<MbtiProductMainRecyclerViewAdapter.MbtiProductMainViewHolder>(){
        inner class MbtiProductMainViewHolder(rowMbtiProductMainBinding: RowMbtiProductMainBinding): RecyclerView.ViewHolder(rowMbtiProductMainBinding.root){
            val rowMbtiProductMainBinding: RowMbtiProductMainBinding

            init {
                this.rowMbtiProductMainBinding = rowMbtiProductMainBinding

                this.rowMbtiProductMainBinding.root.layoutParams = ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT
                )
            }
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MbtiProductMainViewHolder {
            val rowMbtiProductMainBinding = RowMbtiProductMainBinding.inflate(layoutInflater)
            val mbtiProductMainViewHolder = MbtiProductMainViewHolder(rowMbtiProductMainBinding)

            return mbtiProductMainViewHolder
        }

        override fun getItemCount(): Int {
            return 20
        }

        override fun onBindViewHolder(holder: MbtiProductMainViewHolder, position: Int) {
            // position 값에 따라 다른 이미지 설정
            val imageResource = when (position % 5) {
                0 -> R.drawable.iu_image
                1 -> R.drawable.iu_image2
                2 -> R.drawable.iu_image3
                3 -> R.drawable.iu_image4
                else -> R.drawable.iu_image5
            }
            holder.rowMbtiProductMainBinding.itemMbtiProductThumbnail.setImageResource(imageResource)
            holder.rowMbtiProductMainBinding.root.setOnClickListener {
                mainActivity.replaceFragment(MainFragmentName.PRODUCT_FRAGMENT, true, true, null)
            }
        }
    }

    // MBTI를 설정할 BottomSheet를 띄워준다.
    fun showMBTIBottomSheet(){
        val mbtiProductBottomFragment = MbtiProductBottomFragment()
        mbtiProductBottomFragment.show(mainActivity.supportFragmentManager, "MBTIBottomSheet")
    }

    // 성별을 설정할 BottomSheet를 띄워준다.
    fun showGenderBottomSheet(){
        val mbtiProductBottomGenderFragment = MbtiProductBottomGenderFragment()
        mbtiProductBottomGenderFragment.show(mainActivity.supportFragmentManager, "GenderBottomSheet")
    }

    // 뒤로가기 처리
    fun backProcesss(){
        mainActivity.removeFragment(MainFragmentName.MBTI_PRODUCT_MAIN)
    }
}