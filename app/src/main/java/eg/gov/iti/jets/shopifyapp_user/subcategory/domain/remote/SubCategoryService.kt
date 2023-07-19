package eg.gov.iti.jets.shopifyapp_user.subcategory.domain.remote

import eg.gov.iti.jets.shopifyapp_user.base.model.AllProduct
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface SubCategoryService {
    @Headers("Content-Type:application/json","X-Shopify-Access-Token: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")
    @GET("products.json")
    suspend fun getProductsOfSubCategory(
        @Query("product_type") productType: String,
        @Query("collection_id") collectionId: Long
    ): AllProduct
}