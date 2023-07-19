package eg.gov.iti.jets.shopifyapp_user.cart.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class AppliedDiscount(
    var amount: String,
    var description: String?,
    var title: String,
    var value: String,
    var value_type: String
):Parcelable