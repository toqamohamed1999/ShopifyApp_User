package eg.gov.iti.jets.shopifyapp_user.home.domain.repo

import eg.gov.iti.jets.shopifyapp_user.home.domain.model.addsmodels.Discounts
import eg.gov.iti.jets.shopifyapp_user.home.domain.model.addsmodels.PriceRules
import kotlinx.coroutines.flow.MutableStateFlow

interface AddsRepo {
    suspend fun getAllPriceRules(): MutableStateFlow<PriceRules?>

    suspend fun getAllDiscountsForPriceRule(ruleId:String): MutableStateFlow<Discounts?>
}