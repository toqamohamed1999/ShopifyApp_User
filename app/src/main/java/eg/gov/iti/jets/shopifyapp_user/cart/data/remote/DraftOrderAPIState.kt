package eg.gov.iti.jets.shopifyapp_user.cart.data.remote

import eg.gov.iti.jets.shopifyapp_user.cart.data.model.DraftOrderResponse

sealed class DraftOrderAPIState{
    class Success(val order: DraftOrderResponse?):DraftOrderAPIState()
    class Loading(val message:String = "Loading") : DraftOrderAPIState()
    class Error(val errorMessage:String = "Error"):DraftOrderAPIState()
}
