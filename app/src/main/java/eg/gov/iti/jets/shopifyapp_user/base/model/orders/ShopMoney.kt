package eg.gov.iti.jets.shopifyapp_user.base.model.orders

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ShopMoney(
    val amount: String,
    val currency_code: String
):Parcelable