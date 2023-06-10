package eg.gov.iti.jets.shopifyapp_user.home.domain.remote

import eg.gov.iti.jets.shopifyapp_user.home.domain.model.BrandModel
import retrofit2.http.GET
import retrofit2.http.Headers

interface BrandAPIInterface {
    @Headers(
        "Content-Type:application/json",
        "X-Shopify-Access-Token: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"
    )
    @GET("smart_collections.json")
    suspend fun getAllBrands(): BrandModel
}