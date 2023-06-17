package eg.gov.iti.jets.shopifyapp_user.auth.domain.remote

import eg.gov.iti.jets.shopifyapp_user.auth.data.remote.ResponseState
import eg.gov.iti.jets.shopifyapp_user.auth.domain.model.Customer
import eg.gov.iti.jets.shopifyapp_user.auth.domain.model.CustomerResponse
import eg.gov.iti.jets.shopifyapp_user.auth.domain.model.SignupModel
import eg.gov.iti.jets.shopifyapp_user.auth.domain.model.SignupRequest
import kotlinx.coroutines.flow.MutableStateFlow

interface AuthRemoteSourceInterface {
    suspend fun createCustomerAccount(customer: SignupRequest): CustomerResponse
}