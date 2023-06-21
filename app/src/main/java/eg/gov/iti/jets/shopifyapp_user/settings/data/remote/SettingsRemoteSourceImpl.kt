package eg.gov.iti.jets.shopifyapp_user.settings.data.remote

import android.util.Log
import eg.gov.iti.jets.shopifyapp_user.base.remote.AppRetrofit
import eg.gov.iti.jets.shopifyapp_user.settings.domain.model.*
import eg.gov.iti.jets.shopifyapp_user.settings.domain.remote.CurrencyService
import eg.gov.iti.jets.shopifyapp_user.settings.domain.remote.SettingsAPIServices
import eg.gov.iti.jets.shopifyapp_user.settings.domain.remote.SettingsRemoteSource
import kotlinx.coroutines.flow.MutableStateFlow
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create

class SettingsRemoteSourceImpl():SettingsRemoteSource {
    private val settingsAPIServices: SettingsAPIServices = AppRetrofit.retrofit.create(SettingsAPIServices::class.java)
   private val retrofitBuilder: Retrofit = Retrofit.Builder().baseUrl("https://v6.exchangerate-api.com/v6/dd010a73be92b99acb505575/").addConverterFactory(GsonConverterFactory.create()).build()
  private  val service = retrofitBuilder.create(CurrencyService::class.java)
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

    override suspend fun getAllCurrencies(): MutableStateFlow<CurrenciesResponse?> {
        return  try{
           MutableStateFlow(service.getAllCurrencies())
        }catch(e:Exception){
            Log.e("","............................${e.localizedMessage}...................")
            MutableStateFlow(null)
        }
    }

    override suspend fun changeCurrency(
        toCode: String
    ): MutableStateFlow<ExchangerResponse?> {
        return  try{
            MutableStateFlow(service.changeCurrency(toCode))
        }catch(e:Exception){
            MutableStateFlow(null)
        }
    }

}