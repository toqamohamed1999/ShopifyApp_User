package eg.gov.iti.jets.shopifyapp_user.payment.domain.model

data class Transaction(
    val admin_graphql_api_id: String,
    val amount: String,
    val authorization: Any,
    val created_at: String,
    val currency: String,
    val device_id: Any,
    val error_code: Any,
    val gateway: String,
    val id: Long,
    val kind: String,
    val location_id: Any,
    val message: Any,
    val order_id: Long,
    val parent_id: Any,
    val payment_id: String,
    val processed_at: String,
    val receipt: Receipt,
    val source_name: String,
    val status: String,
    val test: Boolean,
    val total_unsettled_set: TotalUnsettledSet,
    val user_id: Any
)