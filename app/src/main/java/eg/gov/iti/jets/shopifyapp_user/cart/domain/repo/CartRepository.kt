package eg.gov.iti.jets.shopifyapp_user.cart.domain.repo

import eg.gov.iti.jets.shopifyapp_user.cart.data.remote.DraftOrderAiState
import eg.gov.iti.jets.shopifyapp_user.cart.domain.model.DraftOrderResponse
import eg.gov.iti.jets.shopifyapp_user.cart.domain.model.LineItem
import kotlinx.coroutines.flow.MutableStateFlow

interface CartRepository {

    fun saveDraftOrderId(draftId:String)
    fun getDraftOrderId():String?
    suspend fun createNewDraftOrder(order:DraftOrderResponse):MutableStateFlow<DraftOrderAiState>
    suspend fun removeDraftOrder(oriderId:String):MutableStateFlow<DraftOrderAiState>
    suspend fun addProductToCart(product:LineItem)
    suspend fun removeProductFromCart(product: LineItem)
    suspend fun updateProductInCart(product: LineItem)
    suspend fun getCartProducts():MutableStateFlow<DraftOrderAiState>

}