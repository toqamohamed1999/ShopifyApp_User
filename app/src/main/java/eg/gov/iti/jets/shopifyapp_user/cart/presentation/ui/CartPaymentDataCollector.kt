package eg.gov.iti.jets.shopifyapp_user.cart.presentation.ui

interface CartPaymentDataCollector {
    fun getOrderPaymentMethod(paymentMethod:Int)//0 chash on delivery 1 google pay
    fun getOrderPaymentDetails(shippingAddress:String,phone:String,saveForEveryTime:Boolean)
}