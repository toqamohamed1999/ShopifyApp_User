package eg.gov.iti.jets.shopifyapp_user.auth.domain.model

import com.google.gson.annotations.SerializedName


data class CustomerResponse(var customer: Customer)


data class Customer(
    var id: Long?=null,
    var email: String?=null,
    val email_marketing_consent: EmailMarketingConsent?=null,
    var accepts_marketing: Boolean?=null,
    var created_at: String?=null,
    var updated_at: String?=null,
    var first_name: String?=null,
    var last_name: String?=null,
    var orders_count: Int?=null,
    var state: String?=null,
    var total_spent: String?=null,
    var last_order_id: Long?=null,
    var note: String?=null,
    var verified_email: Boolean?=null,
    var multipass_identifier: String?=null,
    var tax_exempt: Boolean?=null,
    var phone: String?=null,
    var tags: String?=null,
    var last_order_name: String?=null,
    var  currency: String?=null,
    var addresses: List<Address>?=null,
    var accepts_marketing_updated_at: String?=null,
    val sms_marketing_consent: SmsMarketingConsent?=null,
    var marketing_opt_in_level: Any?=null,
    var tax_exemptions: List<Any>?=null,
    var admin_graphql_api_id: String?=null,
    var default_address: Address?=null
)

data class Address(
    var id: Long?=null,
    var customer_id: Long?=null,
    var first_name: String?=null,
    var last_name: String?=null,
    var company: String?=null,
    var address1: String?=null,
    var address2: String?=null,
    var city: String?=null,
    var province: String?=null,
    var country: String?=null,
    var zip: String?=null,
    var phone: String?=null,
    var name: String?=null,
    var province_code: String?=null,
    var country_code: String?=null,
    var country_name: String?=null,
    var default: Boolean?=null
)

//data class Customer(
//    val email: String,
//    val email_marketing_consent: EmailMarketingConsent,
//    val first_name: String,
//    val id: Long,
//    val last_name: String,
//    val last_order_id: Int,
//    val last_order_name: String,
//    val marketing_opt_in_level: Any,
//    val multipass_identifier: Any,
//    val note: String ="",
//    val orders_count: Long,
//    val phone: String,
//    val sms_marketing_consent: SmsMarketingConsent,
//    val state: String,
//    val tags: String,
//    val tax_exempt: Boolean,
//    val tax_exemptions: List<Any>,
//    val total_spent: String,
//    val updated_at: String,
//    val verified_email: Boolean,
//    var default_address: Address?=null,
//    var addresses: List<Address>?=null
//
//)
//
//
//data class Address (
//    val address1: String,
//    val address2: Any,
//    val city: String,
//    val company: Any,
//    val country: String,
//    val country_code: String,
//    val country_name: String,
//    val customer_id: Int,
//    val default: Boolean,
//    val first_name: String,
//    val id: Int,
//    val last_name: String,
//    val name: String,
//    val phone: String,
//    val province: String,
//    val province_code: String,
//    val zip: String)
data class EmailMarketingConsent (
    val consent_updated_at: Any,
    val opt_in_level: String,
    val state: String)
data class SmsMarketingConsent(
    val consent_collected_from: String,
    val consent_updated_at: Any,
    val opt_in_level: String,
    val state: String
)
data class SignupRequest( val customer: Customer)
data class SignupModel (val email: String,
                        val first_name: String,
                        val last_name: String,
                        @SerializedName("tags") var password: String,
                        val note : String = "",
                        )