package eg.gov.iti.jets.shopifyapp_user.base.model.orders

data class Transaction(
    val amount: String,
    val kind: String,
    val status: String,
    val test: Boolean
)