package eg.gov.iti.jets.shopifyapp_user.settings.data.remote

import com.google.gson.Gson
import eg.gov.iti.jets.shopifyapp_user.settings.data.local.UserSettings
import eg.gov.iti.jets.shopifyapp_user.settings.domain.model.CurrencyExchangeResponse
import kotlinx.coroutines.flow.MutableStateFlow
import okhttp3.OkHttpClient
import okhttp3.Request

object CurrencyExchanger {
    suspend fun convertCurrency(
        amount: Double
    ): MutableStateFlow<CurrencyExchangeResponse?> {
        return  try{
            val client =  OkHttpClient().newBuilder().build()

            val request =  Request.Builder()
                .url("https://api.apilayer.com/exchangerates_data/convert?to=${UserSettings.currencyCode}&from=EGP&amount=10.0")
                .addHeader("apikey", "219PFGMCPp2cWoUIaY6xOI81MR6SP0o5")
                .method("GET",null).build()
            val response = client.newCall(request).execute()
            if(response.code() == 200 ||response.code() ==201)
                MutableStateFlow(
                    Gson().fromJson(response.body().toString(),
                        CurrencyExchangeResponse::class.java))
            else{
                MutableStateFlow(null)
            }
        }catch (exception:Exception){
            MutableStateFlow(null)
        }
    }
}