package eg.gov.iti.jets.shopifyapp_user.settings.data.local

import android.content.SharedPreferences
import eg.gov.iti.jets.shopifyapp_user.base.local.sharedpreferances.SharedOperations
import eg.gov.iti.jets.shopifyapp_user.settings.domain.local.SettingLocalSource

class SettingLocalSourceImpl():SettingLocalSource {
    private   val sharedPrefs:SharedPreferences by lazy {
        SharedOperations.getInstance()
    }
    override fun changeCurrency(currencyCode: String) {
        sharedPrefs.edit().putString("currencyCode",currencyCode).apply()
        UserSettings.currencyCode = currencyCode
    }

    override fun changePhoneNumber(newPhoneNumber: String) {
        sharedPrefs.edit().putString("phoneNumber",newPhoneNumber).apply()
        UserSettings.phoneNumber = newPhoneNumber
    }

    override fun getDefaultSettings(): Map<String, String> {
        return mapOf("currencyCode" to sharedPrefs.getString("currencyCode","USD")!!,
            "phoneNumber" to sharedPrefs.getString("phoneNumber","")!!,
            "shippingAddress" to sharedPrefs.getString("shippingAddress","")!!)
    }

    override fun changeAddress(newShippingAddress: String) {
        sharedPrefs.edit().putString("shippingAddress",newShippingAddress).apply()
        UserSettings.shippingAddress = newShippingAddress
    }
}