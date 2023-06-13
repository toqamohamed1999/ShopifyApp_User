package eg.gov.iti.jets.shopifyapp_user.subcategory.domain.remote

import eg.gov.iti.jets.shopifyapp_user.base.model.AllProduct
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface SubCategoryService {
    @Headers(
        "Content-Type:application/json",
        "X-Shopify-Access-Token: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"
    )
    @GET("products.json")
    suspend fun getProductsOfSubCategory(
        @Query("product_type") productType: String,
        @Query("collection_id") collectionId: Long
    ): AllProduct
}