package eg.gov.iti.jets.shopifyapp_user.products.data.repo

import eg.gov.iti.jets.shopifyapp_user.base.model.AllProduct
import eg.gov.iti.jets.shopifyapp_user.products.domain.remote.ProductsBrandRSInterface
import eg.gov.iti.jets.shopifyapp_user.products.domain.repo.ProductsBrandRepo
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

class ProductsBrandRepoImp(var remoteSource: ProductsBrandRSInterface) : ProductsBrandRepo {

    companion object {
        private var instance: ProductsBrandRepoImp? = null
        fun getInstance(
            remoteSource: ProductsBrandRSInterface
        ): ProductsBrandRepoImp? {
            return instance ?: synchronized(this) {
                instance = ProductsBrandRepoImp(remoteSource)
                instance
            }
        }
    }

    override suspend fun getProductsBrand(vendor: String): Flow<AllProduct> {
        return  flowOf(remoteSource.getProductsBrand(vendor))
    }
}