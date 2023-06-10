package eg.gov.iti.jets.shopifyapp_user.products.domain.repo

import eg.gov.iti.jets.shopifyapp_user.base.model.AllProduct
import kotlinx.coroutines.flow.Flow

interface ProductsBrandRepo {
    suspend fun getProductsBrand(vendor:String): Flow<AllProduct>
}