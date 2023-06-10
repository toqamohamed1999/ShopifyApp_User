package eg.gov.iti.jets.shopifyapp_user.auth.signUp.presentation.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import eg.gov.iti.jets.shopifyapp_user.auth.domain.repo.FirebaseRepoInterface
import eg.gov.iti.jets.shopifyapp_user.auth.signUp.data.model.remote.AuthRepo

class SignUpViewModelFactory (private val _irpo: AuthRepo): ViewModelProvider.Factory{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(SignUpViewModel::class.java))
        {
            SignUpViewModel(_irpo) as T
        }else{
            throw IllegalAccessException("View Model Class not found")
        }
    }
}