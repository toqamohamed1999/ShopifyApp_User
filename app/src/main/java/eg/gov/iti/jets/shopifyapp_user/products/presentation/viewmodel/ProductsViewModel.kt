package eg.gov.iti.jets.shopifyapp_user.products.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import eg.gov.iti.jets.shopifyapp_user.products.data.model.ProductBrandState
import eg.gov.iti.jets.shopifyapp_user.products.domain.repo.ProductsBrandRepo
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ProductsViewModel (private val productsRepo: ProductsBrandRepo) : ViewModel() {

    private var _productsState: MutableStateFlow<ProductBrandState> = MutableStateFlow(ProductBrandState.Loading())
    var productState: StateFlow<ProductBrandState> = _productsState

    fun getProductsBrand(vendor : String) {
        viewModelScope.launch {
            try {
                productsRepo.getProductsBrand(vendor).collect{
                    _productsState.value = ProductBrandState.Success(it.products)
                }
            } catch (e: java.lang.Exception) {
                _productsState.value = ProductBrandState.Error()
            }
        }
    }
}