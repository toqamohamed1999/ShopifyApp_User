package eg.gov.iti.jets.shopifyapp_user.base.model.orders

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class CurrentTotalPriceSet(
    val presentment_money: PresentmentMoney,
    val shop_money: ShopMoney
):Parcelable