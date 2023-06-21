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
    val arr = applied_discount.description?.split(")")
    var url:String? = ""
    if((arr?.size?:0)>1)
    {
        url = arr?.get(1)
    }
   return LineItemsOrder(price,quantity, title ="${title})${arr?.get(0)}", listOf(
        Property("image_url", value = url)
    ))
  }
