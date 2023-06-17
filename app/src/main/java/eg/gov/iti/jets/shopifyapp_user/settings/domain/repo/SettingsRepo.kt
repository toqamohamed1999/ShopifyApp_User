package eg.gov.iti.jets.shopifyapp_user.settings.domain.repo
import eg.gov.iti.jets.shopifyapp_user.settings.domain.model.*
import kotlinx.coroutines.flow.MutableStateFlow

interface SettingsRepo {

   suspend fun getAllAddressesForUser(userId:String): MutableStateFlow<AdressesResponse?>

   suspend fun addNewAddressForUser(userId:String, address: AddressBody?): MutableStateFlow<CustomerAddressResponse?>

   suspend fun removeAddress(address_id:String,userId:String)

   suspend  fun getAllCurrencyCodesAvailable(): MutableStateFlow<CurrencysResponse?>
}