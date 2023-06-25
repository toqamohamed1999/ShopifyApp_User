package eg.gov.iti.jets.shopifyapp_user.productdetails.domain.remote

import eg.gov.iti.jets.shopifyapp_user.base.model.AllProduct
import eg.gov.iti.jets.shopifyapp_user.productdetails.data.model.SingleProductResponse
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query

interface ProductService {
    @Headers("Content-Type:application/json","X-Shopify-Access-Token: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")
    @GET("products/{product_id}.json")
    suspend fun getSingleProductById(@Path("product_id") product_id: Long): SingleProductResponse
}