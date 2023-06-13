package eg.gov.iti.jets.shopifyapp_user.base.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class Options(
    @SerializedName("id") var id: Long?=0,
    @SerializedName("product_id") var productId: Long?=0,
    @SerializedName("name") var name: String?="",
    @SerializedName("position") var position: Int?=0
):Parcelable
