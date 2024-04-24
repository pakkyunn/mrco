package kr.co.lion.team4.mrco.dao

import android.util.Log
import com.google.firebase.Firebase
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.firestore
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kr.co.lion.team4.mrco.ProductState
import kr.co.lion.team4.mrco.model.CoordinatorModel
import kr.co.lion.team4.mrco.model.FaqModel
import kr.co.lion.team4.mrco.model.LikeModel
import kr.co.lion.team4.mrco.model.ProductModel
import kr.co.lion.team4.mrco.model.UserModel

class LikeDao {

    companion object{

        // 좋아요 데이터 번호 시퀀스 값을 가져온다.
        suspend fun getLikeSequence():Int{
            var likeSequence = -1

            val job1 = CoroutineScope(Dispatchers.IO).launch {
                // 컬렉션에 접근할 수 있는 객체를 가져온다.
                val collectionReference = Firebase.firestore.collection("Sequence")
                // 상품 번호 시퀀스 값을 가지고 있는 문서에 접근할 수 있는 객체를 가져온다.
                val documentReference = collectionReference.document("LikeSequence")
                // 문서내에 있는 데이터를 가져올 수 있는 객체를 가져온다.
                val documentSnapShot = documentReference.get().await()
                likeSequence = documentSnapShot.getLong("value")?.toInt()!!
            }
            job1.join()

            return likeSequence
        }

        // 좋아요 데이터 번호 시퀀스 값을 업데이트 한다.
        suspend fun updateLikeSequence(likeSequence:Int){
            val job1 = CoroutineScope(Dispatchers.IO).launch {
                // 컬렉션에 접근할 수 있는 객체를 가져온다.
                val collectionReference = Firebase.firestore.collection("Sequence")
                // 상품 번호 시퀀스 값을 가지고 있는 문서에 접근할 수 있는 객체를 가져온다.
                val documentReference = collectionReference.document("LikeSequence")
                // 저장할 데이터를 담을 HashMap을 만들어준다.
                val map = mutableMapOf<String, Long>()
                map["value"] = likeSequence.toLong()
                // 저장한다.
                documentReference.set(map)
            }
            job1.join()
        }

        // 회원 별 좋아요 데이터를 생성한다.
        suspend fun insertLikeData(likeModel: LikeModel){
            val job1 = CoroutineScope(Dispatchers.IO).launch {
                // 컬렉션에 접근할 수 있는 객체를 가져온다.
                val collectionReference = Firebase.firestore.collection("LikeData")
                // 컬럭션에 문서를 추가한다.
                // 문서를 추가할 때 객체나 맵을 지정한다.
                // 추가된 문서 내부의 필드는 객체가 가진 프로퍼티의 이름이나 맵에 있는 데이터의 이름과 동일하게 결정된다.
                collectionReference.add(likeModel)
            }
            job1.join()
        }

        // 팔로우 추가 (코디네이터)
        suspend fun insertLikeCoordinatorData(like_user_idx: Int, like_coordinator_idx: Int){
            val job1 = CoroutineScope(Dispatchers.IO).launch {
                // 컬렉션에 접근할 수 있는 객체를 가져온다.
                val collectionReference = Firebase.firestore.collection("LikeData")

                // like_user_idx에 해당하는 문서를 가져옵니다.
                val querySnapshot = collectionReference
                    .whereEqualTo("like_user_idx", like_user_idx)
                    .get().await()

                // 가져온 문서가 있다면 해당 문서의 like_product_idx 필드를 업데이트합니다.
                querySnapshot.forEach { documentSnapshot ->
                    val documentReference = documentSnapshot.reference
                    val likeModel = documentSnapshot.toObject(LikeModel::class.java)

                    // like_coordinator_idx 필드에 새로운 값을 추가합니다.
                    likeModel.like_coordinator_idx.add(like_coordinator_idx)

                    // like_coordinator_idx 필드를 업데이트합니다.
                    documentReference.update("like_coordinator_idx", likeModel.like_coordinator_idx)
                        .addOnSuccessListener {
                            // 업데이트 성공 시 작업 수행
                            Log.d("test1234", "팔로우 추가")
                        }
                        .addOnFailureListener { exception ->
                            // 업데이트 실패 시 예외 처리
                            Log.e("test1234", "Error: 팔로우 추가")
                        }
                }

            }
            job1.join()
        }

        // 팔로우 취소 (코디네이터)
        suspend fun deleteLikeCoordinatorData(like_user_idx: Int, like_coordinator_idx: Int) {
            val collectionReference = Firebase.firestore.collection("LikeData")

            // like_user_idx에 해당하는 문서를 가져옵니다.
            val querySnapshot = collectionReference
                .whereEqualTo("like_user_idx", like_user_idx)
                .get().await()

            // 가져온 문서가 있다면 해당 문서의 like_product_idx 필드를 업데이트합니다.
            querySnapshot.forEach { documentSnapshot ->
                val documentReference = documentSnapshot.reference
                val likeModel = documentSnapshot.toObject(LikeModel::class.java)

                // like_coordinator_idx 필드에서 product_idx를 제거합니다.
                likeModel.like_coordinator_idx.remove(like_coordinator_idx)

                // like_coordinator_idx 필드를 업데이트합니다.
                documentReference.update("like_coordinator_idx", likeModel.like_coordinator_idx)
                    .addOnSuccessListener {
                        // 업데이트 성공 시 작업 수행
                        Log.d("test1234", "팔로우 취소")
                    }
                    .addOnFailureListener { exception ->
                        // 업데이트 실패 시 예외 처리
                        Log.e("test1234", "Error: 팔로우 취소")
                    }
            }
        }

        // 해당 유저의 팔로우 목록 가져오기
        suspend fun getfollowCoordinators(like_user_idx: Int): MutableList<LikeModel> {
            val likeList = mutableListOf<LikeModel>()

            val job1 = CoroutineScope(Dispatchers.IO).launch {
                // 모든 상품 정보를 가져온다
                val collectionReference = Firebase.firestore.collection("LikeData")

                val query = collectionReference.whereEqualTo("like_user_idx", like_user_idx)

                // 모든 쿼리를 하나의 쿼리로 병합합니다.
                val querySnapshot = query.get().await()

                // 가져온 문서의 수 만큼 반복한다.
                querySnapshot.forEach {
                    // ProductModel 객체에 담고 객체를 리스트에 담는다.
                    val likeModel = it.toObject(LikeModel::class.java)
                    likeList.add(likeModel)
                }
            }
            job1.join()

            return likeList
        }

        // 코디네이터 정보 페이지에서 사용! / 해당 코디네이터에 맞는 정보를 가져온다
        suspend fun getCoordinatorInfo(coordinatorIdxList: MutableList<Int>): MutableList<CoordinatorModel>{
            // 코디네이터 정보를 담을 리스트
            val coordiList = mutableListOf<CoordinatorModel>()

            val job1 = CoroutineScope(Dispatchers.IO).launch {
                val collectionReference = Firebase.firestore.collection("CoordinatorData")
                // coordinatorIdxList에 포함된 모든 코디네이터 인덱스에 대한 쿼리를 생성합니다.
                coordinatorIdxList.forEach { coordinatorIdx ->
                    val query = collectionReference.whereEqualTo("coordi_idx", coordinatorIdx)
                    val querySnapshot = query.get().await()
                    // 가져온 문서의 수 만큼 반복하여 리스트에 추가합니다.
                    querySnapshot.forEach { documentSnapshot ->
                        val coordinatorModel = documentSnapshot.toObject(CoordinatorModel::class.java)
                        coordiList.add(coordinatorModel)
                    }
                }
            }
            job1.join()

            return coordiList
        }

        // 좋아요를 눌러져있는 상품을 가져온다
        suspend fun gettingLikeList(userIdx:Int): MutableList<LikeModel>{
            // 게시글 정보를 담을 리스트
            val likeList = mutableListOf<LikeModel>()

            val job1 = CoroutineScope(Dispatchers.IO).launch {
                // 모든 상품 정보를 가져온다
                val collectionReference = Firebase.firestore.collection("LikeData")
                // 상품의 상태가 정상 상태이고 상품 인덱스를 기준으로 내림차순 정렬되게 데이터를 가져올 수 있는 Query
                var query = collectionReference.whereEqualTo("coordiState", ProductState.PRODUCT_STATE_NORMAL.num)
                // 내림 차순 정렬
                query = query.orderBy("like_idx", Query.Direction.DESCENDING)
                    // 해당 유저의 좋아요 목록을 가져온다
                    .whereEqualTo("like_user_idx", userIdx)

                val queryShapshot = query.get().await()
                // 가져온 문서의 수 만큼 반복한다.
                queryShapshot.forEach {
                    // ProductModel 객체에 담고 객체를 리스트에 담는다.
                    val likeModel = it.toObject(LikeModel::class.java)
                    likeList.add(likeModel)
                }
            }
            job1.join()

            return likeList
        }
    }

}