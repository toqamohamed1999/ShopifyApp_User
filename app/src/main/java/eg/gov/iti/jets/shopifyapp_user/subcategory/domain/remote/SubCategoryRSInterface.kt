package eg.gov.iti.jets.shopifyapp_user.subcategory.domain.remote

import eg.gov.iti.jets.shopifyapp_user.base.model.AllProduct

interface SubCategoryRSInterface {

    suspend fun getProductSubCategory(productType: String, collectionId: Long): AllProduct
}