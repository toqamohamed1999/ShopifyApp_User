package eg.gov.iti.jets.shopifyapp_user.cart.data.repo

import eg.gov.iti.jets.shopifyapp_user.cart.data.local.CartSharedPrefsOperations
import eg.gov.iti.jets.shopifyapp_user.cart.data.remote.DraftOrderAiState
import eg.gov.iti.jets.shopifyapp_user.cart.domain.model.DraftOrderResponse
import eg.gov.iti.jets.shopifyapp_user.cart.domain.remote.DraftOrderRemoteSource
import eg.gov.iti.jets.shopifyapp_user.cart.domain.repo.CartRepository
import kotlinx.coroutines.flow.MutableStateFlow

class CartRepositoryImpl(
    private  val draftRemoteSource:DraftOrderRemoteSource,
    private val cartSharedPrefs:CartSharedPrefsOperations): CartRepository
{
    override fun saveDraftOrderId(draftId: String) {
        cartSharedPrefs.setCartDraftOrderId(draftId)
    }

    override fun getDraftOrderId(): String? {
        return cartSharedPrefs.getCartDraftOrderId()
    }

    override suspend fun createNewDraftOrder(order: DraftOrderResponse): MutableStateFlow<DraftOrderAiState> {
        return draftRemoteSource.createNewDraftOrder(order)
    }

    override suspend fun removeDraftOrder(oriderId: String): MutableStateFlow<DraftOrderAiState> {
        return draftRemoteSource.deleteDraftOrder(oriderId)
    }

    override suspend fun addProductToCart(product: Any): MutableStateFlow<DraftOrderAiState> {
        return  MutableStateFlow(DraftOrderAiState.Loading())
    }

    override suspend fun removeProductFromCart(product: Any): MutableStateFlow<DraftOrderAiState> {
        return  MutableStateFlow(DraftOrderAiState.Loading())
    }

    override suspend fun updateProductInCart(product: Any): MutableStateFlow<DraftOrderAiState> {
        return  MutableStateFlow(DraftOrderAiState.Loading())
    }

    override suspend fun getCartProducts(): MutableStateFlow<DraftOrderAiState> {
        val id = "1118695391513" //getDraftOrderId()
        return try{
            draftRemoteSource.getDraftOrder(id)}

        catch(error:java.lang.Exception) {

            MutableStateFlow(DraftOrderAiState.Error(error.message.toString()))
        }
    }
}