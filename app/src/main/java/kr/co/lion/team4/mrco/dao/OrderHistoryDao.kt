package kr.co.lion.team4.mrco.dao

import com.google.firebase.Firebase
import com.google.firebase.Timestamp
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.firestore
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kr.co.lion.team4.mrco.model.OrderModel
import kr.co.lion.team4.mrco.model.OrderedProductInfoModel
import kr.co.lion.team4.mrco.model.ProductModel

class OrderHistoryDao {
    companion object{
        // 주문 내역을 저장한다.
        suspend fun insertOrderData(orderModel : OrderModel){
            val job1 = CoroutineScope(Dispatchers.IO).launch {
                // 컬렉션에 접근할 수 있는 객체를 가져온다.
                val collectionReference = Firebase.firestore.collection("OrderData")
                // 컬럭션에 문서를 추가한다.
                // 문서를 추가할 때 객체나 맵을 지정한다.
                // 추가된 문서 내부의 필드는 객체가 가진 프로퍼티의 이름이나 맵에 있는 데이터의 이름과 동일하게 결정된다.
                collectionReference.add(orderModel)
            }
            job1.join()
        }

        // 로그인한 사용자가 주문한 모든 주문 내역을 가져온다.
        suspend fun gettingOrderList(userIdx : Int, startDate: Timestamp, endDate: Timestamp) : MutableList<OrderModel>{
            // 주문 내역 목록을 담을 리스트
            val orderList = mutableListOf<OrderModel>()

            val job1 = CoroutineScope(Dispatchers.IO).launch {
                // 모든 주문 내역을 가져온다.
                val collectionReference = Firebase.firestore.collection("OrderData")
                // 주문한 유저의 인덱스번호가 현재 로그인한 사용자의 인덱스번호와 같은 주문내역만 불러온다.

                val query = collectionReference.whereEqualTo("order_user_idx", userIdx)
                    .whereGreaterThanOrEqualTo("order_date", startDate) // 시작일
                    .whereLessThanOrEqualTo("order_date", endDate) // 종료일
                    .orderBy("order_date", Query.Direction.DESCENDING) // 최신순 정렬
                val querySnapshot = query.get().await()
                // 가져온 문서 수만큼 반복
                querySnapshot.forEach{
                    // 각 문서를 객체에 담는다.
                    val orderModel = it.toObject(OrderModel::class.java)
                    // 객체를 리스트에 넣는다.
                    orderList.add(orderModel)
                }
            }
            job1.join()

            return orderList
        }

        // 주문 내역에서 주문한 상품들의 정보를 가져온다.
        suspend fun gettingOrderedItemInfo(orderList : MutableList<OrderModel> ) : MutableList<ArrayList<OrderedProductInfoModel>>{

            // 주문내역에서 주문한 상품의 정보 목록을 담을 리스트
            // 0월 0일 주문한 상품 A, B가 있다면, A와 B의 상품명, 상품이미지, 상품 가격만을 담아준다.
            val productInfoList = mutableListOf<ArrayList<OrderedProductInfoModel>>()

            val jobMain = CoroutineScope(Dispatchers.IO).launch {
                orderList.forEach {

                    // 고객이 주문한 상품의 상품 정보 목록 (상품의 상품명, 대표이미지 파일명, 가격만 담긴다)
                    val productsInfo = ArrayList<OrderedProductInfoModel>()
                    it.order_product.forEach {

                        val jobItem = CoroutineScope(Dispatchers.IO).launch {
                            // 모든 주문 내역을 가져온다.
                            val collectionReference = Firebase.firestore.collection("ProductData")
                            // 주문한 상품의 인덱스번호와 일치하는 상품 정보를 불러온다.
                            val query = collectionReference.whereEqualTo("productIdx", it.order_product_idx)
                            val querySnapshot = query.get().await()
                            // 가져온 문서 수만큼 반복
                            querySnapshot.forEach{
                                // 각 문서를 객체에 담는다.
                                val productModel = it.toObject(ProductModel::class.java)
                                // 상품 정보에서 원하는 정보만 분리하여 객체에 담아준다.
                                val productInfo = OrderedProductInfoModel(
                                    productModel.productIdx, productModel.coordiName, productModel.price, productModel.codiMainImage)
                                // 상품 정보 객체를 리스트에 넣는다.
                                productsInfo.add(productInfo)
                            }
                        }
                        jobItem.join()

                    }
                    // 주문별 상품 정보를 리스트에 추가한다
                    productInfoList.add(productsInfo)
                }
            }
            jobMain.join()

            return productInfoList
        }

        // 주문 idx 를 이용해 주문 데이터를 가져와 반환한다.
        suspend fun selectOrderData(orderIdx:Int) : OrderModel? {
            var orderModel: OrderModel? = null

            val job1 = CoroutineScope(Dispatchers.IO).launch {
                // 컬렉션에 접근할 수 있는 객체를 가져온다.
                val collectionReference = Firebase.firestore.collection("OrderData")
                // 컬렉션이 가지고 있는 문서들 중에 order_idx 필드가 지정된 orderIdx값하고 같은 Document를 가져온다.
                val querySnapshot = collectionReference.whereEqualTo("order_idx", orderIdx).get().await()
                // orderIdx가 같은 글은 존재할 수가 없기 떄문에, 첫 번째 객체를 바로 추출해서 사용한다.
                orderModel = querySnapshot.documents[0].toObject(OrderModel::class.java)
            }
            job1.join()

            return orderModel
        }


        // 주문 상세정보 화면 - 주문한 상품들의 정보를 가져온다.
        suspend fun selectOrderProductInfo(order: OrderModel): MutableList<OrderedProductInfoModel> {
            // 고객이 주문한 상품의 상품 정보 목록 (상품의 상품명, 대표이미지 파일명, 가격만 담긴다)
            val productsInfo = mutableListOf<OrderedProductInfoModel>()

            order.order_product.forEach {
                val jobItem = CoroutineScope(Dispatchers.IO).launch {
                    // 모든 주문 내역을 가져온다.
                    val collectionReference = Firebase.firestore.collection("ProductData")
                    // 주문한 상품의 인덱스번호와 일치하는 상품 정보를 불러온다.
                    val query =
                        collectionReference.whereEqualTo("productIdx", it.order_product_idx)
                    val querySnapshot = query.get().await()
                    // 가져온 문서 수만큼 반복
                    querySnapshot.forEach {
                        // 각 문서를 객체에 담는다.
                        val productModel = it.toObject(ProductModel::class.java)
                        // 상품 정보에서 원하는 정보만 분리하여 객체에 담아준다.
                        val productInfo = OrderedProductInfoModel(
                            productModel.productIdx,
                            productModel.coordiName,
                            productModel.price,
                            productModel.codiMainImage
                        )
                        // 상품 정보 객체를 리스트에 넣는다.
                        productsInfo.add(productInfo)
                    }
                }
                jobItem.join()
            }
            return productsInfo
        }
    }
}