package eg.gov.iti.jets.shopifyapp_user.categories.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import eg.gov.iti.jets.shopifyapp_user.categories.domain.repo.CategoryRepo

class CategoryFactoryViewModel(private val repo: CategoryRepo) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {

        return if (modelClass.isAssignableFrom(CategoryViewModel::class.java)) {
            CategoryViewModel(repo) as T
        } else {
            throw java.lang.IllegalArgumentException("ProductsViewModel class not found")
        }
    }
}