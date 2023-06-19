package eg.gov.iti.jets.shopifyapp_user.base.model.orders

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class EmailMarketingConsent(
    val consent_updated_at: String,
    val opt_in_level: String,
    val state: String
):Parcelable