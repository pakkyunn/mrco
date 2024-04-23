package kr.co.lion.team4.mrco.model

import kr.co.lion.team4.mrco.CategoryId
import kr.co.lion.team4.mrco.CategoryIdSubMOOD
import kr.co.lion.team4.mrco.CategoryIdSubSEASON
import kr.co.lion.team4.mrco.CategoryIdSubTPO
import kr.co.lion.team4.mrco.CodiMbti

data class ProductModel(
    var productIdx : Int,
    var categoryId : String, // enum
    var coordinatorIdx: Int,
    var coordiName: String,
    var coordiImage: Map<String, String>, // Map
    var codiMainImage: String,
    var coordiGender: Int,
    var coordiText: String,
    var price : Int,
    var coordiItem: ArrayList<MutableMap<String, String>>, // List, Map
    var coordiMBTI: String,
    var coordiTPO: CategoryIdSubTPO?, // Enum
    var coordiSeason: CategoryIdSubSEASON?, // Enum
    var coordiMood: CategoryIdSubMOOD?, // List<Enum>
    var coordiState: Int,
    var coordiWriteDate: String
) {
    constructor(): this(
        0,
        CategoryId.TPO.str,
        0,
        "",
        mapOf(),
        "",
        1,
        "",
        0,
        ArrayList(),
        CodiMbti.INFP.str,
        CategoryIdSubTPO.DEFAULT,
        CategoryIdSubSEASON.DEFAULT,
        CategoryIdSubMOOD.DEFAULT,
        0,
        "")
}