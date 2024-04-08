package kr.co.lion.team4.mrco.fragment.cart

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kr.co.lion.team4.mrco.viewmodel.cart.CartItemViewModel
import kr.co.lion.team4.mrco.viewmodel.cart.CartViewModel
import kr.co.lion.team4.mrco.MainActivity
import kr.co.lion.team4.mrco.MainFragmentName
import kr.co.lion.team4.mrco.R
import kr.co.lion.team4.mrco.databinding.FragmentCartBinding
import kr.co.lion.team4.mrco.databinding.ItemCartBinding

/* (구매자) 장바구니 화면 */
class CartFragment : Fragment() {
    lateinit var fragmentCartBinding: FragmentCartBinding
    lateinit var cartViewModel : CartViewModel

    lateinit var mainActivity: MainActivity
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        fragmentCartBinding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_cart, container, false)
        cartViewModel = CartViewModel()
        fragmentCartBinding.cartViewModel = cartViewModel
        fragmentCartBinding.lifecycleOwner = this

        mainActivity = activity as MainActivity

        mainActivity.removeBottomSheet()
        toolbarSetting()

        mainActivity.removeBottomSheet() // 하단 메뉴 제거

        settingCartProductsRecyclerView()
        checkOutProducts()

        return fragmentCartBinding.root
    }

    // 툴바 설정
    fun toolbarSetting(){
        fragmentCartBinding.toolbarCart.apply {
            // 네비게이션
            setNavigationIcon(R.drawable.arrow_back_24px)
            setNavigationOnClickListener {
                backProcesss()
            }
        }
    }

    // 결제 버튼 클릭이벤트 (결제할 상품 확인 후, 결제 화면으로 이동)
    fun checkOutProducts(){
        fragmentCartBinding.apply {
            // to do - 선택된 상품이 있는지, 없는지 확인

            buttonCartCheckout.setOnClickListener {
                // to do - 장바구니에서 선택한 상품 정보를 주문 화면으로 전달할 것!
                mainActivity.replaceFragment(MainFragmentName.ORDER_FRAGMENT, true, true, null)
            }
        }
    }

    // 장바구니에 담은 상품 목록 리사이클러뷰 세팅
    fun settingCartProductsRecyclerView(){
        fragmentCartBinding.apply {
            recyclerviewCartProducts.apply {
                adapter = CartProductsRecyclerViewAdapter()
                layoutManager = LinearLayoutManager(mainActivity)
            }
        }
    }

    inner class CartProductsRecyclerViewAdapter : RecyclerView.Adapter<CartProductsRecyclerViewAdapter.CartProductViewHolder>(){
        inner class CartProductViewHolder(itemCartBinding:ItemCartBinding):RecyclerView.ViewHolder(itemCartBinding.root){
            val itemCartBinding : ItemCartBinding
            init {
                this.itemCartBinding = itemCartBinding
                this.itemCartBinding.root.layoutParams = ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT
                )
            }
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartProductViewHolder {
            val itemCartBinding = DataBindingUtil.inflate<ItemCartBinding>(
                layoutInflater, R.layout.item_cart, parent, false)
            val cartItemViewModel = CartItemViewModel()
            itemCartBinding.cartItemViewModel = cartItemViewModel
            itemCartBinding.lifecycleOwner = this@CartFragment

            val cartProductViewHolder = CartProductViewHolder(itemCartBinding)

            return cartProductViewHolder
        }

        override fun onBindViewHolder(holder: CartProductViewHolder, position: Int) {
            // 체크박스
            holder.itemCartBinding.cartItemViewModel?.checkboxCartItemSelect?.value = true
            // 코디네이터 명
            holder.itemCartBinding.cartItemViewModel?.textviewCartItemCoordinator?.value = "코디네이터 명"
            // 상품명
            holder.itemCartBinding.cartItemViewModel?.textviewCartItemCoordiName?.value = "코디 상품 명 - OOO룩"
            // 옵션, 수량
            holder.itemCartBinding.cartItemViewModel?.textviewCartItemOption?.value = "옵션: 상의, 하의, 신발 / 수량: 1"
            holder.itemCartBinding.cartItemViewModel?.textviewCartItemPrice?.value = "100,000원"
        }

        override fun getItemCount(): Int {
            return 5
        }
    }

    // 뒤로가기 처리
    fun backProcesss(){
        mainActivity.removeFragment(MainFragmentName.CART_FRAGMENT)
        mainActivity.viewBottomSheet()
    }
}