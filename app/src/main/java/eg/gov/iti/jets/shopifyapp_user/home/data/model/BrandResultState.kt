package eg.gov.iti.jets.shopifyapp_user.home.data.model

import eg.gov.iti.jets.shopifyapp_user.home.domain.model.SmartCollection

sealed class BrandResultState{
    class Success(val brandList:List<SmartCollection>): BrandResultState()
    class Loading(val message:String = "loading"):BrandResultState()
    class Error(val error:String="Error"):BrandResultState()
}
