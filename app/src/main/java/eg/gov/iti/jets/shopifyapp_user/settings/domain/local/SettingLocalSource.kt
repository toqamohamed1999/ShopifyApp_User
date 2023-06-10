package eg.gov.iti.jets.shopifyapp_user.settings.domain.local

interface SettingLocalSource {
    fun changeCurrency(currencyCode:String)
    fun changePhoneNumber(newPhoneNumber:String)
    fun getDefaultSettings():Map<String,String>
    fun changeAddress(newShippingAddress:String)
}