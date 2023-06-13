package eg.gov.iti.jets.shopifyapp_user.subcategory.data.repo

import eg.gov.iti.jets.shopifyapp_user.base.model.AllProduct
import eg.gov.iti.jets.shopifyapp_user.subcategory.domain.remote.SubCategoryRSInterface
import eg.gov.iti.jets.shopifyapp_user.subcategory.domain.repo.SubCategoryRepo
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

class SubCategoryRepoImp(var remoteSource: SubCategoryRSInterface) : SubCategoryRepo {

    companion object {
        private var instance: SubCategoryRepoImp? = null
        fun getInstance(
            remoteSource: SubCategoryRSInterface
        ): SubCategoryRepoImp? {
            return instance ?: synchronized(this) {
                instance = SubCategoryRepoImp(remoteSource)
                instance
            }
        }
    }

    override suspend fun getProductSubCategory(
        productType: String,
        collectionId: Long
    ): Flow<AllProduct> {
        return flowOf(remoteSource.getProductSubCategory(productType, collectionId))
    }
}