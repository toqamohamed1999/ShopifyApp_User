package eg.gov.iti.jets.shopifyapp_user.settings.domain.model

data class Currency(
    val currency: String,
    val enabled: Boolean,
    val rate_updated_at: String
)