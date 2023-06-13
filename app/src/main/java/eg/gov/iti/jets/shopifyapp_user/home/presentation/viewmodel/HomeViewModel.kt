package eg.gov.iti.jets.shopifyapp_user.home.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import eg.gov.iti.jets.shopifyapp_user.home.data.model.BrandResultState
import eg.gov.iti.jets.shopifyapp_user.home.domain.repo.AddsRepo
import eg.gov.iti.jets.shopifyapp_user.home.domain.repo.BrandRepo
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class HomeViewModel(private val brandRepo: BrandRepo,private val addsRepo:AddsRepo) : ViewModel() {

    private var _brandState: MutableStateFlow<BrandResultState> = MutableStateFlow(BrandResultState.Loading())
    var brandState: StateFlow<BrandResultState> = _brandState

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

}