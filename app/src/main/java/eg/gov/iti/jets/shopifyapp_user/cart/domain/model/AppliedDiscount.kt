package eg.gov.iti.jets.shopifyapp_user.cart.domain.model

data class AppliedDiscount(
    val amount: Double=0.0,
    val description: String="",
    val title: String="",
    val value: Double=0.0,
    val value_type: String=""
)