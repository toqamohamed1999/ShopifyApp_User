package eg.gov.iti.jets.shopifyapp_user.auth.login.presentation.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import eg.gov.iti.jets.shopifyapp_user.auth.data.remote.ResponseState
import eg.gov.iti.jets.shopifyapp_user.auth.data.repo.APIRepoImplementation
import eg.gov.iti.jets.shopifyapp_user.auth.domain.model.Customer
import eg.gov.iti.jets.shopifyapp_user.auth.domain.model.CustomerResponse
import eg.gov.iti.jets.shopifyapp_user.auth.domain.model.CustomersResponse
import eg.gov.iti.jets.shopifyapp_user.auth.domain.repo.ApiRepoInterface
import eg.gov.iti.jets.shopifyapp_user.auth.signUp.data.remote.AuthRepo
import eg.gov.iti.jets.shopifyapp_user.base.domain.data.repo.FavDraftOrderRepoImpl
import eg.gov.iti.jets.shopifyapp_user.base.domain.repo.FavDraftOrderRepoInterface
import eg.gov.iti.jets.shopifyapp_user.base.domain.repo.FavOpRepoInterface
import eg.gov.iti.jets.shopifyapp_user.base.model.FavDraftOrderResponse
import eg.gov.iti.jets.shopifyapp_user.base.model.FavRoomPojo
import eg.gov.iti.jets.shopifyapp_user.base.repo.FavOpRepoImpl
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class LoginViewModel(
    private val apiReoInterface: ApiRepoInterface = APIRepoImplementation(),
    private val favRemoteRepo: FavDraftOrderRepoInterface = FavDraftOrderRepoImpl(),
    private val firebaseRepo: AuthRepo= AuthRepo(),
    private val favRepo: FavOpRepoInterface = FavOpRepoImpl()
) : ViewModel() {
    private val _returnCustomer =
        MutableStateFlow<ResponseState<CustomersResponse>>(ResponseState.Loading)
    val returnCustomer: StateFlow<ResponseState<CustomersResponse>> = _returnCustomer

    fun getCustomerByEmail(email: String) {
        viewModelScope.launch {
            try {
                apiReoInterface.getCustomerByEmail(email).collect {
                    _returnCustomer.value = ResponseState.Success(it)
                }
            } catch (e: java.lang.Exception) {
                _returnCustomer.value = ResponseState.Error(e)
            }
        }
    }

    private val _favProducts: MutableStateFlow<ResponseState<FavDraftOrderResponse>> =
        MutableStateFlow(ResponseState.Loading)
    var favProducts: StateFlow<ResponseState<FavDraftOrderResponse>> = _favProducts


    fun getFavRemoteProducts(draftOrderId: Long) {
        viewModelScope.launch {
            favRemoteRepo.getFavDraftOrder(draftOrderId)
                .catch { error -> _favProducts.value = ResponseState.Error(error as Exception) }
                .collectLatest { data ->
                    _favProducts.value = ResponseState.Success(data)
                }
        }
    }

    fun insertFavProduct(favRoomPojo: FavRoomPojo) {
        viewModelScope.launch {
            favRepo.insertFavProduct(favRoomPojo)
        }
    }

    fun resetFlow() {
        _returnCustomer.value = ResponseState.Loading
    }


    private val _isVerified =
        MutableStateFlow<ResponseState<Boolean>>(ResponseState.Loading)
    val isVerified: StateFlow<ResponseState<Boolean>> = _isVerified
    fun resetVerificationFlow() {
        _isVerified.value = ResponseState.Loading
    }
    fun checkVerification(email: String, pass: String) {
        viewModelScope.launch {
            try {
                _isVerified.value = firebaseRepo.checkVerification(email, pass)
            } catch (e: java.lang.Exception) {
                _isVerified.value = ResponseState.Error(e)
            }
        }
    }
    fun updateCustomer(customer_id:Long,customer:Customer){
        viewModelScope.launch{
            apiReoInterface.updateRemoteCustomer(customer_id, CustomerResponse( customer))
        }
    }
}