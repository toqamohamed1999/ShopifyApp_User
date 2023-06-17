package eg.gov.iti.jets.shopifyapp_user.payment.presentation.viewmodel

import androidx.lifecycle.ViewModel
import eg.gov.iti.jets.shopifyapp_user.cart.data.model.DraftOrderResponse
import eg.gov.iti.jets.shopifyapp_user.payment.domain.repo.PaymentRepo

class PaymentViewModel(private val repo: PaymentRepo):ViewModel() {

    private var draftOrder:DraftOrderResponse?=null
    fun setDraftOrder(order:DraftOrderResponse)
    {
        this.draftOrder = order
    }
}