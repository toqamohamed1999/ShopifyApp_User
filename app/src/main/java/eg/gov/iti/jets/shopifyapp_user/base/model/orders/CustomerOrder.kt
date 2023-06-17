package eg.gov.iti.jets.shopifyapp_user.base.model.orders

data class CustomerOrder(
    val accepts_marketing: Boolean,
    val accepts_marketing_updated_at: String,
    val admin_graphql_api_id: String,
    val created_at: String,
    val currency: String,
    val default_address: ShippingAddress,
    val email: String,
    val email_marketing_consent: EmailMarketingConsent,
    val first_name: String,
    val id: Long,
    val last_name: String,
    val marketing_opt_in_level: Any,
    val multipass_identifier: String,
    val note: String,
    val phone: Any,
    val sms_marketing_consent: Any,
    val state: String,
    val tags: String,
    val tax_exempt: Boolean,
    val tax_exemptions: List<Any>,
    val updated_at: String,
    val verified_email: Boolean
)