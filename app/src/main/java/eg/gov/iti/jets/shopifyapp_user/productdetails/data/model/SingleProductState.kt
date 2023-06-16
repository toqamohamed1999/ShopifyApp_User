package eg.gov.iti.jets.shopifyapp_user.productdetails.data.model

sealed class SingleProductState {
    class Success(val product:SingleProductResponse): SingleProductState()
    class Loading(val message:String = "loading"): SingleProductState()
    class Error(val error:String="Error"): SingleProductState()
}