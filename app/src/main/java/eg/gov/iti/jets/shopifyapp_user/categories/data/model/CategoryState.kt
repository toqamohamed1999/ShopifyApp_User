package eg.gov.iti.jets.shopifyapp_user.categories.data.model

import eg.gov.iti.jets.shopifyapp_user.categories.domain.model.CustomCollections

sealed class CategoryState{
    class Success(val categoryList:List<CustomCollections>): CategoryState()
    class Loading(val message:String = "loading"): CategoryState()
    class Error(val error:String="Error"): CategoryState()
}
