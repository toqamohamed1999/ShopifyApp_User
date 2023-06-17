package eg.gov.iti.jets.shopifyapp_user.profile.data.remote

import eg.gov.iti.jets.shopifyapp_user.base.model.orders.OrderDetails
import eg.gov.iti.jets.shopifyapp_user.base.remote.AppRetrofit
import eg.gov.iti.jets.shopifyapp_user.profile.domain.remote.ProfileOrdersService
import eg.gov.iti.jets.shopifyapp_user.profile.domain.remote.ProfileRemoteSourceInterface

class ProfileRemoteSource : ProfileRemoteSourceInterface {

    val orderService: ProfileOrdersService by lazy {
        AppRetrofit.retrofit.create(ProfileOrdersService::class.java)
    }

    override suspend fun getOrdersUser(id: Long): OrderDetails {
        return orderService.getOrdersOfUser(id)
    }
}