package eg.gov.iti.jets.shopifyapp_user.productdetails.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.gson.Gson
import eg.gov.iti.jets.shopifyapp_user.auth.data.remote.ResponseState
import eg.gov.iti.jets.shopifyapp_user.base.domain.data.repo.FavDraftOrderRepoImpl
import eg.gov.iti.jets.shopifyapp_user.base.domain.repo.FavDraftOrderRepoInterface
import eg.gov.iti.jets.shopifyapp_user.base.domain.repo.FavOpRepoInterface
import eg.gov.iti.jets.shopifyapp_user.base.model.FavDraftOrderResponse
import eg.gov.iti.jets.shopifyapp_user.base.model.FavRoomPojo
import eg.gov.iti.jets.shopifyapp_user.base.repo.FavOpRepoImpl
import eg.gov.iti.jets.shopifyapp_user.cart.data.model.DraftOrderResponse
import eg.gov.iti.jets.shopifyapp_user.cart.data.model.LineItem
import eg.gov.iti.jets.shopifyapp_user.cart.data.remote.DraftOrderAPIState
import eg.gov.iti.jets.shopifyapp_user.cart.domain.repo.CartRepository
import eg.gov.iti.jets.shopifyapp_user.productdetails.data.model.SingleProductState
import eg.gov.iti.jets.shopifyapp_user.productdetails.data.repo.ProductRepoImpl
import eg.gov.iti.jets.shopifyapp_user.productdetails.domain.repo.ProductRepoInterface
import eg.gov.iti.jets.shopifyapp_user.settings.data.local.UserSettings
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class ProductDetailsViewModel(
    private val repo: CartRepository,
    private val favRemoteRepo: FavDraftOrderRepoInterface = FavDraftOrderRepoImpl(),
    private val productRepo: ProductRepoInterface = ProductRepoImpl(),
    private val favRepo: FavOpRepoInterface = FavOpRepoImpl(),

    ) : ViewModel() {


    private var productsIncCard: MutableList<LineItem> = mutableListOf()
    private val _addedToCart: MutableLiveData<Int> = MutableLiveData(0)
    val addedToCart: LiveData<Int> = _addedToCart
    private var cartDraftOrder: DraftOrderResponse = DraftOrderResponse(null)

    fun getCartProducts() {
        viewModelScope.launch {
            repo.getCartProducts(UserSettings.cartDraftOrderId).collect {
                when (it) {
                    is DraftOrderAPIState.Success -> {
                        cartDraftOrder = it.order!!
                        productsIncCard = mutableListOf()
                        productsIncCard.addAll(
                            it.order?.draft_order?.line_items?.takeLast(
                                ((it.order.draft_order?.line_items?.size ?: 1) - 1)
                            ) ?: listOf()
                        )

                        calc_quantity()
                    }
                    else -> {

                    }
                }
            }
        }
    }

    private fun calc_quantity() {
        UserSettings.cartQuantity = 0
        productsIncCard.forEach {
            UserSettings.cartQuantity += it.quantity
        }
    }

    fun addProductToCart(product: LineItem?, quantity: Int) {

        val mlist: MutableList<LineItem> = mutableListOf()
        var flag = false
        cartDraftOrder.draft_order?.line_items?.forEach {
            if (product?.applied_discount?.description?.split(")")
                    ?.get(0) == it.applied_discount.description?.split(")")?.get(0)
            ) {
                flag = true
                if (it.quantity < quantity) {
                    increaseProductQuantity(it)
                    UserSettings.cartQuantity += 1
                    UserSettings.saveSettings()
                    _addedToCart.value = 1
                } else {
                    _addedToCart.value = -1
                }
            }
        }
        if (!flag) {
            mlist.addAll(cartDraftOrder.draft_order?.line_items ?: listOf())
            mlist.add(product!!)
            Log.e("", Gson().toJson(cartDraftOrder).toString())
            cartDraftOrder.draft_order?.line_items = mlist
            Log.e("", Gson().toJson(cartDraftOrder).toString())
            viewModelScope.launch {
                repo.updateProductsInCart(
                    UserSettings.cartDraftOrderId,
                    cartDraftOrder
                )
                UserSettings.cartQuantity += 1
                UserSettings.saveSettings()
                _addedToCart.value = 1

            }
        }
    }

    private fun increaseProductQuantity(product: LineItem) {
        cartDraftOrder.draft_order?.line_items?.forEach {
            if (it.product_id == product.product_id) {
                it.quantity += 1
            }
        }
        viewModelScope.launch {
            repo.updateProductsInCart(
                UserSettings.cartDraftOrderId,
                cartDraftOrder
            )
        }
    }

    fun resetAddToCart() {
        _addedToCart.value = 0
    }

    var product = MutableStateFlow<SingleProductState>(SingleProductState.Loading())
    fun getSingleProductById(product_id: Long) {
        viewModelScope.launch {
            productRepo.getSingleProductById(product_id).catch { e ->
                product.value = SingleProductState.Error(e.toString())
            }
                .collectLatest { data ->
                    product.value = SingleProductState.Success(data)
                }
        }
    }

    var favProduct = MutableStateFlow<ResponseState<FavRoomPojo>>(ResponseState.Loading)
    fun getFavProductWithId(productId: Long) {
        viewModelScope.launch {
            favRepo.getFavProductWithId(productId).catch { error ->
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
    //var favDraftOrder: DraftOrderResponse = DraftOrderResponse(null)

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