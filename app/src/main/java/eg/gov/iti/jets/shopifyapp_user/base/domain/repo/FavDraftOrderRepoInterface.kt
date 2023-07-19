package eg.gov.iti.jets.shopifyapp_user.base.domain.repo

import eg.gov.iti.jets.shopifyapp_user.base.model.FavDraftOrderResponse
import kotlinx.coroutines.flow.Flow

interface FavDraftOrderRepoInterface {
    suspend fun createFavDraftOrder(draftOrder: FavDraftOrderResponse): Flow<FavDraftOrderResponse>
    suspend fun getFavDraftOrder(draftOrderId: Long): Flow<FavDraftOrderResponse>
    suspend fun deleteFavDraftOrder(draftOrderId: Long)
    suspend fun updateFavDraftOrder(
        draftOrderId: Long,
        draftOrder: FavDraftOrderResponse
    ): Flow<FavDraftOrderResponse>
}