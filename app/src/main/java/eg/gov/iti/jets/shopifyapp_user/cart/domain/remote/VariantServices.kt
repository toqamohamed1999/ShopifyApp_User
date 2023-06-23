package eg.gov.iti.jets.shopifyapp_user.cart.domain.remote

import eg.gov.iti.jets.shopifyapp_user.cart.data.model.InventoryLevelResponse
import eg.gov.iti.jets.shopifyapp_user.cart.data.model.UpdateQuantityBody
import eg.gov.iti.jets.shopifyapp_user.cart.data.model.VariantRoot
import retrofit2.http.*

interface VariantServices {

    @Headers("Content-Type:application/json", "X-Shopify-Access-Token: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")
    @GET("variants/{variant_id}.json")
    suspend fun getVariantBYId(
        @Path("variant_id") VariantId : Long
    ) : VariantRoot
    @Headers("Content-Type:application/json", "X-Shopify-Access-Token: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")
    @POST("inventory_levels/set.json")
    suspend fun updateProductQuantity(
        @Body body: UpdateQuantityBody
    ) : InventoryLevelResponse
}