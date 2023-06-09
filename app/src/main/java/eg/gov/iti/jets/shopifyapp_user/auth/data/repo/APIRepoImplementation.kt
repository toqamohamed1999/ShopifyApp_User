package eg.gov.iti.jets.shopifyapp_user.auth.data.repo

import eg.gov.iti.jets.shopifyapp_user.auth.data.remote.AuthRemoteSourceImp
import eg.gov.iti.jets.shopifyapp_user.auth.data.remote.ResponseState
import eg.gov.iti.jets.shopifyapp_user.auth.domain.model.Customer
import eg.gov.iti.jets.shopifyapp_user.auth.domain.remote.AuthRemoteSourceInterface
import eg.gov.iti.jets.shopifyapp_user.auth.domain.repo.ApiRepoInterface
import kotlinx.coroutines.flow.MutableStateFlow

class APIRepoImplementation(
    private val authRemoteSource: AuthRemoteSourceInterface
) : ApiRepoInterface {
    override suspend fun createCustomerAccount(customer: Customer): MutableStateFlow<ResponseState<Customer>> {
        return authRemoteSource.createCustomerAccount(customer)
    }
}