package eg.gov.iti.jets.shopifyapp_user.auth.domain.repo

import eg.gov.iti.jets.shopifyapp_user.auth.data.remote.ResponseState
import eg.gov.iti.jets.shopifyapp_user.auth.domain.model.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow

interface ApiRepoInterface {
    suspend fun createCustomerAccount(customer: SignupRequest): Flow<CustomerResponse>
    suspend fun getCustomerByEmail(email: String): Flow<CustomersResponse>
    suspend fun updateRemoteCustomer(
        customer_id: Long,
        customer: CustomerResponse
    ): Flow<CustomerResponse>
}