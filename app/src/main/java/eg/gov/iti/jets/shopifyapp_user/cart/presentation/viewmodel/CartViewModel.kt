package eg.gov.iti.jets.shopifyapp_user.cart.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import eg.gov.iti.jets.shopifyapp_user.cart.data.remote.DraftOrderAiState
import eg.gov.iti.jets.shopifyapp_user.cart.domain.model.DraftOrderResponse
import eg.gov.iti.jets.shopifyapp_user.cart.domain.model.LineItem
import eg.gov.iti.jets.shopifyapp_user.cart.domain.repo.CartRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class CartViewModel(private val repo:CartRepository):ViewModel() {

     private val _cartOrder:MutableStateFlow<DraftOrderAiState> = MutableStateFlow(DraftOrderAiState.Loading())
     val cartOrder:StateFlow<DraftOrderAiState> = _cartOrder

     fun addProductToCart(product: LineItem){
         viewModelScope.launch {
             repo.addProductToCart(product)
         }
     }
     fun removeProductFromCart(product: LineItem){
         viewModelScope.launch {
             repo.removeProductFromCart(product)
         }
     }
     fun updateProductInCart(product: LineItem){
         viewModelScope.launch {
             repo.updateProductInCart(product)
         }
     }
     fun getCartProducts() {
         viewModelScope.launch {
             repo.getCartProducts().collectLatest {
                 _cartOrder.value = it
             }
         }
     }
}