package eg.gov.iti.jets.shopifyapp_user.payment.domain.remote

import eg.gov.iti.jets.shopifyapp_user.base.model.orders.Order

interface PaymentRemoteSource {
    suspend fun postOrder(order: Order?): Order
}