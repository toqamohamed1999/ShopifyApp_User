package eg.gov.iti.jets.shopifyapp_user.auth.data.repo

import eg.gov.iti.jets.shopifyapp_user.auth.data.remote.AuthRemoteSourceImp
import eg.gov.iti.jets.shopifyapp_user.auth.data.remote.ResponseState
import eg.gov.iti.jets.shopifyapp_user.auth.domain.model.Customer
import eg.gov.iti.jets.shopifyapp_user.auth.domain.model.CustomerResponse
import eg.gov.iti.jets.shopifyapp_user.auth.domain.model.SignupModel
import eg.gov.iti.jets.shopifyapp_user.auth.domain.model.SignupRequest
import eg.gov.iti.jets.shopifyapp_user.auth.domain.remote.AuthRemoteSourceInterface
import eg.gov.iti.jets.shopifyapp_user.auth.domain.repo.ApiRepoInterface
import eg.gov.iti.jets.shopifyapp_user.home.data.repo.BrandRepoImp
import eg.gov.iti.jets.shopifyapp_user.home.domain.remote.BrandRemoteSourceInterface
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flowOf

class APIRepoImplementation(
    private val authRemoteSource: AuthRemoteSourceInterface
) : ApiRepoInterface {
    companion object {
        private var instance: APIRepoImplementation? = null
        fun getInstance(
            remoteSource: AuthRemoteSourceInterface
        ): APIRepoImplementation? {
            return instance ?: synchronized(this) {
                instance = APIRepoImplementation(remoteSource)
                instance
            }
        }
    }

    override suspend fun createCustomerAccount(customer: SignupRequest): Flow<CustomerResponse>{
        return flowOf( authRemoteSource.createCustomerAccount(customer))
    }
}