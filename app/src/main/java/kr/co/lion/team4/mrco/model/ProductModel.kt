package kr.co.lion.team4.mrco.model

import kr.co.lion.team4.mrco.CategoryId
import kr.co.lion.team4.mrco.CodiMbti

data class ProductModel(
    var productIdx : Int,
    var categoryId : CategoryId, // enum
    var coordinatorId: Int,
    var coordiName: String,
    var coordiImage: Int, // Map
    var codiMainImage: String,
    var coordiGender: Int,
    var coordiText: String,
    var price : Int,
    var coordiItem: Int, // List, Map
    var coordiMBTI: CodiMbti,
    var coordiTPO: Int, // Enum
    var coordiSeason: Int, // Enum
    var coordiMood: Int, // List<Enum>
    var coordiState: Int
) {
    constructor(): this(
        0,
        CategoryId.LOREM,
        0,
        "",
        0,
        "",
        0,
        "",
        0,
        0,
        CodiMbti.INFP,
        0,
        0,
        0,
        0)
}