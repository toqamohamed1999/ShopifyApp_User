package eg.gov.iti.jets.shopifyapp_user.cart.data.repo

import eg.gov.iti.jets.shopifyapp_user.cart.data.model.*
import eg.gov.iti.jets.shopifyapp_user.cart.data.remote.DraftOrderAPIState
import eg.gov.iti.jets.shopifyapp_user.cart.domain.remote.DraftOrderRemoteSource
import eg.gov.iti.jets.shopifyapp_user.cart.domain.remote.VariantsRemoteSource
import eg.gov.iti.jets.shopifyapp_user.cart.domain.repo.CartRepository
import kotlinx.coroutines.flow.MutableStateFlow

class CartRepositoryImpl(
    private  val draftRemoteSource:DraftOrderRemoteSource,private val variantsRemoteSource:VariantsRemoteSource): CartRepository
{
    override suspend fun createNewDraftOrder(order: DraftOrderResponse): MutableStateFlow<DraftOrderAPIState> {
      return  draftRemoteSource.createNewDraftOrder(order)
    }

    override suspend fun removeDraftOrder(oriderId: String): MutableStateFlow<DraftOrderAPIState> {
        return draftRemoteSource.deleteDraftOrder(oriderId)
    }

    override suspend fun updateProductsInCart(
        oriderId: String?,
        newDraftOrder: DraftOrderResponse
    ): MutableStateFlow<DraftOrderAPIState> {
     return draftRemoteSource.updateDraftOrder(oriderId,newDraftOrder)
    }

    override suspend fun getCartProducts(oriderId: String): MutableStateFlow<DraftOrderAPIState> {
        return draftRemoteSource.getDraftOrder(oriderId)
    }

    override suspend fun getVariantBYId(VariantId: Long): MutableStateFlow<VariantRoot?> {
       return variantsRemoteSource.getVariantBYId(VariantId)
    }

    override suspend fun updateProductQuantity(body: UpdateQuantityBody): MutableStateFlow<InventoryLevelResponse?> {
       return variantsRemoteSource.updateProductQuantity(body)
    }

}