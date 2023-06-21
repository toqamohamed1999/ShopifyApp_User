package eg.gov.iti.jets.shopifyapp_user.cart.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import eg.gov.iti.jets.shopifyapp_user.cart.data.remote.DraftOrderAPIState
import eg.gov.iti.jets.shopifyapp_user.cart.data.model.DraftOrderResponse
import eg.gov.iti.jets.shopifyapp_user.cart.data.model.LineItem
import eg.gov.iti.jets.shopifyapp_user.cart.domain.repo.CartRepository
import eg.gov.iti.jets.shopifyapp_user.settings.data.local.UserSettings
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class CartViewModel(private val repo:CartRepository):ViewModel() {

    private val _cartOrder:MutableStateFlow<DraftOrderAPIState> = MutableStateFlow(DraftOrderAPIState.Loading())
     val cartOrder:StateFlow<DraftOrderAPIState> = _cartOrder
     private var cartDraftOrder: DraftOrderResponse? = null


    fun setCartDraftOrder(order: DraftOrderResponse?){
        cartDraftOrder = order
    }
     fun removeProductFromCart(product: LineItem){
                removeProductFromList(product)
     }
    private fun removeProductFromList(product: LineItem){
        val mlist:MutableList<LineItem> = mutableListOf()
        cartDraftOrder?.draft_order?.line_items?.forEach {
            if(it.id!=product.id)
            {
                mlist.add(it)
            }
        }
        cartDraftOrder?.draft_order?.line_items = mlist
        viewModelScope.launch {
            cartDraftOrder?.let {
                repo.updateProductsInCart(
                    UserSettings.cartDraftOrderId,
                    it
                )
            }
            _cartOrder.value = DraftOrderAPIState.Success(cartDraftOrder)
        }
    }
    fun updateProduct(type:Int,product: LineItem){

        if(type==1)
        {
            increaseProductQuantity(product)
            _cartOrder.value = DraftOrderAPIState.Success(cartDraftOrder)
        }else if (type==-1){
            decreaseProductQuantity(product)
            _cartOrder.value = DraftOrderAPIState.Success(cartDraftOrder)
        }

    }
    private fun increaseProductQuantity(product: LineItem){
        cartDraftOrder?.draft_order?.line_items?.forEach {
            if(it.id==product.id)
            {
                it.quantity+=1
            }
        }
        viewModelScope.launch {
            repo.updateProductsInCart(
                UserSettings.cartDraftOrderId,
                cartDraftOrder ?: DraftOrderResponse(null)
            )
        }
    }
    private fun decreaseProductQuantity(product: LineItem){
        if(product.quantity==1){
            removeProductFromCart(product)
        }else {
            cartDraftOrder?.draft_order?.line_items?.forEach {
                if (it.id == product.id) {
                    it.quantity -= 1
                }
            }
            viewModelScope.launch {
                repo.updateProductsInCart(
                    UserSettings.cartDraftOrderId,
                    cartDraftOrder ?: DraftOrderResponse(null)
                )
                _cartOrder.value = DraftOrderAPIState.Success(cartDraftOrder)
            }
        }
    }

     fun getCartProducts() {
         viewModelScope.launch {
             repo.getCartProducts(UserSettings.cartDraftOrderId).collect {
                 _cartOrder.value = it
             }
         }
     }

    fun clearOrder() {
        cartDraftOrder=null
    }

}