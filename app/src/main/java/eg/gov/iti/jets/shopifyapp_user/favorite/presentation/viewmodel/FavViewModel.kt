package eg.gov.iti.jets.shopifyapp_user.favorite.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import eg.gov.iti.jets.shopifyapp_user.auth.data.remote.ResponseState
import eg.gov.iti.jets.shopifyapp_user.base.domain.data.repo.FavDraftOrderRepoImpl
import eg.gov.iti.jets.shopifyapp_user.base.domain.repo.FavDraftOrderRepoInterface
import eg.gov.iti.jets.shopifyapp_user.base.model.FavDraftOrderResponse
import eg.gov.iti.jets.shopifyapp_user.base.model.FavRoomPojo
import eg.gov.iti.jets.shopifyapp_user.cart.data.model.LineItem
import eg.gov.iti.jets.shopifyapp_user.favorite.data.repo.FavLocalRepoImpl
import eg.gov.iti.jets.shopifyapp_user.favorite.domain.repo.FavLocalRepoInterface
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class FavViewModel(
    private val repo: FavLocalRepoInterface = FavLocalRepoImpl(),
    private val favRemoteRepo: FavDraftOrderRepoInterface = FavDraftOrderRepoImpl(),
) :
    ViewModel() {
    val favorites = repo.getAllFavProducts()

    var list: List<FavRoomPojo>? = null

    fun deleteFavProductWithId(productId: Long) {
        viewModelScope.launch {

            repo.deleteFavProductWithId(productId)
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