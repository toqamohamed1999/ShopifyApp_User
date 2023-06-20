package eg.gov.iti.jets.shopifyapp_user.payment.domain.repo

import eg.gov.iti.jets.shopifyapp_user.base.model.orders.Order
import kotlinx.coroutines.flow.Flow

interface PaymentRepo {
    suspend fun postOrder(order: Order?): Flow<Order>
}