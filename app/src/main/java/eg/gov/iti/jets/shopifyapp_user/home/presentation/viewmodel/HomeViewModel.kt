package eg.gov.iti.jets.shopifyapp_user.home.presentation.viewmodel

import android.icu.text.SimpleDateFormat
import android.util.Log
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
    private var discounts = mutableListOf<DiscountCode>()
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
        discounts = mutableListOf()
        viewModelScope.launch {
            addsRepo.getAllPriceRules().collect {
                it?.price_rules?.forEach { item ->
                    if(!item.ends_at.isNullOrEmpty()){
                       try {
                           val dateFormat = SimpleDateFormat("YYYY-MM-ddTHH:mm:ss")
                           val  currentDate = System.currentTimeMillis()
                           val ts = dateFormat.parse(item.ends_at).time / 1000
                           if(currentDate<ts)
                           {
                               getDiscounts(item.id.toString(), item.value,item.value_type)
                           }
                       }catch (e:java.lang.Exception){
                           Log.e("","Wong Date")
                           getDiscounts(item.id.toString(), item.value, item.value_type)
                       }
                    }else{
                        getDiscounts(item.id.toString(), item.value, item.value_type)
                    }
                }
            }
          }
    }
    private fun getDiscounts(ruleId: String, value: String, valueType: String){
        viewModelScope.launch {
        addsRepo.getAllDiscountsForPriceRule(ruleId).collect { d ->
            d?.discount_codes?.forEach {discount->
                discount.created_at = (value.toDouble()*-1).toString()
                discount.updated_at =valueType
                discounts.add(discount)
                _adds.value = discounts
            }
        }
        }
    }
}