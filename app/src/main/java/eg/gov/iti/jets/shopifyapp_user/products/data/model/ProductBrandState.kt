package eg.gov.iti.jets.shopifyapp_user.products.data.model

import eg.gov.iti.jets.shopifyapp_user.base.model.Product

sealed class ProductBrandState {
    class Success(val productsList:List<Product>): ProductBrandState()
    class Loading(val message:String = "loading"): ProductBrandState()
    class Error(val error:String="Error"): ProductBrandState()
}