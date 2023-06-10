package eg.gov.iti.jets.shopifyapp_user.cart.domain.model

data class DraftOrder(
    var admin_graphql_api_id: String="",
    var applied_discount: AppliedDiscount = AppliedDiscount(),
    var billing_address: String = "",
    var completed_at: String = "",
    var created_at: String ="",
    var currency: String="",
    var email: String = "",
    var id: Long=0,
    var invoice_sent_at: String="",
    var invoice_url: String="",
    var line_items: List<LineItem> = listOf(),
    var name: String="",
    var note: String="",
    var note_attributes: List<Any> = listOf(),
    var order_id: String= "",
    var payment_terms: String="",
    var shipping_address: String="",
    var shipping_line: String="",
    var status: String="",
    var subtotal_price: Double=0.0,
    var tags: String="",
    var tax_exempt: Boolean=false,
    var tax_lines: List<TaxLineX> = listOf(),
    var taxes_included: Boolean = false,
    var total_price: Double =0.0,
    var total_tax: Double=0.0,
    var updated_at: String=""
)