package eg.gov.iti.jets.shopifyapp_user.payment.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import eg.gov.iti.jets.shopifyapp_user.cart.data.model.DraftOrderResponse
import eg.gov.iti.jets.shopifyapp_user.cart.data.remote.DraftOrderAPIState
import eg.gov.iti.jets.shopifyapp_user.cart.domain.repo.CartRepository
import eg.gov.iti.jets.shopifyapp_user.payment.domain.repo.PaymentRepo
import eg.gov.iti.jets.shopifyapp_user.settings.data.local.UserSettings
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class PaymentViewModel(private val cartRepo:CartRepository,private val repo: PaymentRepo):ViewModel() {

    private var draftOrder:DraftOrderResponse?=null


    fun getCartDraftOrder() {
        viewModelScope.launch {
            cartRepo.getCartProducts(UserSettings.cartDraftOrderId).collectLatest {
                if( it is DraftOrderAPIState.Success){
                 draftOrder = it.order
                }
            }
        }
    }
}