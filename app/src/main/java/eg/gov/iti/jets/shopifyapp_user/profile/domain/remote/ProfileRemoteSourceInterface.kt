package eg.gov.iti.jets.shopifyapp_user.profile.domain.remote

import eg.gov.iti.jets.shopifyapp_user.base.model.orders.OrderDetails

interface ProfileRemoteSourceInterface {
    suspend fun getOrdersUser(id: Long): OrderDetails
}