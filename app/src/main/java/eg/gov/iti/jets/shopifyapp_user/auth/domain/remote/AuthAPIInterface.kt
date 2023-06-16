package eg.gov.iti.jets.shopifyapp_user.auth.domain.remote

import eg.gov.iti.jets.shopifyapp_user.auth.data.remote.ResponseState
import eg.gov.iti.jets.shopifyapp_user.auth.domain.model.Customer
import eg.gov.iti.jets.shopifyapp_user.auth.domain.model.CustomerResponse
import eg.gov.iti.jets.shopifyapp_user.auth.domain.model.SignupModel
import eg.gov.iti.jets.shopifyapp_user.auth.domain.model.SignupRequest
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

interface AuthAPIInterface {
    @Headers(
        "Content-Type:application/json",
        "X-Shopify-Access-Token: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"
    )
    @POST("customers.json")
    suspend fun createCustomerAccount(@Body customer: SignupRequest): CustomerResponse
}