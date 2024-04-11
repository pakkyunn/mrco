package kr.co.lion.team4.mrco.model

import kr.co.lion.team4.mrco.ProductColor
import kr.co.lion.team4.mrco.ProductSize

data class ProductCategoryLinkedListModel(
    var productIdx: Int,
    var itemCategory: String,
    var itemName: String,
    var itemStock: Int,
    var itemSize: ProductSize,
    var itemImageFileName: String,
    var itemColor: ProductColor
) {
    constructor(): this(0,"","",0, ProductSize.SIZE_MEDIUM,"", ProductColor.COLOR_WHITE)
}