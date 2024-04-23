package kr.co.lion.team4.mrco.dao

import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kr.co.lion.team4.mrco.model.FaqModel

class FaqDao {

    companion object{
        // 자주 묻는 질문 목록을 가져온다.
        suspend fun gettingFaqList() : MutableList<FaqModel>{
            // 자주 묻는 질문 목록을 담을 리스트
            val faqList = mutableListOf<FaqModel>()

            val job1 = CoroutineScope(Dispatchers.IO).launch {
                // 모든 질문 목록을 가져온다.
                val querySnapshot = Firebase.firestore.collection("FaqData").get().await()
                // 가져온 문서 수만큼 반복
                querySnapshot.forEach{
                    // 각 문서를 객체에 담는다.
                    val faqModel = it.toObject(FaqModel::class.java)
                    // 객체를 리스트에 넣는다.
                    faqList.add(faqModel)
                }
            }
            job1.join()

            return faqList
        }
    }

}