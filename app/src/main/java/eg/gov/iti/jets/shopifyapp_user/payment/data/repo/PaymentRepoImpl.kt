package eg.gov.iti.jets.shopifyapp_user.payment.data.repo

import eg.gov.iti.jets.shopifyapp_user.home.domain.model.addsmodels.PriceRule
import eg.gov.iti.jets.shopifyapp_user.payment.domain.repo.PaymentRepo
import eg.gov.iti.jets.shopifyapp_user.payment.domain.remote.PaymentRemoteSource
import kotlinx.coroutines.flow.MutableStateFlow

class PaymentRepoImpl(private val paymentRemoteSource: PaymentRemoteSource): PaymentRepo {
    override suspend fun getSinglePriceRule(price_rule_id: String): MutableStateFlow<PriceRule?> {
        return paymentRemoteSource.getSinglePriceRule(price_rule_id)
    }
}