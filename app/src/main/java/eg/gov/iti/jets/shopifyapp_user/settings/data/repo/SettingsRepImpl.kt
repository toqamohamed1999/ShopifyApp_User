package eg.gov.iti.jets.shopifyapp_user.settings.data.repo

import eg.gov.iti.jets.shopifyapp_user.settings.domain.repo.SettingRepo

class SettingsRepImpl:SettingRepo {
    override fun changeCurrency(currencyCode: String) {

    }

    override fun changePhoneNumber(newPhoneNumber: String) {

    }

    override fun getDefaultSettings(): Map<String, String> {
        return mapOf()
    }

    override fun changeAddress(newShippingAddress: String) {

    }
}