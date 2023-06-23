package eg.gov.iti.jets.shopifyapp_user.base.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class Variants (
    @SerializedName("id") var id : Long?=0,
    @SerializedName("product_id") var productId : Long?=0,
    @SerializedName("title") var title : String?="",
    @SerializedName("price") var price : String?="",
    @SerializedName("sku") var sku : String?="",
    @SerializedName("position") var position : Int?=0,
    @SerializedName("inventory_policy") var inventoryPolicy : String?="",
    @SerializedName("compare_at_price") var compareAtPrice : String?="",
    @SerializedName("fulfillment_service") var fulfillmentService : String?="",
    @SerializedName("inventory_management") var inventoryManagement : String?="",
    @SerializedName("option1") var option1 : String?="",
    @SerializedName("option2") var option2 : String?="",
    @SerializedName("option3") var option3 : String?="",
    @SerializedName("created_at") var createdAt : String?="",
    @SerializedName("updated_at") var updatedAt : String?="",
    @SerializedName("taxable") var taxable : Boolean?=false,
    @SerializedName("barcode") var barcode : String?="",
    @SerializedName("grams") var grams : Int?=0,
    @SerializedName("tracked")var tracked:Boolean=true,
    @SerializedName("image_id") var imageId : String?="",
    @SerializedName("weight") var weight : Int?=0,
    @SerializedName("weight_unit") var weightUnit : String?="",
    @SerializedName("inventory_item_id") var inventoryItemId : Long?=0,
    @SerializedName("inventory_quantity") var inventoryQuantity : Int?=0,
    @SerializedName("old_inventory_quantity") var oldInventoryQuantity : Int?=0,
    @SerializedName("requires_shipping") var requiresShipping : Boolean?=false,
    @SerializedName("admin_graphql_api_id") var adminGraphqlApiId : String?=""
):Parcelable
