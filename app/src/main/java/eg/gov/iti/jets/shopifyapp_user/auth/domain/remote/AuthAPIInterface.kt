package eg.gov.iti.jets.shopifyapp_user.auth.domain.remote

import eg.gov.iti.jets.shopifyapp_user.auth.data.remote.ResponseState
import eg.gov.iti.jets.shopifyapp_user.auth.domain.model.*
import eg.gov.iti.jets.shopifyapp_user.base.model.FavDraftOrderResponse
import retrofit2.http.*

interface AuthAPIInterface {
    @Headers(
        "Content-Type:application/json",
        "X-Shopify-Access-Token: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"
    )
    @POST("customers.json")
    suspend fun createCustomerAccount(@Body customer: SignupRequest): CustomerResponse
    @Headers(
        "Content-Type:application/json",
        "X-Shopify-Access-Token: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"
    )
    @GET("customers/search.json")
    suspend fun getCustomerByEmail(@Query("email") email:String):CustomersResponse
    @Headers("Content-Type:application/json","X-Shopify-Access-Token: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")
    @PUT("customers/{customer_id}.json")
    suspend fun updateRemoteCustomer(@Path(value = "customer_id")customer_id:Long,
                                    @Body customer: CustomerResponse
    ): CustomerResponse
}