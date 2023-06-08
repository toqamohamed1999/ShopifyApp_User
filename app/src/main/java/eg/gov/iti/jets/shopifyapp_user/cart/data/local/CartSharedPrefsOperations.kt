package eg.gov.iti.jets.shopifyapp_user.cart.data.local

import eg.gov.iti.jets.shopifyapp_user.base.local.sharedpreferances.SharedOperations

class CartSharedPrefsOperations {
    private val prefInstance = SharedOperations.getInstance()
    fun getCartDraftOrderId():String?{
      return  prefInstance.getString("cart_draft_order","")
    }
    fun setCartDraftOrderId(string:String){
        prefInstance.edit().putString("cart_draft_order",string).apply()
    }
}