package eg.gov.iti.jets.shopifyapp_user.base.model.orders

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Order(
    var admin_graphql_api_id: String ?= null,
    var app_id: Long = 0,
    var billing_address: String ?= null,
    var browser_ip: String ?= null,
    var buyer_accepts_marketing: Boolean = false,
    var cancel_reason: String ?= null,
    var cancelled_at: String ?= null,
    var cart_token: String ?= null,
    var checkout_id: String ?= null,
    var checkout_token: String ?= null,
    var client_details: String ?= null,
    var closed_at: String ?= null,
    var compString: String ?= null,
    var confirmed: Boolean = false,
    var contact_email: String ?= null,
    var created_at: String ?= null,
    var currency: String ?= null,
    var current_subtotal_price: String ?= null,
    var current_total_additional_fees_set: String ?= null,
    var current_total_discounts: String ?= null,
    var current_total_duties_set: String ?= null,
    var current_total_price: String ?= null,
    var current_total_tax: String ?= null,
    var customer: CustomerOrder?=null,
    var customer_locale: String ?= null,
    var device_id: String ?= null,
    var email: String ?= null,
    var estimated_taxes: Boolean = false,
    var financial_status: String ?= null,
    var fulfillment_status: String ?= null,
    var id: Long = 0,
    var landing_site: String ?= null,
    var landing_site_ref: String ?= null,
    var line_items: List<LineItemsOrder>?,
    var location_id: String ?= null,
    var merchant_of_record_app_id: String ?= null,
    var name: String ?= null,
    var note: String ?= null,
    var number: Int = 0,
    var order_number: Int = 0,
    var order_status_url: String ?= null,
    var original_total_additional_fees_set: String ?= null,
    var original_total_duties_set: String ?= null,
    var payment_terms: String ?= null,
    var phone: String ?= null,
    var presentment_currency: String ?= null,
    var processed_at: String ?= null,
    var reference: String ?=null,
    var referring_site: String ?= null,
    var shipping_address: String ?= null,
    var source_identifier: String ?= null,
    var source_name: String ?= null,
    var source_url: String ?= null,
    var subtotal_price: String ?= null,
    var tags: String ?= null,
    var taxes_included: Boolean = false,
    var test: Boolean = false,
    var token: String ?= null,
    var total_discounts: String ?= null,
    var total_outstanding: String ?= null,
    var total_price: String ?= null,
    var total_tax: String ?= null,
    var total_tip_received: String ?= null,
    var total_weight: Int = 0,
    var updated_at: String ?= null,
    var user_id: String ?= null
) : Parcelable{
data class OrderBody(var order: Order? = Order(line_items = listOf()))
    override fun hashCode(): Int {
        var result = id.hashCode()
            result *= 31

        return result
}
}


