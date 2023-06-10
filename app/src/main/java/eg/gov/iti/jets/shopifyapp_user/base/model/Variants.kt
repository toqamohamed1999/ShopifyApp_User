package eg.gov.iti.jets.shopifyapp_user.base.model

import com.google.gson.annotations.SerializedName

data class Variants (
    @SerializedName("id") var id : Long,
    @SerializedName("product_id") var productId : Long,
    @SerializedName("title") var title : String,
    @SerializedName("price") var price : String,
    @SerializedName("sku") var sku : String,
    @SerializedName("position") var position : Int,
    @SerializedName("inventory_policy") var inventoryPolicy : String,
    @SerializedName("compare_at_price") var compareAtPrice : String,
    @SerializedName("fulfillment_service") var fulfillmentService : String,
    @SerializedName("inventory_management") var inventoryManagement : String,
    @SerializedName("option1") var option1 : String,
    @SerializedName("option2") var option2 : String,
    @SerializedName("option3") var option3 : String,
    @SerializedName("created_at") var createdAt : String,
    @SerializedName("updated_at") var updatedAt : String,
    @SerializedName("taxable") var taxable : Boolean,
    @SerializedName("barcode") var barcode : String,
    @SerializedName("grams") var grams : Int,
    @SerializedName("image_id") var imageId : String,
    @SerializedName("weight") var weight : Int,
    @SerializedName("weight_unit") var weightUnit : String,
    @SerializedName("inventory_item_id") var inventoryItemId : Long,
    @SerializedName("inventory_quantity") var inventoryQuantity : Int,
    @SerializedName("old_inventory_quantity") var oldInventoryQuantity : Int,
    @SerializedName("requires_shipping") var requiresShipping : Boolean,
    @SerializedName("admin_graphql_api_id") var adminGraphqlApiId : String
)
