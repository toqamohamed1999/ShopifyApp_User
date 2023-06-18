package eg.gov.iti.jets.shopifyapp_user.settings.presentation.viewmodel

 import android.util.Log
 import androidx.lifecycle.ViewModel
 import androidx.lifecycle.viewModelScope
 import eg.gov.iti.jets.shopifyapp_user.settings.data.local.UserSettings
 import eg.gov.iti.jets.shopifyapp_user.settings.domain.model.AddressBody
 import eg.gov.iti.jets.shopifyapp_user.settings.domain.model.AdressesResponse
 import eg.gov.iti.jets.shopifyapp_user.settings.domain.repo.SettingsRepo
 import kotlinx.coroutines.flow.MutableStateFlow
 import kotlinx.coroutines.flow.StateFlow
 import kotlinx.coroutines.launch

class SettingsViewModel(private val settingsRepo: SettingsRepo):ViewModel() {

    private val _addresses: MutableStateFlow<AdressesResponse?> = MutableStateFlow(null)
    private val _currencies: MutableStateFlow<List<List<String>>?> = MutableStateFlow(null)
     val addresses: StateFlow<AdressesResponse?> = _addresses
     val currencies:StateFlow<List<List<String>>?> = _currencies
     fun getAllAddressesForUser(userId:String){
        viewModelScope.launch {
            settingsRepo.getAllAddressesForUser(userId).collect{
                    _addresses.value = it
            }
        }
     }

     fun addNewAddressForUser(userId:String, address: AddressBody?){
         viewModelScope.launch {
             settingsRepo.addNewAddressForUser(userId,address).collect{
                 if(it!=null)
                 {
                     getAllAddressesForUser(userId)
                 }
             }
         }
     }

     fun removeAddress(address_id:String,userId:String){
         viewModelScope.launch {
             launch {
                 settingsRepo.removeAddress(address_id, userId)
             }.join()
             launch {
                 getAllAddressesForUser(userId)
             }
         }
     }

      fun getAllCurrencyCodes(){
          viewModelScope.launch {
              settingsRepo.getAllCurrencies().collect{
                  _currencies.value = it?.supported_codes
              }
          }
      }
    fun changeCurrency(fromCode:String,toCode:String){
        viewModelScope.launch {
            settingsRepo.changeCurrency(fromCode,toCode).collect{
                if(it!=null)
                {
                    UserSettings.currentCurrencyValue = it.conversion_rate
                    UserSettings.saveSettings()
                }
            }
        }
    }

}
