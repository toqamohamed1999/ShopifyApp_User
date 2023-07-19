package eg.gov.iti.jets.shopifyapp_user.home.domain.remote

import eg.gov.iti.jets.shopifyapp_user.home.domain.model.BrandModel
import retrofit2.http.GET
import retrofit2.http.Headers

interface BrandAPIInterface {
    @Headers("Content-Type:application/json","X-Shopify-Access-Token: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")
    @GET("smart_collections.json")
    suspend fun getAllBrands(): BrandModel
}