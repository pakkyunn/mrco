package kr.co.lion.team4.mrco.fragment.product.codi

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.divider.MaterialDividerItemDecoration
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kr.co.lion.team4.mrco.MainActivity
import kr.co.lion.team4.mrco.R
import kr.co.lion.team4.mrco.dao.ProductDao
import kr.co.lion.team4.mrco.databinding.FragmentCodiProductInfoTopBinding
import kr.co.lion.team4.mrco.databinding.RowCodiProductInfoTopBinding
import kr.co.lion.team4.mrco.model.ProductCategoryLinkedListModel
import kr.co.lion.team4.mrco.viewmodel.CodiProductInfoTopViewModel

class CodiProductInfoTopFragment : Fragment() {

    private lateinit var binding: FragmentCodiProductInfoTopBinding
    private lateinit var viewModel: CodiProductInfoTopViewModel
    private lateinit var mainActivity: MainActivity


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        Log.d("taejin", "코디 상품 상세 - 상의")
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_codi_product_info_top, container, false)
        viewModel = CodiProductInfoTopViewModel()
        mainActivity = activity as MainActivity

        binding.lifecycleOwner = this
        binding.codiProductInfoTopViewModel = viewModel

        settingView()

        return binding.root
    }


    fun settingInputForm(){
        val bundle = arguments
        var productIdx = 0
        if (bundle != null){
            productIdx = bundle.getInt("productIdx")
        }

        CoroutineScope(Dispatchers.Main).launch {

            // 현재 글 번호에 해당하는 글 데이터를 가져온다.
            val productModel = ProductDao.selectProductData(productIdx)
            val productCategoryLinkedListModel = ProductDao.selectProductInfoData(productIdx)


//            // 가져온 데이터를 보여준다.
//            viewModel.codiProductName.value = productModel?.coordiName
//
//            viewModel.codiProductType.value = when(productModel?.categoryId){
//                ContentType.TYPE_FREE.number -> ContentType.TYPE_FREE.str
//                ContentType.TYPE_HUMOR.number -> ContentType.TYPE_HUMOR.str
//                ContentType.TYPE_SOCIETY.number -> ContentType.TYPE_SOCIETY.str
//                ContentType.TYPE_SPORTS.number -> ContentType.TYPE_SPORTS.str
//                else -> ""
//            }
//
//            readContentViewModel.textFieldReadContentNickName.value = userModel?.userNickName
//            readContentViewModel.textFieldReadContentDate.value = contentModel?.contentWriteDate
//            readContentViewModel.textFieldReadContentText.value = contentModel?.contentText
//
//            // 글 작성자와 로그인한 사람이 같은지 확인한다.
//            isContentWriter = contentActivity.loginUserIdx == contentModel?.contentWriterIdx
//
//            // 이미지 데이터를 불러온다.
//            if(contentModel?.contentImage != null) {
//                ContentDao.gettingContentImage(contentActivity, contentModel.contentImage!!, fragmentReadContentBinding.imageViewReadContent)
//            }
        }
    }

    private fun settingView(){
        binding.apply {
            recyclerViewProductInfoTop.apply {
                Log.d("CodiProductInfoFragment", "settingView")

                // 어댑터 설정
                adapter = CodiProductInfoTopAdapater()
                // 레이아웃 매니저
                layoutManager = LinearLayoutManager(mainActivity)
                // 구분선 추가
                val deco = MaterialDividerItemDecoration(mainActivity, MaterialDividerItemDecoration.VERTICAL)
                addItemDecoration(deco)
            }
        }
    }



    inner class CodiProductInfoTopAdapater: RecyclerView.Adapter<CodiProductInfoTopAdapater.TopViewHolder>(){
        inner class TopViewHolder(rowCodiProductInfoTopBinding: RowCodiProductInfoTopBinding): RecyclerView.ViewHolder(rowCodiProductInfoTopBinding.root){
            val rowCodiProductInfoTopBinding: RowCodiProductInfoTopBinding
            init {
                this.rowCodiProductInfoTopBinding = rowCodiProductInfoTopBinding

                this.rowCodiProductInfoTopBinding.root.layoutParams = ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT
                )
            }
        }
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CodiProductInfoTopAdapater.TopViewHolder {
            val rowCodiProductInfoTopBinding = DataBindingUtil.inflate<RowCodiProductInfoTopBinding>(layoutInflater, R.layout.row_codi_product_info_top, parent, false)
            val rowCodiProductInfoTopViewModel = CodiProductInfoTopViewModel()
            rowCodiProductInfoTopBinding.rowCodiProductInfoTopViewModel = rowCodiProductInfoTopViewModel
            rowCodiProductInfoTopBinding.lifecycleOwner = this@CodiProductInfoTopFragment

            val topViewHolder = TopViewHolder(rowCodiProductInfoTopBinding)
            return topViewHolder
        }

        override fun onBindViewHolder(holder: CodiProductInfoTopAdapater.TopViewHolder, position: Int) {
            Log.d("CodiProductInfoFragment", "onBindViewHolder start")
//            holder.rowCodiProductInfoTopBinding.rowCodiProductInfoTopViewModel?.productNameTop?.value = productTopList[position].itemName
//            holder.rowCodiProductInfoTopBinding.rowCodiProductInfoTopViewModel?.productTypeTop?.value = productTopList[position].itemCategory
//            holder.rowCodiProductInfoTopBinding.rowCodiProductInfoTopViewModel?.productSizeTop?.value = productTopList[position].itemSize.toString()
            Log.d("CodiProductInfoFragment", "onBindViewHolder end")
        }

        override fun getItemCount(): Int {
//            return productTopList.size
            return 6
        }

    }
}