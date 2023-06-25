package eg.gov.iti.jets.shopifyapp_user.products.domain.remote

import eg.gov.iti.jets.shopifyapp_user.base.model.AllProduct
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface ProductsBrandService {
    @Headers("Content-Type:application/json","X-Shopify-Access-Token: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")
    @GET("products.json")
    suspend fun getProductsOfBrand(@Query("vendor") vendor: String): AllProduct
}