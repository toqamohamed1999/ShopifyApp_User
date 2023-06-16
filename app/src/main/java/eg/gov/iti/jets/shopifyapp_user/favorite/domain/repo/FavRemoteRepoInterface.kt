package eg.gov.iti.jets.shopifyapp_user.favorite.domain.repo

import eg.gov.iti.jets.shopifyapp_user.cart.data.remote.DraftOrderAPIState
import eg.gov.iti.jets.shopifyapp_user.cart.domain.model.DraftOrderResponse
import kotlinx.coroutines.flow.MutableStateFlow

interface FavRemoteRepoInterface {
    suspend fun createNewDraftOrder(order: DraftOrderResponse): MutableStateFlow<DraftOrderAPIState>
    suspend fun removeDraftOrder(orderId:String): MutableStateFlow<DraftOrderAPIState>
    suspend fun updateProductsInFav(orderId: String,newDraftOrder: DraftOrderResponse): MutableStateFlow<DraftOrderAPIState>
    suspend fun getFavProducts(orderId: String): MutableStateFlow<DraftOrderAPIState>
}