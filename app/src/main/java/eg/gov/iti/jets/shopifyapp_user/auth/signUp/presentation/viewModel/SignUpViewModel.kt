package eg.gov.iti.jets.shopifyapp_user.auth.signUp.presentation.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import eg.gov.iti.jets.shopifyapp_user.auth.signUp.data.model.SignupUser
import eg.gov.iti.jets.shopifyapp_user.auth.signUp.data.model.remote.AuthRepo
import eg.gov.iti.jets.shopifyapp_user.auth.data.remote.Result
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class SignUpViewModel(private val authRepository: AuthRepo) : ViewModel() {
    private val _signUpResult = MutableStateFlow<Result<String>>(Result.Loading)
    val signUpResult: StateFlow<Result<String>> = _signUpResult

    fun signUpUser(user: SignupUser) {
        viewModelScope.launch {
            _signUpResult.value = authRepository.signUpUser(user)
        }
    }
}