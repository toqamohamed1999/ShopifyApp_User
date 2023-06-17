package eg.gov.iti.jets.shopifyapp_user.settings.data.remote

import android.util.Log
import eg.gov.iti.jets.shopifyapp_user.settings.domain.model.*
import eg.gov.iti.jets.shopifyapp_user.settings.domain.remote.SettingsAPIServices
import eg.gov.iti.jets.shopifyapp_user.settings.domain.remote.SettingsRemoteSource
import kotlinx.coroutines.flow.MutableStateFlow

class SettingsRemoteSourceImpl(private val settingsAPIServices: SettingsAPIServices):SettingsRemoteSource {
    override suspend fun getAllAddressesForUser(userId: String): MutableStateFlow<AdressesResponse?> {
        return  try{
            MutableStateFlow(settingsAPIServices.getAllAddressesForUser(userId))
        }catch (exception:Exception){
            Log.e("",exception.localizedMessage.toString())
            MutableStateFlow(null)
        }
    }

    override suspend fun addNewAddressForUser(
        userId: String,
        address: AddressBody?
    ): MutableStateFlow<CustomerAddressResponse?> {
        return  try{
            MutableStateFlow(settingsAPIServices.addNewAddressForUser(userId,address))
        }catch (exception:Exception){
            MutableStateFlow(null)
        }
    }

    override suspend fun removeAddress(address_id: String, userId: String) {
          try{
           settingsAPIServices.removeAddress(address_id,userId)
        }catch (exception:Exception){
           Log.e("","No Response")
        }
    }

    override suspend fun getAllCurrencyCodesAvailable(): MutableStateFlow<CurrencysResponse?> {
        return  try{
            MutableStateFlow(settingsAPIServices.getAllCurrencyCodesAvailable())
        }catch (exception:Exception){
            MutableStateFlow(null)
        }
    }


}