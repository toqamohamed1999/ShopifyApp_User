package eg.gov.iti.jets.shopifyapp_user.cart.domain.model

data class LineItem(
    var admin_graphql_api_id: String,
    var applied_discount: AppliedDiscount,
    var custom: Boolean,
    var fulfillment_service: String,
    var gift_card: Boolean,
    var grams: Int,
    var id: Long,
    var name: String,
    var price: Double,
    var product_id: Any?,
    var properties: List<String>,
    var quantity: Int,
    var requires_shipping: Boolean,
    var sku: Any?,
    var tax_lines: List<TaxLineX>,
    var taxable: Boolean,
    var title: String,
    var variant_id: Any?,
    var variant_title: Any?,
    var vendor: Any?
)