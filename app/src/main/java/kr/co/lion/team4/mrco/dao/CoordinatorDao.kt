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

        // 코디네이터의 정보를 저장한다. (코디네이터 가입 신청시)
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

        // 코디네이터 번호를 통해 코디네이터 정보를 가져와 반환한다
        suspend fun gettingCoordinatorInfoByCoordiIdx(coordi_idx:Int) : CoordinatorModel? {

            var coordinatorModel:CoordinatorModel? = null

            val job1 = CoroutineScope(Dispatchers.IO).launch {
                // CoordinatorData 컬렉션 접근 객체를 가져온다.
                val collectionReference = Firebase.firestore.collection("CoordinatorData")
                // coordi_idx 필드가 매개변수로 들어오는 coordi_idx와 같은 문서들을 가져온다.
                val querySnapshot = collectionReference.whereEqualTo("coordi_idx", coordi_idx).get().await()
                // 가져온 문서객체들이 들어 있는 리스트에서 첫 번째 객체를 추출한다.
                // 코디네이터 번호가 동일한 사용는 없기 때문에 무조건 하나만 나오기 때문이다
                coordinatorModel = querySnapshot.documents[0].toObject(CoordinatorModel::class.java)
            }
            job1.join()

            return coordinatorModel
        }

        // 모든 코디네이터의 정보를 가져온다.
        suspend fun getUserAll() : MutableList<CoordinatorModel>{
            // 코디네이터 정보를 담을 리스트
            val coordinatorList = mutableListOf<CoordinatorModel>()

            val job1 = CoroutineScope(Dispatchers.IO).launch {
                // 모든 코디네이터 정보를 가져온다
                val querySnapshot = Firebase.firestore.collection("CoordinatorData").get().await()
                // 가져온 문서의 수 만큼 반복한다.
                querySnapshot.forEach {
                    // CoordinatorModel 객체에 담는다.
                    val coordinatorModel = it.toObject(CoordinatorModel::class.java)
                    // 리스트에 담는다.
                    coordinatorList.add(coordinatorModel)
                }
            }
            job1.join()

            return coordinatorList
        }

        // 수정 절차 맨 마지막으로 여기서 coordinatorModel 대신 modifiedCoordinatorModel
        // 수정된 내용의 coordinatorModel 을 받아서 firebase에서
        // 수정전 사용자 정보를 가져와 여기에 넣어준다.
        suspend fun updateCoordinatorData(modifiedCoordinatorModel: CoordinatorModel){
            val job1 = CoroutineScope(Dispatchers.IO).launch {
                // 컬렉션에 접근할 수 있는 객체를 가져온다.
                val collectionReference = Firebase.firestore.collection("CoordinatorData")

                // 컬렉션이 가지고 있는 문서들 중에 수정할 사용자 정보를 가져온다.
                val query = collectionReference.whereEqualTo("coordi_idx", modifiedCoordinatorModel.coordi_idx).get().await()

                // 저장할 데이터를 담을 HashMap을 만들어준다.
                val map = mutableMapOf<String, Any?>()
                map["coordi_name"] = modifiedCoordinatorModel.coordi_name
                map["coordi_intro_text"] = modifiedCoordinatorModel.coordi_intro_text

                map["coordi_business_phone"] = modifiedCoordinatorModel.coordi_business_phone
                map["coordi_forwarding_loc"] = modifiedCoordinatorModel.coordi_forwarding_loc

                map["coordi_return_loc"] = modifiedCoordinatorModel.coordi_return_loc
                map["coordi_bank"] = modifiedCoordinatorModel.coordi_bank

                map["coordi_account_holder"] = modifiedCoordinatorModel.coordi_account_holder
                map["coordi_account"] = modifiedCoordinatorModel.coordi_account

                // 저장한다. 가져온 문서 중 첫 번째 문서에 접근하여 데이터를 수정한다.
                query.documents[0].reference.update(map)
            }

            job1.join()
        }

    }

}