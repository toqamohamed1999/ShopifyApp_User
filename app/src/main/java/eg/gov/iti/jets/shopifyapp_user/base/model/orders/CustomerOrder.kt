package eg.gov.iti.jets.shopifyapp_user.base.model.orders

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class CustomerOrder(
    val id: Long = 0
):Parcelable