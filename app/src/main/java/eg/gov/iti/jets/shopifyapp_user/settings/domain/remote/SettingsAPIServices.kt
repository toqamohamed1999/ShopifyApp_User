package eg.gov.iti.jets.shopifyapp_user.settings.domain.remote

import eg.gov.iti.jets.shopifyapp_user.auth.domain.model.CustomerResponse
import eg.gov.iti.jets.shopifyapp_user.settings.domain.model.AddressBody
import eg.gov.iti.jets.shopifyapp_user.settings.domain.model.AdressesResponse
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


    @Headers("Content-Type:application/json","X-Shopify-Access-Token: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")
    @GET("customers/{userId}.json")
    suspend fun getUser(@Path(value="userId")userId:String):CustomerResponse



}