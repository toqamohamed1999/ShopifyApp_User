package eg.gov.iti.jets.shopifyapp_user.base.local.database.favorite

import eg.gov.iti.jets.shopifyapp_user.base.model.FavRoomPojo
import eg.gov.iti.jets.shopifyapp_user.base.model.Product
import eg.gov.iti.jets.shopifyapp_user.cart.domain.model.LineItem
import kotlinx.coroutines.flow.Flow

interface FavLocalSourceInterface {
    suspend fun insertFavProduct(favRoomPojo: FavRoomPojo)
    fun getAllFavProducts(): Flow<List<FavRoomPojo>>
    fun getFavProductWithId(productId: Long): Flow<FavRoomPojo>
    suspend fun deleteFavProductWithId(productId: Long)
    fun deleteAllFavoriteProducts()
}