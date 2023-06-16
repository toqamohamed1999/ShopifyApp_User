package eg.gov.iti.jets.shopifyapp_user.cart.domain.repo

import eg.gov.iti.jets.shopifyapp_user.cart.data.remote.DraftOrderAPIState
import eg.gov.iti.jets.shopifyapp_user.cart.data.model.DraftOrderResponse
import eg.gov.iti.jets.shopifyapp_user.cart.data.model.LineItem
import kotlinx.coroutines.flow.MutableStateFlow

interface CartRepository {

    suspend fun createNewDraftOrder(order: DraftOrderResponse):MutableStateFlow<DraftOrderAPIState>
    suspend fun removeDraftOrder(oriderId:String):MutableStateFlow<DraftOrderAPIState>
    suspend fun updateProductsInCart(oriderId: String,newDraftOrder: DraftOrderResponse):MutableStateFlow<DraftOrderAPIState>
    suspend fun getCartProducts(oriderId: String):MutableStateFlow<DraftOrderAPIState>

}