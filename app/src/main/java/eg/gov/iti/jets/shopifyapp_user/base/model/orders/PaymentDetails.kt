package eg.gov.iti.jets.shopifyapp_user.base.model.orders

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class PaymentDetails(
    var avs_result_code: String="",
    var credit_card_bin: String="",
    var credit_card_company: String="",
    var credit_card_number: String="",
    var cvv_result_code: String=""
):Parcelable