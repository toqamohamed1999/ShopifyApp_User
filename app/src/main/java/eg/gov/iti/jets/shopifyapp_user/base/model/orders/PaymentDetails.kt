package eg.gov.iti.jets.shopifyapp_user.base.model.orders

data class PaymentDetails(
    var avs_result_code: String="",
    var credit_card_bin: String="",
    var credit_card_company: String="",
    var credit_card_number: String="",
    var cvv_result_code: String=""
)