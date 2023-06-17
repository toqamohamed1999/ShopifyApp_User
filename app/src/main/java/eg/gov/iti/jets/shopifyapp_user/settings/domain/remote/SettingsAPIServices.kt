package eg.gov.iti.jets.shopifyapp_user.settings.domain.remote

import eg.gov.iti.jets.shopifyapp_user.settings.domain.model.AddressBody
import eg.gov.iti.jets.shopifyapp_user.settings.domain.model.AdressesResponse
import eg.gov.iti.jets.shopifyapp_user.settings.domain.model.CurrencysResponse
import eg.gov.iti.jets.shopifyapp_user.settings.domain.model.CustomerAddressResponse
import retrofit2.http.*

interface SettingsAPIServices {

    //Addresses
    @Headers("Content-Type:application/json","X-Shopify-Access-Token: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")
    @GET("customers/{userId}/addresses.json")
   suspend fun getAllAddressesForUser(@Path(value="userId")userId:String):AdressesResponse

   @Headers("Content-Type:application/json","X-Shopify-Access-Token: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")
    @POST("customers/{userId}/addresses.json")
   suspend fun addNewAddressForUser(@Path(value="userId") userId:String, @Body address: AddressBody?):CustomerAddressResponse

   @Headers("Content-Type:application/json","X-Shopify-Access-Token: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")
    @DELETE("customers/{userId}/addresses/{address_id}.json")
   suspend fun removeAddress(@Path(value="address_id")address_id:String,@Path(value="userId")userId:String)

    // Currency
    @Headers("Content-Type:application/json","X-Shopify-Access-Token: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")
    @GET("currencies.json")
   suspend fun getAllCurrencyCodesAvailable():CurrencysResponse

}