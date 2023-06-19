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
   suspend fun updateDraftOrder(@Path(value = "draftOrderId")draftOrderId:String,
                                @Body draftOrder: DraftOrderResponse
   ): DraftOrderResponse
}