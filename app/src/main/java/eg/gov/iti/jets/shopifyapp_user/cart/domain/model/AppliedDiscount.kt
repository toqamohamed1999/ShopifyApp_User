package eg.gov.iti.jets.shopifyapp_user.cart.domain.model

data class AppliedDiscount(
    var amount: String,
    var description: String?,
    var title: String,
    var value: String,
    var value_type: String
)