package kr.co.lion.team4.mrco.dao


import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kr.co.lion.team4.mrco.model.CoordinatorModel

class CoordinatorDao {

    companion object {

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