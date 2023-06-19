package eg.gov.iti.jets.shopifyapp_user.profile.domain.repo

import eg.gov.iti.jets.shopifyapp_user.base.model.orders.OrderDetails
import kotlinx.coroutines.flow.Flow

interface ProfileRepo {
    suspend fun getOrdersUser(id: Long): Flow<OrderDetails>
}