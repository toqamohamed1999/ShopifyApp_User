package eg.gov.iti.jets.shopifyapp_user.productdetails.domain.repo

import eg.gov.iti.jets.shopifyapp_user.productdetails.data.model.SingleProductResponse
import kotlinx.coroutines.flow.Flow


interface ProductRepoInterface {
    suspend fun getSingleProductById(product_id:Long): Flow<SingleProductResponse>
}