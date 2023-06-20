package eg.gov.iti.jets.shopifyapp_user.products.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import eg.gov.iti.jets.shopifyapp_user.auth.data.remote.ResponseState
import eg.gov.iti.jets.shopifyapp_user.base.domain.data.repo.FavDraftOrderRepoImpl
import eg.gov.iti.jets.shopifyapp_user.base.domain.repo.FavDraftOrderRepoInterface
import eg.gov.iti.jets.shopifyapp_user.base.domain.repo.FavOpRepoInterface
import eg.gov.iti.jets.shopifyapp_user.base.model.FavDraftOrderResponse
import eg.gov.iti.jets.shopifyapp_user.base.model.FavRoomPojo
import eg.gov.iti.jets.shopifyapp_user.base.model.Product
import eg.gov.iti.jets.shopifyapp_user.base.repo.FavOpRepoImpl
import eg.gov.iti.jets.shopifyapp_user.productdetails.data.repo.ProductRepoImpl
import eg.gov.iti.jets.shopifyapp_user.productdetails.domain.repo.ProductRepoInterface
import eg.gov.iti.jets.shopifyapp_user.products.data.model.ProductBrandState
import eg.gov.iti.jets.shopifyapp_user.products.domain.repo.ProductsBrandRepo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ProductsViewModel(
    private val productsRepo: ProductsBrandRepo,
    private val favRemoteRepo: FavDraftOrderRepoInterface = FavDraftOrderRepoImpl(),
    private val favRepo: FavOpRepoInterface = FavOpRepoImpl()
) : ViewModel() {

    private var _productsState: MutableStateFlow<ProductBrandState> =
        MutableStateFlow(ProductBrandState.Loading())
    var productState: StateFlow<ProductBrandState> = _productsState

    private val _filterProduct: MutableStateFlow<List<Product>> = MutableStateFlow(listOf())

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

    var favProduct = MutableStateFlow<ResponseState<List<FavRoomPojo>>>(ResponseState.Loading)
    fun getAllFavProduct() {
        viewModelScope.launch {
            favRepo.getAllFavProducts().catch { error ->
                favProduct.value = ResponseState.Error(Exception(error))
            }.collectLatest { data ->
                favProduct.value = ResponseState.Success(data)
            }
        }
    }

    fun deleteFavProductWithId(productId: Long) {
        viewModelScope.launch {
            favRepo.deleteFavProductWithId(productId)
        }
    }

    fun insertFavProduct(favRoomPojo: FavRoomPojo) {
        viewModelScope.launch {
            favRepo.insertFavProduct(favRoomPojo)
        }
    }

    private val _favProducts: MutableStateFlow<ResponseState<FavDraftOrderResponse>> =
        MutableStateFlow(ResponseState.Loading)
    var favProducts: StateFlow<ResponseState<FavDraftOrderResponse>> = _favProducts

    fun getFavRemoteProducts(draftOrderId: Long) {
        viewModelScope.launch {
            favRemoteRepo.getFavDraftOrder(draftOrderId)
                .catch { error -> _favProducts.value = ResponseState.Error(error as Exception) }
                .collectLatest { data ->
                    _favProducts.value = ResponseState.Success(data)
                }
        }
    }

    fun updateFavDraftOrder(draftOrderId: Long, draftOrder: FavDraftOrderResponse) {
        viewModelScope.launch {
            favRemoteRepo.updateFavDraftOrder(draftOrderId, draftOrder)
        }
    }
}