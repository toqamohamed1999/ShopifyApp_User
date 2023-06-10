package eg.gov.iti.jets.shopifyapp_user.base.model

import com.google.gson.annotations.SerializedName

data class Options(
    @SerializedName("id") var id: Long,
    @SerializedName("product_id") var productId: Long,
    @SerializedName("name") var name: String,
    @SerializedName("position") var position: Int
)
