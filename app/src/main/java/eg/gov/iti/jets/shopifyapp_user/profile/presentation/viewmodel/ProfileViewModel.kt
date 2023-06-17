package eg.gov.iti.jets.shopifyapp_user.profile.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import eg.gov.iti.jets.shopifyapp_user.products.data.model.ProductBrandState
import eg.gov.iti.jets.shopifyapp_user.profile.data.model.OrderState
import eg.gov.iti.jets.shopifyapp_user.profile.domain.repo.ProfileRepo
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ProfileViewModel(private val profileRepo: ProfileRepo) : ViewModel() {

    private var _orderState: MutableStateFlow<OrderState> =
        MutableStateFlow(OrderState.Loading())
    var orderState: StateFlow<OrderState> = _orderState

    fun getOrders(id: Long) {
        viewModelScope.launch {
            try {
                profileRepo.getOrdersUser(id).collect {
                    _orderState.value = OrderState.Success(it.orders)
                }
            } catch (e: java.lang.Exception) {
                _orderState.value = OrderState.Error()
            }
        }
    }
}