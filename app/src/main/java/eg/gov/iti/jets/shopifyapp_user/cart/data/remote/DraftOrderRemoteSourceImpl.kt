package eg.gov.iti.jets.shopifyapp_user.cart.data.remote

import eg.gov.iti.jets.shopifyapp_user.cart.domain.model.DraftOrderResponse
import eg.gov.iti.jets.shopifyapp_user.cart.domain.remote.DraftOrderNetworkServices
import eg.gov.iti.jets.shopifyapp_user.cart.domain.remote.DraftOrderRemoteSource
import kotlinx.coroutines.flow.MutableStateFlow

class DraftOrderRemoteSourceImpl(private  val retrofitServices:DraftOrderNetworkServices) :DraftOrderRemoteSource {
    override suspend fun createNewDraftOrder(order: DraftOrderResponse): MutableStateFlow<DraftOrderAiState> {
       return MutableStateFlow( try {
               DraftOrderAiState.Success(retrofitServices.createNewDraftOrder(order))
           }catch( error:Error){
               DraftOrderAiState.Error(error.message.toString())
           })
    }

    override suspend fun getDraftOrder(orderId: String): MutableStateFlow<DraftOrderAiState> {
       return  MutableStateFlow(
           try{
               DraftOrderAiState.Success(retrofitServices.getDraftOrder(orderId))
           }catch (error:Error){
               DraftOrderAiState.Error(error.message.toString())
           }
       )
    }

    override suspend fun deleteDraftOrder(orderId: String): MutableStateFlow<DraftOrderAiState> {
        return  MutableStateFlow(
            try{
                retrofitServices.deleteDraftOrder(orderId)
                DraftOrderAiState.Success(null)
            }catch (error:Error){
                DraftOrderAiState.Error(error.message.toString())
            }
        )
    }

    override suspend fun updateDraftOrder(
        orderId: String,
        order: DraftOrderResponse
    ): MutableStateFlow<DraftOrderAiState> {
        return MutableStateFlow(
            try{
                DraftOrderAiState.Success(retrofitServices.updateDraftOrder(orderId,order))
            }catch (error:Error){
                DraftOrderAiState.Error(error.message.toString())
            }
        )
    }

}