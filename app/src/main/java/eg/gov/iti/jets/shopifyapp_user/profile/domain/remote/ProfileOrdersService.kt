package eg.gov.iti.jets.shopifyapp_user.profile.domain.remote

import eg.gov.iti.jets.shopifyapp_user.base.model.orders.OrderDetails
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path

interface ProfileOrdersService {
    @Headers(
        "Content-Type:application/json",
        "X-Shopify-Access-Token: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"
    )
    @GET("customers/{id}/orders.json")
    suspend fun getOrdersOfUser(@Path("id") id: Long): OrderDetails
}