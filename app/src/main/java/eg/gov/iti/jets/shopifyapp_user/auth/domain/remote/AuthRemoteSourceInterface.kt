package eg.gov.iti.jets.shopifyapp_user.auth.domain.remote

import eg.gov.iti.jets.shopifyapp_user.auth.data.remote.ResponseState
import eg.gov.iti.jets.shopifyapp_user.auth.domain.model.Customer
import kotlinx.coroutines.flow.MutableStateFlow

interface AuthRemoteSourceInterface {
    suspend fun createCustomerAccount(customer: Customer):MutableStateFlow<ResponseState<Customer>>
}