package eg.gov.iti.jets.shopifyapp_user.categories.domain.remote

import eg.gov.iti.jets.shopifyapp_user.categories.domain.model.AllCategories
import retrofit2.http.GET
import retrofit2.http.Headers

interface CategoryService {
    @Headers(
        "Content-Type:application/json",
        "X-Shopify-Access-Token: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"
    )
    @GET("custom_collections.json")
    suspend fun getAllCategoriesFromApi(): AllCategories
}