package eg.gov.iti.jets.shopifyapp_user.base.model.orders

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class CustomerOrder(
    val accepts_marketing: Boolean = false,
    val accepts_marketing_updated_at: String = "",
    val admin_graphql_api_id: String = "",
    val created_at: String = "",
    val currency: String = "",
    val default_address: ShippingAddress,
    val email: String = "",
    val first_name: String = "",
    val id: Long = 0,
    val last_name: String = "",
    val marketing_opt_in_level: String = "",
    val multipass_identifier: String = "",
    val note: String = "",
    val phone: String = "",
    val sms_marketing_consent: String = "",
    val state: String = "",
    val tags: String = "",
    val tax_exempt: Boolean = false,
    val updated_at: String = "",
    val verified_email: Boolean = false
):Parcelable