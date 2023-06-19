package eg.gov.iti.jets.shopifyapp_user.profile.data.model

import eg.gov.iti.jets.shopifyapp_user.base.model.orders.Order

sealed class OrderState{
    class Success(val orderList:List<Order>): OrderState()
    class Loading(val message:String = "loading"): OrderState()
    class Error(val error:String="Error"): OrderState()
}
