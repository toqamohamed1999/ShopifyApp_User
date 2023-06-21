package eg.gov.iti.jets.shopifyapp_user.payment.domain.repo

import eg.gov.iti.jets.shopifyapp_user.home.domain.model.addsmodels.PriceRule
import kotlinx.coroutines.flow.MutableStateFlow
import retrofit2.http.Path

import eg.gov.iti.jets.shopifyapp_user.base.model.orders.Order
import kotlinx.coroutines.flow.Flow

interface PaymentRepo {
    suspend fun getSinglePriceRule(price_rule_id:String): MutableStateFlow<PriceRule?>
    suspend fun postOrder(order: Order.OrderBody?): Flow<Order.OrderBody>
}