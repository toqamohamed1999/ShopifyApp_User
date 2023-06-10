package eg.gov.iti.jets.shopifyapp_user.home.domain.remote

import eg.gov.iti.jets.shopifyapp_user.home.domain.model.BrandModel

interface BrandRemoteSourceInterface {
    suspend fun getAllBrands(): BrandModel
}