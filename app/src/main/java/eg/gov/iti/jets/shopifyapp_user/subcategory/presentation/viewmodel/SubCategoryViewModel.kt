package eg.gov.iti.jets.shopifyapp_user.subcategory.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import eg.gov.iti.jets.shopifyapp_user.subcategory.data.model.SubCategoryState
import eg.gov.iti.jets.shopifyapp_user.subcategory.domain.repo.SubCategoryRepo
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class SubCategoryViewModel(private val productsRepo: SubCategoryRepo) : ViewModel() {

    private var _productsState: MutableStateFlow<SubCategoryState> =
        MutableStateFlow(SubCategoryState.Loading())
    var productState: StateFlow<SubCategoryState> = _productsState

    fun getProductSubCategory(productType: String, collectionId: Long) {
        viewModelScope.launch {
            try {
                productsRepo.getProductSubCategory(productType, collectionId).collect {
                    _productsState.value = SubCategoryState.Success(it.products)
                }
            } catch (e: java.lang.Exception) {
                _productsState.value = SubCategoryState.Error()
            }
        }
    }
}