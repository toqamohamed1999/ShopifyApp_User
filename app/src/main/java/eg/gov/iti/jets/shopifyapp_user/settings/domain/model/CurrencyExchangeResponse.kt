package eg.gov.iti.jets.shopifyapp_user.settings.domain.model

data class CurrencyExchangeResponse(
    val date: String,
    val info: Info,
    val query: Query,
    val result: Double,
    val success: Boolean
)