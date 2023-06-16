package eg.gov.iti.jets.shopifyapp_user.productdetails.domain.remote

import eg.gov.iti.jets.shopifyapp_user.base.model.AllProduct
import eg.gov.iti.jets.shopifyapp_user.productdetails.data.model.SingleProductResponse
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query

interface ProductService {
    @Headers(
        "Content-Type:application/json",
        "X-Shopify-Access-Token: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"
    )
    @GET("products/{product_id}.json")
    suspend fun getSingleProductById(@Path("product_id") product_id: Long): SingleProductResponse
}