package eg.gov.iti.jets.shopifyapp_user.auth.domain.remote

import eg.gov.iti.jets.shopifyapp_user.auth.data.remote.ResponseState
import eg.gov.iti.jets.shopifyapp_user.auth.domain.model.*
import kotlinx.coroutines.flow.MutableStateFlow
import retrofit2.http.Body
import retrofit2.http.Path
import retrofit2.http.Query

interface AuthRemoteSourceInterface {
    suspend fun createCustomerAccount(customer: SignupRequest): CustomerResponse
    suspend fun getCustomerByEmail(email: String): CustomersResponse
    suspend fun updateRemoteCustomer(
        customer_id: Long,
        customer: CustomerResponse
    ): CustomerResponse
}