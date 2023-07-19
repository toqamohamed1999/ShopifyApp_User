package eg.gov.iti.jets.shopifyapp_user.categories.domain.remote

import eg.gov.iti.jets.shopifyapp_user.categories.domain.model.AllCategories
import retrofit2.http.GET
import retrofit2.http.Headers

interface CategoryService {
    @Headers("Content-Type:application/json","X-Shopify-Access-Token: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")
    @GET("custom_collections.json")
    suspend fun getAllCategoriesFromApi(): AllCategories
}