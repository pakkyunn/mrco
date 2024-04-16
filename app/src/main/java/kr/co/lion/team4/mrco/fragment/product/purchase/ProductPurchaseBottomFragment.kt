package kr.co.lion.team4.mrco.fragment.product.purchase

import android.app.Dialog
import android.os.Bundle
import android.util.DisplayMetrics
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.divider.MaterialDividerItemDecoration
import kr.co.lion.team4.mrco.MainActivity
import kr.co.lion.team4.mrco.MainFragmentName
import kr.co.lion.team4.mrco.R
import kr.co.lion.team4.mrco.databinding.FragmentProductPurchaseBottomBinding
import kr.co.lion.team4.mrco.databinding.RowBottomProductPurchaseBinding
import kr.co.lion.team4.mrco.viewmodel.product.ProductPurchaseBottomViewModel

class ProductPurchaseBottomFragment(var buttonIdx:Boolean) : BottomSheetDialogFragment() {

    lateinit var fragmentProductPurchaseBottomBinding: FragmentProductPurchaseBottomBinding
    lateinit var mainActivity: MainActivity
    lateinit var productPurchaseBottomViewModel: ProductPurchaseBottomViewModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        fragmentProductPurchaseBottomBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_product_purchase_bottom, container, false)
        productPurchaseBottomViewModel = ProductPurchaseBottomViewModel()
        fragmentProductPurchaseBottomBinding.productPurchaseBottomViewModel = productPurchaseBottomViewModel
        fragmentProductPurchaseBottomBinding.lifecycleOwner = this

        mainActivity = activity as MainActivity


        settingRecyclerViewPurchaseBottom()



        return fragmentProductPurchaseBottomBinding.root
    }


    // 다이얼로그가 만들어질 때 자동으로 호출되는 메서드
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        // 다이얼로그를 받는다.
        val dialog = super.onCreateDialog(savedInstanceState)
        // 다이얼로그가 보일때 동작하는 리스너
        dialog.setOnShowListener {

            val bottomSheetDialog = it as BottomSheetDialog
            // 높이를 설정한다.
            settingBottomSheetHeight(bottomSheetDialog)
            settingButton()
        }
        return dialog
    }

    fun settingButton(){
        fragmentProductPurchaseBottomBinding.apply {
            buttonApply.apply {
                if (buttonIdx == false) { // 장바구니버튼을 눌러서 다이얼로그가 실행된 경우
                    text = "장바구니에 담기"
                    setOnClickListener {
                        // 장바구니에 데이터 담고 다이얼로그 종료
                    }
                } else { // 구매하기버튼을 눌러서 다이얼로그가 실행된 경우
                    text = "상품 구매하기"
                    setOnClickListener {
                        // 다이얼로그 종료
                        mainActivity.replaceFragment(
                            MainFragmentName.ORDER_FRAGMENT,true,true,null) // bundle에 구매하는 상품 정보 전달해야 함
                    }
                }
            }
        }
    }



    // BottomSheet의 높이를 설정해준다.
    fun settingBottomSheetHeight(bottomSheetDialog: BottomSheetDialog){
        // BottomSheet의 기본 뷰 객체를 가져온다
        val bottomSheet = bottomSheetDialog.findViewById<View>(com.google.android.material.R.id.design_bottom_sheet)!!

        // 바텀시트의 배경색을 변경한다 (둥근 모서리를 유지하기 위해 drawable 지정)
        bottomSheet.setBackgroundResource(R.drawable.bottom_sheet_background)

        // BottomSheet 높이를 설정할 수 있는 객체를 생성한다.
        val behavior = BottomSheetBehavior.from(bottomSheet)

        // 높이를 설정한다.
        val layoutParams = bottomSheet.layoutParams
        layoutParams.height = getBottomSheetDialogHeight()
        bottomSheet.layoutParams = layoutParams
        behavior.state = BottomSheetBehavior.STATE_EXPANDED
    }

    // BottomSheet의 높이를 구한다(화면 액정의 85% 크기)
    fun getBottomSheetDialogHeight() : Int {
        return (getWindowHeight() * 0.8).toInt()
    }

    // 사용자 단말기 액정의 길이를 구해 반환하는 메서드
    fun getWindowHeight() : Int {
        // 화면 크기 정보를 담을 배열 객체
        val displayMetrics = DisplayMetrics()
        // 액정의 가로 세로 길이 정보를 담아준다.
        mainActivity.windowManager.defaultDisplay.getMetrics(displayMetrics)
        // 세로길이를 반환해준다.
        return displayMetrics.heightPixels
    }

    // RecyclerView 구성
    fun settingRecyclerViewPurchaseBottom(){
        fragmentProductPurchaseBottomBinding.apply {
            recyclerViewProductPurchaseBottom.apply {
                // 어뎁터
                adapter = BottomRecyclerViewAdapter()
                // 레이아웃 매니저
                layoutManager = LinearLayoutManager(mainActivity)
            }
        }
    }

    // 댓글 목록을 보여줄 RecyclerView의 어뎁터
    inner class BottomRecyclerViewAdapter : RecyclerView.Adapter<BottomRecyclerViewAdapter.BottomViewHolder>(){

        inner class BottomViewHolder(rowBottomProductPurchaseBinding: RowBottomProductPurchaseBinding) : RecyclerView.ViewHolder(rowBottomProductPurchaseBinding.root){
            val rowBottomProductPurchaseBinding : RowBottomProductPurchaseBinding

            init {
                this.rowBottomProductPurchaseBinding = rowBottomProductPurchaseBinding

                rowBottomProductPurchaseBinding.root.layoutParams = ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT
                )
            }
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BottomViewHolder {
            // val rowBottomProductPurchaseBinding = RowBottomProductPurchaseBinding.inflate(layoutInflater)
            val rowBottomProductPurchaseBinding = DataBindingUtil.inflate<RowBottomProductPurchaseBinding>(layoutInflater, R.layout.row_bottom_product_purchase, parent, false)
            val productPurchaseBottomViewModel = ProductPurchaseBottomViewModel()
            rowBottomProductPurchaseBinding.productPurchaseBottomViewModel = productPurchaseBottomViewModel
            rowBottomProductPurchaseBinding.lifecycleOwner = this@ProductPurchaseBottomFragment

            val bottomViewHolder = BottomViewHolder(rowBottomProductPurchaseBinding)
            return bottomViewHolder
        }

        override fun getItemCount(): Int {
            return 3
        }

        override fun onBindViewHolder(holder: BottomViewHolder, position: Int) {

        }
    }

}