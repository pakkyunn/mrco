package kr.co.lion.team4.mrco

class Tools {

    companion object {

    }

}

// MainActivity에서 보여줄 프레그먼트들의 이름
enum class MainFragmentName(var str: String) {
    COORDINATOR_INFO("Coordinator_Info_Fragment"),
    COORDINATOR_RANK("Coordinator_Rank_Fragment"),
    LIKE_COORDINATOR("Like_Coordinator_Fragment"),
}