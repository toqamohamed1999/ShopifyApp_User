package eg.gov.iti.jets.shopifyapp_user.favorite.domain.repo

import eg.gov.iti.jets.shopifyapp_user.cart.data.model.LineItem
import kotlinx.coroutines.flow.Flow

interface FavLocalRepoInterface {
    suspend fun deleteFavProductWithId(lineItemId: Long)
    fun getAllFavProducts(): Flow<List<LineItem>>
}