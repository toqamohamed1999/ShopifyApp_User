package eg.gov.iti.jets.shopifyapp_user.products.domain.remote

import eg.gov.iti.jets.shopifyapp_user.base.model.AllProduct

interface ProductsBrandRSInterface {
    suspend fun getProductsBrand(vendor:String): AllProduct
}