package eg.gov.iti.jets.shopifyapp_user.payment.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import eg.gov.iti.jets.shopifyapp_user.payment.domain.repo.PaymentRepo

class PaymentViewModelFactory(private val repo:PaymentRepo):ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return if(modelClass.isAssignableFrom(PaymentViewModel::class.java))
        {
            PaymentViewModel(repo) as T
        }else{
            throw IllegalArgumentException()
        }
    }
}