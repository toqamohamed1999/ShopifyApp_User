package eg.gov.iti.jets.shopifyapp_user.cart.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class TaxLineX(
    val price: String,
    val rate: Double,
    val title: String
):Parcelable