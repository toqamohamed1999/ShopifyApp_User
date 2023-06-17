package eg.gov.iti.jets.shopifyapp_user.settings.data.repo

import eg.gov.iti.jets.shopifyapp_user.settings.domain.model.*
import eg.gov.iti.jets.shopifyapp_user.settings.domain.remote.SettingsRemoteSource
import eg.gov.iti.jets.shopifyapp_user.settings.domain.repo.SettingsRepo
import kotlinx.coroutines.flow.MutableStateFlow

class SettingsRepoImpl(private val settingsRemoteSource: SettingsRemoteSource):SettingsRepo {
    override suspend fun getAllAddressesForUser(userId: String): MutableStateFlow<AdressesResponse?> {
        return settingsRemoteSource.getAllAddressesForUser(userId)
    }

    override suspend fun addNewAddressForUser(
        userId: String,
        address: AddressBody?
    ): MutableStateFlow<CustomerAddressResponse?> {
        return settingsRemoteSource.addNewAddressForUser(userId,address)
    }

    override suspend fun removeAddress(address_id: String, userId: String) {
        return settingsRemoteSource.removeAddress(address_id,userId)
    }

    override suspend fun getAllCurrencyCodesAvailable(): MutableStateFlow<CurrencysResponse?> {
       return settingsRemoteSource.getAllCurrencyCodesAvailable()
    }
}