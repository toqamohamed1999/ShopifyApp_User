package eg.gov.iti.jets.shopifyapp_user.base.model.orders

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Order(
    val admin_graphql_api_id: String ?= null,
    val app_id: Long = 0,
    val billing_address: String ?= null,
    val browser_ip: String ?= null,
    val buyer_accepts_marketing: Boolean = false,
    val cancel_reason: String ?= null,
    val cancelled_at: String ?= null,
    val cart_token: String ?= null,
    val checkout_id: String ?= null,
    val checkout_token: String ?= null,
    val client_details: String ?= null,
    val closed_at: String ?= null,
    val compString: String ?= null,
    val confirmed: Boolean = false,
    val contact_email: String ?= null,
    val created_at: String ?= null,
    val currency: String ?= null,
    val current_subtotal_price: String ?= null,
    val current_total_additional_fees_set: String ?= null,
    val current_total_discounts: String ?= null,
    val current_total_duties_set: String ?= null,
    val current_total_price: String ?= null,
    val current_total_tax: String ?= null,
    val customer: CustomerOrder,
    val customer_locale: String ?= null,
    val device_id: String ?= null,
    val email: String ?= null,
    val estimated_taxes: Boolean = false,
    val financial_status: String ?= null,
    val fulfillment_status: String ?= null,
    val id: Long = 0,
    val landing_site: String ?= null,
    val landing_site_ref: String ?= null,
    val line_items: List<LineItemsOrder>,
    val location_id: String ?= null,
    val merchant_of_record_app_id: String ?= null,
    val name: String ?= null,
    val note: String ?= null,
    val number: Int = 0,
    val order_number: Int = 0,
    val order_status_url: String ?= null,
    val original_total_additional_fees_set: String ?= null,
    val original_total_duties_set: String ?= null,
    val payment_terms: String ?= null,
    val phone: String ?= null,
    val presentment_currency: String ?= null,
    val processed_at: String ?= null,
    val reference: String ?=null,
    val referring_site: String ?= null,
    val shipping_address: String ?= null,
    val source_identifier: String ?= null,
    val source_name: String ?= null,
    val source_url: String ?= null,
    val subtotal_price: String ?= null,
    val tags: String ?= null,
    val taxes_included: Boolean = false,
    val test: Boolean = false,
    val token: String ?= null,
    val total_discounts: String ?= null,
    val total_outstanding: String ?= null,
    val total_price: String ?= null,
    val total_tax: String ?= null,
    val total_tip_received: String ?= null,
    val total_weight: Int = 0,
    val updated_at: String ?= null,
    val user_id: String ?= null
) : Parcelable{

    override fun hashCode(): Int {
        var result = id.hashCode()
            result *= 31

        return result
}
}


