package eg.gov.iti.jets.shopifyapp_user.settings.domain.remote

import eg.gov.iti.jets.shopifyapp_user.auth.domain.model.CustomerResponse
import eg.gov.iti.jets.shopifyapp_user.settings.domain.model.*
import kotlinx.coroutines.flow.MutableStateFlow
import retrofit2.http.Path

interface SettingsRemoteSource {
    suspend fun getAllAddressesForUser(userId:String): MutableStateFlow<AdressesResponse?>

   suspend fun addNewAddressForUser(userId:String, address: AddressBody?): MutableStateFlow<CustomerAddressResponse?>

   suspend fun removeAddress(address_id:String,userId:String)
suspend fun getAllCurrencies(): MutableStateFlow<CurrenciesResponse?>
suspend fun changeCurrency(toCode:String):MutableStateFlow<ExchangerResponse?>
    suspend fun getUser(userId:String): MutableStateFlow<CustomerResponse?>
}