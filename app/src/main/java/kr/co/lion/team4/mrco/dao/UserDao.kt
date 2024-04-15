package kr.co.lion.team4.mrco.dao

import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kr.co.lion.team4.mrco.UserState
import kr.co.lion.team4.mrco.model.UserModel

class UserDao {

    companion object{
        // 사용자 번호 시퀀스값을 가져온다.
        suspend fun getUserSequence():Int{

            var userSequence = -1

            val job1 = CoroutineScope(Dispatchers.IO).launch {
                // 컬렉션에 접근할 수 있는 객체를 가져온다.
                val collectionReference = Firebase.firestore.collection("Sequence")
                // 사용자 번호 시퀀스 값을 가지고 있는 문서에 접근할 수 있는 객체를 가져온다.
                val documentReference = collectionReference.document("UserSequence")
                // 문서내에 있는 데이터를 가져올 수 있는 객체를 가져온다.
                val documentSnapShot = documentReference.get().await()
                userSequence = documentSnapShot.getLong("value")?.toInt()!!
            }
            job1.join()

            return userSequence
        }

        // 사용자 시퀀스 값을 업데이트 한다.
        suspend fun updateUserSequence(userSequence:Int){
            val job1 = CoroutineScope(Dispatchers.IO).launch {
                // 컬렉션에 접근할 수 있는 객체를 가져온다.
                val collectionReference = Firebase.firestore.collection("Sequence")
                // 사용자 번호 시퀀스 값을 가지고 있는 문서에 접근할 수 있는 객체를 가져온다.
                val documentReference = collectionReference.document("UserSequence")
                // 저장할 데이터를 담을 HashMap을 만들어준다.
                val map = mutableMapOf<String, Long>()
                map["value"] = userSequence.toLong()
                // 저장한다.
                documentReference.set(map)
            }
            job1.join()
        }

        // 사용자 정보를 저장한다.
        suspend fun insertUserData(userModel: UserModel){
            val job1 = CoroutineScope(Dispatchers.IO).launch {
                // 컬렉션에 접근할 수 있는 객체를 가져온다.
                val collectionReference = Firebase.firestore.collection("UserData")
                // 컬럭션에 문서를 추가한다.
                // 문서를 추가할 때 객체나 맵을 지정한다.
                // 추가된 문서 내부의 필드는 객체가 가진 프로퍼티의 이름이나 맵에 있는 데이터의 이름과 동일하게 결정된다.
                collectionReference.add(userModel)
            }
            job1.join()
        }

        // 입력한 아이디가 저장되어 있는 문서가 있는지 확인한다(중복처리)
        // 사용할 수 있는 아이디라면 true, 존재하는 아이디라면 false를 반환한다.
        suspend fun checkUserIdExist(joinUserId:String) : Boolean{

            var chk = false

            val job1 = CoroutineScope(Dispatchers.IO).launch {
                // 컬렉션에 접근할 수 있는 객체를 가져온다.
                val collectionReference = Firebase.firestore.collection("UserData")
                // UserId 필드가 사용자가 입력한 아이디와 같은 문서들을 가져온다.
                // whereEqualTo : 같은것
                // whereGreaterThan : 큰것
                // whereGreaterThanOrEqualTo : 크거나 같은 것
                // whereLessThan : 작은 것
                // whereLessThanOrEqualTo : 작거나 같은 것
                // whereNotEqualTo : 다른 것
                // 필드의 이름, 값 형태로 넣어준다
                val querySnapshot = collectionReference.whereEqualTo("userId", joinUserId).get().await()
                // 반환되는 리스트에 담긴 문서 객체가 없다면 존재하는 아이디로 취급한다.
                chk = querySnapshot.isEmpty
            }

            job1.join()

            return chk
        }

        // 아이디를 통해 사용자 정보를 가져오는 메서드
        suspend fun getUserDataById(userId:String) : UserModel?{
            // 사용자 정보 객체를 담을 변수
            var userModel:UserModel? = null

            val job1 = CoroutineScope(Dispatchers.IO).launch {
                // UserData 컬렉션 접근 객체를 가져온다.
                val collectionReference = Firebase.firestore.collection("UserData")
                // userId 필드가 매개변수로 들어오는 userId와 같은 문서들을 가져온다.
                val querySnapshot = collectionReference.whereEqualTo("userId", userId).get().await()
                // 만약 가져온 것이 있다면
                if(querySnapshot.isEmpty == false){
                    // 가져온 문서객체들이 들어 있는 리스트에서 첫 번째 객체를 추출한다.
                    // 아이디가 동일한 사용는 없기 때문에 무조건 하나만 나오기 때문이다
                    userModel = querySnapshot.documents[0].toObject(UserModel::class.java)
                    //Log.d("test1234", "userModel")
                }
            }
            job1.join()

            return userModel
        }

        // 사용자 번호를 통해 사용자 정보를 가져와 반환한다
        suspend fun gettingUserInfoByUserIdx(userIdx:Int) : UserModel? {

            var userModel:UserModel? = null

            val job1 = CoroutineScope(Dispatchers.IO).launch {
                // UserData 컬렉션 접근 객체를 가져온다.
                val collectionReference = Firebase.firestore.collection("UserData")
                // userIdx 필드가 매개변수로 들어오는 userIdx와 같은 문서들을 가져온다.
                val querySnapshot = collectionReference.whereEqualTo("userIdx", userIdx).get().await()
                // 가져온 문서객체들이 들어 있는 리스트에서 첫 번째 객체를 추출한다.
                // 회원 번호가 동일한 사용는 없기 때문에 무조건 하나만 나오기 때문이다
                userModel = querySnapshot.documents[0].toObject(UserModel::class.java)
            }
            job1.join()

            return userModel
        }

        // 모든 사용자의 정보를 가져온다.
        suspend fun getUserAll() : MutableList<UserModel>{
            // 사용자 정보를 담을 리스트
            val userList = mutableListOf<UserModel>()

            val job1 = CoroutineScope(Dispatchers.IO).launch {
                // 모든 사용자 정보를 가져온다
                val querySnapshot = Firebase.firestore.collection("UserData").get().await()
                // 가져온 문서의 수 만큼 반복한다.
                querySnapshot.forEach {
                    // UserModel 객체에 담는다.
                    val userModel = it.toObject(UserModel::class.java)
                    // 리스트에 담는다.
                    userList.add(userModel)
                }
            }
            job1.join()

            return userList
        }

//        // 사용자 정보를 수정하는 메서드
//        suspend fun updateUserData(userModel: UserModel, isPasswordChanged:Boolean){
//            val job1 = CoroutineScope(Dispatchers.IO).launch {
//                // 컬렉션에 접근할 수 있는 객체를 가져온다.
//                val collectionReference = Firebase.firestore.collection("UserData")
//
//                // 컬렉션이 가지고 있는 문서들 중에 수정할 사용자 정보를 가져온다.
//                val query = collectionReference.whereEqualTo("userIdx", userModel.userIdx).get().await()
//
//                // 저장할 데이터를 담을 HashMap을 만들어준다.
//                val map = mutableMapOf<String, Any?>()
//                map["userNickName"] = userModel.userNickName
//                map["userAge"] = userModel.userAge
//                map["userGender"] = userModel.userGender
//                map["userHobby1"] = userModel.userHobby1
//                map["userHobby2"] = userModel.userHobby2
//                map["userHobby3"] = userModel.userHobby3
//                map["userHobby4"] = userModel.userHobby4
//                map["userHobby5"] = userModel.userHobby5
//                map["userHobby6"] = userModel.userHobby6
//
//                // 비밀번호를 변경한 적이 있다면..
//                if(isPasswordChanged){
//                    map["userPw"] = userModel.userPw
//                }
//                // 저장한다.
//                // 가져온 문서 중 첫 번째 문서에 접근하여 데이터를 수정한다.
//                query.documents[0].reference.update(map)
//            }
//
//            job1.join()
//        }

        // 사용자의 상태를 변경하는 (회원탈퇴) 메서드
        suspend fun updateUserState(userIdx:Int, newState: UserState){
            val job1 = CoroutineScope(Dispatchers.IO).launch {
                // 컬렉션에 접근할 수 있는 객체를 가져온다.
                val collectionReference = Firebase.firestore.collection("UserData")
                // 컬렉션이 가지고 있는 문서들 중에 userIdx 필드가 지정된 사용자 번호값하고 같은 Document들을 가져온다.
                val query = collectionReference.whereEqualTo("userIdx", userIdx).get().await()

                // 저장할 데이터를 담을 HashMap을 만들어준다.
                val map = mutableMapOf<String, Any>()
                map["userState"] = newState.num.toLong()
                // 저장한다.
                // 가져온 문서 중 첫 번째 문서에 접근하여 데이터를 수정한다.
                query.documents[0].reference.update(map)
            }
            job1.join()
        }
    }
}