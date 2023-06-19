package eg.gov.iti.jets.shopifyapp_user.home.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import eg.gov.iti.jets.shopifyapp_user.home.data.model.BrandResultState
import eg.gov.iti.jets.shopifyapp_user.home.domain.model.addsmodels.DiscountCode
import eg.gov.iti.jets.shopifyapp_user.home.domain.repo.AddsRepo
import eg.gov.iti.jets.shopifyapp_user.home.domain.repo.BrandRepo
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class HomeViewModel(private val brandRepo: BrandRepo,private val addsRepo:AddsRepo) : ViewModel() {

    private var _brandState: MutableStateFlow<BrandResultState> = MutableStateFlow(BrandResultState.Loading())
    private val discounts = mutableListOf<DiscountCode>()
    var brandState: StateFlow<BrandResultState> = _brandState
    private var _adds:MutableLiveData<List<DiscountCode>> = MutableLiveData(discounts)
    var adds:LiveData<List<DiscountCode>> = _adds
    fun getBrands() {
        viewModelScope.launch {
            try {
                brandRepo.getAllBrands().collect {
                    _brandState.value = BrandResultState.Success(it.smart_collections)
                }
            } catch (e: java.lang.Exception) {
                _brandState.value = BrandResultState.Error()
            }
        }
    }
    fun getAdds(){
        if(discounts.isEmpty()){
        viewModelScope.launch {
            addsRepo.getAllPriceRules().collect {
                it?.price_rules?.forEach { item ->
                    getDiscounts(item.id.toString(), item.value)
                }
            }
          }
        }else{
            _adds.value = discounts
        }
    }
    private fun getDiscounts(ruleId:String,value:String){
        viewModelScope.launch {
        addsRepo.getAllDiscountsForPriceRule(ruleId).collect { d ->
            d?.discount_codes?.forEach {discount->
                discount.created_at = value
                discounts.add(discount)
            }
        }
        }
    }
}