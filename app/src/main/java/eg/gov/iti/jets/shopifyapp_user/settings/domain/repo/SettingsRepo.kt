package eg.gov.iti.jets.shopifyapp_user.settings.domain.repo
import eg.gov.iti.jets.shopifyapp_user.auth.domain.model.CustomerResponse
import eg.gov.iti.jets.shopifyapp_user.auth.domain.model.CustomersResponse
import eg.gov.iti.jets.shopifyapp_user.settings.domain.model.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow

interface SettingsRepo {

   suspend fun getAllAddressesForUser(userId:String): MutableStateFlow<AdressesResponse?>

   suspend fun addNewAddressForUser(userId:String, address: AddressBody?): MutableStateFlow<CustomerAddressResponse?>

   suspend fun removeAddress(address_id:String,userId:String)
   suspend fun getAllCurrencies(): MutableStateFlow<CurrenciesResponse?>
   suspend fun changeCurrency(toCode:String):MutableStateFlow<ExchangerResponse?>
    suspend fun getCustomerById(id: String): MutableStateFlow<CustomerResponse?>

    suspend fun updateRemoteCustomer(
      customer_id: Long,
      customer: CustomerResponse
   ): Flow<CustomerResponse?>
}