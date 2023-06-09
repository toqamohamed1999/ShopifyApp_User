package eg.gov.iti.jets.shopifyapp_user.auth.domain.repo

import eg.gov.iti.jets.shopifyapp_user.auth.data.remote.ResponseState
import eg.gov.iti.jets.shopifyapp_user.auth.domain.model.Customer
import kotlinx.coroutines.flow.MutableStateFlow

interface ApiRepoInterface {
    suspend fun createCustomerAccount(customer: Customer): MutableStateFlow<ResponseState<Customer>>
}