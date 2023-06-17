package eg.gov.iti.jets.shopifyapp_user.profile.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import eg.gov.iti.jets.shopifyapp_user.profile.domain.repo.ProfileRepo

class ProfileFactoryViewModel (private val repo: ProfileRepo) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {

        return if (modelClass.isAssignableFrom(ProfileViewModel::class.java)) {
            ProfileViewModel(repo) as T
        } else {
            throw java.lang.IllegalArgumentException("ProductsViewModel class not found")
        }
    }
}