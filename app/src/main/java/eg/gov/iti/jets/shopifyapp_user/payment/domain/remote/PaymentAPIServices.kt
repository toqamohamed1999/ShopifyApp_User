package eg.gov.iti.jets.shopifyapp_user.payment.domain.remote

import eg.gov.iti.jets.shopifyapp_user.payment.domain.model.TransactionBody
import eg.gov.iti.jets.shopifyapp_user.payment.domain.model.TransactionResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface PaymentAPIServices {

    @GET("orders/{orderId}/transactions.json")
    fun retrieveTransactionForOrder(@Path(value="orderId")orderId:String):TransactionResponse
    @POST("orders/{orderId}/transactions.json")
    fun captureTransactionForOrder(@Path(value="orderId")orderId:String,@Body tansactionBody:TransactionBody):TransactionResponse



}