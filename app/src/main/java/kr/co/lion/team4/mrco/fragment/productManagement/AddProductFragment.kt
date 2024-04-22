package kr.co.lion.team4.mrco.fragment.productManagement

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Color
import android.graphics.ImageDecoder
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.divider.MaterialDividerItemDecoration
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.storage.FirebaseStorage
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kr.co.lion.team4.mrco.CategoryId
import kr.co.lion.team4.mrco.CategoryIdSubMOOD
import kr.co.lion.team4.mrco.CategoryIdSubSEASON
import kr.co.lion.team4.mrco.CategoryIdSubTPO
import kr.co.lion.team4.mrco.CodiMbti
import kr.co.lion.team4.mrco.viewmodel.productManagement.AddProductDetailViewModel
import kr.co.lion.team4.mrco.viewmodel.productManagement.AddProductViewModel
import kr.co.lion.team4.mrco.MainActivity
import kr.co.lion.team4.mrco.MainFragmentName
import kr.co.lion.team4.mrco.ProductState
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
    fun onAddProductClicked(productData: MutableMap<String, String>)
}

class AddProductFragment : Fragment(), AddProductDialogListener {

    // FirebaseStorage 인스턴스 생성
    val storage = FirebaseStorage.getInstance()

    // 앨범 런처를 실행할 때 사용할 변수
    private var currentPosition: Int = -1

    lateinit var fragmentAddProductBinding: FragmentAddProductBinding
    lateinit var addProductViewModel: AddProductViewModel

    lateinit var mainActivity: MainActivity

    // 첨부 이미지 인덱스
    var imageIdx = -1

    // 첨부 이미지 리스트
    val imageProductList = mapOf<String, String>()

    private val individualProductData: ArrayList<MutableMap<String, String>> = ArrayList()

    // Activity 실행을 위한 런처
    lateinit var albumLauncher: ActivityResultLauncher<Intent>

    // 개별 상품 추가 -> 이미지를 첨부한 적이 있는지...
    var isProductAddPicture = false

    // 리사이클러뷰 아이템의 개수에 맞게 비트맵 배열 초기화
    var imageBitmaps = arrayListOf<Bitmap>()

    // firestore 이미지 경로 배열
    var imagePath = arrayListOf<String>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        fragmentAddProductBinding = DataBindingUtil.inflate(inflater,
            R.layout.fragment_add_product, container, false)
        addProductViewModel = AddProductViewModel()
        fragmentAddProductBinding.addProductViewModel = addProductViewModel
        fragmentAddProductBinding.lifecycleOwner = this

        mainActivity = activity as MainActivity

        // setting toolbar, bottom navigation
        settingToolbarAddProduct()

        // 입력 요소 초기화
        settingInputForm()

        // 카테고리 눌렀을때
        settingCategoryClickEvent()

        // 추가 등록하기 버튼 눌렀을때
        settingButtonAddProductDetail()

        // 최하단 (취소, 등록) 버튼
        settingBottomButton()

        // 리사이클러뷰 어댑터
        settingAddProductPhotoRecyclerView()
        settingAddProductDetailRecyclerView()

        settingItemsAlbumLauncher()

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

    // 등록, 취소 버튼
    fun settingBottomButton(){
        fragmentAddProductBinding.apply {
            // 취소 버튼
            buttonAddProductCancel.setOnClickListener {
                backProcess()
            }
            // 등록 버튼
            buttonAddProductSubmit.setOnClickListener {
                // 입력 요소 유효성 검사
                val chk = checkInputForm()

                if(chk == true) {
                    uploadProductData()
                }
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


            holder.itemAddproductDetailBinding.imageviewAddProductDetailThumbnail.setOnClickListener {
                startItemsAlbumLauncher(position)
                // holder.itemAddproductDetailBinding.imageviewAddProductDetailThumbnail.setImageBitmap(imageBitmaps[position])
            }

            // 이미지뷰에 해당 포지션의 비트맵 설정
            if (position < imageBitmaps.size) {
                holder.itemAddproductDetailBinding.imageviewAddProductDetailThumbnail.setImageBitmap(imageBitmaps[position])
            } else {
                // 해당 인덱스에 비트맵이 없는 경우 기본 이미지를 설정
                holder.itemAddproductDetailBinding.imageviewAddProductDetailThumbnail.setImageResource(R.drawable.photo_add)
            }

            holder.itemAddproductDetailBinding.buttonAddProductDetailRemove.setOnClickListener {
                individualProductData.removeAt(position)
                if (imageBitmaps.size > 0) {
                    imageBitmaps.removeAt(position)
                    imagePath.removeAt(position)
                }
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
    override fun onAddProductClicked(productData: MutableMap<String, String>) {
        individualProductData.add(productData)

        // 리사이클러 뷰 초기화 
        fragmentAddProductBinding.recyclerviewAddProductDetail.adapter?.notifyDataSetChanged()
    }

    // Chip 입력 처리 메서드
    fun settingChipEvent(){
        fragmentAddProductBinding.apply {

            // 성별
            chipgroupAddProductGender.apply {
                if (chipAddProductMale.isChecked){
                    Log.d("test1234", "ChipGroup is Working!")
                    addProductViewModel?.chipgroupAddProductGender?.value = 0
                } else if (chipAddProductFemale.isChecked){
                    addProductViewModel?.chipgroupAddProductGender?.value = 1
                } else {
                    addProductViewModel?.chipgroupAddProductGender?.value = -1
                }
            }
            // TPO
            chipgroupAddProductTpoSub.apply {
                if (chipAddProductTravel.isChecked) {
                    addProductViewModel?.chipgroupAddProductTpoSub?.value = CategoryIdSubTPO.TRAVEL
                } else if (chipAddProductDate.isChecked) {
                    addProductViewModel?.chipgroupAddProductTpoSub?.value = CategoryIdSubTPO.DATE
                } else if (chipAddProductCafe.isChecked) {
                    addProductViewModel?.chipgroupAddProductTpoSub?.value = CategoryIdSubTPO.CAFE
                } else if (chipAddProductWork.isChecked) {
                    addProductViewModel?.chipgroupAddProductTpoSub?.value = CategoryIdSubTPO.WORK
                } else if (chipAddProductDaily.isChecked) {
                    addProductViewModel?.chipgroupAddProductTpoSub?.value = CategoryIdSubTPO.DAILY
                } else if (chipAddProductCampus.isChecked) {
                    addProductViewModel?.chipgroupAddProductTpoSub?.value = CategoryIdSubTPO.CAMPUS
                } else if (chipAddProductOcean.isChecked) {
                    addProductViewModel?.chipgroupAddProductTpoSub?.value = CategoryIdSubTPO.OCEAN
                } else if (chipAddProductWedding.isChecked) {
                    addProductViewModel?.chipgroupAddProductTpoSub?.value = CategoryIdSubTPO.WEDDING
                }
            }

            // SEASON
            chipgroupAddProductSeasonSub.apply {
                if (chipAddProductSpring.isChecked){
                    addProductViewModel?.chipgroupAddProductSeasonSub?.value = CategoryIdSubSEASON.SPRING
                }
                else if ( chipAddProductSummer.isChecked){
                    addProductViewModel?.chipgroupAddProductSeasonSub?.value = CategoryIdSubSEASON.SUMMER
                }
                else if ( chipAddProductFall.isChecked){
                    addProductViewModel?.chipgroupAddProductSeasonSub?.value = CategoryIdSubSEASON.FALL
                }
                else if ( chipAddProductWinter.isChecked){
                    addProductViewModel?.chipgroupAddProductSeasonSub?.value = CategoryIdSubSEASON.WINTER
                }
            }

            // MOOD
            chipgroupAddProductMoodSub.apply {
                if(chipAddProductMinimal.isChecked){
                    addProductViewModel?.chipgroupAddProductMoodSub?.value = CategoryIdSubMOOD.MINIMAL
                }
                else if (chipAddProductBusiness.isChecked){
                    addProductViewModel?.chipgroupAddProductMoodSub?.value = CategoryIdSubMOOD.BUSINESS
                }
                else if (chipAddProductOneMile.isChecked){
                    addProductViewModel?.chipgroupAddProductMoodSub?.value = CategoryIdSubMOOD.ONE_MILE
                }
                else if (chipAddProductAmecaji.isChecked){
                    addProductViewModel?.chipgroupAddProductMoodSub?.value = CategoryIdSubMOOD.AMECAJI
                }
                else if (chipAddProductCityBoy.isChecked){
                    addProductViewModel?.chipgroupAddProductMoodSub?.value = CategoryIdSubMOOD.CITY_BOY
                }
                else if (chipAddProductStreet.isChecked){
                    addProductViewModel?.chipgroupAddProductMoodSub?.value = CategoryIdSubMOOD.STREET
                }
                else if (chipAddProductSporty.isChecked){
                    addProductViewModel?.chipgroupAddProductMoodSub?.value = CategoryIdSubMOOD.SPORTY
                }
                else if (chipAddProductRetro.isChecked){
                    addProductViewModel?.chipgroupAddProductMoodSub?.value = CategoryIdSubMOOD.RETRO
                }
                else if (chipAddProductLovely.isChecked){
                    addProductViewModel?.chipgroupAddProductMoodSub?.value = CategoryIdSubMOOD.LOVELY
                }
                else if (chipAddProductModern.isChecked){
                    addProductViewModel?.chipgroupAddProductMoodSub?.value = CategoryIdSubMOOD.MODERN
                }
            }


//            for (items in chipgroupAddProductTpoSub){
//                if (items.chip ){
//                    Log.d("test1234", "Working")
//                    addProductViewModel?.chipgroupAddProductTpoSub?.value = TRAVEL
//                }
//            }

//            // TPO
//            chipgroupAddProductTpoSub.apply {
//                if (this.checkedChipId){
//                    Log.d("test1234", "Working")
//                    addProductViewModel?.chipgroupAddProductTpoSub?.value = TRAVEL
//                }
//            }


//            chipgroupAddProductGender.setOnCheckedStateChangeListener{ group, checkedId ->
//                Log.d("test1234","Working")
//                addProductViewModel?.chipgroupAddProductGender?.value = when (group.checkedChipId){
//                    R.id.chip_add_product_male -> 0
//                    R.id.chip_add_product_female -> 1
//                    else -> 0
//                }
//                Log.d("test1234","End")
//            }

            // TPO
//            chipgroupAddProductTpoSub.setOnCheckedStateChangeListener{ _, checkedId ->
//                addProductViewModel?.chipgroupAddProductTpoSub?.value = when (checkedId){
//
//                    chipAddProductTravel -> TRAVEL
//                    chipAddProductDate -> DATE
//                    chipAddProductCafe -> CAFE
//                    chipAddProductWork -> WORK
//                    chipAddProductDaily -> DAILY
//                    chipAddProductCampus -> CAMPUS
//                    chipAddProductOcean -> OCEAN
//                    chipAddProductWedding -> WEDDING
//                    else -> TRAVEL
//                }
//            }

//            // Season
//            chipgroupAddProductSeasonSub.setOnCheckedStateChangeListener{ group, checkedId ->
//                addProductViewModel?.chipgroupAddProductSeasonSub?.value = when(checkedId){
//                    chipAddProductSpring -> SPRING
//                    chipAddProductSummer -> SUMMER
//                    chipAddProductFall -> FALL
//                    chipAddProductWinter -> WINTER
//                    else -> SPRING
//                }
//            }

//            // Mood
//            chipgroupAddProductMoodSub.setOnCheckedStateChangeListener{ group, checkedId ->
//                addProductViewModel?.chipgroupAddProductMoodSub?.value = when(checkedId){
//                    chipAddProductMinimal -> MINIMAL
//                    chipAddProductBusiness -> BUSINESS
//                    chipAddProductOneMile -> ONE_MILE
//                    chipAddProductAmecaji -> AMECAJI
//                    chipAddProductCityBoy -> CITY_BOY
//                    chipAddProductStreet -> STREET
//                    chipAddProductSporty -> SPORTY
//                    chipAddProductRetro -> RETRO
//                    chipAddProductLovely -> LOVELY
//                    chipAddProductModern -> MODERN
//                    else -> MINIMAL
//                }
//            }
        }
    }


    // 글 작성처리 메서드
    fun uploadProductData() {
        CoroutineScope(Dispatchers.Main).launch {

            // 첨부된 이미지가 있다면
            if(isProductAddPicture == true) {
                for (i in 0 until imageBitmaps.size) {
                    // 이미지의 뷰의 이미지 데이터를 파일로 저장한다.
                    Tools.saveImageViewIndividualItemData(mainActivity, imageBitmaps[i], "uploadTemp${i}.jpg")
                    // 서버로 업로드한다.
                    ProductDao.uploadItemsImage(mainActivity, "uploadTemp${i}.jpg", imagePath[i])
                    Log.d("test1234", "${i}번째 상품 아이템 - ${imageBitmaps[i]}}개별 상품 아이템 서버로 업로드 완료")
                }
            }
            // 개별 상품 아이템 별 이미지 경로 수정
            for (i in 0 until individualProductData.size) {
                val temp = individualProductData[i]
                temp["5"] = imagePath[i]
            }

            Log.d("test1234", "AddProductFragment : ${individualProductData}")

            // 게시글 시퀀스 값을 가져온다.
            val productSequence = ProductDao.getContentSequence()
            // 게시글 시퀀스 값을 업데이트 한다.
            ProductDao.updateProductSequence(productSequence + 1)

            // Chip 클릭시 정보 동기화
            settingChipEvent()

            // 현재 시간 년월일_시분초
            val currentTimeText = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())

            // 업로드할 정보를 담아준다.
            val productIdx = productSequence + 1
            val categoryId = CategoryId.TPO.str
            val coordinatorIdx = 0 // TEMP
            val coordiName = addProductViewModel.edittextAddProductName.value!!
            val coordiImage = imageProductList
            val codiMainImage = "image_codiMain_${currentTimeText}.jpg"
            val coordiGender = addProductViewModel.chipgroupAddProductGender.value!!
            val coordiText = addProductViewModel.edittextAddProductComments.value!!
            val price = (addProductViewModel.edittextAddProductPrice.value!!).toInt()
            val coordiItem = individualProductData
            val coordiMBTI = CodiMbti.ENFJ.str
            val coordiTPO = addProductViewModel.chipgroupAddProductTpoSub.value
            val coordiSeason = addProductViewModel.chipgroupAddProductSeasonSub.value
            val coordiMood = addProductViewModel.chipgroupAddProductMoodSub.value
            val coordiState = ProductState.PRODUCT_STATE_NORMAL.num

            // 현재 시간 등록 yyyy년 MM월 dd일 HH시간 mm분 ss초
            val simpleDateFormat = SimpleDateFormat("yyyy_MM_dd")
            val coordiWriteDate = simpleDateFormat.format(Date())

            val productModel = ProductModel(productIdx, categoryId, coordinatorIdx, coordiName, coordiImage, codiMainImage, coordiGender,
                coordiText, price, coordiItem, coordiMBTI, coordiTPO, coordiSeason, coordiMood, coordiState, coordiWriteDate)

            // 업로드한다.
            ProductDao.insertProductData(productModel)

            // ReadContentFragment로 이동한다.
            Tools.hideSoftInput(mainActivity)

            val snackbar = Snackbar.make(fragmentAddProductBinding.root, "코디상품이 등록되었습니다", Snackbar.LENGTH_SHORT)
            snackbar.setTextColor(Color.WHITE)
            snackbar.setBackgroundTint(Color.parseColor("#757575"))
            snackbar.animationMode = Snackbar.ANIMATION_MODE_FADE
            snackbar.show()

            // 글 번호를 담는다.
            val readBundle = Bundle()
            readBundle.putInt("productIdx", productIdx)

            mainActivity.replaceFragment(MainFragmentName.HOME_MAIN_FULL, false, false, readBundle)
        }
    }

    // 입력 요소 설정
    fun settingInputForm() {
        addProductViewModel.edittextAddProductName.value = ""
        addProductViewModel.edittextAddProductComments.value = ""
        addProductViewModel.edittextAddProductPrice.value = ""

        addProductViewModel.chipgroupAddProductGender.value = -1
        addProductViewModel.chipgroupAddProductMbti.value = CodiMbti.DEFAULT_MBTI
    }

    // 입력 요소 유효성 검사
    fun checkInputForm(): Boolean{
        // 입력한 내용을 가져온다.
        val productName = addProductViewModel.edittextAddProductName.value!!
        val productComments = addProductViewModel.edittextAddProductComments.value!!
        val productPrice = addProductViewModel.edittextAddProductPrice.value!!

//        val productGender = addProductViewModel.chipgroupAddProductGender.value!!
//        val productMbti = addProductViewModel.chipgroupAddProductMbti.value!!

        if(productName.isEmpty()){
            Tools.showErrorDialog(mainActivity, fragmentAddProductBinding.edittextAddProductName,
                "상품명 입력 오류", "상품명을 입력해주세요")
            return false
        }
        if(productComments.isEmpty()){
            Tools.showErrorDialog(mainActivity, fragmentAddProductBinding.edittextAddProductComments,
                "코디 소개 내용 입력 오류", "코디 소개 내용을 입력해주세요")
            return false
        }
        if(productPrice.isEmpty()){
            Tools.showErrorDialog(mainActivity, fragmentAddProductBinding.edittextAddProductPrice,
                "코디 상품 가격 오류", "코디 상품 가격을 입력해주세요")
            return false
        }
//        if (productGender == -1){
//            Tools.showErrorDialog(mainActivity, fragmentAddProductBinding.chipgroupAddProductGender,
//                "코디 성별 미입력 오류", "코디 상품 대상 성별을 입력해주세요")
//            return false
//        }
//        if (productMbti == CodiMbti.DEFAULT_MBTI){
//            Tools.showErrorDialog(mainActivity, fragmentAddProductBinding.chipAddProductMbti,
//                "코디 MBTI 미입력 오류", "코디 상품 대상 MBTI를 입력해주셍요")
//            return false
//        }
        return true
    }

    // 앨범 런처 설정
    fun settingItemsAlbumLauncher() {
        // 앨범 실행을 위한 런처
        val contract2 = ActivityResultContracts.StartActivityForResult()
        albumLauncher = registerForActivityResult(contract2){
            // 사진 선택을 완료한 후 돌아왔다면
            if(it.resultCode == AppCompatActivity.RESULT_OK){
                // 선택한 이미지의 경로 데이터를 관리하는 Uri 객체를 추출한다.
                val uri = it.data?.data
                if (uri != null){
                    // 안드로이드 Q(10) 이상이라면
                    val bitmap = if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q){
                        // 이미지를 생성할 수 있는 객체를 생성한다.
                        val source = ImageDecoder.createSource(mainActivity.contentResolver, uri)
                        // Bitmap을 생성한다.
                        ImageDecoder.decodeBitmap(source)
                    } else {
                        // 컨텐츠 프로바이더를 통해 이미지 데이터에 접근한다.
                        val cursor = mainActivity.contentResolver.query(uri, null, null, null, null)
                        if(cursor != null){
                            cursor.moveToNext()

                            // 이미지의 경로를 가져온다.
                            val idx = cursor.getColumnIndex(MediaStore.Images.Media.DATA)
                            val source = cursor.getString(idx)

                            // 이미지를 생성한다
                            BitmapFactory.decodeFile(source)
                        }  else {
                            null
                        }
                    }

                    // 회전 각도값을 가져온다.
                    val degree = Tools.getDegree(mainActivity, uri)
                    // 회전 이미지를 가져온다
                    val bitmap2 = Tools.rotateBitmap(bitmap!!, degree.toFloat())
                    // 크기를 줄인 이미지를 가져온다.
                    val bitmap3 = Tools.resizeBitmap(bitmap2, 256)

                    // 이미지 비트맵이 추가되는지 확인하고 추가
                    imageBitmaps.add(bitmap3)

                    Log.d("test1234", "이미지 배열: $imageBitmaps")

                    val simpleDateFormat = SimpleDateFormat("yyyyMMdd_HHmmss")
                    val currentTimeText = simpleDateFormat.format(Date())

                    // 서버에서의 첨부 이미지 파일 이름
                    // var serverFileName = "image_individual_item${currentPosition}_${System.currentTimeMillis()}.jpg"
                    var serverFileName = "image_individual_item${currentPosition}_${currentTimeText}.jpg"
                    imagePath.add(serverFileName)

                    // 리사이클러뷰를 새로 고침하여 업데이트
                    fragmentAddProductBinding.recyclerviewAddProductDetail.adapter?.notifyDataSetChanged()

                    // dialogAddProductBinding.imageviewDialogAddProduct.setImageBitmap(bitmap3)
                    isProductAddPicture = true
                }
            }
        }
    }

    // 앨범 런처를 실행하는 메서드
    fun startItemsAlbumLauncher(position: Int) {

        // currentPosition 변수에 현재 포지션 값을 저장
        currentPosition = position

        // 앨범에서 사진을 선택할 수 있도록 셋팅된 인텐트를 생성한다.
        val albumIntent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        // 실행할 액티비티의 타입을 설정(이미지를 선택할 수 있는 것이 뜨게 한다)
        albumIntent.setType("image/*")
        // 선택할 수 있는 파들의 MimeType을 설정한다.
        // 여기서 선택한 종류의 파일만 선택이 가능하다. 모든 이미지로 설정한다.
        val mimeType = arrayOf("image/*")
        albumIntent.putExtra(Intent.EXTRA_MIME_TYPES, mimeType)
        albumLauncher.launch(albumIntent)
    }
}