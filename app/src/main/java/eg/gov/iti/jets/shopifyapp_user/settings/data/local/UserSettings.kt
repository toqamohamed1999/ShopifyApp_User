package eg.gov.iti.jets.shopifyapp_user.settings.data.local

import eg.gov.iti.jets.shopifyapp_user.base.local.sharedpreferances.SharedOperations

object UserSettings {
    var userName:String = ""
    var userEmail:String = ""
    var userPassword:String = "" // user firebase id
    var currencyCode:String = "EGB"
    var shippingAddress:String = ""
    var phoneNumber:String = ""
    var favoriteDraftOrderId:String = ""
    var cartDraftOrderId:String = ""
    fun saveSettings(){
        SharedOperations.getInstance().edit()
            .putString("userName", userName)
            .putString("userEmail", userEmail)
            .putString("userPassword",userPassword)
            .putString("currencyCode",currencyCode)
            .putString("shippingAddress",shippingAddress)
            .putString("phoneNumber",phoneNumber)
            .putString("favoriteDraftOrderId",favoriteDraftOrderId)
            .putString("cartDraftOrderId",cartDraftOrderId)
            .apply()
    }
    fun getPrice(value:Double):Double{
        return when (currencyCode) {
            "USD" -> {
                value*30.9
            }
            "EUR" -> {
                value*33.23
            }
            else -> {
                value
            }
        }
    }
    fun loadSettings(){
       val instance = SharedOperations.getInstance()
         userName = instance.getString("userName","")!!
         userEmail = instance.getString("userEmail","")!!
         userPassword = instance.getString("userPassword","")!!
         currencyCode = instance.getString("currencyCode","")!!
         shippingAddress = instance.getString("shippingAddress","")!!
         phoneNumber = instance.getString("phoneNumber","")!!
         favoriteDraftOrderId =instance.getString("favoriteDraftOrderId","")!!
         cartDraftOrderId = instance.getString("cartDraftOrderId","")!!
    }
}