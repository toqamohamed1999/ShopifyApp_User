package eg.gov.iti.jets.shopifyapp_user.payment.domain.remote

interface PaymentAPIServices {
    fun retrieveTransactionForOrder(id:String)
}