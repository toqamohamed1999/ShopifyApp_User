package eg.gov.iti.jets.shopifyapp_user.home.brand.remote

import eg.gov.iti.jets.shopifyapp_user.home.domain.model.BrandModel
import eg.gov.iti.jets.shopifyapp_user.home.domain.remote.BrandRemoteSourceInterface

class BrandRemoteSourceTest : BrandRemoteSourceInterface {

    override suspend fun getAllBrands(): BrandModel {
        return BrandModel(mutableListOf())
    }
}