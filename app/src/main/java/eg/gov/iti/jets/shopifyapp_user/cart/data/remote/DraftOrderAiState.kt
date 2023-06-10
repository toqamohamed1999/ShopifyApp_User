package eg.gov.iti.jets.shopifyapp_user.cart.data.remote

import eg.gov.iti.jets.shopifyapp_user.cart.domain.model.DraftOrderResponse

sealed class DraftOrderAiState{
    class Success(val order:DraftOrderResponse?):DraftOrderAiState()
    class Loading(val message:String = "Loading") : DraftOrderAiState()
    class Error(val errorMessage:String = "Error"):DraftOrderAiState()
}
