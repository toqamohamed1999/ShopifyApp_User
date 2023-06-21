package eg.gov.iti.jets.shopifyapp_user.base.model

import com.google.gson.annotations.SerializedName
import eg.gov.iti.jets.shopifyapp_user.auth.domain.model.Customer
import eg.gov.iti.jets.shopifyapp_user.cart.data.model.DraftOrder


data class FavDraftOrderResponse(val draft_order: DraftOrderFav? = null)
data class DraftOrderFav(
    @SerializedName("id") var id: Long? = null,
    @SerializedName("note") var note: String? = null,
    @SerializedName("email") var email: String? = null,
    @SerializedName("taxes_included") var taxesIncluded: Boolean? = null,
    @SerializedName("currency") var currency: String? = null,
    @SerializedName("invoice_sent_at") var invoiceSentAt: String? = null,
    @SerializedName("created_at") var createdAt: String? = null,
    @SerializedName("updated_at") var updatedAt: String? = null,
    @SerializedName("tax_exempt") var taxExempt: Boolean? = null,
    @SerializedName("completed_at") var completedAt: String? = null,
    @SerializedName("name") var name: String? = null,
    @SerializedName("status") var status: String? = null,
    @SerializedName("line_items") var lineItems: ArrayList<LineItems> = arrayListOf(),
    @SerializedName("shipping_address") var shippingAddress: ShippingAddress? = ShippingAddress(),
    @SerializedName("billing_address") var billingAddress: BillingAddress? = BillingAddress(),
    @SerializedName("invoice_url") var invoiceUrl: String? = null,
    @SerializedName("applied_discount") var appliedDiscount: AppliedDiscount? = AppliedDiscount(),
    @SerializedName("order_id") var orderId: String? = null,
    @SerializedName("shipping_line") var shippingLine: ShippingLine? = ShippingLine(),
    @SerializedName("tax_lines") var taxLines: ArrayList<TaxLine> = arrayListOf(),
    @SerializedName("tags") var tags: String? = null,
    @SerializedName("note_attributes") var noteAttributes: ArrayList<String> = arrayListOf(),
    @SerializedName("total_price") var totalPrice: String? = null,
    @SerializedName("subtotal_price") var subtotalPrice: String? = null,
    @SerializedName("total_tax") var totalTax: String? = null,
    @SerializedName("admin_graphql_api_id") var adminGraphqlApiId: String? = null,
    @SerializedName("customer") var customer: Customer? = Customer()
)

data class AppliedDiscountFav(

    @SerializedName("description") var description: String? = null,
    @SerializedName("value") var value: String? = null,
    @SerializedName("title") var title: String? = null,
    @SerializedName("amount") var amount: String? = null,
    @SerializedName("value_type") var valueType: String? = null

)

data class LineItems(

    @SerializedName("variant_id") var variantId: Long? = null,
    @SerializedName("product_id") var productId: Long? = null,
    @SerializedName("title") var title: String? = null,
    @SerializedName("variant_title") var variantTitle: String? = null,
    @SerializedName("sku") var sku: String? = null,
    @SerializedName("vendor") var vendor: String? = null,
    @SerializedName("quantity") var quantity: Int? = null,
    @SerializedName("requires_shipping") var requiresShipping: Boolean? = null,
    @SerializedName("taxable") var taxable: Boolean? = null,
    @SerializedName("gift_card") var giftCard: Boolean? = null,
    @SerializedName("fulfillment_service") var fulfillmentService: String? = null,
    @SerializedName("grams") var grams: Int? = null,
    @SerializedName("tax_lines") var taxLines: ArrayList<TaxLine> = arrayListOf(),
    @SerializedName("applied_discount") var appliedDiscount: AppliedDiscount? = null,
    @SerializedName("name") var name: String? = null,
    @SerializedName("properties") var properties: ArrayList<Property> = arrayListOf(),
    @SerializedName("custom") var custom: Boolean? = null,
    @SerializedName("price") var price: String? = null,
    @SerializedName("admin_graphql_api_id") var adminGraphqlApiId: String? = null

)

data class ShippingAddress(

    @SerializedName("first_name") var firstName: String? = null,
    @SerializedName("address1") var address1: String? = null,
    @SerializedName("phone") var phone: String? = null,
    @SerializedName("city") var city: String? = null,
    @SerializedName("zip") var zip: String? = null,
    @SerializedName("province") var province: String? = null,
    @SerializedName("country") var country: String? = null,
    @SerializedName("last_name") var lastName: String? = null,
    @SerializedName("address2") var address2: String? = null,
    @SerializedName("company") var company: String? = null,
    @SerializedName("latitude") var latitude: Double? = null,
    @SerializedName("longitude") var longitude: Double? = null,
    @SerializedName("name") var name: String? = null,
    @SerializedName("country_code") var countryCode: String? = null,
    @SerializedName("province_code") var provinceCode: String? = null

)

data class BillingAddress(

    @SerializedName("first_name") var firstName: String? = null,
    @SerializedName("address1") var address1: String? = null,
    @SerializedName("phone") var phone: String? = null,
    @SerializedName("city") var city: String? = null,
    @SerializedName("zip") var zip: String? = null,
    @SerializedName("province") var province: String? = null,
    @SerializedName("country") var country: String? = null,
    @SerializedName("last_name") var lastName: String? = null,
    @SerializedName("address2") var address2: String? = null,
    @SerializedName("company") var company: String? = null,
    @SerializedName("latitude") var latitude: Double? = null,
    @SerializedName("longitude") var longitude: Double? = null,
    @SerializedName("name") var name: String? = null,
    @SerializedName("country_code") var countryCode: String? = null,
    @SerializedName("province_code") var provinceCode: String? = null

)

data class ShippingLine(

    @SerializedName("title") var title: String? = null,
    @SerializedName("custom") var custom: Boolean? = null,
    @SerializedName("handle") var handle: String? = null,
    @SerializedName("price") var price: String? = null

)

data class AppliedDiscount(

    @SerializedName("description") var description: String? = null,
    @SerializedName("value") var value: String? = null,
    @SerializedName("title") var title: String? = null,
    @SerializedName("amount") var amount: String? = null,
    @SerializedName("value_type") var valueType: String? = null

)

data class TaxLine(
    val price: String,
    val rate: Double,
    val title: String
)

data class Property(
    val name: String? = "url_image",
    val value: String?
)

fun LineItems.toFavRoomPojo(): FavRoomPojo {
    return FavRoomPojo(
        productId = productId,
        price = price,
        imageSrc = appliedDiscount?.description,
        title = title,
        bodyHtml = variantTitle.toString()
    )
}