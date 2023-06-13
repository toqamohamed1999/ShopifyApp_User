package eg.gov.iti.jets.shopifyapp_user.subcategory.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import eg.gov.iti.jets.shopifyapp_user.subcategory.domain.repo.SubCategoryRepo

class SubCategoryFactoryVM (private val repo: SubCategoryRepo) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {

        return if (modelClass.isAssignableFrom(SubCategoryViewModel::class.java)) {
            SubCategoryViewModel(repo) as T
        } else {
            throw java.lang.IllegalArgumentException("SubCategoryViewModel class not found")
        }
    }
}