package eg.gov.iti.jets.shopifyapp_user.base.model.orders

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class LineItemsOrder(
    val price: String?="",
    val quantity: Int?=0,
    val title: String?=""
): Parcelable