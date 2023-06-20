package eg.gov.iti.jets.shopifyapp_user.base.model.orders

import android.os.Parcelable
import eg.gov.iti.jets.shopifyapp_user.cart.data.model.LineItem
import kotlinx.android.parcel.Parcelize

@Parcelize
data class LineItemsOrder(
    val price: String?="",
    val quantity: Int?=0,

    val title: String?="",
    val properties: List<Property>
): Parcelable
fun LineItem.toLineItemOrder():LineItemsOrder{
    return LineItemsOrder(price,quantity,title)
}
