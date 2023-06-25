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