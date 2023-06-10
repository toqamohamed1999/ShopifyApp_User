package eg.gov.iti.jets.shopifyapp_user.settings.domain.repo

interface SettingRepo {
    fun changeCurrency(currencyCode:String)
    fun changePhoneNumber(newPhoneNumber:String)
    fun getDefaultSettings():Map<String,String>
    fun changeAddress(newShippingAddress:String)

}