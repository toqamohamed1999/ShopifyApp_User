package eg.gov.iti.jets.shopifyapp_user.base.remote

import eg.gov.iti.jets.shopifyapp_user.util.Constants

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object AppRetrofit {
    val retrofit =  Retrofit.Builder()
    .baseUrl(Constants.BASE_URL)
    .addConverterFactory(GsonConverterFactory.create()).build()
}
