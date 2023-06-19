package eg.gov.iti.jets.shopifyapp_user.productdetails.domain.remote

import eg.gov.iti.jets.shopifyapp_user.productdetails.data.model.SingleProductResponse

interface ProductRemoteSourceInterface {
    suspend fun getSingleProductById(product_id:Long): SingleProductResponse
}