package eg.gov.iti.jets.shopifyapp_user.base.model.orders

data class PaymentDetails(
    val avs_result_code: String,
    val credit_card_bin: String,
    val credit_card_company: String,
    val credit_card_number: String,
    val cvv_result_code: String
)