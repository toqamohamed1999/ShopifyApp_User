package eg.gov.iti.jets.shopifyapp_user.cart.domain.remote

import eg.gov.iti.jets.shopifyapp_user.cart.data.remote.DraftOrderAPIState
import eg.gov.iti.jets.shopifyapp_user.cart.data.model.DraftOrderResponse
import kotlinx.coroutines.flow.MutableStateFlow

interface DraftOrderRemoteSource{
  suspend fun createNewDraftOrder(order: DraftOrderResponse): MutableStateFlow<DraftOrderAPIState>
  suspend fun getDraftOrder(orderId:String):MutableStateFlow<DraftOrderAPIState>
  suspend fun  deleteDraftOrder(orderId:String):MutableStateFlow<DraftOrderAPIState>
  suspend fun  updateDraftOrder(orderId: String?, order: DraftOrderResponse):MutableStateFlow<DraftOrderAPIState>
 }