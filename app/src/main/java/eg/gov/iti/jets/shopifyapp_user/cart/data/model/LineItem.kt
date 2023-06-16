package eg.gov.iti.jets.shopifyapp_user.cart.domain.model

import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey


data class LineItem(
    var admin_graphql_api_id: String,
    var applied_discount: AppliedDiscount,
    var custom: Boolean,
    var fulfillment_service: String,
    var gift_card: Boolean,
    var grams: Int,
    var id: Long?,
    var name: String?,
    var price: String,
    var product_id: Long?,
    var properties: List<String?>,
    var quantity: Int,
    var requires_shipping: Boolean,
    var sku: String?,
    var tax_lines: List<TaxLineX>,
    var taxable: Boolean,
    var title: String?,
    var variant_id: Long?,
    var variant_title: String?,
    var vendor: String?
)