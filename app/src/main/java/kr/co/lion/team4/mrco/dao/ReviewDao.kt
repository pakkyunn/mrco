package kr.co.lion.team4.mrco.dao

import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kr.co.lion.team4.mrco.model.ReviewModel

class ReviewDao {
    companion object{
        // 작성된 리뷰를 가져온다.
        suspend fun gettingReviewAllList() : MutableList<ReviewModel>{
            // 리뷰 목록을 담을 리스트
            val reviewList = mutableListOf<ReviewModel>()

            val job1 = CoroutineScope(Dispatchers.IO).launch {

                // 모든 리뷰 목록을 가져온다.
                val querySnapshot = Firebase.firestore.collection("ReviewData").get().await()
                // 가져온 문서 수만큼 반복
                querySnapshot.forEach{
                    // 각 문서를 객체에 담는다.
                    val reviewModel = it.toObject(ReviewModel::class.java)
                    // 객체를 리스트에 넣는다.
                    reviewList.add(reviewModel)
                }
            }
            job1.join()

            return reviewList
        }
    }
}