package eg.gov.iti.jets.shopifyapp_user.products.data.remote

import eg.gov.iti.jets.shopifyapp_user.base.remote.AppRetrofit
import eg.gov.iti.jets.shopifyapp_user.base.model.AllProduct
import eg.gov.iti.jets.shopifyapp_user.products.domain.remote.ProductsBrandRSInterface
import eg.gov.iti.jets.shopifyapp_user.products.domain.remote.ProductsBrandService

class ProductsBrandRS: ProductsBrandRSInterface {

    val productsService: ProductsBrandService by lazy {
        AppRetrofit.retrofit.create(ProductsBrandService::class.java)
    }

    override suspend fun getProductsBrand(vendor:String): AllProduct {
        return productsService.getProductsOfBrand(vendor)
    }
}