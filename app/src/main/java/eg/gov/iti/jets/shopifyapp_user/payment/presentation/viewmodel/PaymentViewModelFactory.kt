package eg.gov.iti.jets.shopifyapp_user.payment.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import eg.gov.iti.jets.shopifyapp_user.cart.domain.repo.CartRepository
import eg.gov.iti.jets.shopifyapp_user.home.domain.repo.AddsRepo
import eg.gov.iti.jets.shopifyapp_user.payment.domain.repo.PaymentRepo

class PaymentViewModelFactory(private val cartRepo: CartRepository, private val repo:PaymentRepo,private val addsRepo: AddsRepo):ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return if(modelClass.isAssignableFrom(PaymentViewModel::class.java))
        {
            PaymentViewModel(cartRepo,repo,addsRepo) as T
        }else{
            throw IllegalArgumentException()
        }
    }
}