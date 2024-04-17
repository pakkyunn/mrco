package kr.co.lion.team4.mrco.fragment.productManagement

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.divider.MaterialDividerItemDecoration
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kr.co.lion.team4.mrco.CategoryId
import kr.co.lion.team4.mrco.CodiMbti
import kr.co.lion.team4.mrco.viewmodel.productManagement.AddProductDetailViewModel
import kr.co.lion.team4.mrco.viewmodel.productManagement.AddProductViewModel
import kr.co.lion.team4.mrco.MainActivity
import kr.co.lion.team4.mrco.MainFragmentName
import kr.co.lion.team4.mrco.R
import kr.co.lion.team4.mrco.databinding.FragmentAddProductBinding
import kr.co.lion.team4.mrco.databinding.ItemAddproductDetailBinding
import kr.co.lion.team4.mrco.databinding.ItemAddproductPhotoBinding
import java.text.SimpleDateFormat
import java.util.Date
import kr.co.lion.team4.mrco.Tools
import kr.co.lion.team4.mrco.dao.ProductDao
import kr.co.lion.team4.mrco.model.ProductModel

/* (판매자) 코디 상품 등록 화면 */

// 다이얼로그 코디상품(개별) 등록 인터페이스
interface AddProductDialogListener {
    fun onAddProductClicked(productData: Map<String, String>, isAddPicture: Boolean)
}

class AddProductFragment : Fragment(), AddProductDialogListener {
    lateinit var fragmentAddProductBinding: FragmentAddProductBinding
    lateinit var addProductViewModel: AddProductViewModel

    lateinit var mainActivity: MainActivity

    private val individualProductData: ArrayList<Map<String, String>> = ArrayList()

    // 개별 상품 추가 -> 이미지를 첨부한 적이 있는지...
    var isProductAddPicture = false

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        fragmentAddProductBinding = DataBindingUtil.inflate(inflater,
            R.layout.fragment_add_product, container, false)
        addProductViewModel = AddProductViewModel()
        fragmentAddProductBinding.addProductViewModel = addProductViewModel
        fragmentAddProductBinding.lifecycleOwner = this

        mainActivity = activity as MainActivity

        // setting toolbar, bottom navigation
        settingToolbarAddProduct()

        settingCategoryClickEvent()

        settingButtonAddProductDetail()

        settingBottomButton()


        // individualProductList의 모든 요소를 individualProductData에 추가 (테스트 용)
        // val individualProductLists: ArrayList<String> = arrayListOf("MRCO 맨투맨", "L(100)", "500", "상의", "Gray", "R.drawable.iu_image")
        // individualProductData[0] = individualProductLists

        // 리사이클러뷰 어댑터
        settingAddProductPhotoRecyclerView()
        settingAddProductDetailRecyclerView()

        return fragmentAddProductBinding.root
    }

    // setting toolbar
    fun settingToolbarAddProduct(){
        fragmentAddProductBinding.toolbarAddProduct.apply {
            setNavigationOnClickListener {
                backProcess()
            }
        }
    }

    // 코디 상품 및 재고 등록
    fun settingButtonAddProductDetail(){
        fragmentAddProductBinding.buttonAddProductDetail.setOnClickListener {
            val width = getDeviceWidth()
            val dialog = AddProductDialog(width!!)
            // 다이얼로그페이지 리스너 등록
            dialog.setListener(this)
            dialog.show(childFragmentManager, "AddProductDialog")
        }
    }

    // 카테고리 클릭 이벤트 설정
    fun settingCategoryClickEvent(){
        showSubCategoryTPO()
        showSubCategorySeason()
        showSubCategoryMood()
    }

    // 카테고리 TPO 선택하면 하위 카테고리 보이게 표시
    fun showSubCategoryTPO(){
        fragmentAddProductBinding.apply {
            chipAddProductTpo.setOnCheckedChangeListener { chip, isChecked ->
                // 체크된 상태
                if(isChecked){
                    textviewAddProductSubCategory.visibility = View.VISIBLE
                    chipgroupAddProductTpoSub.visibility = View.VISIBLE
                }
                // 체크 해제된 상태
                else{
                    textviewAddProductSubCategory.visibility = View.GONE
                    chipgroupAddProductTpoSub.visibility = View.GONE
                }
            }
        }
    }

    // 카테고리 SEASON 선택하면 하위 카테고리 보이게 표시
    fun showSubCategorySeason(){
        fragmentAddProductBinding.apply {
            chipAddProductSeason.setOnCheckedChangeListener { chip, isChecked ->
                // 체크된 상태
                if(isChecked){
                    textviewAddProductSubCategory.visibility = View.VISIBLE
                    chipgroupAddProductSeasonSub.visibility = View.VISIBLE
                }else{
                    textviewAddProductSubCategory.visibility = View.GONE
                    chipgroupAddProductSeasonSub.visibility = View.GONE
                }
            }
        }
    }

    // 카테고리 MOOD 선택하면 하위 카테고리 보이게 표시
    fun showSubCategoryMood(){
        fragmentAddProductBinding.apply {
            chipAddProductMood.setOnCheckedChangeListener { chip, isChecked ->
                // 체크된 상태
                if (isChecked){
                    textviewAddProductSubCategory.visibility = View.VISIBLE
                    chipgroupAddProductMoodSub.visibility = View.VISIBLE
                } else{
                    textviewAddProductSubCategory.visibility = View.GONE
                    chipgroupAddProductMoodSub.visibility = View.GONE
                }
            }
        }
    }

    // 취소 버튼
    fun settingBottomButton(){
        fragmentAddProductBinding.apply {
            // 취소 버튼
            buttonAddProductCancel.setOnClickListener {
                backProcess()
            }
            // 등록 버튼
            buttonAddProductSubmit.setOnClickListener {
                Log.d("test1234", "${individualProductData}")
                uploadProductData()
            }
        }
    }

    // 이전 화면으로 돌아가기
    fun backProcess(){
        mainActivity.removeFragment(MainFragmentName.ADD_PRODUCT_FRAGMENT)
    }

    // 코디 사진의 리사이클러뷰 setting
    fun settingAddProductPhotoRecyclerView(){
        fragmentAddProductBinding.apply {
            recyclerviewAddProductPhotos.apply {
                adapter = AddPhotoRecyclerViewAdapter()
                layoutManager = LinearLayoutManager(mainActivity, LinearLayoutManager.HORIZONTAL, false)
            }
        }
    }

    // 입력된 코디 상품 및 재고 리사이클러뷰
    fun settingAddProductDetailRecyclerView(){
        fragmentAddProductBinding.apply {
            recyclerviewAddProductDetail.apply {
                adapter = AddDetailRecyclerViewAdapter()
                layoutManager = LinearLayoutManager(mainActivity)
                val deco = MaterialDividerItemDecoration(mainActivity, MaterialDividerItemDecoration.VERTICAL)
                deco.isLastItemDecorated = false
                addItemDecoration(deco)
            }
        }
    }

    // 코디 사진의 리사이클러뷰 Adapter
    inner class AddPhotoRecyclerViewAdapter : RecyclerView.Adapter<AddPhotoRecyclerViewAdapter.AddPhotoViewHolder>(){
        inner class AddPhotoViewHolder(itemAddProductPhotoBinding: ItemAddproductPhotoBinding) : RecyclerView.ViewHolder(itemAddProductPhotoBinding.root){
            val itemAddProductPhotoBinding : ItemAddproductPhotoBinding
            init {
                this.itemAddProductPhotoBinding = itemAddProductPhotoBinding
                this.itemAddProductPhotoBinding.root.layoutParams = ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT
                )
            }
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AddPhotoViewHolder {
            val itemAddProductPhotoBinding = ItemAddproductPhotoBinding.inflate(layoutInflater)
            val addPhotoViewHolder = AddPhotoViewHolder(itemAddProductPhotoBinding)

            return addPhotoViewHolder
        }

        override fun onBindViewHolder(holder: AddPhotoViewHolder, position: Int) {
            // 이미지 세팅
        }

        override fun getItemCount(): Int {
            return 4
        }
    }

    // 입력된 코디 상품 및 재고 리사이클러뷰 Adapter
    inner class AddDetailRecyclerViewAdapter : RecyclerView.Adapter<AddDetailRecyclerViewAdapter.AddDetailViewHolder>(){
        inner class AddDetailViewHolder(itemAddproductDetailBinding: ItemAddproductDetailBinding): RecyclerView.ViewHolder(itemAddproductDetailBinding.root){
            val itemAddproductDetailBinding: ItemAddproductDetailBinding
            init {
                this.itemAddproductDetailBinding = itemAddproductDetailBinding
                this.itemAddproductDetailBinding.root.layoutParams = ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT
                )
            }
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AddDetailViewHolder {
            val itemAddproductDetailBinding = DataBindingUtil.inflate<ItemAddproductDetailBinding>(
                layoutInflater, R.layout.item_addproduct_detail, parent, false)
            val addProductDetailViewModel = AddProductDetailViewModel()
            itemAddproductDetailBinding.addProductDetailViewModel = addProductDetailViewModel
            itemAddproductDetailBinding.lifecycleOwner = this@AddProductFragment

            val addDetailViewHolder = AddDetailViewHolder(itemAddproductDetailBinding)

            return addDetailViewHolder
        }

        override fun onBindViewHolder(holder: AddDetailViewHolder, position: Int) {
            holder.itemAddproductDetailBinding.addProductDetailViewModel?.textviewAddProductDetailName?.value = "${individualProductData[position]["0"]}"
            holder.itemAddproductDetailBinding.addProductDetailViewModel?.textviewAddProductDetailOption?.value =
                "${individualProductData[position]["1"]} / ${individualProductData[position]["2"]}개 / ${individualProductData[position]["3"]} / ${individualProductData[position]["4"]}"

            holder.itemAddproductDetailBinding.imageviewAddProductDetailThumbnail.setImageResource(R.drawable.logo_mrco_removebg)

            holder.itemAddproductDetailBinding.buttonAddProductDetailRemove.setOnClickListener {
                individualProductData.removeAt(position)
                fragmentAddProductBinding.recyclerviewAddProductDetail.adapter?.notifyDataSetChanged()
            }
        }

        override fun getItemCount(): Int {
            return individualProductData.size
        }
    }

    // Dialog의 가로 길이를 조정하기 위해 필요한 '디바이스의 가로 길이' 구하기
   fun getDeviceWidth() : Int? {
        val display = mainActivity.applicationContext?.resources?.displayMetrics
        val deviceWidth = display?.widthPixels // 디바이스의 가로길이

        return deviceWidth
    }

    // 리스너 실행 후
    override fun onAddProductClicked(productData: Map<String, String>, isAddPicture: Boolean) {
        individualProductData.add(productData)
        isProductAddPicture = isAddPicture
        Log.d("test1234", "개별 상품 사진 등록 여부: $isProductAddPicture")
        // 리사이클러 뷰 초기화 
        fragmentAddProductBinding.recyclerviewAddProductDetail.adapter?.notifyDataSetChanged()
    }

    // 글 작성처리 메서드
    fun uploadProductData() {
        CoroutineScope(Dispatchers.Main).launch {

            // 서버에서의 첨부 이미지 파일 이름
            var serverFileName:String? = null

            // 첨부된 이미지가 있다면
            /*
            if(isProductAddPicture == true) {
                // 이미지의 뷰의 이미지 데이터를 파일로 저장한다.
                Tools.saveImageViewData(mainActivity, fragmentAddProductBinding.imageviewAddProductPhoto, "uploadTemp.jpg")
                // 서버에서의 파일 이름
                serverFileName = "image_${System.currentTimeMillis()}.jpg"
                // 서버로 업로드한다.
                ProductDao.uploadImage(mainActivity, "uploadTemp.jpg", serverFileName)
            }
            */

            // 게시글 시퀀스 값을 가져온다.
            val productSequence = ProductDao.getContentSequence()
            // 게시글 시퀀스 값을 업데이트 한다.
            ProductDao.updateProductSequence(productSequence + 1)

            // 업로드할 정보를 담아준다.
            val productIdx = productSequence + 1
            val categoryId = CategoryId.TPO.str
            val coordinatorIdx = 0
            val coordiName = addProductViewModel.edittextAddProductName.value!!
            val coordiImage = "코디 이미지들(이거 Map이나 List로 바꿔야함)"
            val codiMainImage = "코디 대표 이미지"
            val coordiGender = 0
            val coordiText = addProductViewModel.edittextAddProductComments.value!!
            val price = (addProductViewModel.edittextAddProductPrice.value!!).toInt()
            val coordiItem = individualProductData
            val coordiMBTI = CodiMbti.ENFJ.str
            val coordiTPO = 0
            val coordiSeason = 0
            val coordiMood = 0
            val coordiState = 0

            val simpleDateFormat = SimpleDateFormat("yyyy-MM-dd")
            val coordiWriteDate = simpleDateFormat.format(Date())
            /*
            val contentSubject = addContentViewModel.textFieldAddContentSubject.value!!
            val contentType = addContentViewModel.gettingContentType().number
            val contentText = addContentViewModel.textFieldAddContentText.value!!
            val contentImage = serverFileName
            val contentWriterIdx = contentActivity.loginUserIdx

            val contentState = ContentState.CONTENT_STATE_NORMAL.number
            */
            val productModel = ProductModel(productIdx, categoryId, coordinatorIdx, coordiName, coordiImage, codiMainImage, coordiGender,
                coordiText, price, coordiItem, coordiMBTI, coordiTPO, coordiSeason, coordiMood, coordiState, coordiWriteDate)
            // 업로드한다.
            ProductDao.insertProductData(productModel)

            // ReadContentFragment로 이동한다.
            Tools.hideSoftInput(mainActivity)

            // 글 번호를 담는다.
            val readBundle = Bundle()
            readBundle.putInt("productIdx", productIdx)

            mainActivity.replaceFragment(MainFragmentName.HOME_MAIN_FULL, false, false, readBundle)
        }
    }
}