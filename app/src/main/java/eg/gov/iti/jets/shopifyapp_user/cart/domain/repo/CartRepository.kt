package eg.gov.iti.jets.shopifyapp_user.cart.domain.repo

import eg.gov.iti.jets.shopifyapp_user.cart.data.remote.DraftOrderAiState
import eg.gov.iti.jets.shopifyapp_user.cart.domain.model.DraftOrderResponse
import kotlinx.coroutines.flow.MutableStateFlow

interface CartRepository {

    fun saveDraftOrderId(draftId:String)
    fun getDraftOrderId():String?
    suspend fun createNewDraftOrder(order:DraftOrderResponse):MutableStateFlow<DraftOrderAiState>
    suspend fun removeDraftOrder(oriderId:String):MutableStateFlow<DraftOrderAiState>
    suspend fun addProductToCart(product:Any):MutableStateFlow<DraftOrderAiState>
    suspend fun removeProductFromCart(product: Any):MutableStateFlow<DraftOrderAiState>
    suspend fun updateProductInCart(product: Any):MutableStateFlow<DraftOrderAiState>
    suspend fun getCartProducts():MutableStateFlow<DraftOrderAiState>

}