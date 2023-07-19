package eg.gov.iti.jets.shopifyapp_user.cart.domain.remote

import eg.gov.iti.jets.shopifyapp_user.cart.data.model.InventoryLevelResponse
import eg.gov.iti.jets.shopifyapp_user.cart.data.model.UpdateQuantityBody
import eg.gov.iti.jets.shopifyapp_user.cart.data.model.VariantRoot
import retrofit2.http.*

interface VariantServices {

    @Headers("Content-Type:application/json","X-Shopify-Access-Token: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")
    @GET("variants/{variant_id}.json")
    suspend fun getVariantBYId(
        @Path("variant_id") VariantId : Long
    ) : VariantRoot
    @Headers("Content-Type:application/json","X-Shopify-Access-Token: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")
    @POST("inventory_levels/set.json")
    suspend fun updateProductQuantity(
        @Body body: UpdateQuantityBody
    ) : InventoryLevelResponse
}