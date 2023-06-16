package eg.gov.iti.jets.shopifyapp_user.productdetails.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.gson.Gson
import eg.gov.iti.jets.shopifyapp_user.cart.data.remote.DraftOrderAPIState
import eg.gov.iti.jets.shopifyapp_user.cart.data.model.DraftOrderResponse
import eg.gov.iti.jets.shopifyapp_user.cart.data.model.LineItem
import eg.gov.iti.jets.shopifyapp_user.cart.domain.repo.CartRepository
import eg.gov.iti.jets.shopifyapp_user.settings.data.local.UserSettings
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.lang.Thread.State

class ProductDetailsViewModel(private val repo:CartRepository):ViewModel() {


      private val _addedToCart: MutableLiveData<Int> = MutableLiveData(0)
      val addedToCart:LiveData<Int> = _addedToCart
      private  var cartDraftOrder: DraftOrderResponse = DraftOrderResponse(null)

    init {
        viewModelScope.launch {
            repo.getCartProducts(UserSettings.cartDraftOrderId).collect {
                when (it) {
                    is DraftOrderAPIState.Success -> {
                        cartDraftOrder = it.order!!
                        UserSettings.cartQuantity=(it.order.draft_order?.line_items?.size?:0)-1
                        if(UserSettings.cartQuantity<0)UserSettings.cartQuantity=0
                    }
                    else -> {

                    }
                }
            }
        }
    }
    fun addProductToCart(product: LineItem?, quantity:Int){

        val mlist:MutableList<LineItem> = mutableListOf()
        var flag =false
        cartDraftOrder.draft_order?.line_items?.forEach {
            if(product?.id==it.id){
                flag =true
                if(it.quantity<quantity)
                {
                   increaseProductQuantity(it)
                    UserSettings.cartQuantity+=1
                    UserSettings.saveSettings()
                    _addedToCart.value = 1
                }else{
                    _addedToCart.value = -1
                }
            }
        }
        if(!flag){
            mlist.addAll(cartDraftOrder.draft_order?.line_items?: listOf())
            mlist.add(product!!)
            Log.e("", Gson().toJson(cartDraftOrder).toString())
            cartDraftOrder.draft_order?.line_items = mlist
            Log.e("", Gson().toJson(cartDraftOrder).toString())
            viewModelScope.launch {
                repo.updateProductsInCart(
                    UserSettings.cartDraftOrderId,
                    cartDraftOrder
                )
                UserSettings.cartQuantity+=1
                UserSettings.saveSettings()
                _addedToCart.value = 1

            }
        }
    }
    private fun increaseProductQuantity(product: LineItem){
        cartDraftOrder.draft_order?.line_items?.forEach {
            if(it.product_id==product.product_id)
            {
                it.quantity+=1
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
}