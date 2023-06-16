package eg.gov.iti.jets.shopifyapp_user.favorite.domain.repo

import eg.gov.iti.jets.shopifyapp_user.base.model.FavRoomPojo
import eg.gov.iti.jets.shopifyapp_user.cart.domain.model.LineItem
import kotlinx.coroutines.flow.Flow

interface FavLocalRepoInterface {
    suspend fun deleteFavProductWithId(productId: Long)
    fun getAllFavProducts(): Flow<List<FavRoomPojo>>
}