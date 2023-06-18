package eg.gov.iti.jets.shopifyapp_user.base.domain.remote

import eg.gov.iti.jets.shopifyapp_user.base.model.FavDraftOrderResponse
import retrofit2.http.*

interface DraftOrderAPIService {
    @Headers("Content-Type:application/json","X-Shopify-Access-Token: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")
    @POST("draft_orders.json")
    suspend fun createFavDraftOrder(@Body draftOrder: FavDraftOrderResponse): FavDraftOrderResponse

    @Headers("Content-Type:application/json","X-Shopify-Access-Token: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")
    @GET("draft_orders/{draft_order_id}.json")
    suspend fun getFavDraftOrder(@Path(value = "draft_order_id")draftOrderId:Long): FavDraftOrderResponse


    @Headers("Content-Type:application/json","X-Shopify-Access-Token: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")
    @DELETE("draft_orders/{draftOrderId}.json")
    suspend fun deleteFavDraftOrder(@Path(value = "draftOrderId")draftOrderId:Long)

    @Headers("Content-Type:application/json","X-Shopify-Access-Token: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")
    @PUT("draft_orders/{draft_order_id}.json")
    suspend fun updateFavDraftOrder(@Path(value = "draft_order_id")draftOrderId:Long,
                                 @Body draftOrder: FavDraftOrderResponse
    ): FavDraftOrderResponse
}