package eg.gov.iti.jets.shopifyapp_user.cart.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Weight(
    val unit: String,
    val value: Double
):Parcelable