package eg.gov.iti.jets.shopifyapp_user.base.domain.remote

import eg.gov.iti.jets.shopifyapp_user.base.model.FavDraftOrderResponse

interface FavDraftOrderRemoteSource {
    suspend fun createFavDraftOrder(draftOrder: FavDraftOrderResponse): FavDraftOrderResponse
    suspend fun getFavDraftOrder(draftOrderId: Long): FavDraftOrderResponse
    suspend fun deleteFavDraftOrder(draftOrderId: Long)
    suspend fun updateFavDraftOrder(
        draftOrderId: Long,
        draftOrder: FavDraftOrderResponse
    ): FavDraftOrderResponse
}