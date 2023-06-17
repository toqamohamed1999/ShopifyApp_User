package eg.gov.iti.jets.shopifyapp_user.payment.data.repo

import eg.gov.iti.jets.shopifyapp_user.payment.domain.repo.PaymentRepo
import eg.gov.iti.jets.shopifyapp_user.payment.domain.remote.PaymentRemoteSource

class PaymentRepoImpl(private val paymentRemoteSource: PaymentRemoteSource): PaymentRepo {
}