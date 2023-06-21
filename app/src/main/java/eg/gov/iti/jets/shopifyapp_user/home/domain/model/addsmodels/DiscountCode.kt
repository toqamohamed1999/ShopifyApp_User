package eg.gov.iti.jets.shopifyapp_user.home.domain.model.addsmodels

data class DiscountCode(
    val code: String,
    var created_at: String,
    val id: Long,
    val price_rule_id: Long,
    var updated_at: String,
    val usage_count: Int
)