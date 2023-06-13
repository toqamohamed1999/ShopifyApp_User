package eg.gov.iti.jets.shopifyapp_user.home.data.repo

import eg.gov.iti.jets.shopifyapp_user.home.domain.model.addsmodels.Discounts
import eg.gov.iti.jets.shopifyapp_user.home.domain.model.addsmodels.PriceRules
import eg.gov.iti.jets.shopifyapp_user.home.domain.remote.AddsRemoteSource
import eg.gov.iti.jets.shopifyapp_user.home.domain.repo.AddsRepo
import kotlinx.coroutines.flow.MutableStateFlow

class AddsRepoImpl(private  val addsRemote:AddsRemoteSource):AddsRepo {
    override suspend fun getAllPriceRules(): MutableStateFlow<PriceRules?> {
        return addsRemote.getAllPriceRules()
    }

    override suspend fun getAllDiscountsForPriceRule(ruleId: String): MutableStateFlow<Discounts?> {
        return addsRemote.getAllDiscountsForPriceRule(ruleId)
    }


}