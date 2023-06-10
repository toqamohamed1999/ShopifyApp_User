package eg.gov.iti.jets.shopifyapp_user.settings.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import eg.gov.iti.jets.shopifyapp_user.settings.domain.repo.SettingRepo

class SettingViewModelFactory(private val repo:SettingRepo):ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return  if(modelClass.isAssignableFrom(SettingViewModel::class.java)){
            SettingViewModel(repo) as T
        }else{
           throw IllegalArgumentException("")
        }
    }
}