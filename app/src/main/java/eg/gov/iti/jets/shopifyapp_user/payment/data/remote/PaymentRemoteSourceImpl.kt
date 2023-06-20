package eg.gov.iti.jets.shopifyapp_user.payment.data.remote

import eg.gov.iti.jets.shopifyapp_user.base.model.orders.Order
import eg.gov.iti.jets.shopifyapp_user.base.remote.AppRetrofit
import eg.gov.iti.jets.shopifyapp_user.home.domain.model.addsmodels.PriceRule
import eg.gov.iti.jets.shopifyapp_user.payment.domain.remote.PaymentAPIServices
import eg.gov.iti.jets.shopifyapp_user.payment.domain.remote.PaymentRemoteSource
import kotlinx.coroutines.flow.MutableStateFlow

class PaymentRemoteSourceImpl():PaymentRemoteSource {
    private val services:PaymentAPIServices = AppRetrofit.retrofit.create(PaymentAPIServices::class.java)
    override suspend fun getSinglePriceRule(price_rule_id: String): MutableStateFlow<PriceRule?> {
        return try {
            MutableStateFlow(services.getSinglePriceRule(price_rule_id))
        }catch (exception:java.lang.Exception)
        {
            MutableStateFlow(null)
        }
    }
    override suspend fun postOrder(order: Order?): Order {
        return services.postOrder(order)
    }

}