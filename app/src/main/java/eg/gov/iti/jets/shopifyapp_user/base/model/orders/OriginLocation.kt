package eg.gov.iti.jets.shopifyapp_user.base.model.orders

data class OriginLocation(
    val address1: String,
    val address2: String,
    val city: String,
    val country_code: String,
    val id: Long,
    val name: String,
    val province_code: String,
    val zip: String
)