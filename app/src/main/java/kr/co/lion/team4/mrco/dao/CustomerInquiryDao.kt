package kr.co.lion.team4.mrco.dao

import android.content.Context
import android.net.Uri
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore
import com.google.firebase.storage.storage
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kr.co.lion.team4.mrco.model.CustomerInquiryModel
import java.io.File

/* 고객센터 1:1 문의 작성화면 DAO */
class CustomerInquiryDao {
    companion object{

        // 문의 남기면서 첨부한 사진을 서버에 업로드한다
        suspend fun uploadAttachedImage(context: Context, fileName:String, uploadFileName:String) {
            // 외부저장소 까지의 경로를 가져온다.
            val filePath = context.getExternalFilesDir(null).toString()
            // 서버로 업로드할 파일의 경로
            val file = File("${filePath}/${fileName}")
            val uri = Uri.fromFile(file)

            val job1 = CoroutineScope(Dispatchers.IO).launch {
                // Storage에 접근할 수 있는 객체를 가져온다.(폴더의 이름과 파일이름을 저장해준다.
                val storageRef = Firebase.storage.reference.child("customer_inquiry_image/items/$uploadFileName")
                // 업로드한다.
                storageRef.putFile(uri)
            }

            job1.join()
        }

        // 문의 번호 시퀀스값을 가져온다.
        suspend fun getCustomerInquirySequence():Int{
            var customerInquirySequence = -1
            val job1 = CoroutineScope(Dispatchers.IO).launch {
                // 컬렉션에 접근할 수 있는 객체를 가져온다.
                val collectionReference = Firebase.firestore.collection("Sequence")
                // 게시글 번호 시퀀스 값을 가지고있는 문서에 접근할 수 있는 객체를 가져온다.
                val documentReference = collectionReference.document("CustomerInquirySequence")
                // 문서 내에 있는 데이터를 가져올 수 있는 객체를 가져온다.
                val documentSnapshot = documentReference.get().await()
                customerInquirySequence = documentSnapshot.getLong("value")?.toInt()!!
            }

            job1.join()
            return customerInquirySequence
        }

        // 게시글 시퀀스 값을 업데이트 한다.
        suspend fun updateCustomerInquirySequence(customerInquirySequence: Int){
            val job1 = CoroutineScope(Dispatchers.IO).launch {
                // 컬렉션에 접근할 수 있는 객체를 가져온다.
                val collectionReference = Firebase.firestore.collection("Sequence")
                // 게시글 번호 시퀀스 값을 가지고있는 문서에 접근할 수 있는 객체를 가져온다.
                val documentReference = collectionReference.document("CustomerInquirySequence")
                // 저장할 데이터를 담을 HashMap을 만들어준다.
                val map = mutableMapOf<String, Long>()
                map["value"] = customerInquirySequence.toLong()
                // 저장한다.
                documentReference.set(map)
            }
            job1.join()
        }

        // 고객센터 문의 내역을 저장한다.
        suspend fun insertCustomerInquiryData(inquiryModel : CustomerInquiryModel){
            val job1 = CoroutineScope(Dispatchers.IO).launch {
                // 컬렉션에 접근할 수 있는 객체를 가져온다.
                val collectionReference = Firebase.firestore.collection("CustomerInquiryData")
                // 컬럭션에 문서를 추가한다.
                // 문서를 추가할 때 객체나 맵을 지정한다.
                // 추가된 문서 내부의 필드는 객체가 가진 프로퍼티의 이름이나 맵에 있는 데이터의 이름과 동일하게 결정된다.
                collectionReference.add(inquiryModel)
            }
            job1.join()
        }
    }
}