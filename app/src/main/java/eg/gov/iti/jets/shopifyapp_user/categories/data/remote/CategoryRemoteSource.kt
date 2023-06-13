package eg.gov.iti.jets.shopifyapp_user.categories.data.remote

import eg.gov.iti.jets.shopifyapp_user.base.remote.AppRetrofit
import eg.gov.iti.jets.shopifyapp_user.categories.domain.model.AllCategories
import eg.gov.iti.jets.shopifyapp_user.categories.domain.remote.CategoryRemoteSourceInterface
import eg.gov.iti.jets.shopifyapp_user.categories.domain.remote.CategoryService

class CategoryRemoteSource : CategoryRemoteSourceInterface {

    val categoryService: CategoryService by lazy {
        AppRetrofit.retrofit.create(CategoryService::class.java)
    }

    override suspend fun getCategories(): AllCategories {
        return categoryService.getAllCategoriesFromApi()
    }
}