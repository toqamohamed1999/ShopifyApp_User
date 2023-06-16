package eg.gov.iti.jets.shopifyapp_user.payment.domain.model

data class TransactionX(
    val amount: String,
    val currency: String,
    val kind: String,
    val parent_id: Long,
    val test: String
)