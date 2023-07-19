package eg.gov.iti.jets.shopifyapp_user.cart.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kotlinx.parcelize.RawValue

@Parcelize
data class DraftOrder(
    var admin_graphql_api_id: String,
    var applied_discount: AppliedDiscount,
    var billing_address: @RawValue Any,
    var completed_at: String,
    var created_at: String,
    var currency: String,
    var email: @RawValue Any,
    var id: Long,
    var invoice_sent_at:@RawValue Any,
    var invoice_url: String,
    var line_items: List<LineItem>,
    var name: String,
    var note: @RawValue Any,
    var note_attributes:@RawValue List<Any>,
    var order_id: @RawValue Any,
    var payment_terms: @RawValue Any,
    var shipping_address:@RawValue Any,
    var shipping_line: @RawValue Any,
    var status: String,
    var subtotal_price: String,
    var tags: String,
    var tax_exempt: Boolean,
    var tax_lines: List<TaxLineX>,
    var taxes_included: Boolean,
    var total_price: String,
    var total_tax: String,
    var updated_at: String
):Parcelable