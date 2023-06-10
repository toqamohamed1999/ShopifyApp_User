package eg.gov.iti.jets.shopifyapp_user.settings.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import eg.gov.iti.jets.shopifyapp_user.settings.domain.repo.SettingRepo

class SettingViewModel(private val repo:SettingRepo):ViewModel() {

   private val _userSettings:MutableLiveData<Map<String,String>> = MutableLiveData()
    val userSettings:LiveData<Map<String,String>> = _userSettings
    fun changeCurrency(currencyCode:String){
     repo.changeCurrency(currencyCode)
    }
    fun changePhoneNumber(newPhoneNumber:String){
        repo.changePhoneNumber(newPhoneNumber)
    }
    fun getDefaultSettings(){
        _userSettings.value = repo.getDefaultSettings()
    }
    fun changeAddress(newShippingAddress:String){
        repo.changeAddress(newShippingAddress)
    }
}