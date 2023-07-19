package eg.gov.iti.jets.shopifyapp_user.settings.domain.model

data class CurrenciesResponse(
    val documentation: String,
    val result: String,
    val supported_codes: List<List<String>>,
    val terms_of_use: String
)