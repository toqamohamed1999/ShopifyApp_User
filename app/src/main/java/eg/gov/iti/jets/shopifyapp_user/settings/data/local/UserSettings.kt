package eg.gov.iti.jets.shopifyapp_user.settings.data.local

import android.location.Address
import eg.gov.iti.jets.shopifyapp_user.base.local.sharedpreferances.SharedOperations
import eg.gov.iti.jets.shopifyapp_user.home.domain.model.addsmodels.DiscountCode
import eg.gov.iti.jets.shopifyapp_user.settings.domain.model.AddressBody
import eg.gov.iti.jets.shopifyapp_user.settings.domain.model.Addresse

object UserSettings {
    var currentCurrencyValue: Double = 1.0
    var userName:String = "Yasmeen"
    var userEmail:String = ""
    var userPassword:String = "" // user firebase id
    var currencyCode:String = "EGP"
    var shippingAddress:String = ""
    var phoneNumber:String = ""
    var favoriteDraftOrderId:String = "1118696472857"
    var cartDraftOrderId:String = "1118663508249"
    var userAPI_Id = "7098003489049"
    var userCurrentDiscountCopy:DiscountCode? = null
    var selectedAddress: Address?=null
    var isSelected=false
    var cartQuantity = 0
    fun saveSettings(){
        SharedOperations.getInstance().edit()
            .putString("currentCurrencyValue",currentCurrencyValue.toString())
            .putString("userName", userName)
            .putString("userEmail", userEmail)
            .putString("userPassword",userPassword)
            .putString("currencyCode",currencyCode)
            .putString("shippingAddress",shippingAddress)
            .putString("phoneNumber",phoneNumber)
            .putString("favoriteDraftOrderId",favoriteDraftOrderId)
            .putString("cartDraftOrderId",cartDraftOrderId)
            .putInt("cartQuantity",cartQuantity)
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
    fun getPriceSymbol():String{
            return when (currencyCode) {
                "USD" -> {
                    " $"
                }
                "EUR" -> {
                    " €"
                }
                else -> {
                    " £"
                }
            }
    }
    fun loadSettings(){
       val instance = SharedOperations.getInstance()
        currentCurrencyValue= instance.getString("currentCurrencyValue","0.0")?.toDouble()?:1.0
         userName = instance.getString("userName","")!!
         userEmail = instance.getString("userEmail","")!!
         userPassword = instance.getString("userPassword","")!!
         currencyCode = instance.getString("currencyCode","")!!
         shippingAddress = instance.getString("shippingAddress","")!!
         phoneNumber = instance.getString("phoneNumber","")!!
         favoriteDraftOrderId =instance.getString("favoriteDraftOrderId","")!!
         cartDraftOrderId = instance.getString("cartDraftOrderId","")!!
         cartQuantity = instance.getInt("cartQuantity",0)
    }
    fun clearSettings(){
         userName = ""
         userEmail = ""
         userPassword = "" // user firebase id
         currencyCode = ""
         shippingAddress = ""
         phoneNumber = ""
         favoriteDraftOrderId = ""
         cartDraftOrderId = "1118663508249"
         userCurrentDiscountCopy = null
         cartQuantity = 0
        currentCurrencyValue=1.0
        SharedOperations.getInstance().edit()
            .putString("currentCurrencyValue",currentCurrencyValue.toString())
            .putString("userName", userName)
            .putString("userEmail", userEmail)
            .putString("userPassword",userPassword)
            .putString("currencyCode",currencyCode)
            .putString("shippingAddress",shippingAddress)
            .putString("phoneNumber",phoneNumber)
            .putString("favoriteDraftOrderId",favoriteDraftOrderId)
            .putString("cartDraftOrderId",cartDraftOrderId)
            .putInt("cartQuantity",cartQuantity)
            .apply()
    }

    fun saveNewAddress(address:String) {
        shippingAddress = address
        isSelected = false
        SharedOperations.getInstance().edit().putString("shippingAddress",shippingAddress).apply()
    }
    fun Address.toAddressBody():AddressBody{
        return AddressBody(
            Addresse(
            address1 = this.getAddressLine(0)?.toString(), address2 = this.getAddressLine(1)?.toString()
        , city = this.adminArea,company = null, country = this.countryName, country_code = this.countryCode
                , first_name =  userName, name =  userName
            , last_name =  userName, phone =  phoneNumber,
                zip = this.postalCode?:"", country_name = "", customer_id = userAPI_Id.toLong(), default = false, province_code = ""
        ))
    }
}