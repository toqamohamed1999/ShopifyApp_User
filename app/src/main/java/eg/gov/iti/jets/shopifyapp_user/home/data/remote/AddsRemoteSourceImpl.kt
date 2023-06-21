package eg.gov.iti.jets.shopifyapp_user.home.data.remote

import eg.gov.iti.jets.shopifyapp_user.base.remote.AppRetrofit
import eg.gov.iti.jets.shopifyapp_user.home.domain.model.addsmodels.DiscountCodeBody
import eg.gov.iti.jets.shopifyapp_user.home.domain.model.addsmodels.Discounts
import eg.gov.iti.jets.shopifyapp_user.home.domain.model.addsmodels.PriceRules
import eg.gov.iti.jets.shopifyapp_user.home.domain.remote.AddsAPIServices
import eg.gov.iti.jets.shopifyapp_user.home.domain.remote.AddsRemoteSource
import kotlinx.coroutines.flow.MutableStateFlow

class AddsRemoteSourceImpl():AddsRemoteSource {
    private val services:AddsAPIServices= AppRetrofit.retrofit.create(AddsAPIServices::class.java)
    override suspend fun getAllPriceRules(): MutableStateFlow<PriceRules?> {
        return  try{
            MutableStateFlow(services.getAllPriceRules())
        }catch (exception:java.lang.Exception){
            print(exception.localizedMessage)
            MutableStateFlow(null)
        }
    }

    override suspend fun getAllDiscountsForPriceRule(ruleId: String): MutableStateFlow<Discounts?> {
        return  try{
            MutableStateFlow(services.getAllDiscountsForPriceRule(ruleId))
        }catch (exception:java.lang.Exception){
            MutableStateFlow(null)
        }
    }

    override suspend fun updateUsageCountForDiscount(
        ruleId: String,
        discount_code_id: String,
        discountCode: DiscountCodeBody
    ): MutableStateFlow<DiscountCodeBody?> {
        return  try{
            MutableStateFlow(services.updateUsageCountForDiscount(ruleId,discount_code_id,discountCode))
        }catch (exception:java.lang.Exception){
            MutableStateFlow(null)
        }
    }
}