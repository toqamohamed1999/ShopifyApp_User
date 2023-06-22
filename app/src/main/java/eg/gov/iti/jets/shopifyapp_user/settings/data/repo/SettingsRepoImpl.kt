package eg.gov.iti.jets.shopifyapp_user.settings.data.repo

import eg.gov.iti.jets.shopifyapp_user.auth.domain.model.CustomerResponse
import eg.gov.iti.jets.shopifyapp_user.auth.domain.model.CustomersResponse
import eg.gov.iti.jets.shopifyapp_user.auth.domain.remote.AuthRemoteSourceInterface
import eg.gov.iti.jets.shopifyapp_user.auth.domain.repo.ApiRepoInterface
import eg.gov.iti.jets.shopifyapp_user.settings.domain.model.*
import eg.gov.iti.jets.shopifyapp_user.settings.domain.remote.SettingsRemoteSource
import eg.gov.iti.jets.shopifyapp_user.settings.domain.repo.SettingsRepo
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow

class SettingsRepoImpl(private val settingsRemoteSource: SettingsRemoteSource,private val customerRemoteSource:ApiRepoInterface):SettingsRepo {
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

    override suspend fun getAllCurrencies(): MutableStateFlow<CurrenciesResponse?> {
       return settingsRemoteSource.getAllCurrencies()
    }

    override suspend fun changeCurrency(
        toCode: String
    ): MutableStateFlow<ExchangerResponse?> {
        return  settingsRemoteSource.changeCurrency(toCode)
    }

    override suspend fun getCustomerById(id: String): MutableStateFlow<CustomerResponse?> {
        return settingsRemoteSource.getUser(id)
    }

    override suspend fun updateRemoteCustomer(
        customer_id: Long,
        customer: CustomerResponse
    ): Flow<CustomerResponse> {
      return customerRemoteSource.updateRemoteCustomer(customer_id,customer)
    }

}