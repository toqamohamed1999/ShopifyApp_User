package eg.gov.iti.jets.shopifyapp_user.subcategory.domain.repo

import eg.gov.iti.jets.shopifyapp_user.base.model.AllProduct
import kotlinx.coroutines.flow.Flow

interface SubCategoryRepo {

    suspend fun getProductSubCategory(productType: String, collectionId: Long): Flow<AllProduct>
}