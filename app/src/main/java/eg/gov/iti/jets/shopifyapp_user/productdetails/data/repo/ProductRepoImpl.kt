package eg.gov.iti.jets.shopifyapp_user.productdetails.data.repo

import eg.gov.iti.jets.shopifyapp_user.productdetails.data.model.SingleProductResponse
import eg.gov.iti.jets.shopifyapp_user.productdetails.data.remote.ProductRemoteSourceImpl
import eg.gov.iti.jets.shopifyapp_user.productdetails.domain.remote.ProductRemoteSourceInterface
import eg.gov.iti.jets.shopifyapp_user.productdetails.domain.repo.ProductRepoInterface
import eg.gov.iti.jets.shopifyapp_user.products.data.repo.ProductsBrandRepoImp
import eg.gov.iti.jets.shopifyapp_user.products.domain.remote.ProductsBrandRSInterface
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

class ProductRepoImpl(private val remoteSource: ProductRemoteSourceInterface = ProductRemoteSourceImpl()) :
    ProductRepoInterface {

    companion object {
        private var instance: ProductRepoImpl? = null
        fun getInstance(
            remoteSource: ProductsBrandRSInterface
        ): ProductRepoImpl? {
            return instance ?: synchronized(this) {
                instance = ProductRepoImpl()
                instance
            }
        }
    }
    override suspend fun getSingleProductById(product_id: Long): Flow<SingleProductResponse> {
       return flowOf( remoteSource.getSingleProductById(product_id))
    }
}