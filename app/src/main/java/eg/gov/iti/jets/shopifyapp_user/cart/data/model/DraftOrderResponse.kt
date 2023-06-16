package eg.gov.iti.jets.shopifyapp_user.cart.data.model

import android.os.Parcelable
import eg.gov.iti.jets.shopifyapp_user.base.model.orders.Order
import eg.gov.iti.jets.shopifyapp_user.base.model.orders.OrderBody
import eg.gov.iti.jets.shopifyapp_user.base.model.orders.PaymentDetails
import eg.gov.iti.jets.shopifyapp_user.base.model.orders.ShippingAddress
import eg.gov.iti.jets.shopifyapp_user.settings.data.local.UserSettings
import kotlinx.parcelize.Parcelize

@Parcelize
data class DraftOrderResponse(
    var draft_order: DraftOrder?
):Parcelable
fun DraftOrderResponse.toOrderBody():OrderBody{
    return OrderBody(
        Order(
            draft_order?.applied_discount?.value?:"",
            draft_order?.total_price?:"",
            listOf(),"","Bogus",draft_order?.line_items?: listOf(), PaymentDetails()
        , ShippingAddress(),true, listOf(),UserSettings.userAPI_Id.toInt()
        )
    )
}