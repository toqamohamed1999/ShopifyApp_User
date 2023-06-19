package eg.gov.iti.jets.shopifyapp_user.cart.data.model

import android.os.Parcelable
import eg.gov.iti.jets.shopifyapp_user.base.model.FavRoomPojo
import kotlinx.parcelize.Parcelize
import kotlinx.parcelize.RawValue

@Parcelize
data class LineItem(
    var applied_discount: AppliedDiscount,
    var custom: Boolean,
    var fulfillment_service: String,
    var gift_card: Boolean,
    var grams: Int,
    var id: Long?,
    var name: String?,
    var price: String,
    var product_id: Long?,
    var properties:@RawValue List<Any>,
    var quantity: Int,
    var requires_shipping: Boolean,
    var sku: String?,
    var tax_lines: List<TaxLineX>,
    var taxable: Boolean,
    var title: String?,
    var variant_id: @RawValue Any?,
    var variant_title: @RawValue Any?,
    var vendor: @RawValue Any?
):Parcelable
