package eg.gov.iti.jets.shopifyapp_user.home.data.remote

import eg.gov.iti.jets.shopifyapp_user.base.remote.AppRetrofit
import eg.gov.iti.jets.shopifyapp_user.home.domain.model.BrandModel
import eg.gov.iti.jets.shopifyapp_user.home.domain.remote.BrandAPIInterface
import eg.gov.iti.jets.shopifyapp_user.home.domain.remote.BrandRemoteSourceInterface

class BrandRemoteSource : BrandRemoteSourceInterface {

    val brandService: BrandAPIInterface  by lazy {
        AppRetrofit.retrofit.create(BrandAPIInterface::class.java)
    }
    override suspend fun getAllBrands(): BrandModel {
        return brandService.getAllBrands()
    }
}