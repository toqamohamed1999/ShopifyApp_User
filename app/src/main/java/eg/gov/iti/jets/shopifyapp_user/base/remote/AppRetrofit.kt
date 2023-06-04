package eg.gov.iti.jets.shopifyapp_user.base.remote

import eg.gov.iti.jets.shopifyapp_user.util.BASE_URL

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object AppRetrofit {
    val retrofit =  Retrofit.Builder()
    .baseUrl(BASE_URL)
    .addConverterFactory(GsonConverterFactory.create()).build()
}
