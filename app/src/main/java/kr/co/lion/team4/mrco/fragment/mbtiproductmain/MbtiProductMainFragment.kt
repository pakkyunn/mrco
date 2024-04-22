package kr.co.lion.team4.mrco.fragment.mbtiproductmain

import android.content.DialogInterface
import android.os.Bundle
import android.util.Log
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
import kr.co.lion.team4.mrco.Tools
import kr.co.lion.team4.mrco.databinding.FragmentHomeRecommendBinding
import kr.co.lion.team4.mrco.databinding.FragmentMbtiProductMainBinding
import kr.co.lion.team4.mrco.databinding.RowMbtiProductMainBinding
import kr.co.lion.team4.mrco.viewmodel.home.recommend.HomeRecommendViewModel
import kr.co.lion.team4.mrco.viewmodel.mbtiproductmain.MbtiProductMainViewModel
import kr.co.lion.team4.mrco.viewmodel.mbtiproductmain.RowMbtiProductMainViewModel

class MbtiProductMainFragment : Fragment() {

    lateinit var fragmentMbtiProductMainBinding: FragmentMbtiProductMainBinding
    lateinit var mainActivity: MainActivity

    lateinit var mbtiProductMainViewModel: MbtiProductMainViewModel

    // 더보기 버튼 위에꺼/아래꺼
    var buttonInt = 0

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        fragmentMbtiProductMainBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_mbti_product_main, container, false)
        mbtiProductMainViewModel = MbtiProductMainViewModel()
        fragmentMbtiProductMainBinding.mbtiProductMainViewModel = MbtiProductMainViewModel()
        fragmentMbtiProductMainBinding.lifecycleOwner = this

        mainActivity = activity as MainActivity

        buttonInt = arguments?.getInt("buttonInt")!!

        // 툴바, 하단바, 탭 관련
        settingToolbar()

        // 리사이클러 뷰
        settingRecyclerViewMbtiProductMain()

        // 버튼
        settingButton()

        // 기본 세팅
        settingInit()

        // MBTI TextView 관찰
        mbtiProductMainViewModel.textViewMbtiProductMainMBTI.observe(viewLifecycleOwner) { mbti ->
            // MBTI TextView 업데이트
            fragmentMbtiProductMainBinding.textViewMbti.text = mbti
            fragmentMbtiProductMainBinding.recyclerViewMbtiProductMain.adapter?.notifyDataSetChanged()
        }

        return fragmentMbtiProductMainBinding.root
    }

    // 기본 세팅
    fun settingInit() {
        // 첫번째 버튼으로 들어왔을때
        if (buttonInt == 1) {
            mbtiProductMainViewModel.textViewMbtiProductMainMBTI.value = mainActivity.loginUserMbti
            fragmentMbtiProductMainBinding.textViewSideMbti.setText("에게 잘 어울리는 코디")
            // 남자일때
            if (mainActivity.loginUserGender == 1) {

            }
            // 여자일때
            else {

            }
        }
        // 두번째 버튼으로 들어왔을때
        else {
            mbtiProductMainViewModel.textViewMbtiProductMainMBTI.value = mainActivity.loginUserMbti
            // 남자일때
            if (mainActivity.loginUserGender == 1) {
                fragmentMbtiProductMainBinding.textViewSideMbti.setText("여성이 좋아하는 남자 코디")
            }
            // 여자일때
            else {
                fragmentMbtiProductMainBinding.textViewSideMbti.setText("남성이 좋아하는 여자 코디")
            }
        }
    }

    fun settingToolbar(){
        fragmentMbtiProductMainBinding.apply {
            toolbarMbtiProductMain.apply {
                // subtitle = "$MBTI 에게 잘 어울리는 코디"
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
            val screenWidthDp = resources.configuration.screenWidthDp
            if (screenWidthDp >= 600) {
                // 너비가 600dp 이상인 디바이스에서 실행될 동작
                recyclerViewMbtiProductMain.apply {
                    // 어뎁터 및 레이아웃 매니저 설정
                    adapter = MbtiProductMainRecyclerViewAdapter()
                    layoutManager = GridLayoutManager(mainActivity, 3)
                }
            }
            else {
                // 너비가 600dp 미만인 디바이스에서 실행될 동작
                recyclerViewMbtiProductMain.apply {
                    // 어뎁터 및 레이아웃 매니저 설정
                    adapter = MbtiProductMainRecyclerViewAdapter()
                }
            }
        }
    }

    fun settingButton(){
        fragmentMbtiProductMainBinding.apply {
            buttonMbtiProductMainMBTI.setOnClickListener {
                showMBTIBottomSheet()
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
            val rowMbtiProductMainBinding = DataBindingUtil.inflate<RowMbtiProductMainBinding>(
                layoutInflater, R.layout.row_mbti_product_main, parent, false
            )
            val rowMbtiProductMainViewModel = RowMbtiProductMainViewModel()
            rowMbtiProductMainBinding.rowMbtiProductMainViewModel = rowMbtiProductMainViewModel
            rowMbtiProductMainBinding.lifecycleOwner = this@MbtiProductMainFragment

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
            holder.rowMbtiProductMainBinding.itemMainMbtiFullProductThumbnail.setImageResource(imageResource)
            holder.rowMbtiProductMainBinding.root.setOnClickListener {
                mainActivity.replaceFragment(MainFragmentName.PRODUCT_FRAGMENT, true, true, null)
            }
        }
    }

    // MBTI를 설정할 BottomSheet를 띄워준다.
    fun showMBTIBottomSheet(){
        val mbtiProductBottomFragment = MbtiProductBottomFragment(mbtiProductMainViewModel?.textViewMbtiProductMainMBTI!!)
        mbtiProductBottomFragment.show(mainActivity.supportFragmentManager, "MBTIBottomSheet")
    }

    // 뒤로가기 처리
    fun backProcesss(){
        mainActivity.removeFragment(MainFragmentName.MBTI_PRODUCT_MAIN)
    }
}