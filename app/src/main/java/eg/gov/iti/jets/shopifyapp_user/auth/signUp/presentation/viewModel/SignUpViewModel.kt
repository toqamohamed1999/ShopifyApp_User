package eg.gov.iti.jets.shopifyapp_user.auth.signUp.presentation.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import eg.gov.iti.jets.shopifyapp_user.auth.signUp.data.model.SignupUser
import eg.gov.iti.jets.shopifyapp_user.auth.signUp.data.remote.AuthRepo
import eg.gov.iti.jets.shopifyapp_user.auth.data.remote.ResponseState
import eg.gov.iti.jets.shopifyapp_user.auth.data.repo.APIRepoImplementation
import eg.gov.iti.jets.shopifyapp_user.auth.domain.model.Customer
import eg.gov.iti.jets.shopifyapp_user.auth.domain.repo.ApiRepoInterface
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class SignUpViewModel(
    private val authRepository: AuthRepo,
    private val apiReoInterface: APIRepoImplementation
) : ViewModel() {
    private val _signUpResult = MutableStateFlow<ResponseState<String>>(ResponseState.Loading)
    val signUpResult: StateFlow<ResponseState<String>> = _signUpResult

    fun signUpUser(user: SignupUser) {
        viewModelScope.launch {
            _signUpResult.value = authRepository.signUpUser(user)
        }
    }

    fun createCustomerAccount(customer: Customer) {
        viewModelScope.launch { apiReoInterface.createCustomerAccount(customer) }
    }
}