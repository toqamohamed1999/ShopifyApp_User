package eg.gov.iti.jets.shopifyapp_user.auth.data.remote

import eg.gov.iti.jets.shopifyapp_user.auth.domain.model.Customer
import eg.gov.iti.jets.shopifyapp_user.auth.domain.remote.AuthAPIInterface
import eg.gov.iti.jets.shopifyapp_user.auth.domain.remote.AuthRemoteSourceInterface
import eg.gov.iti.jets.shopifyapp_user.base.remote.AppRetrofit
import kotlinx.coroutines.flow.MutableStateFlow
import java.lang.Exception

class AuthRemoteSourceImp:AuthRemoteSourceInterface {
    private val customerService: AuthAPIInterface by lazy {
        AppRetrofit.retrofit.create(AuthAPIInterface::class.java)
    }

    override suspend fun createCustomerAccount(customer: Customer): MutableStateFlow<ResponseState<Customer>> {
        return MutableStateFlow( try {
            ResponseState.Success(customerService.createCustomerAccount(customer))
        }catch( error:Error){
            ResponseState.Error(Exception( error.message))
        })
    }
}