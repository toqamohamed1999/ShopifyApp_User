package eg.gov.iti.jets.shopifyapp_user.payment.data.remote

import eg.gov.iti.jets.shopifyapp_user.base.remote.AppRetrofit
import eg.gov.iti.jets.shopifyapp_user.payment.domain.remote.PaymentAPIServices
import eg.gov.iti.jets.shopifyapp_user.payment.domain.remote.PaymentRemoteSource

class PaymentRemoteSourceImpl():PaymentRemoteSource {
    private val services:PaymentAPIServices = AppRetrofit.retrofit.create(PaymentAPIServices::class.java)

}