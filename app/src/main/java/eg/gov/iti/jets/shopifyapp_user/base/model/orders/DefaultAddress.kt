package eg.gov.iti.jets.shopifyapp_user.base.model.orders

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ShippingAddress(
    val address1: String? = "",
    val address2: String = "",
    val city: String = "",
    val company: String = "",
    val country: String = "",
    val country_code: String = "",
    val first_name: String = "",
    val last_name: String = "",
    val latitude: String = "",
    val longitude: String = "",
    val name: String = "",
    val phone: String = "",
    val province: String = "",
    val province_code: String = "",
    val zip: String = ""
) : Parcelable