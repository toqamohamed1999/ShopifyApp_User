package eg.gov.iti.jets.shopifyapp_user.cart.data.model

import android.os.Parcelable
import eg.gov.iti.jets.shopifyapp_user.base.model.Property
import kotlinx.parcelize.Parcelize
import kotlinx.parcelize.RawValue

@Parcelize
data class LineItem(
    var applied_discount: AppliedDiscount =AppliedDiscount("1","","Custom","25","Percentage"),
    var custom: Boolean =false,
    var fulfillment_service: String = "manual",
    var gift_card: Boolean=false,
    var grams: Int? =60,
    var id: Long?=0,
    var name: String?="Custom Tee",
    var price: String ="22",
    var product_id: Long?=0,
    var properties:@RawValue List<Property> = listOf(),
    var quantity: Int=1,
    var requires_shipping: Boolean? =false,
    var sku: String?="",
    var tax_lines: List<TaxLineX> = listOf(TaxLineX("",0.0,"")),
    var taxable: Boolean? =false,
    var title: String?="Custom Tee",
    var variant_id: @RawValue Any?=null,
    var variant_title: @RawValue Any?=null,
    var vendor: @RawValue Any?=null
):Parcelable
