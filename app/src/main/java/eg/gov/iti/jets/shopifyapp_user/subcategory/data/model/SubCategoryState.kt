package eg.gov.iti.jets.shopifyapp_user.subcategory.data.model

import eg.gov.iti.jets.shopifyapp_user.base.model.Product

sealed class SubCategoryState{
    class Success(val productsList:List<Product>): SubCategoryState()
    class Loading(val message:String = "loading"): SubCategoryState()
    class Error(val error:String="Error"): SubCategoryState()
}
