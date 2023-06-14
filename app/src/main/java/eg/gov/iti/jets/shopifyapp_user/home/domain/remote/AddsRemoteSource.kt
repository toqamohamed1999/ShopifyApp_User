package eg.gov.iti.jets.shopifyapp_user.home.domain.remote

import eg.gov.iti.jets.shopifyapp_user.home.domain.model.addsmodels.DiscountCodeBody
import eg.gov.iti.jets.shopifyapp_user.home.domain.model.addsmodels.Discounts
import eg.gov.iti.jets.shopifyapp_user.home.domain.model.addsmodels.PriceRules
import kotlinx.coroutines.flow.MutableStateFlow

interface AddsRemoteSource {

    suspend fun getAllPriceRules(): MutableStateFlow<PriceRules?>

    suspend fun getAllDiscountsForPriceRule(ruleId:String): MutableStateFlow<Discounts?>

    suspend fun updateUsageCountForDiscount(ruleId:String,discount_code_id:String,discountCode: DiscountCodeBody
    ): MutableStateFlow<DiscountCodeBody?>
}