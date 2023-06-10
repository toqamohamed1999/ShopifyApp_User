package eg.gov.iti.jets.shopifyapp_user.base.model

import com.google.gson.annotations.SerializedName

data class Image (
    @SerializedName("id") var id : Long,
    @SerializedName("product_id") var productId : Long,
    @SerializedName("position") var position : Int,
    @SerializedName("created_at") var createdAt : String,
    @SerializedName("updated_at") var updatedAt : String,
    @SerializedName("alt") var alt : String,
    @SerializedName("width") var width : Int,
    @SerializedName("height") var height : Int,
    @SerializedName("src") var src : String,
    @SerializedName("variant_ids") var variantIds : List<String>,
    @SerializedName("admin_graphql_api_id") var adminGraphqlApiId : String
)
