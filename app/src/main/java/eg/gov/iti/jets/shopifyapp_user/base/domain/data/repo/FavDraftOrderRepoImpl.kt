package eg.gov.iti.jets.shopifyapp_user.base.domain.data.repo

import eg.gov.iti.jets.shopifyapp_user.base.domain.data.remote.FavDraftOrderRemoteSourceImpl
import eg.gov.iti.jets.shopifyapp_user.base.domain.remote.FavDraftOrderRemoteSource
import eg.gov.iti.jets.shopifyapp_user.base.domain.repo.FavDraftOrderRepoInterface
import eg.gov.iti.jets.shopifyapp_user.base.model.FavDraftOrderResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

class FavDraftOrderRepoImpl(private val remoteSource:FavDraftOrderRemoteSource=FavDraftOrderRemoteSourceImpl()) :FavDraftOrderRepoInterface{
    override suspend fun createFavDraftOrder(draftOrder: FavDraftOrderResponse): Flow<FavDraftOrderResponse> {
        return flowOf( remoteSource.createFavDraftOrder(draftOrder))
    }

    override suspend fun getFavDraftOrder(draftOrderId: Long): Flow<FavDraftOrderResponse> {
        return flowOf(remoteSource.getFavDraftOrder(draftOrderId))
    }

    override suspend fun deleteFavDraftOrder(draftOrderId: Long) {
        remoteSource.deleteFavDraftOrder(draftOrderId)
    }

    override suspend fun updateFavDraftOrder(
        draftOrderId: Long,
        draftOrder: FavDraftOrderResponse
    ): Flow<FavDraftOrderResponse> {
        return flowOf(remoteSource.updateFavDraftOrder(draftOrderId, draftOrder))
    }
}