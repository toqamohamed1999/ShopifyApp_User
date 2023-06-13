package eg.gov.iti.jets.shopifyapp_user.categories.domain.model

import com.google.gson.annotations.SerializedName

data class ImageCategory(
    @SerializedName("created_at") var createdAt : String,
    @SerializedName("alt") var alt : String,
    @SerializedName("width") var width : Int,
    @SerializedName("height") var height : Int,
    @SerializedName("src") var src : String
)
