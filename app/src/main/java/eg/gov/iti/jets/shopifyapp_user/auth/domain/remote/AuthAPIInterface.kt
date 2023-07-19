package eg.gov.iti.jets.shopifyapp_user.auth.domain.remote

import eg.gov.iti.jets.shopifyapp_user.auth.data.remote.ResponseState
import eg.gov.iti.jets.shopifyapp_user.auth.domain.model.*
import eg.gov.iti.jets.shopifyapp_user.base.model.FavDraftOrderResponse
import retrofit2.http.*

interface AuthAPIInterface {
    @Headers("Content-Type:application/json","X-Shopify-Access-Token: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")
    @POST("customers.json")
    suspend fun createCustomerAccount(@Body customer: SignupRequest): CustomerResponse
    @Headers("Content-Type:application/json","X-Shopify-Access-Token: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")
    @GET("customers/search.json")
    suspend fun getCustomerByEmail(@Query("email") email:String):CustomersResponse
    @Headers("Content-Type:application/json","X-Shopify-Access-Token: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")
    @PUT("customers/{customer_id}.json")
    suspend fun updateRemoteCustomer(@Path(value = "customer_id")customer_id:Long,
                                    @Body customer: CustomerResponse
    ): CustomerResponse
}