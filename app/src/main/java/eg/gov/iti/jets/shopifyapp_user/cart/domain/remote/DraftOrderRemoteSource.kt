package eg.gov.iti.jets.shopifyapp_user.cart.domain.remote

import eg.gov.iti.jets.shopifyapp_user.cart.data.remote.DraftOrderAiState
import eg.gov.iti.jets.shopifyapp_user.cart.domain.model.DraftOrderResponse
import kotlinx.coroutines.flow.MutableStateFlow

interface DraftOrderRemoteSource{
  suspend fun createNewDraftOrder(order: DraftOrderResponse): MutableStateFlow<DraftOrderAiState>
  suspend fun getDraftOrder(orderId:String):MutableStateFlow<DraftOrderAiState>
  suspend fun  deleteDraftOrder(orderId:String):MutableStateFlow<DraftOrderAiState>
  suspend fun  updateDraftOrder(orderId:String,order:DraftOrderResponse):MutableStateFlow<DraftOrderAiState>
 }