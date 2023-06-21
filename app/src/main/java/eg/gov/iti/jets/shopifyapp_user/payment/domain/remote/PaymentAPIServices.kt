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