package eg.gov.iti.jets.shopifyapp_user.payment.data.model

data class Card(
    val firstName: String,
    val lastName: String,
    val cardNumber: String,
    val expireMonth: String,
    val expireYear: String,
    val verificationCode: String
)