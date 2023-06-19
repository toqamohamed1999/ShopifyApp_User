package eg.gov.iti.jets.shopifyapp_user.favorite.data.repo


import eg.gov.iti.jets.shopifyapp_user.cart.data.remote.DraftOrderAPIState
import eg.gov.iti.jets.shopifyapp_user.cart.data.model.DraftOrderResponse
import eg.gov.iti.jets.shopifyapp_user.cart.domain.remote.DraftOrderRemoteSource
import eg.gov.iti.jets.shopifyapp_user.favorite.domain.repo.FavRemoteRepoInterface
import kotlinx.coroutines.flow.MutableStateFlow

class FavRemoteRepoImpl(private val draftRemoteSource: DraftOrderRemoteSource):FavRemoteRepoInterface {
    override suspend fun createNewDraftOrder(order: DraftOrderResponse): MutableStateFlow<DraftOrderAPIState> {
      return draftRemoteSource.createNewDraftOrder(order)
    }

    override suspend fun removeDraftOrder(orderId: String): MutableStateFlow<DraftOrderAPIState> {
        return draftRemoteSource.deleteDraftOrder(orderId)
    }

    override suspend fun updateProductsInFav(
        orderId: String,
        newDraftOrder: DraftOrderResponse
    ): MutableStateFlow<DraftOrderAPIState> {
       return draftRemoteSource.updateDraftOrder(orderId,newDraftOrder)
    }

    override suspend fun getFavProducts(orderId: String): MutableStateFlow<DraftOrderAPIState> {
       return draftRemoteSource.getDraftOrder(orderId)
    }
}