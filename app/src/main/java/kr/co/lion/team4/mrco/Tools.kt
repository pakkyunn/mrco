package kr.co.lion.team4.mrco

class Tools {
    companion object{

    }
}

enum class FragmentName(var str: String){
    FRAGMENT_CODI_PRODUCT_INFO("CodiProductInfoFragment"),
    FRAGMENT_CODI_PRODUCT_INFO_ALL("CodiProductInfoAllFragment"),
    FRAGMENT_CODI_PRODUCT_INFO_TOP("CodiProductInfoTopFragment"),
    FRAGMENT_CODI_PRODUCT_INFO_BOTTOM("CodiProductInfoBottomFragment"),
    FRAGMENT_CODI_PRODUCT_INFO_SHOES("CodiProductInfoShoesFragment"),
    FRAGMENT_CODI_PRODUCT_INFO_ACCESSORY("CodiProductInfoAccessoryFragment"),

}

enum class ProductSize(var ps: String){
    SIZE_XXXSMALL("XXXS"),
    SIZE_XXSMALL("XXS"),
    SIZE_XSMALL("XS"),
    SIZE_SMALL("S"),
    SIZE_MEDIUM("M"),
    SIZE_LARGE("L"),
    SIZE_XLARGE("XL"),
    SIZE_XXLARGE("XXL"),
    SIZE_XXXLARGE("XXXL")
}

enum class ProductColor(var pc: String){
    COLOR_BLACK("Black"),
    COLOR_WHITE("White")
}