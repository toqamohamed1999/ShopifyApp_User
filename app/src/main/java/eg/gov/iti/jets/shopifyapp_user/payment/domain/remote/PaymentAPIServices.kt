package eg.gov.iti.jets.shopifyapp_user.payment.domain.remote

import eg.gov.iti.jets.shopifyapp_user.base.model.orders.Order
import eg.gov.iti.jets.shopifyapp_user.home.domain.model.addsmodels.PriceRule
import eg.gov.iti.jets.shopifyapp_user.payment.domain.model.TransactionBody
import eg.gov.iti.jets.shopifyapp_user.payment.domain.model.TransactionResponse
import retrofit2.http.*

interface PaymentAPIServices {
    @Headers("Content-Type:application/json","X-Shopify-Access-Token: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")

    @GET("price_rules/{price_rule_id}.json")
   suspend fun getSinglePriceRule(@Path(value="price_rule_id")price_rule_id:String):PriceRule

    @Headers("Content-Type:application/json","X-Shopify-Access-Token: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")

    @POST("orders.json")
    suspend fun postOrder(@Body order: Order.OrderBody?): Order.OrderBody
}