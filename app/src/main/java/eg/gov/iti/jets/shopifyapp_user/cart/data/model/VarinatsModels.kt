package eg.gov.iti.jets.shopifyapp_user.cart.data.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import eg.gov.iti.jets.shopifyapp_user.base.model.Variants
import kotlinx.parcelize.Parcelize

@Parcelize
data class VariantRoot(

    @SerializedName("variant") var variant: Variants? = Variants()

) : Parcelable
data class InventoryLevel(

    @SerializedName("inventory_item_id") var inventoryItemId: Int? = null,
    @SerializedName("location_id") var locationId: Int? = null,
    @SerializedName("available") var available: Int? = null,
    @SerializedName("updated_at") var updatedAt: String? = null,
    @SerializedName("admin_graphql_api_id") var adminGraphqlApiId: String? = null

)

data class  InventoryLevelResponse(

    @SerializedName("inventory_level" ) var inventoryLevel : InventoryLevel? = InventoryLevel()
)
data class UpdateQuantityBody(

    @SerializedName("location_id") var locationId: Long? = null,
    @SerializedName("inventory_item_id") var inventoryItemId: Long? = null,
    @SerializedName("available") var available: Int? = null

)