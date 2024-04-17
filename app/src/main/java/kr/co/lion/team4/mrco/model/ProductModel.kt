package kr.co.lion.team4.mrco.model

import kr.co.lion.team4.mrco.CategoryId
import kr.co.lion.team4.mrco.CodiMbti

data class ProductModel(
    var productIdx : Int,
    var categoryId : String, // enum
    var coordinatorIdx: Int,
    var coordiName: String,
    var coordiImage: String, // Map
    var codiMainImage: String,
    var coordiGender: Int,
    var coordiText: String,
    var price : Int,
    var coordiItem: ArrayList<Map<String, String>>, // List, Map
    var coordiMBTI: String,
    var coordiTPO: Int, // Enum
    var coordiSeason: Int, // Enum
    var coordiMood: Int, // List<Enum>
    var coordiState: Int,
    var coordiWriteDate: String
) {
    constructor(): this(
        0,
        CategoryId.TPO.str,
        0,
        "",
        "",
        "",
        0,
        "",
        0,
        ArrayList(),
        CodiMbti.INFP.str,
        0,
        0,
        0,
        0,
        "")
}