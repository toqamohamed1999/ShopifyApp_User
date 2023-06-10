package eg.gov.iti.jets.shopifyapp_user.cart.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import eg.gov.iti.jets.shopifyapp_user.cart.domain.repo.CartRepository

class CartViewModelFactory(private val repo:CartRepository) :ViewModelProvider.Factory{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return  if(modelClass .isAssignableFrom(CartViewModel::class.java)){
            CartViewModel(repo) as T
        }else{
            throw java.lang.IllegalArgumentException()
        }
    }
}