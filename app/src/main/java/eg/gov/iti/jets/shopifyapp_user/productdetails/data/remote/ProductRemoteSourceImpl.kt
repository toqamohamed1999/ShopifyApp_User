package eg.gov.iti.jets.shopifyapp_user.productdetails.data.remote

import eg.gov.iti.jets.shopifyapp_user.base.remote.AppRetrofit
import eg.gov.iti.jets.shopifyapp_user.productdetails.data.model.SingleProductResponse
import eg.gov.iti.jets.shopifyapp_user.productdetails.domain.remote.ProductRemoteSourceInterface
import eg.gov.iti.jets.shopifyapp_user.productdetails.domain.remote.ProductService

class ProductRemoteSourceImpl:ProductRemoteSourceInterface {
    private val productService: ProductService by lazy {
        AppRetrofit.retrofit.create(ProductService::class.java)
    }
    override suspend fun getSingleProductById(product_id: Long): SingleProductResponse {
       return productService.getSingleProductById(product_id)
    }
}