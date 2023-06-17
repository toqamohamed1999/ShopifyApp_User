package eg.gov.iti.jets.shopifyapp_user.settings.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import eg.gov.iti.jets.shopifyapp_user.settings.domain.repo.SettingsRepo

class SettingViewModelFactory(private val settingsRepo: SettingsRepo):ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return if(modelClass.isAssignableFrom(SettingsViewModel::class.java))
        {
            SettingsViewModel(settingsRepo) as T
        }else {
            throw  java.lang.IllegalArgumentException("No ViewModel Matches")
        }
    }
}