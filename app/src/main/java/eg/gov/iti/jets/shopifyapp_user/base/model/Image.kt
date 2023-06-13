package eg.gov.iti.jets.shopifyapp_user.base.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class Image (
    @SerializedName("id") var id : Long?=0,
    @SerializedName("product_id") var productId : Long?=0,
    @SerializedName("position") var position : Int?=0,
    @SerializedName("created_at") var createdAt : String?="",
    @SerializedName("updated_at") var updatedAt : String?="",
    @SerializedName("alt") var alt : String?="",
    @SerializedName("width") var width : Int?=0,
    @SerializedName("height") var height : Int?=0,
    @SerializedName("src") var src : String?="",
    @SerializedName("variant_ids") var variantIds : List<String>,
    @SerializedName("admin_graphql_api_id") var adminGraphqlApiId : String?=""
):Parcelable
