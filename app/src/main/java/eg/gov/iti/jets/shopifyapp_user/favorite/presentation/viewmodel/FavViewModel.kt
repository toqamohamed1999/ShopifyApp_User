package eg.gov.iti.jets.shopifyapp_user.favorite.presentation.viewmodel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import eg.gov.iti.jets.shopifyapp_user.base.model.FavRoomPojo
import eg.gov.iti.jets.shopifyapp_user.cart.data.model.LineItem
import eg.gov.iti.jets.shopifyapp_user.favorite.data.repo.FavLocalRepoImpl
import eg.gov.iti.jets.shopifyapp_user.favorite.domain.repo.FavLocalRepoInterface
import kotlinx.coroutines.launch

class FavViewModel(private val repo:FavLocalRepoInterface=FavLocalRepoImpl()) :ViewModel(){
    val favorites = repo.getAllFavProducts()

    var list: List<FavRoomPojo>? = null

    fun deleteFavProductWithId(productId: Long) {
        viewModelScope.launch {

            repo.deleteFavProductWithId(productId)
        }
    }
}