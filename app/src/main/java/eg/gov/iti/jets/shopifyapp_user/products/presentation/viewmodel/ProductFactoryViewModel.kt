package eg.gov.iti.jets.shopifyapp_user.products.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import eg.gov.iti.jets.shopifyapp_user.products.domain.repo.ProductsBrandRepo

class ProductFactoryViewModel (private val repo: ProductsBrandRepo) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {

        return if (modelClass.isAssignableFrom(ProductsViewModel::class.java)) {
            ProductsViewModel(repo) as T
        } else {
            throw java.lang.IllegalArgumentException("ProductsViewModel class not found")
        }
    }
}