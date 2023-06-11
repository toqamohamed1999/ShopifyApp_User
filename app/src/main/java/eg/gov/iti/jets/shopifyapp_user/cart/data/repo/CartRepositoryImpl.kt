package eg.gov.iti.jets.shopifyapp_user.cart.data.repo

import eg.gov.iti.jets.shopifyapp_user.cart.data.local.CartSharedPrefsOperations
import eg.gov.iti.jets.shopifyapp_user.cart.data.remote.DraftOrderAPIState
import eg.gov.iti.jets.shopifyapp_user.cart.domain.model.DraftOrderResponse
import eg.gov.iti.jets.shopifyapp_user.cart.domain.model.LineItem
import eg.gov.iti.jets.shopifyapp_user.cart.domain.remote.DraftOrderRemoteSource
import eg.gov.iti.jets.shopifyapp_user.cart.domain.repo.CartRepository
import kotlinx.coroutines.flow.MutableStateFlow

class CartRepositoryImpl(
    private  val draftRemoteSource:DraftOrderRemoteSource): CartRepository
{
    override suspend fun createNewDraftOrder(order: DraftOrderResponse): MutableStateFlow<DraftOrderAPIState> {
      return  draftRemoteSource.createNewDraftOrder(order)
    }

    override suspend fun removeDraftOrder(oriderId: String): MutableStateFlow<DraftOrderAPIState> {
        return draftRemoteSource.deleteDraftOrder(oriderId)
    }

    override suspend fun updateProductsInCart(
        oriderId: String,
        newDraftOrder: DraftOrderResponse
    ): MutableStateFlow<DraftOrderAPIState> {
     return draftRemoteSource.updateDraftOrder(oriderId,newDraftOrder)
    }

    override suspend fun getCartProducts(oriderId: String): MutableStateFlow<DraftOrderAPIState> {
        return draftRemoteSource.getDraftOrder(oriderId)
    }

}