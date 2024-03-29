package eg.gov.iti.jets.shopifyapp_user.settings.domain.remote

import eg.gov.iti.jets.shopifyapp_user.settings.domain.model.CurrenciesResponse
import eg.gov.iti.jets.shopifyapp_user.settings.domain.model.ExchangerResponse
import retrofit2.http.GET
import retrofit2.http.Path

interface CurrencyService {
    @GET("codes")
   suspend fun getAllCurrencies():CurrenciesResponse
    @GET("pair/EGP/{toCode}")
   suspend fun changeCurrency(@Path (value = "toCode")toCode:String): ExchangerResponse
}