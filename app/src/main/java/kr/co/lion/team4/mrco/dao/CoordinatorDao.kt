package kr.co.lion.team4.mrco.dao


import android.content.Context
import android.net.Uri
import com.google.firebase.Firebase
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.firestore
import com.google.firebase.storage.storage
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kr.co.lion.team4.mrco.ProductState
import kr.co.lion.team4.mrco.model.CoordinatorModel
import kr.co.lion.team4.mrco.model.ProductModel
import java.io.File

class CoordinatorDao {

    companion object {

        // 이미지 데이터를 firebase storage에 업로드하는 메서드
        suspend fun uploadCoordinatorJoinImage(context: Context, fileName:String, uploadFileName:String) {
            // 외부저장소 까지의 경로를 가져온다.
            val filePath = context.getExternalFilesDir(null).toString()
            // 서버로 업로드할 파일의 경로
            val file = File("${filePath}/${fileName}")
            val uri = Uri.fromFile(file)

            val job1 = CoroutineScope(Dispatchers.IO).launch {
                // Storage에 접근할 수 있는 객체를 가져온다.(폴더의 이름과 파일이름을 저장해준다.
                val storageRef = Firebase.storage.reference.child("coordinator/images/$uploadFileName")
                // 업로드한다.
                storageRef.putFile(uri)
            }

            job1.join()
        }


        // 모든 코디네이터의 정보를 가져온다.
        suspend fun getCoordinatorAll() : MutableList<CoordinatorModel>{
            // 코디네이터 정보를 담을 리스트
            val coordiList = mutableListOf<CoordinatorModel>()

            val job1 = CoroutineScope(Dispatchers.IO).launch {
                val collectionReference = Firebase.firestore.collection("CoordinatorData")

                // 코디네이터 등록상태가 참인 경우에만..
                // var query = collectionReference.whereEqualTo("userCoordinatorSignStatus", true)
                // query = query.whereEqualTo("")

                // 모든 사용자 정보를 가져온다
                val querySnapshot = Firebase.firestore.collection("CoordinatorData").get().await()
                // 가져온 문서의 수 만큼 반복한다.
                querySnapshot.forEach {
                    // CoordinatorModel 객체에 담는다.
                    val coordinatorModel = it.toObject(CoordinatorModel::class.java)
                    // 리스트에 담는다.
                    coordiList.add(coordinatorModel)
                }
            }
            job1.join()

            return coordiList
        }

        // 해당 코디네이터에 맞는 정보를 가져온다
        suspend fun getCoordinatorInfo(cordinatorIdx: Int): MutableList<CoordinatorModel>{
            // 코디네이터 정보를 담을 리스트
            val coordiList = mutableListOf<CoordinatorModel>()

            val job1 = CoroutineScope(Dispatchers.IO).launch {
                val collectionReference = Firebase.firestore.collection("CoordinatorData")
                // 인덱스 값과 일치하는 코디네이터 정보만 가져온다
                var query = collectionReference.whereEqualTo("coordi_idx", cordinatorIdx)
                val queryShapshot = query.get().await()
                // 가져온 문서의 수 만큼 반복한다.
                queryShapshot.forEach {
                    // CoordinatorModel 객체에 담는다.
                    val coordinatorModel = it.toObject(CoordinatorModel::class.java)
                    // 리스트에 담는다.
                    coordiList.add(coordinatorModel)
                }
            }
            job1.join()

            return coordiList
        }

        // 코디네이터 정보를 저장한다. (코디네이터 가입 신청시)
        suspend fun insertCoordinatorData(coordinatorModel: CoordinatorModel){
            val job1 = CoroutineScope(Dispatchers.IO).launch {

                // 컬렉션에 접근할 수 있는 객체를 가져온다.
                val collectionReference = Firebase.firestore.collection("CoordinatorData")

                // 컬렉션에 문서를 추가한다.
                // 문서를 추가할 때 객체나 맵을 지정한다.
                // 추가된 문서 내부의 필드는 객체가 가진 프로퍼티의 이름이나 맵에 있는 데이터의 이름과 동일하게 결정된다.
                collectionReference.add(coordinatorModel)
            }
            job1.join()

        }

        suspend fun checkCoordiName(coodiName:String) : Boolean{
            var chk = false

            val job1 = CoroutineScope(Dispatchers.IO).launch {
                // 컬렉션에 접근할 수 있는 객체를 가져온다.
                val collectionReference = Firebase.firestore.collection("CoordiData")
                // UserId 필드가 사용자가 입력한 아이디와 같은 문서들을 가져온다.
                // whereEqualTo : 같은것
                // whereGreaterThan : 큰것
                // whereGreaterThanOrEqualTo : 크거나 같은 것
                // whereLessThan : 작은 것
                // whereLessThanOrEqualTo : 작거나 같은 것
                // whereNotEqualTo : 다른 것
                // 필드의 이름, 값 형태로 넣어준다
                val queryShapshot = collectionReference.whereEqualTo("coordiName", coodiName).get().await()
                // 반환되는 리스트에 담긴 문서 객체가 없다면 존재하는 아이디로 취급한다.
                chk = queryShapshot.isEmpty
            }
            job1.join()

            return chk
        }


        // 코디네이터 시퀀스 값을 가져온다.
        suspend fun getCoordinatorSequence(): Int{

            var coordinatorSequence = -1

            val job1 = CoroutineScope(Dispatchers.IO).launch {
                // 컬렉션에 접근할 수 있는 객체를 가져온다.
                val collectionReference = Firebase.firestore.collection("Sequence")
                // 사용자 번호 시퀀스 값을 가지고 있는 문서에 접근할 수 있는 객체를 가져온다.
                val documentReference = collectionReference.document("CoordinatorSequence")
                // 문서내에 있는 데이터를 가져올 수 있는 객체를 가져온다.
                val documentSnapShot = documentReference.get().await()
                coordinatorSequence = documentSnapShot.getLong("value")?.toInt()!!
            }
            job1.join()

            return coordinatorSequence
        }

        // 코디네이터 시퀀스 값을 업데이트 한다.
        suspend fun updateCoordinatorSequence(coordinatorSequence:Int){
            val job1 = CoroutineScope(Dispatchers.IO).launch {
                // 컬렉션에 접근할 수 있는 객체를 가져온다.
                val collectionReference = Firebase.firestore.collection("Sequence")
                // 사용자 번호 시퀀스 값을 가지고 있는 문서에 접근할 수 있는 객체를 가져온다.
                val documentReference = collectionReference.document("CoordinatorSequence")
                // 저장할 데이터를 담을 HashMap을 만들어준다.
                val map = mutableMapOf<String, Long>()
                map["value"] = coordinatorSequence.toLong()
                // 저장한다.
                documentReference.set(map)
            }
            job1.join()
        }
    }

}