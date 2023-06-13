package eg.gov.iti.jets.shopifyapp_user.cart.data.remote

import eg.gov.iti.jets.shopifyapp_user.cart.domain.model.DraftOrderResponse
import eg.gov.iti.jets.shopifyapp_user.cart.domain.remote.DraftOrderNetworkServices
import eg.gov.iti.jets.shopifyapp_user.cart.domain.remote.DraftOrderRemoteSource
import kotlinx.coroutines.flow.MutableStateFlow

class DraftOrderRemoteSourceImpl(private  val retrofitServices:DraftOrderNetworkServices) :DraftOrderRemoteSource {
    override suspend fun createNewDraftOrder(order: DraftOrderResponse): MutableStateFlow<DraftOrderAPIState> {
       return MutableStateFlow( try {
               DraftOrderAPIState.Success(retrofitServices.createNewDraftOrder(order))
           }catch( error:java.lang.Exception){
               DraftOrderAPIState.Error(error.message.toString())
           })
    }

    override suspend fun getDraftOrder(orderId: String): MutableStateFlow<DraftOrderAPIState> {
       return  MutableStateFlow(
           try{
               DraftOrderAPIState.Success(retrofitServices.getDraftOrder(orderId))
           }catch (error:java.lang.Exception){
               DraftOrderAPIState.Error(error.message.toString())
           }
       )
    }

    override suspend fun deleteDraftOrder(orderId: String): MutableStateFlow<DraftOrderAPIState> {
        return  MutableStateFlow(
            try{
                retrofitServices.deleteDraftOrder(orderId)
                DraftOrderAPIState.Success(null)
            }catch (error:java.lang.Exception){
                DraftOrderAPIState.Error(error.message.toString())
            }
        )
    }

    override suspend fun updateDraftOrder(
        orderId: String,
        order: DraftOrderResponse
    ): MutableStateFlow<DraftOrderAPIState> {
        return MutableStateFlow(
            try{
                DraftOrderAPIState.Success(retrofitServices.updateDraftOrder(orderId,order))
            }catch (error:java.lang.Exception){
                DraftOrderAPIState.Error(error.message.toString())
            }
        )
    }

}