package kr.co.lion.team4.mrco.dao

import android.content.Context
import android.net.Uri
import android.view.View
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.google.firebase.Firebase
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.firestore
import com.google.firebase.storage.storage
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kr.co.lion.team4.mrco.ProductState
import kr.co.lion.team4.mrco.model.ProductCategoryLinkedListModel
import kr.co.lion.team4.mrco.model.ProductModel
import java.io.File

class ProductDao {
    companion object {

        // 이미지 데이터를 firebase storage에 업로드는 메서드
        suspend fun uploadItemsImage(context: Context, fileName:String, uploadFileName:String) {
            // 외부저장소 까지의 경로를 가져온다.
            val filePath = context.getExternalFilesDir(null).toString()
            // 서버로 업로드할 파일의 경로
            val file = File("${filePath}/${fileName}")
            val uri = Uri.fromFile(file)

            val job1 = CoroutineScope(Dispatchers.IO).launch {
                // Storage에 접근할 수 있는 객체를 가져온다.(폴더의 이름과 파일이름을 저장해준다.
                val storageRef = Firebase.storage.reference.child("product_image/items/$uploadFileName")
                // 업로드한다.
                storageRef.putFile(uri)
            }

            job1.join()
        }

        // 이미지 데이터를 받아오는 메서드
        suspend fun gettingProductImage(context: Context, imageFileName:String, imageView: ImageView){
            val job1 = CoroutineScope(Dispatchers.IO).launch {
                // 이미지에 접근할 수 있는 객체를 가져온다.
                val storageRef = Firebase.storage.reference.child("product_image/items/$imageFileName")
                // 이미지의 주소를 가지고 있는 Uri 객체를 받아온다.
                val imageUri = storageRef.downloadUrl.await()
                // 이미지 데이터를 받아와 이미지 뷰에 보여준다.
                val job2 = CoroutineScope(Dispatchers.Main).launch {
                    val requestOptions = RequestOptions()
                        .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC) // 자동으로 디스크 캐시 사용
                        .skipMemoryCache(false) // 메모리 캐시 사용
                    Glide.with(context).load(imageUri).apply(requestOptions).into(imageView)
                    // 이미지 뷰가 나타나게 한다.
                    imageView.visibility = View.VISIBLE
                }
                job2.join()
            }
            job1.join()

            // 이미지는 용량이 매우 클 수 있다. 즉 이미지 데이터를 내려받는데 시간이 오래걸릴 수도 있다.
            // 이에, 이미지 데이터를 받아와 보여주는 코루틴을 작업이 끝날 때 까지 대기 하지 않는다.
            // 그 이유는 데이터를 받아오는데 걸리는 오랜 시간 동안 화면에 아무것도 나타나지 않을 수 있기 때문이다.
            // 따라서 이 메서드는 제일 마지막에 호출해야 한다.(다른 것들을 모두 보여준 후에...)
        }


        // 모든 상품의 정보를 가져온다.
        suspend fun gettingProductAll(): MutableList<ProductModel> {
            // 상품 정보를 담을 리스트
            val productList = mutableListOf<ProductModel>()

            val job1 = CoroutineScope(Dispatchers.IO).launch {
                // 모든 상품 정보를 가져온다
                val collectionReference = Firebase.firestore.collection("ProductData")
                // 상품의 상태가 정상 상태이고 상품 인덱스를 기준으로 내림차순 정렬되게 데이터를 가져올 수 있는 Query
                var query = collectionReference.whereEqualTo("coordiState", ProductState.PRODUCT_STATE_NORMAL.num)
                // 내림 차순 정렬
                query = query.orderBy("productIdx", Query.Direction.DESCENDING)
                val queryShapshot = query.get().await()
                // 가져온 문서의 수 만큼 반복한다.
                queryShapshot.forEach {
                    // ProductModel 객체에 담고 객체를 리스트에 담는다.
                    val productModel = it.toObject(ProductModel::class.java)
                    productList.add(productModel)
                }
            }
            job1.join()

            return productList
        }

        // 가장 좋아요 수가 높은 상품 최대 20개를 가져온다.
        suspend fun gettingRecommendProductList(productGender: Int): MutableList<ProductModel> {
            val productList = mutableListOf<ProductModel>()

            val job1 = CoroutineScope(Dispatchers.IO).launch {
                val collectionReference = Firebase.firestore.collection("ProductData")
                var query = collectionReference.whereEqualTo("coordiState", ProductState.PRODUCT_STATE_NORMAL.num)
                    .orderBy("productLikes", Query.Direction.DESCENDING)
                    .whereEqualTo("coordiGender", productGender)

                val querySnapshot = query.get().await()
                val documents = querySnapshot.documents.take(20) // 최대 20개까지만 가져오도록 조정

                documents.forEach { document ->
                    val productModel = document.toObject(ProductModel::class.java)
                    productModel?.let {
                        productList.add(it)
                    }
                }
            }
            job1.join()

            return productList
        }

        // 가장 최신 상품 최대 20개를 가져온다.
        suspend fun gettingNewProductList(productGender: Int): MutableList<ProductModel> {
            val productList = mutableListOf<ProductModel>()

            val job1 = CoroutineScope(Dispatchers.IO).launch {
                val collectionReference = Firebase.firestore.collection("ProductData")
                var query = collectionReference.whereEqualTo("coordiState", ProductState.PRODUCT_STATE_NORMAL.num)
                    .orderBy("productIdx", Query.Direction.DESCENDING)
                    .whereEqualTo("coordiGender", productGender)

                val querySnapshot = query.get().await()
                val documents = querySnapshot.documents.take(20) // 최대 20개까지만 가져오도록 조정

                documents.forEach { document ->
                    val productModel = document.toObject(ProductModel::class.java)
                    productModel?.let {
                        productList.add(it)
                    }
                }
            }
            job1.join()

            return productList
        }

        // 해당 성별, MBTI에 맞는 상품을 가져온다
        suspend fun gettingProductMBTIList(productMBTI:String, productGender: Int): MutableList<ProductModel>{
            // 게시글 정보를 담을 리스트
            val productList = mutableListOf<ProductModel>()

            val job1 = CoroutineScope(Dispatchers.IO).launch {
                // 모든 상품 정보를 가져온다
                val collectionReference = Firebase.firestore.collection("ProductData")
                // 상품의 상태가 정상 상태이고 상품 인덱스를 기준으로 내림차순 정렬되게 데이터를 가져올 수 있는 Query
                var query = collectionReference.whereEqualTo("coordiState", ProductState.PRODUCT_STATE_NORMAL.num)
                // 내림 차순 정렬
                query = query.orderBy("productIdx", Query.Direction.DESCENDING)
                // 해당 MBTI에 맞는 상품 찾기
                query = query.whereEqualTo("coordiMBTI", productMBTI)
                // 해당 성별에 맞는 상품 찾기
                query = query.whereEqualTo("coordiGender", productGender)

                val queryShapshot = query.get().await()
                // 가져온 문서의 수 만큼 반복한다.
                queryShapshot.forEach {
                    // ProductModel 객체에 담고 객체를 리스트에 담는다.
                    val productModel = it.toObject(ProductModel::class.java)
                    productList.add(productModel)
                }
            }
            job1.join()

            return productList
        }

        // 해당 성별, MBTI에 맞는 상품을 가져온다
        suspend fun gettingProductListOneCoordinator(coordinatorIdx:Int): MutableList<ProductModel>{
            // 게시글 정보를 담을 리스트
            val productList = mutableListOf<ProductModel>()

            val job1 = CoroutineScope(Dispatchers.IO).launch {
                // 모든 상품 정보를 가져온다
                val collectionReference = Firebase.firestore.collection("ProductData")
                // 상품의 상태가 정상 상태이고 상품 인덱스를 기준으로 내림차순 정렬되게 데이터를 가져올 수 있는 Query
                var query = collectionReference.whereEqualTo("coordiState", ProductState.PRODUCT_STATE_NORMAL.num)
                // 내림 차순 정렬
                query = query.orderBy("productIdx", Query.Direction.DESCENDING)
                // 해당 코디네이터의 상품만 찾기
                query = query.whereEqualTo("coordinatorIdx", coordinatorIdx)

                val queryShapshot = query.get().await()
                // 가져온 문서의 수 만큼 반복한다.
                queryShapshot.forEach {
                    // ProductModel 객체에 담고 객체를 리스트에 담는다.
                    val productModel = it.toObject(ProductModel::class.java)
                    productList.add(productModel)
                }
            }
            job1.join()

            return productList
        }

        // 상품 번호 시퀀스값을 가져온다.
        suspend fun getContentSequence():Int{
            var productSequence = -1

            val job1 = CoroutineScope(Dispatchers.IO).launch {
                // 컬렉션에 접근할 수 있는 객체를 가져온다.
                val collectionReference = Firebase.firestore.collection("Sequence")
                // 상품 번호 시퀀스 값을 가지고 있는 문서에 접근할 수 있는 객체를 가져온다.
                val documentReference = collectionReference.document("ProductSequence")
                // 문서내에 있는 데이터를 가져올 수 있는 객체를 가져온다.
                val documentSnapShot = documentReference.get().await()
                productSequence = documentSnapShot.getLong("value")?.toInt()!!
            }
            job1.join()

            return productSequence
        }

        // 상품 시퀀스 값을 업데이트 한다.
        suspend fun updateProductSequence(productSequence:Int){
            val job1 = CoroutineScope(Dispatchers.IO).launch {
                // 컬렉션에 접근할 수 있는 객체를 가져온다.
                val collectionReference = Firebase.firestore.collection("Sequence")
                // 상품 번호 시퀀스 값을 가지고 있는 문서에 접근할 수 있는 객체를 가져온다.
                val documentReference = collectionReference.document("ProductSequence")
                // 저장할 데이터를 담을 HashMap을 만들어준다.
                val map = mutableMapOf<String, Long>()
                map["value"] = productSequence.toLong()
                // 저장한다.
                documentReference.set(map)
            }
            job1.join()
        }

        // 상품 정보를 저장한다.
        suspend fun insertProductData(productModel: ProductModel){
            val job1 = CoroutineScope(Dispatchers.IO).launch {
                // 컬렉션에 접근할 수 있는 객체를 가져온다.
                val collectionReference = Firebase.firestore.collection("ProductData")
                // 컬럭션에 문서를 추가한다.
                // 문서를 추가할 때 객체나 맵을 지정한다.
                // 추가된 문서 내부의 필드는 객체가 가진 프로퍼티의 이름이나 맵에 있는 데이터의 이름과 동일하게 결정된다.
                collectionReference.add(productModel)
            }
            job1.join()
        }


        // 상품 목록을 가져온다.
        suspend fun gettingProductList(coordinatorIdx: Int):MutableList<ProductModel>{
            // 댓글 정보를 담을 리스트
            val plyList = mutableListOf<ProductModel>()
            val job1 = CoroutineScope(Dispatchers.IO).launch {
                // 컬렉션에 접근할 수 있는 객체를 가져온다.
                val collectionReference = Firebase.firestore.collection("ProductData")
                // 댓글 상태가 정상 상태이고 댓글 번호를 기준으로 내림차순 정렬되게 데이터를 가져올 수 있는
                // Query를 생성한다.
                // 댓글 상태가 정상 상태인 것만..
                var query = collectionReference.whereEqualTo("coordiState", ProductState.PRODUCT_STATE_NORMAL.num)
                // 코디네이터 번호에 해당하는 것들만
//                query = query.whereEqualTo("coordinatorIdx", coordinatorIdx)
                // 작성일자를 기준으로 내림 차순 정렬..
//                query = query.orderBy("coordiWriteDate", Query.Direction.DESCENDING)
                val queryShapshot = query.get().await()
                // 가져온 문서의 수 만큼 반복한다.
                queryShapshot.forEach {
                    // 현재 번째의 문서를 객체로 받아온다.
                    val productModel = it.toObject(ProductModel::class.java)
                    // 객체를 리스트에 담는다.
                    plyList.add(productModel)
                }
            }
            job1.join()
            return plyList
        }

        // 선택한 상품의 상세 구성품 목록을 가져오는 함수
        suspend fun selectProductInfoData(productIdx:Int/*, coordinatorIdx: Int*/): ArrayList<List<Map<String, String>>>{
            var tempList = ArrayList<List<Map<String,String>>>()
            val job1 = CoroutineScope(Dispatchers.IO).launch {
                // 컬렉션에 접근할 수 있는 객체를 가져온다.
                val collectionReference = Firebase.firestore.collection("ProductData")
                // 컬렉션이 가지고 있는 문서들 중에 contentIdx 필드가 지정된 글 번호값하고 같은 Document들을 가져온다.
                // .whereEqualTo("coordinatorIdx", coordinatorIdx)
                val querySnapshot = collectionReference.whereEqualTo("productIdx", productIdx).get().await()
                for (document in querySnapshot){
                    val coordiItemArray = document["coordiItem"] as List<Map<String, String>>
                    tempList.add(coordiItemArray)
                }
            }
            job1.join()
            return tempList
        }

        // collection 내에 존재하는 모든 상세 구성품 정보를 가져오는 함수
        suspend fun gettingIndividualProductList(coordinatorIdx: Int): ArrayList<List<Map<String, String>>>{
            var resultList: ArrayList<List<Map<String, String>>> = arrayListOf()
            var tempList: ArrayList<List<Map<String, String>>> = arrayListOf()
            val job1 = CoroutineScope(Dispatchers.IO).launch{
                // 컬랙션 접근 객체
                val collectionReference = Firebase.firestore.collection("ProductData")
                // coordinatorIdx가 같은 데이터만 가져옴
                val querySnapshot = collectionReference.whereEqualTo("coordinatorIdx", coordinatorIdx).get().await()
                // document 내에서 coordiItem의 정보만 가져옴
                for (document in querySnapshot){
                    val individualItemArray = document["coordiItem"] as List<Map<String, String>>
                    tempList.add(individualItemArray)
                }
                // 중복되는 데이터 제거
                for (i in 0 until tempList.size){
                    var tempFilterList = tempList[i].distinctBy {
                        "${it["0"]}-${it["1"]}-${it["2"]}-${it["3"]}-${it["4"]}-${it["5"]}"
                    }
                    resultList.add(tempFilterList)
                }
            }
            job1.join()
            return  resultList
        }
    }
}