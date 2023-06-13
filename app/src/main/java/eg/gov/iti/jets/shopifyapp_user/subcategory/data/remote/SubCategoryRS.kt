package eg.gov.iti.jets.shopifyapp_user.subcategory.data.remote

import eg.gov.iti.jets.shopifyapp_user.base.model.AllProduct
import eg.gov.iti.jets.shopifyapp_user.base.remote.AppRetrofit
import eg.gov.iti.jets.shopifyapp_user.subcategory.domain.remote.SubCategoryRSInterface
import eg.gov.iti.jets.shopifyapp_user.subcategory.domain.remote.SubCategoryService

class SubCategoryRS : SubCategoryRSInterface {
    val productsService: SubCategoryService by lazy {
        AppRetrofit.retrofit.create(SubCategoryService::class.java)
    }

    override suspend fun getProductSubCategory(
        productType: String,
        collectionId: Long
    ): AllProduct {
        return productsService.getProductsOfSubCategory(productType, collectionId)
    }
}