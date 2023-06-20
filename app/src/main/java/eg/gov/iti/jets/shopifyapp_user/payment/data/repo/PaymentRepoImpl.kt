package eg.gov.iti.jets.shopifyapp_user.payment.data.repo

import eg.gov.iti.jets.shopifyapp_user.base.model.orders.Order
import eg.gov.iti.jets.shopifyapp_user.payment.domain.repo.PaymentRepo
import eg.gov.iti.jets.shopifyapp_user.payment.domain.remote.PaymentRemoteSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

class PaymentRepoImpl(private val paymentRemoteSource: PaymentRemoteSource): PaymentRepo {

    override suspend fun postOrder(order: Order?): Flow<Order> {
        return flowOf(paymentRemoteSource.postOrder(order))
    }
}