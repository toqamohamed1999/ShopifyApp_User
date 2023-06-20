package eg.gov.iti.jets.shopifyapp_user.subcategory.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import eg.gov.iti.jets.shopifyapp_user.auth.data.remote.ResponseState
import eg.gov.iti.jets.shopifyapp_user.base.domain.data.repo.FavDraftOrderRepoImpl
import eg.gov.iti.jets.shopifyapp_user.base.domain.repo.FavDraftOrderRepoInterface
import eg.gov.iti.jets.shopifyapp_user.base.domain.repo.FavOpRepoInterface
import eg.gov.iti.jets.shopifyapp_user.base.model.FavDraftOrderResponse
import eg.gov.iti.jets.shopifyapp_user.base.model.FavRoomPojo
import eg.gov.iti.jets.shopifyapp_user.base.repo.FavOpRepoImpl
import eg.gov.iti.jets.shopifyapp_user.subcategory.data.model.SubCategoryState
import eg.gov.iti.jets.shopifyapp_user.subcategory.domain.repo.SubCategoryRepo
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class SubCategoryViewModel(
    private val productsRepo: SubCategoryRepo,
    private val favRemoteRepo: FavDraftOrderRepoInterface = FavDraftOrderRepoImpl(),
    private val favRepo: FavOpRepoInterface = FavOpRepoImpl()
) : ViewModel() {

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