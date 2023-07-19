package eg.gov.iti.jets.shopifyapp_user.profile.domain.remote

import eg.gov.iti.jets.shopifyapp_user.base.model.orders.OrderDetails
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path

interface ProfileOrdersService {
    @Headers("Content-Type:application/json","X-Shopify-Access-Token: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")
    @GET("customers/{id}/orders.json")
    suspend fun getOrdersOfUser(@Path("id") id: Long): OrderDetails
}