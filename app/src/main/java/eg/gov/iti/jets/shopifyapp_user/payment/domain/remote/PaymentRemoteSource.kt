package eg.gov.iti.jets.shopifyapp_user.payment.domain.remote

import eg.gov.iti.jets.shopifyapp_user.home.domain.model.addsmodels.PriceRule
import kotlinx.coroutines.flow.MutableStateFlow
import retrofit2.http.Path

import eg.gov.iti.jets.shopifyapp_user.base.model.orders.Order

interface PaymentRemoteSource {
    suspend fun getSinglePriceRule(price_rule_id:String): MutableStateFlow<PriceRule?>
    suspend fun postOrder(order: Order.OrderBody?): Order.OrderBody
}