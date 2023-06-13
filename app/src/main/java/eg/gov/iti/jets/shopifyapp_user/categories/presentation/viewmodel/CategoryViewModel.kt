package eg.gov.iti.jets.shopifyapp_user.categories.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import eg.gov.iti.jets.shopifyapp_user.categories.data.model.CategoryState
import eg.gov.iti.jets.shopifyapp_user.categories.domain.repo.CategoryRepo
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class CategoryViewModel(private val categoryRepo: CategoryRepo) : ViewModel() {
    private var _categoryState: MutableStateFlow<CategoryState> =
        MutableStateFlow(CategoryState.Loading())
    var categoryState: StateFlow<CategoryState> = _categoryState

    fun getCategories() {
        viewModelScope.launch {
            try {
                categoryRepo.getAllCategories().collect {
                    _categoryState.value = CategoryState.Success(it.customCollections)
                }
            } catch (e: java.lang.Exception) {
                _categoryState.value = CategoryState.Error()
            }
        }
    }
}