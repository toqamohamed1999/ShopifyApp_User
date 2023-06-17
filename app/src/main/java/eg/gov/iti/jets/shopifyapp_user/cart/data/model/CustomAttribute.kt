package eg.gov.iti.jets.shopifyapp_user.cart.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class CustomAttribute(
    val key: String,
    val value: String
):Parcelable