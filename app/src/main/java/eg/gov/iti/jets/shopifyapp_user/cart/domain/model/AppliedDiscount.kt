package eg.gov.iti.jets.shopifyapp_user.cart.domain.model

data class AppliedDiscount(
    val amount: Double,
    val description: String,
    val title: String,
    val value: Double,
    val value_type: String
)