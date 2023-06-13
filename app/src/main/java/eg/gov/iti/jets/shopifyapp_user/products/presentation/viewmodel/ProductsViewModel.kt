package eg.gov.iti.jets.shopifyapp_user.products.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import eg.gov.iti.jets.shopifyapp_user.base.model.Product
import eg.gov.iti.jets.shopifyapp_user.products.data.model.ProductBrandState
import eg.gov.iti.jets.shopifyapp_user.products.domain.repo.ProductsBrandRepo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ProductsViewModel(private val productsRepo: ProductsBrandRepo) : ViewModel() {

    private var _productsState: MutableStateFlow<ProductBrandState> =
        MutableStateFlow(ProductBrandState.Loading())
    var productState: StateFlow<ProductBrandState> = _productsState

    private val _filterProduct: MutableStateFlow<List<Product>> = MutableStateFlow(listOf())
    val filterProduct: StateFlow<List<Product>> = _filterProduct
    private val productsList: MutableList<Product> = mutableListOf()

    fun getProductsBrand(vendor: String) {
        viewModelScope.launch {
            try {
                productsRepo.getProductsBrand(vendor).collect {
                    _productsState.value = ProductBrandState.Success(it.products)
                }
            } catch (e: java.lang.Exception) {
                _productsState.value = ProductBrandState.Error()
            }
        }
    }

    fun filterProducts(productPrice : Float){
        viewModelScope.launch {
            _filterProduct.value = withContext(Dispatchers.Default) {
                productsList.filter { product ->
                    val price: Float = product.variants[0].price.toFloat()
                    price >= productPrice
                }
            }
        }
    }

}