package eg.gov.iti.jets.shopifyapp_user.cart.domain.remote

import eg.gov.iti.jets.shopifyapp_user.cart.data.model.DraftOrderResponse
import retrofit2.http.*
interface DraftOrderNetworkServices {
    @Headers("Content-Type:application/json","X-Shopify-Access-Token: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")
    @POST("draft_orders.json")
   suspend fun createNewDraftOrder(@Body draftOrder: DraftOrderResponse): DraftOrderResponse

    @Headers("Content-Type:application/json","X-Shopify-Access-Token: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")
    @GET("draft_orders/{draftOrderId}.json?")
   suspend fun getDraftOrder(@Path(value = "draftOrderId")draftOrderId:String): DraftOrderResponse


    @Headers("Content-Type:application/json","X-Shopify-Access-Token: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")
    @DELETE("draft_orders/{draftOrderId}.json")
   suspend fun deleteDraftOrder(@Path(value = "draftOrderId")draftOrderId:String):Any

    @Headers("Content-Type:application/json","X-Shopify-Access-Token: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")
    @PUT("draft_orders/{draftOrderId}.json")
   suspend fun updateDraftOrder(@Path(value = "draftOrderId") draftOrderId: String?,
                                @Body draftOrder: DraftOrderResponse
   ): DraftOrderResponse
}