package eg.gov.iti.jets.shopifyapp_user.payment.domain.remote

import eg.gov.iti.jets.shopifyapp_user.base.model.orders.Order
import eg.gov.iti.jets.shopifyapp_user.home.domain.model.addsmodels.PriceRule
import eg.gov.iti.jets.shopifyapp_user.payment.domain.model.TransactionBody
import eg.gov.iti.jets.shopifyapp_user.payment.domain.model.TransactionResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface PaymentAPIServices {
    @GET("price_rules/{price_rule_id}.json")
   suspend fun getSinglePriceRule(@Path(value="price_rule_id")price_rule_id:String):PriceRule

    @POST("orders.json")
    suspend fun postOrder(@Body order: Order?): Order
}